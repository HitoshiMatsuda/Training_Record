package jp.co.futureantiques.trainingrecord.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import androidx.fragment.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;

import java.util.Calendar;

import jp.co.futureantiques.trainingrecord.Activity.TrainingRegisterActivity;

public class DatePick extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    //カレンダー要素
    protected Calendar calendar;
    protected int year;
    protected int month;
    protected int day;

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), (TrainingRegisterActivity)getActivity(), year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}