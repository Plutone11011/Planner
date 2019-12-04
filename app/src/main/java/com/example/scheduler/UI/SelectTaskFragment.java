package com.example.scheduler.UI;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.scheduler.Model.TasksTable;
import com.example.scheduler.R;
import com.example.scheduler.Repository.TasksRepo;
import com.example.scheduler.Adapters.TasksAdapter;
import com.example.scheduler.Viewmodels.SelectTaskFragmentViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class SelectTaskFragment extends DialogFragment implements TasksRepo.TaskWithPrimaryKey {

    private ListView viewOfCurrentTasks ;
    private SelectTaskFragmentViewModel selectDateFragmentVM ;
    private Bundle args ;

    @Override
    public void onTaskResult(TasksTable tasksTable) {
        Intent intent = new Intent(getContext(),TaskActivity.class);

        //intent.putExtra("id",tasksTable.getId());
        intent.putExtra("name",tasksTable.getName());
        intent.putExtra("date",tasksTable.getDate());
        intent.putExtra("priority",tasksTable.getPriority());
        intent.putExtra("type",tasksTable.getType());
        intent.putExtra("state",tasksTable.getState());

        startActivity(intent);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectDateFragmentVM = ViewModelProviders.of(this).get(SelectTaskFragmentViewModel.class);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        args = getArguments() ;

        final String []listOfCurrentTasks = args.getStringArray(getString(R.string.name_list_dialog));
        final String[] datesOfCurrentTasks = args.getStringArray(getString(R.string.date_list_dialog));

        selectDateFragmentVM.setAction(args.getInt(getString(R.string.action_dialog)));
        //inflates Listview
        View v = getActivity().getLayoutInflater().inflate(R.layout.listview_dialog,null);
        viewOfCurrentTasks = v.findViewById(R.id.current_tasks);

        //adapter takes also a textview to use when istantiating the view
        final TasksAdapter adapter = new TasksAdapter(getContext(), R.layout.adapter_textview, listOfCurrentTasks);


        viewOfCurrentTasks.setAdapter(adapter);
        viewOfCurrentTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long rowId) {

                selectDateFragmentVM.setT_name((String) adapterView.getItemAtPosition(position));
                //sets date knowing that every dates and names of the same event are stored at the same position
                selectDateFragmentVM.setT_date(datesOfCurrentTasks[Arrays.asList(listOfCurrentTasks).indexOf(selectDateFragmentVM.getT_name())]);

                Log.d("SelectTaskFragment",selectDateFragmentVM.getT_name());
                Log.d("SelectTaskFragment",selectDateFragmentVM.getT_date());
                if (selectDateFragmentVM.getAction() == 2){

                    try {
                        SimpleDateFormat dateformat = new SimpleDateFormat(getString(R.string.dateformat)
                                + " " + getString(R.string.timeformat));
                        if (dateformat.parse(selectDateFragmentVM.getT_date()).getTime() < Calendar.getInstance().getTime().getTime()){
                            //then we surpassed task time
                            selectDateFragmentVM.updateStatetoCompleted(selectDateFragmentVM.getT_name(), selectDateFragmentVM.getT_date());
                        }
                        else {
                            Toast.makeText(getContext(),"The time to be completed has not come for this task yet", Toast.LENGTH_LONG);
                        }

                    }
                    catch (ParseException p){
                        p.printStackTrace();
                    }
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);

                }
                else if (selectDateFragmentVM.getAction() == 0){
                    //delete tasktable
                    selectDateFragmentVM.deleteTaskWithPrimaryKey(selectDateFragmentVM.getT_date(),selectDateFragmentVM.getT_name());
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);

                }
                else {
                    //it's 1, so editing
                    //intent to task activity
                    selectDateFragmentVM.getTaskWithPrimaryKey(selectDateFragmentVM.getT_date(),selectDateFragmentVM.getT_name(),
                            SelectTaskFragment.this);
                }

            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(v);

        return builder.create();
    }

    @Override
    public void onDestroyView() {
        viewOfCurrentTasks = null ;
        super.onDestroyView();
    }
}
