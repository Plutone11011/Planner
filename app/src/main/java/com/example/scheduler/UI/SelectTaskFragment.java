package com.example.scheduler.UI;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.scheduler.R;
import com.example.scheduler.TasksAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelectTaskFragment extends DialogFragment {

    private ListView viewOfCurrentTasks ;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        String []listOfCurrentTasks ;
        Bundle args = getArguments() ;

        //inflates Listview
        View v = getActivity().getLayoutInflater().inflate(R.layout.listview_dialog,null);
        viewOfCurrentTasks = v.findViewById(R.id.current_tasks);

        //list to represent in the listview
        List<String> tmp = new ArrayList<>(args.getStringArrayList(getString(R.string.name_list_dialog)));

        listOfCurrentTasks = new String[tmp.size()];
        for (int i = 0; i < tmp.size(); i++){
            listOfCurrentTasks[i] = tmp.get(i);
        }

        //adapter takes also a textview to use when istantiating the view
        final TasksAdapter adapter = new TasksAdapter(getContext(), R.layout.adapter_textview, listOfCurrentTasks);


        viewOfCurrentTasks.setAdapter(adapter);

        viewOfCurrentTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(adapterView.getContext(), "Ah stronzo", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(v);

        return builder.create();
    }
}
