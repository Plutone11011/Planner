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
import android.widget.Toast;

import com.example.scheduler.Model.nameClassPOJO;
import com.example.scheduler.R;
import com.example.scheduler.Viewmodels.PieViewModel;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabPie extends Fragment {

    private PieChartView pieChartView ;
    private List<SliceValue> pieData ;
    private PieViewModel pieVM ;

    public TabPie() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pieVM = ViewModelProviders.of(this).get(PieViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_tab_pie, container, false);

        pieChartView = v.findViewById(R.id.chartpie);
        pieData  = new ArrayList<>();

        pieVM.getListOfNamesandTypes().observe(this, new Observer<List<nameClassPOJO>>() {
            @Override
            public void onChanged(List<nameClassPOJO> nameClassPOJOS) {

                Set<String> SetOfTaskClasses = new HashSet<>();
                AbstractMap<String, Integer> NumberOfTasksPerClass = new HashMap<>();
                float totalNumberOfTasks = (float)nameClassPOJOS.size() ;
                Random rnd = new Random();
                int color ;

                for (nameClassPOJO nameclass : nameClassPOJOS){
                    //current type is not mapped yet, need to add it to set and initialize counter
                    if (!SetOfTaskClasses.contains(nameclass.Type)){
                        SetOfTaskClasses.add(nameclass.Type);
                        NumberOfTasksPerClass.put(nameclass.Type,1);
                    }
                    else { //current type is already mapped, only need to increment counter
                        try {
                            NumberOfTasksPerClass.put(nameclass.Type,NumberOfTasksPerClass.get(nameclass.Type) + 1);
                        }
                        catch(NullPointerException n) {
                            n.printStackTrace();
                        }
                    }
                }

                //iterate through map <key, value> couples
                //adds float sizes of the pie chart with a randomly generated color
                //since types of tasks can be dynamically added by the user
                //so we can't use a predefined set of colors
                for (Map.Entry<String, Integer> entry : NumberOfTasksPerClass.entrySet()){
                    color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                    try{
                        pieData.add(new SliceValue((100 * entry.getValue()) / totalNumberOfTasks, color)
                                .setLabel(entry.getKey()));
                    }
                    catch(ArithmeticException a){
                        Toast.makeText(getContext(),"There are no tasks recorded", Toast.LENGTH_LONG).show();
                    }
                }

                PieChartData pieChartData = new PieChartData(pieData);
                pieChartData.setHasLabels(true);
                pieChartView.setPieChartData(pieChartData);
            }
        });

        return v ;
    }

}
