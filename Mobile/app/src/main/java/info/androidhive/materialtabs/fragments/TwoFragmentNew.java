package info.androidhive.materialtabs.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import info.androidhive.materialtabs.R;


public class TwoFragmentNew extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    public TwoFragmentNew() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_two, container, false);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

// Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(c.getTime());
    }



}
//
//public class DatePickerDialogFragment extends DialogFragment {
//
//    private DatePickerDialog.OnDateSetListener mDateSetListener;
//
//    public DatePickerDialogFragment() {
//        // nothing to see here, move along
//    }
//
//    public DatePickerDialogFragment(DatePickerDialog.OnDateSetListener callback) {
//        mDateSetListener = (DatePickerDialog.OnDateSetListener) callback;
//    }
//
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        Calendar cal = Calendar.getInstance();
//
//        return new DatePickerDialog(getActivity(),
//                mDateSetListener, cal.get(Calendar.YEAR),
//                cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
//    }
//
//}