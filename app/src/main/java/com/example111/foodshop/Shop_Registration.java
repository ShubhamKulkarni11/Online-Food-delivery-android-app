package com.example111.foodshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class Shop_Registration extends AppCompatActivity {
    public TextView shopkeeperName;
    private TextView shopkeeperContact;
    private TextView shopkeeperEmail;
    private  TextView shopkeeperPassword;
    private Button next;
    FirebaseAuth auth;
    private ProgressDialog pd;
    public  String name;
    String contact;
    FirebaseFirestore firebaseStorage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop__registration);

        shopkeeperName=findViewById(R.id.shopkeeperName);
        shopkeeperContact=findViewById(R.id.shopkeeperContact);
        shopkeeperEmail=findViewById(R.id.shopkeeperEmail);
        shopkeeperPassword=findViewById(R.id.customer_Password);
        next=findViewById(R.id.customerRegistration);
        auth=FirebaseAuth.getInstance();
        pd=new ProgressDialog(this);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                name=shopkeeperName.getText().toString();
                 contact=shopkeeperContact.getText().toString();
                String password=shopkeeperPassword.getText().toString();
                String email=shopkeeperEmail.getText().toString();
                firebaseStorage=FirebaseFirestore.getInstance();


                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(contact) || TextUtils.isEmpty(name)){
                    Toast.makeText(Shop_Registration.this, "every field required", Toast.LENGTH_SHORT).show();
                }
                else if(password.length()<6){
                    Toast.makeText(Shop_Registration.this, "Enter the strong password", Toast.LENGTH_SHORT).show();
                }
                else{


                   HashMap<String,Object> map=new HashMap<>();
                    map.put("Name",name);
                    map.put("Email",email);
                    map.put("Contact",contact);
                    FirebaseDatabase.getInstance().getReference().child("Restaurant").setValue(map);
                    FirebaseFirestore db=FirebaseFirestore.getInstance();
                    Map<String,Object> map1= new HashMap<>();
                    map1.put("Name",name);
                    map1.put("Email",email);
                    map1.put("Contact",contact);
                    db.collection("Restaurant").document(name).set(map1);



                    registration(email,password);
                }
            }
        });



    }

    private void registration(String email, String password) {
        pd.setMessage("Registering.....");
        pd.show();
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                pd.dismiss();
                if(task.isSuccessful()) {

                    FirebaseUser user;
                    user=FirebaseAuth.getInstance().getCurrentUser();
                    String email=user.getEmail();
                    String uid=user.getUid();

                    HashMap<String,Object> map=new HashMap<>();
                    map.put("Name",name);
                    map.put("Email",email);
                    map.put("Contact",contact);
                    map.put("Uid",uid);
                    FirebaseDatabase.getInstance().getReference().child("ShopOwners").child(uid).setValue(map);
                    DocumentReference documentReference=firebaseStorage.collection("users").document(uid);
                    documentReference.set(map);

                   auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful()){
                               Toast.makeText(Shop_Registration.this, "Link send on email Please Verify your Email", Toast.LENGTH_LONG).show();
                           }
                           else{
                               Toast.makeText(Shop_Registration.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                           }

                       }
                   });


                    startActivity(new Intent(Shop_Registration.this, registration_page2.class));
                    finish();
                }
                else{

                    Toast.makeText(Shop_Registration.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        });



    }
}