package com.example111.foodshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import static com.example111.foodshop.R.id.backHome;

public class shopOwnerProfile extends AppCompatActivity {
    private FirebaseUser firebaseUser;

    private TextView userName;
    private TextView userEmail;
    private TextView userContact;
    private ImageButton backHome;
    FirebaseFirestore firebaseStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_owner_profile);
        // backHome=findViewById(R.id.backHome)

        TextView logoutprofile = findViewById(R.id.logoutProfile);
        userName=findViewById(R.id.shopOwnerProfileName);
        userEmail=findViewById(R.id.shopOwnerEmail);
        userContact=findViewById(R.id.shopOwnerContact);

       /* backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(shopOwnerProfile.this,ShopHomePage.class));
                finish();
            }
        });*/

        logoutprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(shopOwnerProfile.this,shop_login.class));
                finish();
                Toast.makeText(shopOwnerProfile.this, "Logout successfully", Toast.LENGTH_SHORT).show();
            }
        });
        Shop_Registration shop_registration=new Shop_Registration();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid=user.getUid();
        firebaseStorage=FirebaseFirestore.getInstance();
        DocumentReference documentReference=firebaseStorage.collection("users").document(uid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                userName.setText(value.getString("Name"));
                userEmail.setText(value.getString("Email"));
                userContact.setText(value.getString("Contact"));

            }
        });


    }
}