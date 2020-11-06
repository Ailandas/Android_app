package com.example.ailandas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RowClass extends AppCompatActivity {
    LinearLayout lr;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lr= findViewById(R.id.LINEARAS);
        lr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Buttonas");

            }

        });

    }





}
