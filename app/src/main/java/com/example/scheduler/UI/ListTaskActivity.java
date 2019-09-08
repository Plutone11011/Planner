package com.example.scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.example.scheduler.Adapters.SectionsPagerAdapter;
import com.example.scheduler.R;
import com.google.android.material.tabs.TabLayout;

public class ListTaskActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter ;
    private ViewPager mViewPager ;

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

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.pager); //ViewPager allows scrollable behaviour
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        //links TabLayout with ViewPager
        tabLayout.setupWithViewPager(mViewPager);

        //back navigation
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListTaskActivity.this,MainActivity.class));
            }
        });
    }

}
