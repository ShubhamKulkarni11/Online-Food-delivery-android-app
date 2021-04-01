package com.example111.foodshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import static com.google.android.material.bottomnavigation.BottomNavigationView.*;


public class ShopHomePage extends AppCompatActivity {

    BottomNavigationView bnv;
    Toolbar toolbar;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_home_page2);
        getSupportFragmentManager().beginTransaction().replace(R.id.shopHomePFrame,new Home_fragment()).commit();

        bnv=findViewById(R.id.BottomNav);
        toolbar=findViewById(R.id.shopToolbar);
        setSupportActionBar(toolbar);


        bnv.setOnNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @SuppressLint("ResourceType")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment temp;
                temp = null;

                switch (item.getItemId()){

                    case R.id.bottomNavHome:
                        temp=new Home_fragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.shopHomePFrame,temp).commit();
                        break;
                    case R.id.bottomNavAdd:
                        startActivity(new Intent(ShopHomePage.this,Chef.class));


                        break;
                    case R.id.shopUserp:
                       // temp=new Setting_fragment();
                       // getSupportFragmentManager().beginTransaction().replace(R.id.shopHomePFrame,temp).commit();
                        startActivity(new Intent(ShopHomePage.this,shopOwnerProfile.class));
                }

                return true;

            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Fragment temp=null;

        switch(item.getItemId())
        {
            case R.id.about:
                temp=new Devoloper_fragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.shopHomePFrame,temp).commit();
                break;
            case R.id.shopLogout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ShopHomePage.this,shop_login.class));
                finish();
                Toast.makeText(this, "Logout successfully", Toast.LENGTH_SHORT).show();
                break;

            case R.id.toolbarDev:
                Toast.makeText(this, "You clicked on Developer option", Toast.LENGTH_SHORT).show();
        }

        return true;
    }


}