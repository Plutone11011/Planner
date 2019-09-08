package com.example.scheduler.UI;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.scheduler.Adapters.ChartPagerAdapter;
import com.example.scheduler.R;
import com.google.android.material.tabs.TabLayout;

public class UsageGraphActivity extends AppCompatActivity {

    private ChartPagerAdapter mChartPagerAdapter ;
    private ViewPager mViewPager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usage_graph);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(myToolbar);

        //do not want title showing in this activity
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        myToolbar.setTitle("Charts");
        myToolbar.setSubtitle("");

        //back navigation
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UsageGraphActivity.this,MainActivity.class));
            }
        });

        mChartPagerAdapter = new ChartPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.pagerChart); //ViewPager allows scrollable behaviour
        mViewPager.setAdapter(mChartPagerAdapter);

        final TabLayout tabLayout = findViewById(R.id.tablayoutChart);
        //links TabLayout with ViewPager
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_pie_chart_black_24dp);
        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_assessment_black_24dp);
        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_show_chart_black_24dp);
        tabLayout.getTabAt(2).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

    }
}
