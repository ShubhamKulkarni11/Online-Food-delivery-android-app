package com.example111.foodshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class registration_page2 extends AppCompatActivity {
    private TextView shopName;
    private TextView shopAddress;
    private TextView shopDescription;
    public TextView shopkeeperName;
    public Button submit;
    public TextView owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page2);


        shopAddress=findViewById(R.id.shopAddress);
        shopName =findViewById(R.id.shopName);
        shopDescription=findViewById(R.id.shopDescription);
        submit=findViewById(R.id.submitData);

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Submiting");
      

       submit.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {

               FirebaseUser user;
               user= FirebaseAuth.getInstance().getCurrentUser();
               String uid = user.getUid();
               Shop_Registration shop_registration=new Shop_Registration();


               progressDialog.show();

               String shopName= registration_page2.this.shopName.getText().toString();
               String shop_address=shopAddress.getText().toString();
               String shop_description=shopDescription.getText().toString();


               FirebaseFirestore db=FirebaseFirestore.getInstance();
               Map<String,Object> map1= new HashMap<>();
               map1.put("Shop  Name",shopName);
               map1.put("Shop Address",shop_address);
               map1.put("Description ",shop_description);
               db.collection("Restaurant").document("Name").set(map1,SetOptions.merge());

               HashMap<String,Object> map=new HashMap<>();
               map.put("Shop  Name",shopName);
               map.put("Shop Address",shop_address);
               map.put("Description ",shop_description);
               FirebaseDatabase.getInstance().getReference().child("Restaurant").updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {

                       progressDialog.dismiss();
                       Toast.makeText(registration_page2.this, "Registration successful", Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(registration_page2.this,shop_login.class));
                   }
               });



           }
       });









    }
}