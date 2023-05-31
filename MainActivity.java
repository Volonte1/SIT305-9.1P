package com.example.lostandfoundapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    //declare variables
    Button create, showitem,map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        create = findViewById(R.id.button);
        showitem = findViewById(R.id.button2);
        map = findViewById(R.id.show);
        //each individual button press onclick
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), createadvertActivity.class);
                startActivity(intent);

            }
        });
        showitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), showadvertActivity.class);
                startActivity(intent);
            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(intent);

            }
        });
    }
}