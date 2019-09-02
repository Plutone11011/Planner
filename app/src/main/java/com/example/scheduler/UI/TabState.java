package com.example.scheduler.UI;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.scheduler.Model.nameStatePOJO;
import com.example.scheduler.R;
import com.example.scheduler.Viewmodels.TabStateViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabState extends Fragment{


    private ListView listView ;
    private ArrayList<String> items ;
    private ArrayAdapter<String> arrayAdapter;
    private TabStateViewModel tabStateVM ;

    public TabState() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tabStateVM = ViewModelProviders.of(this).get(TabStateViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_tab_state, container, false );
        items = new ArrayList<>();
        listView = v.findViewById(R.id.listview_state);

        tabStateVM.getListOfNamesandStates().observe(this, new Observer<List<nameStatePOJO>>() {
            @Override
            public void onChanged(List<nameStatePOJO> nameStatePOJOS) {

                for (nameStatePOJO namestate : nameStatePOJOS){
                    items.add(namestate.Name + "  " + namestate.State);
                }

                arrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,items);
                listView.setAdapter(arrayAdapter);

            }
        });

        return v ;

    }

}
