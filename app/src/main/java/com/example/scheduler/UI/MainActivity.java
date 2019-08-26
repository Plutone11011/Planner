package com.example.scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LiveData;
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
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.scheduler.Model.datetimePOJO;
import com.example.scheduler.R;
import com.example.scheduler.Repository.TasksRepo;
import com.example.scheduler.Viewmodels.MainActivityViewModel;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    private CompactCalendarView mCompactCalendarView;
    private ArrayList<Event> eventArrayList ;
    private MainActivityViewModel mainVM ;

    //add event to the calendar view
    private void addEvent(long ldate, String name){
        Event registeredActivity = new Event(Color.BLACK,ldate,name);
        eventArrayList.add(registeredActivity);
        mCompactCalendarView.addEvent(registeredActivity);
        List<Event> events = mCompactCalendarView.getEvents(ldate);
        Log.d("TAG",eventArrayList.toString());
    }

    private void startDialogFragment(ArrayList<String> listOfCurrentTasks, DialogFragment dialogFragment, String tag, Boolean isDelete){
        Bundle args = new Bundle();

        args.putStringArrayList(getString(R.string.name_list_dialog), listOfCurrentTasks);
        args.putBoolean(getString(R.string.isDelete),isDelete);

        dialogFragment.setArguments(args);
        dialogFragment.show(getSupportFragmentManager(), tag);
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



        final SimpleDateFormat dateformat = new SimpleDateFormat(getString(R.string.dateformat)
                + " " + getString(R.string.timeformat));
        mainVM = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        //mainVM.nukeTable();
        mainVM.getDates().observe(this, new Observer<List<datetimePOJO>>() {
            @Override
            public void onChanged(List<datetimePOJO> datetimePOJOS) {
                boolean eventIsAlreadyPresent  ;
                //Log.d("STR",datetimePOJOS.toString());

                for (datetimePOJO datetime : datetimePOJOS){
                    Date currentDate ;
                    try{
                        currentDate = dateformat.parse(datetime.Date);
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
                        addEvent(currentDate.getTime(),datetime.Name);
                    }
                }
            }
        });


        //sets the toolbar title to current month
        //mCompactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        getSupportActionBar().setTitle(new SimpleDateFormat("MMM - yyyy", Locale.getDefault())
                .format(mCompactCalendarView.getFirstDayOfCurrentMonth()));
        //set title on calendar scroll


        //calendar listener

        mCompactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {


                //Log.d("CLICK", dateClicked.toString());
                Boolean isDayOfEvent = false ;
                Calendar cal = Calendar.getInstance();

                //if date clicked is part of registered events
                for (Event ev : eventArrayList){
                    Date dateEv = new Date(ev.getTimeInMillis());
                    cal.setTime(dateEv);
                    //for some reason compact calendar view doesn't store hh:mm:ss
                    //so it defaults to 00:00:00
                    cal.set(Calendar.HOUR_OF_DAY,0);
                    cal.set(Calendar.MINUTE,0);
                    cal.set(Calendar.SECOND,0);
                    if (dateClicked.getTime() == cal.getTime().getTime()){
                        isDayOfEvent = true ;
                        break ;
                    }
                }

                if (isDayOfEvent){
                    //popup menu to delete and edit task
                    PopupMenu popup = new PopupMenu(MainActivity.this, mCompactCalendarView);
                    popup.inflate(R.menu.popup_menu);


                    final List<String> listOfCurrentTasks = new ArrayList<>();
                    List<Event> list = mCompactCalendarView.getEvents(dateClicked);
                    for (Event ev : list){
                        listOfCurrentTasks.add((String) ev.getData());
                    }

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {

                            DialogFragment dialogFragment = new SelectTaskFragment();
                            dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE,0);

                            switch(item.getItemId()){
                                case R.id.popup_delete:
                                    //need to ask which one I guess?
                                    startDialogFragment((ArrayList<String>)listOfCurrentTasks,dialogFragment,"delete",true);

                                    break ;
                                case R.id.popup_edit:
                                    startDialogFragment((ArrayList<String>)listOfCurrentTasks,dialogFragment,"edit",false);

                                    break ;

                                default:
                                    return false ;

                            }
                            return true;
                        }
                    });
                    popup.show();
                }

                //}

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
    /*
    @Override
    protected void onResume() {
        //needs to show new task on the calendar
        super.onResume();
        Intent intent = getIntent();

        Long now = new Date().getTime();

        mCompactCalendarView = findViewById(R.id.compactcalendar_view);
        Long ldate = intent.getLongExtra("date",now);
        String name = intent.getStringExtra("name");

        if (ldate.longValue() != now.longValue()){
            addEvent(ldate, name);
        }
        //Toast.makeText(this,ldate.toString(),Toast.LENGTH_LONG).show();



        List<Event> events = mCompactCalendarView.getEvents(ldate);
        //Log.d("TAG",events.toString());

        //mCompactCalendarView.removeAllEvents();
    }*/
}
