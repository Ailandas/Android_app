package com.example.ailandas;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ailandas.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;


import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class NewEvent extends AppCompatActivity {

    EditText et;
    Spinner spinner;
    TextView tw;
    Date date;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        getSupportActionBar().hide();

        spinner = findViewById(R.id.spinner);
        tw = findViewById(R.id.textView1);
        et = findViewById(R.id.txt);

        PopulateSpinner(spinner, this);
        Context context=this;
        username=SaveSharedPreference.getUserName(context);

        Bundle mBundle = getIntent().getExtras();
        Long time = mBundle.getLong("Date");
        date = new Date(time);

            Button buttonOne = findViewById(R.id.buttonAtsaukti); //When i press Exit
                buttonOne.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        System.out.println("Atsaukti");
                        Intent Ketinimas = new Intent(getApplicationContext(), ViewCalendar.class);
                        startActivity(Ketinimas);
                    }
                });

            Button buttonTwo = findViewById(R.id.buttonPatvirtinti);
            buttonTwo.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    System.out.println("Patvirtinti");
                    try {
                           AddEvent();
                           Intent Ketinimas = new Intent(getApplicationContext(), ViewCalendar.class);
                           startActivity(Ketinimas);

                    } catch (Exception exc) {

                    }
                }
            });
    }

    private void PopulateSpinner(Spinner mySpinner, Context x) {
        java.util.ArrayList<String> strings = new java.util.ArrayList<>();
        strings.add("Darbas");
        strings.add("Gimtadienis");
        strings.add("Įvykis");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strings);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }
      private void AddEvent() {
        long data=date.getTime();
        Object text = et.getText();
        FileHelper fh = new FileHelper();
        int position = spinner.getSelectedItemPosition();
        String itemType = spinner.getItemAtPosition(position).toString();
        if(username.length()==0){
            fh.WriteBtn(this, data + "," + text.toString() + "," + itemType,"myTextFile.txt");
        }
        else{
            fh.WriteBtn(this, data + "," + text.toString() + "," + itemType,username+".txt");
        }

    }

}

