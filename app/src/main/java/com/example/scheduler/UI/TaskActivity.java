package com.example.scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.scheduler.R;
import com.example.scheduler.viewmodels.TaskActivityViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class TaskActivity extends AppCompatActivity {

    private static final Integer textviewErr = 4 ;
    private Spinner spinnerPriority, spinnerClass ;
    private TaskActivityViewModel taskVM ;

    //returns true if the form has been completed in at least
    //the required fields
    private boolean isTaskFormCompleted(TextInputEditText inputDateText, TextInputEditText inputNameText, TextInputEditText inputTimeText){
        if(!TextUtils.isEmpty(inputNameText.getText()) && !TextUtils.isEmpty(inputDateText.getText()) && !TextUtils.isEmpty(inputTimeText.getText())){

            try{
                Date date = new SimpleDateFormat(getString(R.string.dateformat), Locale.US).parse(inputDateText.getText().toString());
                taskVM.setTask_date(date);
                //e altri set?
            }
            catch(ParseException | NullPointerException | IllegalArgumentException ex){
                //maybe toast
            }

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

    //auxiliary method to start dialog fragments for date and time
    private void startDialogFragment(TextInputEditText mTextInputEditText, DialogFragment dialogFragment, String tag, String key){
        Bundle args = new Bundle();
        args.putCharSequence(key,mTextInputEditText.getText());

        dialogFragment.setArguments(args);
        dialogFragment.show(getSupportFragmentManager(), tag);
    }
    /*
    private void initWidgetListeners(){



        dateInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                valueChanged = true ;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                //syntactic check, date format validity dd/MM/yyyy
                //start + count indicates the current character changing

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!valueChanged){

                }



            }
        });
    }

        spinnerPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

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

        taskVM = ViewModelProviders.of(this).get(TaskActivityViewModel.class);
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
                TextInputEditText inputTimeText = findViewById(R.id.task_time);
                DialogFragment newFragment = new TimePickerFragment();

                startDialogFragment(inputTimeText,newFragment,"time dialog",getString(R.string.inputTimekey));


            }
        });
        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText inputDateText = findViewById(R.id.task_date);
                DialogFragment newFragment = new SelectDateFragment();

                startDialogFragment(inputDateText,newFragment,"date dialog",getString(R.string.inputDatekey));
            }
        });

        //spinners
        spinnerPriority = findViewById(R.id.priority_spinner);
        spinnerClass = findViewById(R.id.class_spinner);
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
            TextInputEditText inputDateText = findViewById(R.id.task_date);
            TextInputEditText inputNameText = findViewById(R.id.task_name);
            TextInputEditText inputTimeText = findViewById(R.id.task_time);

            if (isTaskFormCompleted(inputDateText,inputNameText, inputTimeText)) {

                Intent returnToCalendar = new Intent(this, MainActivity.class);
                returnToCalendar.putExtra("date", taskVM.getTask_date().getTime());
                //probably save other things here, viewmodel
                startActivity(returnToCalendar);
                //need to make an Intent passing the date as a parameter

                //Event registeredActivity = new Event(Color.BLACK,);
                //mCompacCalendarView.addEvent(registeredActivity);

            }
            return true;
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




}
