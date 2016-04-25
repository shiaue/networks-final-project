package info.androidhive.materialtabs.fragments;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.design.widget.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import info.androidhive.materialtabs.R;
import info.androidhive.materialtabs.activity.AlarmReceiver;
import info.androidhive.materialtabs.activity.MainActivity;


public class OneFragment extends Fragment{
    private OnFragmentInteractionListener mListener;
    Button sendSMSBtn;
    Button scheduleSMSBtn;
    EditText toPhoneNumberET;
    EditText smsMessageET;

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void sendSMS() {
        String toPhoneNumber = toPhoneNumberET.getText().toString();
        String smsMessage = smsMessageET.getText().toString();
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(toPhoneNumber, null, smsMessage, null, null);
            Toast.makeText(getActivity(), "SMS sent.",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getActivity(),
                    "Sending SMS failed.",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    protected void scheduleSMS()    {
        String toPhoneNumber = toPhoneNumberET.getText().toString();
        String smsMessage = smsMessageET.getText().toString();
        // time at which alarm will be scheduled here alarm is scheduled at 1 day from current time,
        // we fetch  the current time in milliseconds and added 1 day time
        // i.e. 24*60*60*1000= 86,400,000   milliseconds in a day

        //TODO must calculate time in millis from user specified time and now
        int millis = 1000*10;
        Long time = new GregorianCalendar().getTimeInMillis()+ millis;
        Calendar now = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");


        // create an Intent and set the class which will execute when Alarm triggers, here we have
        // given AlarmReciever in the Intent, the onRecieve() method of this class will execute when
        // alarm triggers and
        //we will write the code to send SMS inside onRecieve() method of Alarmreciever class
        Intent intentAlarm = new Intent(getActivity(), AlarmReceiver.class);

        intentAlarm.putExtra("phone number", toPhoneNumber);
        intentAlarm.putExtra("sms message", smsMessage);
        Log.w("Context: ", this.getContext().toString() + " Activity: "+ this.getActivity().toString());
        // this.getContext() == this.getActivity()
        // XTODO The application is properly entering the alarm receiver

        // create the alarm manager object
        AlarmManager alarmManager = (AlarmManager) this.getActivity().getSystemService(Context.ALARM_SERVICE);

        //set the alarm for particular time
        PendingIntent pending = PendingIntent.getBroadcast(this.getActivity(), 0, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pending);

        now.add(Calendar.MILLISECOND, millis);
//        Log.w("AlarmManager", "SMS Scheduled for: " + now.MONTH + "/" + now.DATE + "/" + now.YEAR);
        Log.w("AlarmManager", "SMS Scheduled for: " + sdf.format(now.getTime()));
        Toast.makeText(this.getActivity(), "SMS Scheduled for: " + sdf.format(now.getTime()), Toast.LENGTH_LONG).show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);

        sendSMSBtn = (Button) view.findViewById(R.id.btnSendSMS);
        scheduleSMSBtn = (Button) view.findViewById(R.id.btnScheduleSMS);

        toPhoneNumberET = (EditText) view.findViewById(R.id.editTextPhoneNo);
        smsMessageET = (EditText) view.findViewById(R.id.editTextSMS);
        sendSMSBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendSMS();
            }
        });

        scheduleSMSBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                scheduleSMS();
            }
        });


        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    // must send phone # and message to 3rd fragment
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String userContent, int size, int colorValue);
    }


}
