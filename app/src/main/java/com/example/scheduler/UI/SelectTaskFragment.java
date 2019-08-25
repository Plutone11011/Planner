package com.example.scheduler.UI;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.scheduler.R;

import java.util.ArrayList;

public class SelectTaskFragment extends DialogFragment {

    private ListView viewOfCurrentTasks ;
    private ArrayList<String> listOfCurrentTasks ;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Bundle args = getArguments() ;

        View v = getLayoutInflater().inflate(R.layout.listview_dialog,null);

        return super.onCreateDialog(savedInstanceState);
    }
}
