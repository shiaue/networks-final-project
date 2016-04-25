package info.androidhive.materialtabs.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import info.androidhive.materialtabs.R;


public class ThreeFragment extends Fragment{

    TextView detailTextView;
    Button sendButton;

    public ThreeFragment() {
        // Required empty public constructor
    }

    protected void onReload(TextView tv) {

        String data = getArguments().getString("date_time"); // nothing here
        tv.append(data);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        detailTextView = (TextView) view.findViewById(R.id.date_time_details);
        sendButton = (Button) view.findViewById(R.id.reload_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onReload(detailTextView);
            }
        });

        // Inflate the layout for this fragment







        return view;
    }




}
