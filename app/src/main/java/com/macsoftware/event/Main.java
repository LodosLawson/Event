package com.macsoftware.event;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button Admin = findViewById(R.id.button);
        Button User = findViewById(R.id.button2);

        User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserFrame();
            }
        });
        Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminFrame();
            }
        });
    }

    public void AdminFrame(){
        // İkinci aktiviteye geçiş için Intent'i oluşturun
        Intent intent = new Intent(Main.this, User.class);

        // İkinci aktiviteyi başlatın
        startActivity(intent);
    }
    public void UserFrame(){
        // İkinci aktiviteye geçiş için Intent'i oluşturun
        Intent intent = new Intent(Main.this, Admin.class);

        // İkinci aktiviteyi başlatın
        startActivity(intent);
    }
}