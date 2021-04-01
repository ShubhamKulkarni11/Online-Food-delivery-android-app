package com.example111.foodshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home_page extends AppCompatActivity {
    private Button customer;
    private Button shopKeeper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        customer=findViewById(R.id.customer);
        shopKeeper=findViewById(R.id.shopOwner);

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home_page.this,Customer_login.class));
            }
        });
        shopKeeper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home_page.this,shop_login.class));
            }
        });


    }
   /* @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null){
            startActivity(new Intent(Home_page.this,ShopHomePage.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK ));

        }
    }*/
}