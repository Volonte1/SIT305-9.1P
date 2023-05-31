package com.example.lostandfoundapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class removeadvertActivity extends AppCompatActivity {

    TextView nameview, dateview, locationview;
    DbHelper dbHelper;
    Button remove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DbHelper(this);
        setContentView(R.layout.activity_removeadvert);

        nameview = findViewById(R.id.removename);

        dateview = findViewById(R.id.removedate);

        locationview = findViewById(R.id.removelocation);

        remove = findViewById(R.id.remove);
        //declar variables, also sets data in box


        int position = 1;
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            position = extras.getInt("key");
        }
        nameview.setText(dbHelper.name(position));
        dateview.setText(dbHelper.date(position));
        locationview.setText(dbHelper.location(position));
        //remove function button
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.delete(nameview.getText().toString());
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
            }
        });

    }
}
