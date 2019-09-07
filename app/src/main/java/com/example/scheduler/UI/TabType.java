package com.example.scheduler.UI;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.scheduler.Model.nameClassPOJO;
import com.example.scheduler.R;
import com.example.scheduler.Viewmodels.TabClassViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabType extends Fragment{


    private ListView listView ;
    private ArrayList<String> items ;
    private ArrayAdapter<String> arrayAdapter;
    private TabClassViewModel tabTypeVM ;

    public TabType() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tabTypeVM = ViewModelProviders.of(this).get(TabClassViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_tab_type, container, false );
        items = new ArrayList<>();
        listView = v.findViewById(R.id.listview_type);

        tabTypeVM.getListOfNamesandTypes().observe(this, new Observer<List<nameClassPOJO>>() {
            @Override
            public void onChanged(List<nameClassPOJO> nameClassPOJOS) {

                for (nameClassPOJO nameclass : nameClassPOJOS){
                    items.add(nameclass.Name + "  " + nameclass.Type);
                }

                arrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,items);
                listView.setAdapter(arrayAdapter);
            }
        });

        return v ;
    }

}
