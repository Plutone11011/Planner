package com.example.scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.scheduler.MyDateFormat;
import com.example.scheduler.R;
import com.example.scheduler.Viewmodels.MainActivityViewModel;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private CompactCalendarView mCompactCalendarView;
    private ArrayList<Event> eventArrayList ;
    private MainActivityViewModel mainVM ;

    //add event to the calendar view
    private void addEvent(long ldate){
        Event registeredActivity = new Event(Color.BLACK,ldate);
        eventArrayList.add(registeredActivity);
        mCompactCalendarView.addEvent(registeredActivity);
        List<Event> events = mCompactCalendarView.getEvents(ldate);
        Log.d("TAG",eventArrayList.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toolbar myToolbar ;

        eventArrayList = new ArrayList<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCompactCalendarView = findViewById(R.id.compactcalendar_view);
        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);



        final MyDateFormat dateformat = new MyDateFormat(getString(R.string.dateformat)
                + " " + getString(R.string.timeformat));
        mainVM = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        mainVM.nukeTable();

        mainVM.getDates().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                boolean eventIsAlreadyPresent  ;
                Log.d("STR",strings.toString());
                //Toast.makeText(getApplicationContext(),"Ah stronzo",Toast.LENGTH_SHORT);
                for (String date : strings){
                    Date currentDate ;
                    try{
                         currentDate = dateformat.StringtoDate(date);
                    }
                    catch(ParseException p){
                        currentDate = new Date();
                        p.printStackTrace();
                    }
                    eventIsAlreadyPresent = false ;
                    for (Event e : eventArrayList){
                        if (e.getTimeInMillis() == currentDate.getTime()){
                            eventIsAlreadyPresent = true ;
                        }
                    }
                    if (!eventIsAlreadyPresent){
                        addEvent(currentDate.getTime());
                    }
                }
            }
        });
        /*mainVM.getDates().observe(this, new Observer<List<Date>>() {
            @Override
            public void onChanged(List<Date> dates) {
                //dates = new ArrayList<>(dates);
                boolean eventIsAlreadyPresent ;
                for (Date newDates : dates){
                    eventIsAlreadyPresent = false ;
                    for (Event registeredEvents : eventArrayList){
                        if (registeredEvents.getTimeInMillis() == newDates.getTime()){
                            //there is already an event with that date
                            eventIsAlreadyPresent = true ;
                        }
                    }
                    if (!eventIsAlreadyPresent){
                        //if not,then need to create it
                        addEvent(newDates.getTime());
                    }
                }

            }
        });*/
        //sets the toolbar title to current month
        //mCompactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        getSupportActionBar().setTitle(new SimpleDateFormat("MMM - yyyy", Locale.getDefault())
                .format(mCompactCalendarView.getFirstDayOfCurrentMonth()));
        //set title on calendar scroll



        mCompactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                getSupportActionBar().setTitle(new SimpleDateFormat("MMM - yyyy", Locale.getDefault())
                        .format(firstDayOfNewMonth));
            }
        });



        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TaskActivity.class));
            }
        });

    }

    //shows menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return true;
    }

    //callback to react to selection of menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_statistics:
                // User chose the statistics action, show usage graphs
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    //after task activity main will always call onResume, not sure about other lifecycle methods
    @Override
    protected void onResume() {
        //needs to show new task on the calendar
        super.onResume();
        Intent intent = getIntent();

        Long now = new Date().getTime();

        mCompactCalendarView = findViewById(R.id.compactcalendar_view);
        Long ldate = intent.getLongExtra("date",now);

        if (ldate.longValue() != now.longValue()){
            addEvent(ldate);
        }
        //Toast.makeText(this,ldate.toString(),Toast.LENGTH_LONG).show();



        List<Event> events = mCompactCalendarView.getEvents(ldate);
        //Log.d("TAG",events.toString());

        //mCompactCalendarView.removeAllEvents();
    }
}
