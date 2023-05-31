package com.example.lostandfoundapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class showadvertActivity extends AppCompatActivity {
    //declare variables
    RecyclerView recyclerView;

    DbHelper DB;

    ArrayList<String> newArray;

    Button again;

    private Array.RecyclerViewClickListener listener1;

    //run database for all data, if toast responds all good
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        newArray = new ArrayList<>();

        setContentView(R.layout.activity_showadvert);

        recyclerView = findViewById(R.id.re);

        again = findViewById(R.id.back);

        DB = new DbHelper(this);

        if(DB.alldata2()==null){}
        else {
            newArray = DB.alldata2();}
        listener1 = new Array.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(showadvertActivity.this, "Working", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), removeadvertActivity.class);
                    intent.putExtra("key", newArray.get(position));
                startActivity(intent);


            }
        };

        //function to set database to 0 and start anew
        Array adapter = new Array(this, newArray,listener1);
        recyclerView.hasFixedSize();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);

        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);

            }
        });

    }
}
