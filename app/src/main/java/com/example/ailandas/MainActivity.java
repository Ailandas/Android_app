package com.example.ailandas;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    ListView listView;
    AlertDialog.Builder builder;
    DialogInterface.OnClickListener dialogClickListener;
    int Position;
    Context context;
    BottomNavigationView bottomNavigationView;

    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        listView=findViewById(R.id.listView);
        context=this;
        //About username

        username=SaveSharedPreference.getUserName(context);


        //
        bottomNavigationView=(BottomNavigationView) findViewById(R.id.botomnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        int id=bottomNavigationView.getMenu().findItem(R.id.home).getItemId();
        bottomNavigationView.setSelectedItemId(id);
        if(SaveSharedPreference.getPrefActivityId(this)!=0) {
            bottomNavigationView.getMenu().findItem(SaveSharedPreference.getPrefActivityId(this)).setChecked(true);
        }
        FileHelper fh=new FileHelper();

        if(username.length()==0) {
            boolean existance = fh.getExistance(this,"myTextFile.txt");
            if (existance == true) {
                fh.SortTxtFile(this,"myTextFile.txt");
                PopulateListView(listView, this);
            }
            ////////////
        }
        else{
            boolean existance = fh.getExistance(this,username+".txt");
            if (existance == true) {
                fh.SortTxtFile(this,username+".txt");
                PopulateListView(listView, this);
            }
        }


        //////////////
         dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Object listItem = listView.getItemAtPosition(Position);
                        long Deletable=StringToLong(listItem.toString());
                        FileHelper fh=new FileHelper();
                        boolean isDeleted=false;
                        if(username.length()==0) {
                             isDeleted = fh.DeleteItem(Deletable, context,"myTextFile.txt");
                        }
                        else{
                            isDeleted = fh.DeleteItem(Deletable, context,username+".txt");
                        }
                        if(isDeleted==true){
                            Toast.makeText(context, "Įrašas sėkmingai ištrintas",
                                    Toast.LENGTH_SHORT).show();
                            if(username.length()==0) {
                                fh.SortTxtFile(context,"myTextFile.txt");
                            }
                            else{
                                fh.SortTxtFile(context,username+".txt");
                            }
                            listView.setAdapter(null);
                            PopulateListView(listView, context);

                        }
                        else{
                            Toast.makeText(context, "Nepavyko ištrinti",
                                    Toast.LENGTH_SHORT).show();
                        }
                       break;



                    case DialogInterface.BUTTON_NEGATIVE:
                        Toast.makeText(context, "Sėkmingai atšaukta",
                                Toast.LENGTH_SHORT).show();

                        break;
                }
            }
        };
        builder = new AlertDialog.Builder(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Position =position;
                DialogBox();

            }
        });
    }

    /*
    I/System.out: 2131230809
    I/System.out: 2131230724
    I/System.out: 2131230851
    I/System.out: 2131230892
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        String name=menuItem.toString();
        int idas=menuItem.getItemId();
        SaveSharedPreference.setPrefActivityId(this,idas);
        System.out.println("ID : "+idas);

        if(menuItem.toString()=="Home"){

        }
        if(menuItem.toString().contains("Calendar")){



            Intent Ketinimas = new Intent(getApplicationContext(), ViewCalendar.class);
            startActivity(Ketinimas);


        }
        if(menuItem.toString()=="Sponsor"){

        }
        if(menuItem.toString().contains("Profile")){



            Intent Ketinimas = new Intent(getApplicationContext(), account_management.class);
            startActivity(Ketinimas);

        }
        return false;
    }


    class MyAdapter extends ArrayAdapter<String>{

        Context context;
        String[] rTitle;
        String[] rDescription;
        int[] rImgs;

        MyAdapter(Context c, String[] title, String[] description, int[] imgs){
            super(c,R.layout.row,R.id.TextView1,title);
            this.context=c;
            this.rTitle=title;
            this.rDescription=description;
            this.rImgs=imgs;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=layoutInflater.inflate(R.layout.row,parent,false);
            ImageView images=row.findViewById(R.id.image);
            TextView myTitle=row.findViewById(R.id.TextView1);
            TextView myDescription=row.findViewById(R.id.TextView2);

            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);

            return row;
        }
    }
    private void PopulateListView(ListView listView,Context x){
        FileHelper fh=new FileHelper();
        String listas="";
        if(username.length()==0){
             listas = fh.ReadBtn(this,"myTextFile.txt");
        }
        else{
            listas = fh.ReadBtn(this,username+".txt");
        }
        String[] lines = listas.split("\\r?\\n");
        String temp="";
        String date="";
        String[] DATE=new String[lines.length];
        String[] DESC=new String[lines.length];
        int[] Img=new int[lines.length];
        String imgTemp="";


        if(lines.length>0) {

            for (int i = 0; i < lines.length; i++) {

                temp = lines[i];

                date = temp.split(",")[0];

                Date currentDate = new Date(Long.parseLong(date));
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                DATE[i]=df.format(currentDate);
               // DATE[i]=date;


                DESC[i]=temp.split(",")[1];

                if( temp.split(",")[2].contains("Darbas")){
                    Img[i]=R.drawable.darbas;
                }
                if(temp.split(",")[2].contains("Gimtadienis")){
                    Img[i]=R.drawable.giftas;
                }
                if(temp.split(",")[2].contains("Įvykis")){
                    Img[i]=R.drawable.laisvalaikis;
                }
            }


            MyAdapter myAdapter=new MyAdapter(x,DATE,DESC,Img);
            listView.setAdapter(myAdapter);
        }
    }
    private void DialogBox(){

        builder.setMessage("Ar tikrai norite pašalinti?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener);
        AlertDialog dialog = builder.show();
        TextView messageView = dialog.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

        Button btnPositive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button btnNegative = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
        layoutParams.weight = 10;
        btnPositive.setLayoutParams(layoutParams);
        btnNegative.setLayoutParams(layoutParams);
    }
    private long StringToLong(String stringDate){
        try {
            SimpleDateFormat df=  new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date d=df.parse(stringDate);

            long mills = d.getTime();
            System.out.println(mills);
            return mills;
        }
        catch (Exception exc){
            System.out.println("error: "+ exc.getMessage());
            return 0;
        }
    }








}