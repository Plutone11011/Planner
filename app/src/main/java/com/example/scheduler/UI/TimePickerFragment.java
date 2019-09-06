package com.example.scheduler.UI;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.scheduler.R;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public TimePickerFragment(){
        //required empty constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        //the user chose a time
        Bundle args = getArguments();
        Editable inputField = (Editable) args.getCharSequence(getString(R.string.inputTimekey));
        String  first10minutes = "0";


        if (minute >= 10){
            first10minutes = "";
        }

        inputField.clear();
        if (hourOfDay >= 0 && hourOfDay <= 9){
            inputField.append("0" + ((Integer)hourOfDay).toString() + ":" + first10minutes + ((Integer)minute).toString());
        }
        else{
            inputField.append(((Integer)hourOfDay).toString() + ":" + first10minutes + ((Integer)minute).toString());
        }

    }
}
