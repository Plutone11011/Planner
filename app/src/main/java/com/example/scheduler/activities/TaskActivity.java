package com.example.scheduler.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.scheduler.R;


public class TaskActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    private static final int MENU_BACK = 1 ;
    private Spinner spinnerPriority, spinnerClass ;


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

        //listener for back navigation button
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Mah",Toast.LENGTH_SHORT).show();
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

        //needs to observe viewmodel, because the user might want to modify an already created task

        //Toast.makeText(this,myToolbar.getTitle().toString(),Toast.LENGTH_SHORT).show();

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
        switch (item.getItemId()) {
            case R.id.action_ok:
                // User ok, save task
                //Toast.makeText(this,item.getTitle().toString(),Toast.LENGTH_SHORT).show();
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
        spinnerPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
