package com.example.scheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TasksAdapter extends ArrayAdapter<String> {

    private Context context ;
    private String[] tasksnames ;
    private LayoutInflater inflater ;

    public TasksAdapter(Context context,int resource, String[] tasksnames) {
        super(context, resource,tasksnames);

        this.tasksnames = tasksnames;
        this.context = context ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //inflates textview which represents the listview elements
        if (convertView == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_textview,null);

        }

        TextView taskTextView = convertView.findViewById(R.id.a_textview);
        taskTextView.setText(tasksnames[position]);
        return convertView;
    }
}
