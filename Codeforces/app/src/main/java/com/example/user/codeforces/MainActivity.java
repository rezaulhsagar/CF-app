package com.example.user.codeforces;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button MainUserStatInvoke = (Button) findViewById(R.id.MainUserStatInvoke);
        final Button MainUserSubInvoke = (Button) findViewById(R.id.MainUserSubInvoke);
        final Button Mymap = (Button) findViewById(R.id.Mymap);

        MainUserStatInvoke.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent gotoUserStatInvoke = new Intent(MainActivity.this, UserStatInvoke.class);
                startActivity(gotoUserStatInvoke);
            }
        });

        MainUserSubInvoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoUserSubInvoke = new Intent(MainActivity.this, UserSubInvoke.class);
                startActivity(gotoUserSubInvoke);
            }
        });

        Mymap.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent gotoMymap = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(gotoMymap);
            }
        });
    }



}