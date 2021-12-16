package com.example.covid19tracker.HealthStatus;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.covid19tracker.R;

public class HealthStatusFragment extends Fragment {

    private View view;
    private RadioGroup group1,group2,group3,group4,group5,group6,group7,group8,group9,group10,group11;
    private Button submit;
    private int id1,id2,id3,id4,id5,id6,id7,id8,id9,id10,id11;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_health_status, container, false);

        assign();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkHealthStatus();
                group1.clearCheck();
                group2.clearCheck();
                group3.clearCheck();
                group4.clearCheck();
                group5.clearCheck();
                group6.clearCheck();
                group7.clearCheck();
                group8.clearCheck();
                group9.clearCheck();
                group10.clearCheck();
                group11.clearCheck();

            }
        });

        return view;
    }

    private void assign(){
        group1 = view.findViewById(R.id.radioGroup1);
        group2 = view.findViewById(R.id.radioGroup2);
        group3 = view.findViewById(R.id.radioGroup3);
        group4 = view.findViewById(R.id.radioGroup4);
        group5  = view.findViewById(R.id.radioGroup5);
        group6 = view.findViewById(R.id.radioGroup6);
        group7 = view.findViewById(R.id.radioGroup7);
        group8 = view.findViewById(R.id.radioGroup8);
        group9 = view.findViewById(R.id.radioGroup9);
        group10 = view.findViewById(R.id.radioGroup10);
        group11 = view.findViewById(R.id.radioGroup11);

        submit = view.findViewById(R.id.healthStatus);

    }


    private void checkHealthStatus(){

        id1 = group1.getCheckedRadioButtonId();
        id2 = group2.getCheckedRadioButtonId();
        id3 = group3.getCheckedRadioButtonId();
        id4 = group4.getCheckedRadioButtonId();
        id5 = group5.getCheckedRadioButtonId();
        id6 = group6.getCheckedRadioButtonId();
        id7 = group7.getCheckedRadioButtonId();
        id8 = group8.getCheckedRadioButtonId();
        id9 = group9.getCheckedRadioButtonId();
        id10 = group10.getCheckedRadioButtonId();
        id11 = group11.getCheckedRadioButtonId();


        if((id8 == (R.id.yes8)) || (id9 == (R.id.yes9)) || (id10 == (R.id.yes10)) || (id11 == (R.id.yes11))) {
            startActivity(new Intent(view.getContext(),PDFActivity.class));
        }
        else if((id1 == R.id.yes1) || (id2 == (R.id.yes2)) || (id3 == (R.id.yes3)) || (id4 == (R.id.yes4))
        || (id5 == (R.id.yes5)) || (id6 == (R.id.yes6)) || (id7 ==(R.id.yes7))) {
           showMessage("We recommend you to consult a doctor","Covid Alert!");
        }
        else if(id1 == (R.id.no1) || id2 == (R.id.no2) || id3 == (R.id.no3) || id4 == (R.id.no4)
                || id5 == (R.id.no5) || id6 == (R.id.no6) || id7 == (R.id.no7) || id8 == (R.id.no8)
                || id9 == (R.id.no9) || id10 == (R.id.no10) || id11 == (R.id.no11)){
                showMessage("You are safe","Safe");

        }

    }

    public void showMessage(String message,String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setCancelable(false);
        if(message.equals("You are safe")){
            builder.setIcon(R.drawable.a4);
        }else if(message.equals("We recommend you to consult a doctor")){
            builder.setIcon(R.drawable.a3);
        }
        builder.setPositiveButton("Ok",null);
        builder.create();
        builder.show();
    }
}