package co.com.udistrital.presbyapp.util;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * Created by Rolando Baron on 21/09/2015.
 */
public class  DateDialog extends DialogFragment implements  DatePickerDialog.OnDateSetListener{

    EditText txtDate;

    public DateDialog(View view){
        this.txtDate=(EditText)view;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), this, 1999, 0, 1);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        String date = day+"-"+(month+1)+"-"+year;
        txtDate.setText(date);
    }
}