package com.example.scheduler.UI;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.scheduler.Model.namePrioPOJO;
import com.example.scheduler.R;
import com.example.scheduler.Viewmodels.TabDateViewModel;
import com.example.scheduler.Viewmodels.TabPriorityViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabPriority extends Fragment{


    private ListView listView ;
    private ArrayList<String> items ;
    private ArrayAdapter<String> arrayAdapter;
    private TabPriorityViewModel tabPrioVM ;

    public TabPriority() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tabPrioVM = ViewModelProviders.of(this).get(TabPriorityViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab_priority, container, false );
        items = new ArrayList<>();
        listView = v.findViewById(R.id.listview_priority);

        tabPrioVM.getListOfNamesandPriorities().observe(this, new Observer<List<namePrioPOJO>>() {
            @Override
            public void onChanged(List<namePrioPOJO> namePrioPOJOS) {

                for (namePrioPOJO nameprio : namePrioPOJOS){
                    items.add(nameprio.Name + "  " + nameprio.Priority);
                }

                arrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,items);
                listView.setAdapter(arrayAdapter);
            }
        });

        return v ;


    }

}
