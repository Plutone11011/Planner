package com.example.scheduler.UI;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.scheduler.Model.datetimePOJO;
import com.example.scheduler.R;
import com.example.scheduler.Viewmodels.TabDateViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabDate extends Fragment{

    private ListView listView ;
    private ArrayList<String> items ;
    private ArrayAdapter<String> arrayAdapter;
    private TabDateViewModel tabDateVM ;

    public TabDate() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tabDateVM = ViewModelProviders.of(this).get(TabDateViewModel.class);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_tab_date, container, false );

        items = new ArrayList<>();
        listView = v.findViewById(R.id.listview_date);


        tabDateVM.getAllDates().observe(this, new Observer<List<datetimePOJO>>() {
            @Override
            public void onChanged(List<datetimePOJO> datetimePOJOS) {

                for (datetimePOJO datetime : datetimePOJOS){
                    items.add(datetime.Name + "  " + datetime.Date);
                }
                arrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,items);
                listView.setAdapter(arrayAdapter);
            }
        });


        return v ;
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_date, container, false);
    }*/

}
