package com.example.scheduler.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.scheduler.R;
import com.example.scheduler.viewmodels.TaskActivityViewModel;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class TaskActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    private static final Integer textviewErr = 4 ;
    private Spinner spinnerPriority, spinnerClass ;
    private TaskActivityViewModel taskVM ;

    //returns true if the inserted date is in a proper format
    //otherwise returns false and prints an error message
    private boolean isInputDateCorrect(String value){
        try{
            Date date = new SimpleDateFormat(getString(R.string.dateformat), Locale.US).parse(value);
            taskVM.setTask_date(date);
            return true ;
        }
        catch(NullPointerException | IllegalArgumentException | ParseException ex){
            //the user didn't insert a good formatted date
            LinearLayout linearLayout = findViewById(R.id.task_layout);

            TextView errorMessage = new TextView(getApplicationContext());
            errorMessage.setText("Not a valid date");
            errorMessage.setId(textviewErr);
            errorMessage.setTextColor(getColor(R.color.floatingActionButtonColor));
            errorMessage.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            linearLayout.addView(errorMessage);
            return false ;
        }
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);


        Toolbar myToolbar = findViewById(R.id.toolbar_task);
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

            if (isInputDateCorrect(inputDateText.getText().toString())) {
                if (TextUtils.isEmpty(inputNameText.getText())) {
                    //need to notify user that a required field is empty
                } else {
                    Intent returnToCalendar = new Intent(this, MainActivity.class);
                    returnToCalendar.putExtra("date", taskVM.getTask_date().getTime());
                    //probably save other things here, viewmodel
                    startActivity(returnToCalendar);
                    //need to make an Intent passing the date as a parameter

                    //Event registeredActivity = new Event(Color.BLACK,);
                    //mCompacCalendarView.addEvent(registeredActivity);
                }
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
