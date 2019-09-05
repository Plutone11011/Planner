package com.example.scheduler.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.scheduler.AlarmReceiver;
import com.example.scheduler.Model.TasksTable;
import com.example.scheduler.R;
import com.example.scheduler.Viewmodels.TaskActivityViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TaskActivity extends AppCompatActivity {

    private static final Integer textviewErr = 4 ;
    private Spinner spinnerPriority, spinnerClass ;
    private TaskActivityViewModel taskVM ;
    private TextInputEditText inputTimeText, inputDateText, inputNameText ;
    private ArrayAdapter<CharSequence> adapterPriority  ;
    private ArrayAdapter<String> adapterClass ;
    private SharedPreferences sharedPrefClassOptions, sharedPrefPendingIntentID ; //shared preference for this activity only, contain the array of task types


    //auxiliary method to start dialog fragments for date and time
    private void startDialogFragment(TextInputEditText mTextInputEditText, DialogFragment dialogFragment, String tag, String key){
        //sends editable object as bundle argument for the dialog to update the form automatically
        Bundle args = new Bundle();
        args.putCharSequence(key,mTextInputEditText.getText());


        dialogFragment.setArguments(args);
        dialogFragment.show(getSupportFragmentManager(), tag);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button setTime, setDate ;
        Toolbar myToolbar;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);


        myToolbar = findViewById(R.id.toolbar_task);
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(myToolbar);

        //do not want title showing in this activity
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        myToolbar.setTitle("");
        myToolbar.setSubtitle("");

        inputDateText = findViewById(R.id.task_date);
        inputNameText = findViewById(R.id.task_name);
        inputTimeText = findViewById(R.id.task_time);
        spinnerPriority = findViewById(R.id.priority_spinner);
        spinnerClass = findViewById(R.id.class_spinner);

        sharedPrefPendingIntentID = getSharedPreferences(null,Context.MODE_PRIVATE);

        sharedPrefClassOptions = TaskActivity.this.getPreferences(Context.MODE_PRIVATE);

        //editor.clear();

        if (sharedPrefClassOptions.getStringSet(getString(R.string.class_task_sharedpref),null) == null){
            //first time, they're being created


            SharedPreferences.Editor editor = sharedPrefClassOptions.edit();
            String []init = new String[] {"Homework","Training","Family"};
            Set<String> typesOfTasks = new HashSet<>(Arrays.asList(init));
            editor.putStringSet(getString(R.string.class_task_sharedpref),typesOfTasks);
            editor.apply();

        }

        taskVM = ViewModelProviders.of(this).get(TaskActivityViewModel.class);
        taskVM.setIntentForUpdate(false); //defaults to false, only true when onresume because of intent from fragment
        //observe livedata changes

        //listener for back navigation button
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TaskActivity.this,MainActivity.class));
            }
        });

        /* Listener for button to set date and time*/
        setDate = findViewById(R.id.buttonForDate);
        setTime = findViewById(R.id.buttonForTime);
        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputTimeText = findViewById(R.id.task_time);
                DialogFragment newFragment = new TimePickerFragment();

                startDialogFragment(inputTimeText,newFragment,"time dialog",getString(R.string.inputTimekey));


            }
        });
        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputDateText = findViewById(R.id.task_date);
                DialogFragment newFragment = new SelectDateFragment();

                startDialogFragment(inputDateText,newFragment,"date dialog",getString(R.string.inputDatekey));
            }
        });

        /*Button listener for adding new task classes*/
        Button button = findViewById(R.id.new_type_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(TaskActivity.this);
                alert.setTitle("Task type");
                alert.setMessage("Insert a new type of task :");

                // Set an EditText view to get user input
                final EditText input = new EditText(TaskActivity.this);
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString();

                        adapterClass.add(value);
                        SharedPreferences.Editor editor = sharedPrefClassOptions.edit();
                        //creates a new instance because as per documentation
                        //modifying the returned set instance does not guarantee consistency
                        Set<String> s = new HashSet<>(sharedPrefClassOptions.getStringSet(getString(R.string.class_task_sharedpref),new HashSet<String>()));

                        s.add(value);

                        editor.putStringSet(getString(R.string.class_task_sharedpref),s);
                        editor.apply();


                        Log.d("", "Pin Value : " + value);
                        return;
                    }
                });

                alert.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                return;
                            }
                        });
                alert.show();
            }
        });
        /*spinners, adapter array*/

        //create array adapter using string array and a default spinner layout
        adapterPriority = ArrayAdapter.createFromResource(this,
                R.array.priority_array, R.layout.support_simple_spinner_dropdown_item);

        //create array adapter using shared preference set
        //since spinner options are editable by the user
        ArrayList<String> spinnerClassArray = new ArrayList<>();

        for (String type : sharedPrefClassOptions.getStringSet(getString(R.string.class_task_sharedpref), new HashSet<String>())){
            spinnerClassArray.add(type);
        }

        adapterClass = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, spinnerClassArray);
        //adapterClass = ArrayAdapter.createFromResource(this,
        //        R.array.class_array, R.layout.support_simple_spinner_dropdown_item);

        adapterPriority.setDropDownViewResource( R.layout.support_simple_spinner_dropdown_item);
        adapterClass.setDropDownViewResource( R.layout.support_simple_spinner_dropdown_item);

        spinnerPriority.setAdapter(adapterPriority);
        spinnerClass.setAdapter(adapterClass);


    }
    //shows menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.taskmenu, menu);

        return true;
    }


    private void setAlarmForNotifications(){
        //sets alarm manager for this new task
        PendingIntent pendingIntent ;
        Calendar calendar = Calendar.getInstance();
        try{
            calendar.setTime(new SimpleDateFormat(getString(R.string.dateformat)
                    + " " + getString(R.string.timeformat)).parse(taskVM.getTask_date()));
        }
        catch (ParseException p){
            p.printStackTrace();
        }
        Intent intent = new Intent(TaskActivity.this, AlarmReceiver.class);
        intent.putExtra("name",taskVM.getName());
        intent.putExtra("id", taskVM.getId());
        intent.putExtra("date",taskVM.getTask_date());
        intent.putExtra("priority",taskVM.getPriority());
        intent.putExtra("type",taskVM.getType());
        intent.putExtra("state",taskVM.getState());
        //lets broadcast receiver execute code with application permission
        //even when the app is not active

        SharedPreferences.Editor editor = sharedPrefPendingIntentID.edit();
        Set<String> PendingIntentIDs = sharedPrefPendingIntentID.getStringSet(getString(R.string.pending_intent_sharedpref),null);

        if (PendingIntentIDs != null){
            Log.d("PENDING",PendingIntentIDs.toString());
        }

        if (PendingIntentIDs == null){
            //no ID created yet

            PendingIntentIDs = new HashSet<>();
            PendingIntentIDs.add("0");

            editor.putStringSet(getString(R.string.pending_intent_sharedpref),PendingIntentIDs);
            editor.apply();
            intent.putExtra(getString(R.string.id_for_alarmmanager),0);
            pendingIntent = PendingIntent.getBroadcast(TaskActivity.this,0,intent,0);
        }
        else {
            //the ID for the new Pending Intent must either be
            //the predecessor of the minimum in the set, if it exists
            //or the successor of the maximum in the set. That is, the set tends to reuse ids of
            //already used pending intent, in order to not overflow (doesn't use negative integers)
            /*String []arrayIDs = PendingIntentIDs.toArray(new String[PendingIntentIDs.size()]);
            Integer minID = Integer.parseInt(arrayIDs[0])  ;
            Integer maxID = minID ;
            for (int i = 1 ; i < arrayIDs.length; i++){
                if (Integer.parseInt(arrayIDs[i]) < minID){
                    minID = Integer.parseInt(arrayIDs[i]);
                }
                if (Integer.parseInt(arrayIDs[i]) > maxID){
                    maxID = Integer.parseInt(arrayIDs[i]) ;
                }
            }
            if (minID == 0){
                PendingIntentIDs.add((++maxID).toString());
                intent.putExtra(getString(R.string.id_for_alarmmanager),minID);
                pendingIntent = PendingIntent.getBroadcast(TaskActivity.this,maxID,intent,0);

            }
            else {
                PendingIntentIDs.add((--minID).toString());
                intent.putExtra(getString(R.string.id_for_alarmmanager),maxID);
                pendingIntent = PendingIntent.getBroadcast(TaskActivity.this,minID,intent,0);
            }*/
            //adds everytime a new pending id to the shared pref, specifically the successor
            String []arrayIDs = PendingIntentIDs.toArray(new String[PendingIntentIDs.size()]);
            Integer currentMax = Integer.parseInt(arrayIDs[PendingIntentIDs.size() - 1]);
            PendingIntentIDs.add((++currentMax).toString());
            editor.putStringSet(getString(R.string.pending_intent_sharedpref),PendingIntentIDs);
            editor.apply();
            intent.putExtra(getString(R.string.id_for_alarmmanager),currentMax);
            pendingIntent = PendingIntent.getBroadcast(TaskActivity.this,currentMax,intent,0);

        }
        Log.d("PENDING",PendingIntentIDs.toString());

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis(),pendingIntent),pendingIntent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_ok) {
            // User ok, save task and return to main activity
            //Toast.makeText(this,item.getTitle().toString(),Toast.LENGTH_SHORT).show();

            //if all 3 of the required fields are filled
            //can set the view model and the db, and return to the main calendar activity
            if(!TextUtils.isEmpty(inputNameText.getText()) && !TextUtils.isEmpty(inputDateText.getText()) && !TextUtils.isEmpty(inputTimeText.getText())){

                taskVM.setTask_date(inputDateText.getText().toString() + " " + inputTimeText.getText().toString());
                taskVM.setName(inputNameText.getText().toString());
                if (!taskVM.getIntentForUpdate()){
                    taskVM.setState("Pending");
                }
                taskVM.setPriority(spinnerPriority.getSelectedItem().toString());
                taskVM.setType(spinnerClass.getSelectedItem().toString());
                TasksTable t = new TasksTable(taskVM.getName(),taskVM.getTask_date(),taskVM.getState(),
                        taskVM.getType(), taskVM.getPriority());

                setAlarmForNotifications();

                if (taskVM.getIntentForUpdate()){
                    t.setId(taskVM.getId()); //sets primary key so the system knows which row to update
                    taskVM.update(t);
                }
                else {
                    //there is no need to set primary key at first creation since it's autogenerated
                    taskVM.insert(t);
                }
                taskVM.setIntentForUpdate(false);
                startActivity(new Intent(TaskActivity.this,MainActivity.class));
                return true ;
            }
            else{
                //the user didn't insert one of the required fields
                LinearLayout linearLayout = findViewById(R.id.task_layout);


                TextView errorMessage = new TextView(getApplicationContext());
                errorMessage.setText("Not a valid date, time, or name");
                errorMessage.setId(textviewErr);
                errorMessage.setTextColor(getColor(R.color.floatingActionButtonColor));
                errorMessage.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                linearLayout.addView(errorMessage);
                return false ;
            }
        }
        else {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //probably observing changes in live data
    }


    @Override
    protected void onResume() {

        super.onResume();

        Intent intent = getIntent();
        String fullDate ;

        //retrieves intent extra data and fills the input fields
        if (intent.getCharSequenceExtra("name") != null && intent.getCharSequenceExtra("date") != null){
            inputNameText.getText().append(intent.getCharSequenceExtra("name"));
            spinnerPriority.setSelection(adapterPriority.getPosition(intent.getCharSequenceExtra("priority")));
            spinnerClass.setSelection(adapterClass.getPosition((String)intent.getCharSequenceExtra("type")));

            fullDate = intent.getCharSequenceExtra("date").toString();

            Pattern pattern = Pattern.compile(" +");//1 or more repetitions of space
            Matcher matcher = pattern.matcher(fullDate);
            if (matcher.find()){
                inputDateText.getText().append(fullDate.substring(0, matcher.start()));
                inputTimeText.getText().append(fullDate.substring(matcher.end()));
            }
            taskVM.setIntentForUpdate(true);
            taskVM.setId(intent.getIntExtra("id",-1));
            //sets state because it could have been changed by notifications
            //while the default state for creation of a new task is pending
            taskVM.setState(intent.getCharSequenceExtra("state").toString());
        }


    }
}
