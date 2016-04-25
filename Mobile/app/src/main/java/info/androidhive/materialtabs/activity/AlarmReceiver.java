package info.androidhive.materialtabs.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        // TODO Auto-generated method stub
        Toast.makeText(context, "Alarm Triggered", Toast.LENGTH_LONG).show();

        // Why doesn't this Log work?
        Log.w("AlarmReceiver", "You have arrived in the AlarmReceiver ");

        // here you can start an activity or service depending on your need
        // for ex you can start an activity to vibrate phone or to ring the phone

        String phoneNumberReciver="8479222186";// phone number to which SMS to be send
        String message="AlarmManager SMS Test FAILED (Message did not pass to receiver)";// message to send

        String action = intent.getAction();
        Log.w("AlarmReceiver", "Action: "+ action);


        phoneNumberReciver = intent.getExtras().getString("phone number");
            message = intent.getExtras().getString("sms message");
            Log.w("AlarmManager", "To "+ phoneNumberReciver +": " + message);

//        SmsManager sms = SmsManager.getDefault();
//        sms.sendTextMessage(phoneNumberReciver, null, message, null, null);
//        Toast.makeText(context, "Alarm Manager Triggered and SMS Sent", Toast.LENGTH_LONG).show();
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumberReciver, null, message, null, null);
            Toast.makeText(context, "SMS sent.",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(context,
                    "Sending SMS failed.",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

}
