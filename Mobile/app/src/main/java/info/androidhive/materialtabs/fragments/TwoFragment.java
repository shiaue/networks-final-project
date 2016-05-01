package info.androidhive.materialtabs.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;

import info.androidhive.materialtabs.R;
import info.androidhive.materialtabs.activity.IconTextTabsActivity;


public class TwoFragment extends Fragment{
    private OnFragmentInteractionListener2 mListener2;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    Button setButton;




    public TwoFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) { //
        super.onCreate(savedInstanceState);

    }

    protected void setDateAndTime(int hr, int min, int mon, int day, int yr) {
        Intent intent = new Intent(getActivity().getBaseContext(),IconTextTabsActivity.class);
        String hr_s = Integer.toString(hr);
        String min_s = Integer.toString(min);
        String mon_s = Integer.toString(mon);
        String day_s = Integer.toString(day);
        String yr_s = Integer.toString(yr);

        int [] date_time = {hr, min, mon, day, yr};
        String [] date_time_s = {hr_s, min_s, mon_s, day_s, yr_s};

        // Must pass this to 3rd fragment
        intent.putExtra("Date_Time", date_time_s);
        getActivity().startActivity(intent);

//        Bundle bundle = new Bundle();
//        bundle.putStringArray("date_time", date_time_s);
//        ThreeFragment fragobj = new ThreeFragment();
//        fragobj.setArguments(bundle);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) { // This runs after onCreate
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_two, container, false);

        setButton = (Button) view.findViewById(R.id.time_button);
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.DatePicker);
        final TimePicker tp = (TimePicker) view.findViewById(R.id.TimePicker);


        // close keyboard service
        keyboard.hideKeyboard(getContext());

        final Calendar c = Calendar.getInstance();

        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);


        // set current date into datepicker
        datePicker.init(year, month, day, null);
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);

        tp.setCurrentHour(hour);
        tp.setCurrentMinute(minute);

        setButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                hour = tp.getCurrentHour();
//                hour = tp.getHour();
                minute = tp.getCurrentMinute();
//                minute = tp.getMinute();
                year = datePicker.getYear();
                month = datePicker.getMonth() + 1;
                day = datePicker.getDayOfMonth();

                Toast.makeText(getActivity(), "Time: "+ hour+ ":"+ minute + "\nDate: "+month+"/"+day+"/"+year, Toast.LENGTH_LONG).show();
                Log.w("Date/Time", "Time: " + hour + ":" + minute + "\nDate: "+month+"/"+day+"/"+year);

                setDateAndTime(hour, minute, month, day, year);
            }
        });
        return view;


    }

    public void onButtonPressed(String [] date_time_s) {
        if (mListener2 != null) {
            mListener2.onFragmentInteraction2(date_time_s);
        }
    }
    public interface OnFragmentInteractionListener2 {
        public void onFragmentInteraction2(String [] date_time_s);
    }

}
