package com.example.scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.scheduler.Model.TasksTable;
import com.example.scheduler.R;
import com.example.scheduler.Viewmodels.ListTaskViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListTaskActivity extends AppCompatActivity {

    private ArrayList<String> items ;
    private ArrayAdapter<String> arrayAdapter;
    private ListView listView ;
    private ListTaskViewModel listTaskVM ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_task);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(myToolbar);

        //do not want title showing in this activity
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        myToolbar.setTitle("Every task");
        myToolbar.setSubtitle("");

        items = new ArrayList<>();
        listView = findViewById(R.id.listviewSearch);
        final TextView textView = findViewById(R.id.emptyViewSearch);//textview that also contains "No results" in case search goes wrong

        listTaskVM = ViewModelProviders.of(this).get(ListTaskViewModel.class);

        listTaskVM.getRows().observe(this, new Observer<List<TasksTable>>() {
            @Override
            public void onChanged(List<TasksTable> tasksTables) {
                for (TasksTable rows : tasksTables){
                    items.add(rows.getName() + " " + rows.getDate() + " " + rows.getPriority() + " " + rows.getState() + " " + rows.getType());
                }
                arrayAdapter = new ArrayAdapter<String>(ListTaskActivity.this, android.R.layout.simple_list_item_1,items);
                listView.setAdapter(arrayAdapter);
                listView.setEmptyView(textView);
            }
        });
        //back navigation
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListTaskActivity.this,MainActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);

        MenuItem search = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) search.getActionView(); //actual searchview
        searchView.setQueryHint("Search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
