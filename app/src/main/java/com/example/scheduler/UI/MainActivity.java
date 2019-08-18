package com.example.scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.scheduler.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private CompactCalendarView mCompactCalendarView;
    private static Integer tmp = 0 ;
    private static ArrayList<Event> eventArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toolbar myToolbar ;
        final DateFormat dateformat ;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCompactCalendarView = findViewById(R.id.compactcalendar_view);
        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        //sets the toolbar title to current month
        //mCompactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        dateformat = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
        getSupportActionBar().setTitle(dateformat.format(mCompactCalendarView.getFirstDayOfCurrentMonth()));
        //set title on calendar scroll



        mCompactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                getSupportActionBar().setTitle(dateformat.format(firstDayOfNewMonth));
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
        tmp++;



        mCompactCalendarView = findViewById(R.id.compactcalendar_view);
        Long ldate = intent.getLongExtra("date",new Date().getTime());

        //Toast.makeText(this,ldate.toString(),Toast.LENGTH_LONG).show();
        Event registeredActivity = new Event(Color.BLACK,ldate,tmp.toString());
        eventArrayList.add(registeredActivity);
        mCompactCalendarView.addEvents(eventArrayList);
        //mCompactCalendarView.addEvent(registeredActivity);

        List<Event> events = mCompactCalendarView.getEvents(ldate);
        Log.d("TAG",events.toString());
    }
}
