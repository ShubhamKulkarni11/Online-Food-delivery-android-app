package com.example111.foodshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Customer_Homepage extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__homepage);

        getSupportFragmentManager().beginTransaction().replace(R.id.customerHomePFrame,new Setting_fragment()).commit();

        bottomNavigationView=findViewById(R.id.customerBottomNav);
        toolbar=findViewById(R.id.customerToolbar);
        setSupportActionBar(toolbar);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint({"ResourceType", "NonConstantResourceId"})
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment temp;

                switch (item.getItemId()){

                    case R.id.customerHomeMenu:
                        temp=new Setting_fragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.customerHomePFrame,temp).commit();
                        break;
                    case R.id.customerProfileMenu:
                        startActivity(new Intent(Customer_Homepage.this,shopOwnerProfile.class));



                        break;
                    case R.id.customerCart:
                        temp=new customerCart();
                        getSupportFragmentManager().beginTransaction().replace(R.id.customerHomePFrame,temp).commit();
                }

                return true;

            }
        });

    }
}