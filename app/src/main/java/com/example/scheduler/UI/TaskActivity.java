package com.example.scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.scheduler.Model.TasksTable;
import com.example.scheduler.R;
import com.example.scheduler.Viewmodels.TaskActivityViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class TaskActivity extends AppCompatActivity {

    private static final Integer textviewErr = 4 ;
    private Spinner spinnerPriority, spinnerClass ;
    private TaskActivityViewModel taskVM ;
    private TextInputEditText inputTimeText, inputDateText, inputNameText ;



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

        taskVM = ViewModelProviders.of(this).get(TaskActivityViewModel.class);

        //observe livedata changes

        //listener for back navigation button
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Mah",Toast.LENGTH_SHORT).show();
                //maybe cache content?
                //return to calendar activity
            }
        });
        //buttons
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

        //spinners

        //create array adapter using string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterPriority = ArrayAdapter.createFromResource(this,
                R.array.priority_array, R.layout.support_simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapterClass = ArrayAdapter.createFromResource(this,
                R.array.class_array, R.layout.support_simple_spinner_dropdown_item);

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
                taskVM.setState("Pending");
                taskVM.setPriority(spinnerPriority.getSelectedItem().toString());
                taskVM.setType(spinnerClass.getSelectedItem().toString());
                taskVM.insert(new TasksTable(taskVM.getName(),taskVM.getTask_date(),taskVM.getState(),
                        taskVM.getType(), taskVM.getPriority()));
                startActivity(new Intent(TaskActivity.this,MainActivity.class));
                return true ;
            }
            else{
                //the user didn't insert a one of the required fields
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

        if (intent.getCharSequenceExtra("name") != null){
            inputNameText.getText().append(intent.getCharSequenceExtra("name"));
        }


    }
}
