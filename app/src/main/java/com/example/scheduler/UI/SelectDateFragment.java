package com.example.scheduler.UI;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.scheduler.R;

import java.util.Calendar;

public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, yy, mm, dd);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        Bundle args = getArguments();
        Editable inputField = (Editable) args.getCharSequence(getString(R.string.inputDatekey));

        inputField.clear();
        Log.d("Mese",((Integer)month).toString());//month va da 0-11
        month++;

        inputField.append(((Integer)day).toString() + "/" + ((Integer)month).toString() + "/" + ((Integer)year).toString());
    }
}
