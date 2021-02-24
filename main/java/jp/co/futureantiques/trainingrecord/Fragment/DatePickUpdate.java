package jp.co.futureantiques.trainingrecord.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

import jp.co.futureantiques.trainingrecord.Activity.EditActivity;

public class DatePickUpdate extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    //カレンダー要素
    protected Calendar calendar;
    protected int year;
    protected int month;
    protected int dayOfMonth;

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), (EditActivity) getActivity(), year, month, dayOfMonth);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
    }
}