package com.example.scheduler.UI;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scheduler.Model.namePrioPOJO;
import com.example.scheduler.PriorityEnum;
import com.example.scheduler.R;
import com.example.scheduler.Viewmodels.HistogramViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabHistogram extends Fragment {

    private BarChart barChartView;
    private BarData barChartData ;
    private HistogramViewModel histogramViewModel; //needs update of priority



    public TabHistogram() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        histogramViewModel = ViewModelProviders.of(this).get(HistogramViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_histogram, container, false);

        barChartView = v.findViewById(R.id.barchart);
        barChartView.setEnabled(true);
        final int []prioCounter = new int[]{0,0,0};

        histogramViewModel.getListOfNamesandPriorities().observe(this, new Observer<List<namePrioPOJO>>() {
            @Override
            public void onChanged(List<namePrioPOJO> namePrioPOJOS) {
                //will show the count of tasks for each priority
                for (namePrioPOJO nameprio : namePrioPOJOS){
                    switch(nameprio.Priority){
                        case "Urgent":
                            prioCounter[PriorityEnum.Urgent.getValue()]++;
                        case "Important":
                            prioCounter[PriorityEnum.Important.getValue()]++;
                        case "Secondary":
                            prioCounter[PriorityEnum.Secondary.getValue()]++;
                    }
                    //creates dataset values for histogram
                    ArrayList<BarEntry> values = new ArrayList<>();
                    values.add(new BarEntry(0,prioCounter[PriorityEnum.Urgent.getValue()]));
                    values.add(new BarEntry(1,prioCounter[PriorityEnum.Important.getValue()]));
                    values.add(new BarEntry(2,prioCounter[PriorityEnum.Secondary.getValue()]));

                    BarDataSet barDataSet = new BarDataSet(values, "Dataset");
                    barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                    barChartData = new BarData();
                    barChartData.addDataSet(barDataSet);
                    //sets axis labels
                    XAxis mXAxis = barChartView.getXAxis();
                    mXAxis.setValueFormatter(new MyXAxisValueFormatter(new String[]{"Urgent","Important","Secondary"}));
                    mXAxis.setGranularity(1);
                    mXAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);

                    barChartView.setData(barChartData);
                    barChartView.invalidate();
                }
            }
        });






        return v ;
    }

    public class MyXAxisValueFormatter implements IAxisValueFormatter{

        private String[] labels;
        public MyXAxisValueFormatter(String[] labels) {
            this.labels = labels ;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return labels[(int) value];
        }
    }
}
