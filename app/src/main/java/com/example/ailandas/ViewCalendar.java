package com.example.ailandas;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ViewCalendar extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    FileHelper db = new FileHelper();
    //CalendarView cw;
    CompactCalendarView compactCalendarView;
    ConstraintLayout cl;
    TextView tw;
    BottomNavigationView bottomNavigationView;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_calendar);
        getSupportActionBar().hide();
        cl = findViewById(R.id.Gridas);
       // et = findViewById(R.id.txt);
        compactCalendarView = findViewById(R.id.Kalendorius);
       // spinner = findViewById(R.id.spinner);
        tw = findViewById(R.id.textView1);

        Context context=this;
        username=SaveSharedPreference.getUserName(context);

        bottomNavigationView= (BottomNavigationView) findViewById(R.id.botomnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        if(SaveSharedPreference.getPrefActivityId(this)!=0) {
            bottomNavigationView.getMenu().findItem(SaveSharedPreference.getPrefActivityId(this)).setChecked(true);
        }

        String Year = getYear();
        String Month = getMonthName();
        tw.setText(Year + " " + Month);

        FileHelper fh = new FileHelper();
        if(username.length()==0){
            boolean existance = fh.getExistance(this,"myTextFile.txt");
            if (existance == true) {
                PopulateCalendar(compactCalendarView);
            }
        }
        else{
            boolean existance = fh.getExistance(this,username+".txt");
            if (existance == true) {
                PopulateCalendar(compactCalendarView);
            }
        }


        //PopulateSpinner(spinner, this);
        //Listener
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                Intent Ketinimas = new Intent(getApplicationContext(), NewEvent.class);
                Ketinimas.putExtra("Date",dateClicked.getTime());
                startActivity(Ketinimas);
               /* List<Event> events = compactCalendarView.getEvents(dateClicked);
                // System.out.println( "Day was clicked: " + dateClicked + " with events " + events);
                long date = dateClicked.getTime();
                compactCalendarView.setVisibility(View.INVISIBLE);
                cl.setVisibility(View.VISIBLE);
                tw.setVisibility(View.INVISIBLE);
                et.setText("");
                Date = date;*/


            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                String Month = getMonthName();
                String Year = getYear();
                tw.setText(Year + " " + Month);

            }
        });
        //Listener


        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        java.lang.String[] array ={"PIRM","ANTR","TREČ","KETV","PENK","ŠEŠT","SEKM"};
        compactCalendarView.setDayColumnNames(array);


    }

    private void PopulateCalendar(CompactCalendarView compactCalendarView) {

        String DESC = "";
        String date;
        String temp = "";
        String ITEMTYPE = "";
        long DATE;


        FileHelper fh = new FileHelper();
        String listas="";
        if(username.length()==0){
            listas = fh.ReadBtn(this,"myTextFile.txt");
        }
        else{
            listas = fh.ReadBtn(this,username+".txt");
        }

        String[] lines = listas.split("\\r?\\n");
        if (lines.length > 0) {

            for (int i = 0; i < lines.length; i++) {

                temp = lines[i];

                date = temp.split(",")[0];
                DATE = Long.parseLong(date);
                DESC = temp.split(",")[1];
                ITEMTYPE = temp.split(",")[2];
                Event event = new Event(Color.GREEN, DATE, DESC);
                compactCalendarView.addEvent(event);


            }
        }

    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

    private String getMonthName() {
        Date date = compactCalendarView.getFirstDayOfCurrentMonth();
        DateFormat dfMonth = new SimpleDateFormat("MM");
        String Month = dfMonth.format(date);
        if (Month.contains("01")) {
            return "Sausis";
        }
        if (Month.contains("02")) {
            return "Vasaris";
        }
        if (Month.contains("03")) {
            return "Kovas";
        }
        if (Month.contains("04")) {
            return "Balandis";
        }
        if (Month.contains("05")) {
            return "Gegužė";
        }
        if (Month.contains("06")) {
            return "Birželis";
        }
        if (Month.contains("07")) {
            return "Liepa";
        }
        if (Month.contains("08")) {
            return "Rugpjūtis";
        }
        if (Month.contains("09")) {
            return "Rugsėjis";
        }
        if (Month.contains("10")) {
            return "Spalis";
        }
        if (Month.contains("11")) {
            return "Lapkritis";
        }
        if (Month.contains("12")) {
            return "Gruodis";
        }
        return null;

    }

    private String getYear() {
        Date date = compactCalendarView.getFirstDayOfCurrentMonth();
        DateFormat dfYear = new SimpleDateFormat("yyyy");
        String Year = dfYear.format(date);
        return Year;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        String name=menuItem.toString();
        System.out.println(name);
        int idas=menuItem.getItemId();
        SaveSharedPreference.setPrefActivityId(this,idas);
        System.out.println("ID : "+idas);

        if(menuItem.toString().contains("Home")){

            Intent Ketinimas = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(Ketinimas);


        }
        if(menuItem.toString().contains("Calendar")){

        }
        if(menuItem.toString()=="Sponsor"){

        }
        if(menuItem.toString().contains("Profile")){

            Intent Profile = new Intent(getApplicationContext(), account_management.class);
            startActivity(Profile);

        }
        return false;

    }
}

