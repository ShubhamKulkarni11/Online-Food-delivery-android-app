package com.example111.foodshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        Thread thread=new Thread(){
            public void run(){
                try {
                    sleep(2000);

                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    Intent intent=new Intent(Splashscreen.this,Home_page.class);
                    startActivity(intent);
                    finish();

                }
            }

        };thread.start();

    }

}