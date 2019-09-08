package com.example.scheduler.UI;


import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scheduler.Model.DateStatePOJO;
import com.example.scheduler.R;
import com.example.scheduler.Viewmodels.TabLineChartViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * A simple {@link Fragment} subclass.
 */
//shows the number of completed task in a year, in different months
public class TabLineChart extends Fragment {

    private String[] axisData ; //the 3 initials of months
    private Integer[] yAxisData ; //number of
    private LineChartView lineChartView ;
    private TabLineChartViewModel tabLineChartVM ;
    private SimpleDateFormat dateformat ;
    private LineChartData data;

    public TabLineChart() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dateformat = new SimpleDateFormat(getString(R.string.dateformat)
                + " " + getString(R.string.timeformat));
        tabLineChartVM = ViewModelProviders.of(this).get(TabLineChartViewModel.class);
        axisData =  new String[]{"Jan", "Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        yAxisData = new Integer[axisData.length];
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_line_chart, container, false);
        lineChartView = v.findViewById(R.id.chartLine);

        tabLineChartVM.getmDateStatePojoList().observe(this, new Observer<List<DateStatePOJO>>() {
            @Override
            public void onChanged(List<DateStatePOJO> dateStatePOJOS) {
                Calendar calendar = Calendar.getInstance();
                Calendar myCalendar = Calendar.getInstance();

                List<AxisValue> axisValues = new ArrayList<>(); //will hold data for x and y axis
                List<PointValue> yAxisValues = new ArrayList<>();



                yAxisData = new Integer[axisData.length]; //must have points corresponding to months, x data
                for (DateStatePOJO datestate: dateStatePOJOS){
                    if (datestate.State.equals("Completed")){
                        //here the control that the task state is actually "complete"
                        //parse every date string, discard those that are not of this current year
                        try{
                            Date d = dateformat.parse(datestate.Date);
                            myCalendar.setTime(d);
                        }
                        catch(ParseException p){
                            p.printStackTrace();
                        }
                        if (calendar.get(Calendar.YEAR) == myCalendar.get(Calendar.YEAR)){
                            //if the task is recorded for the current year
                            //then we increment the y axis data corresponding to the date month
                            if (yAxisData[myCalendar.get(Calendar.MONTH)] == null){
                                yAxisData[myCalendar.get(Calendar.MONTH)] = 1 ;
                            }
                            else {
                                yAxisData[myCalendar.get(Calendar.MONTH)]++;
                            }
                        }
                    }

                }
                //adds x data to the list of x values. The months are the labels
                for(int i = 0; i < axisData.length; i++){
                    axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
                }
                for (int i = 0; i < yAxisData.length; i++){
                    if (yAxisData[i] == null){
                        //there wasn't a single task this month
                        yAxisData[i] = 0 ;
                    }
                    yAxisValues.add(new PointValue(i, yAxisData[i]));
                }
                Line line = new Line(yAxisValues);//the line holding y values which will appear inside the chart
                //add the line holding y axis values to the list of lines of the chart
                List<Line> lines = new ArrayList<>();
                lines.add(line);

                data = new LineChartData();
                data.setLines(lines);

                Axis axis = new Axis();//sets the axis values and make the visible
                axis.setValues(axisValues);
                axis.setTextColor(Color.parseColor("#03A9F4"));
                data.setAxisXBottom(axis);


                //same with y axis values
                try {
                    Axis yAxis = new Axis();
                    data.setAxisYLeft(yAxis);
                    yAxis.setTextColor(Color.parseColor("#03A9F4"));
                }
                catch(ArithmeticException a){
                    a.printStackTrace();
                }


                lineChartView.setLineChartData(data);

            }
        });



        return v ;
    }

}
