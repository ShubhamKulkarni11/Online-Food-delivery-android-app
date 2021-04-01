package com.example111.foodshop;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;

public class Customer_registration extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1;


    private TextView customerName;
    private TextView customerContact;
    private TextView customerEmail;
    private  TextView customerPassword;
    private FirebaseAuth auth;
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private ProgressDialog pd;
    String name;
    String contact;
    String semail;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);

        customerName=findViewById(R.id.customerName);
        customerContact=findViewById(R.id.customerContact);
        customerEmail=findViewById(R.id.customerEmail);
        customerPassword=findViewById(R.id.customerPassword);
        Button customerRegistration = findViewById(R.id.customerRegistration);
        Button btnGoogle = findViewById(R.id.btnGoogle);
        pd=new ProgressDialog(this);




        auth=FirebaseAuth.getInstance();

        customerRegistration.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                name=customerName.getText().toString();
               contact=customerContact.getText().toString();
               semail=customerEmail.getText().toString();
               String password=customerPassword.getText().toString();

               if(TextUtils.isEmpty(name) || TextUtils.isEmpty(password) || TextUtils.isEmpty(contact) || TextUtils.isEmpty(semail)){
                   Toast.makeText(Customer_registration.this, "Every field required", Toast.LENGTH_SHORT).show();
               }

               else if(contact.length() !=10){
                   Toast.makeText(Customer_registration.this, "Enter valid contact Number", Toast.LENGTH_SHORT).show();
               }
               else if(password.length()<6){
                   Toast.makeText(Customer_registration.this, "Enter strong password", Toast.LENGTH_SHORT).show();
               }
               else{



                   registration(semail,password);
               }

            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();

            }
        });



    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Toast.makeText(this, "Sign In", Toast.LENGTH_SHORT).show();
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {

                Toast.makeText(this, "Hello" , Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            assert user != null;
                            Toast.makeText(Customer_registration.this,user.getEmail()+" "+user.getDisplayName(), Toast.LENGTH_LONG).show();
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(Customer_registration.this, "Task unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                        updateUI();

                        // ...
                    }
                });
    }

    public void updateUI() {
        Intent intent=new Intent(Customer_registration.this,Customer_Homepage.class);

        startActivity(intent);
    }


    private void registration(String email, String password) {


        pd.show();
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Objects.requireNonNull(auth.getCurrentUser()).sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid=user.getUid();
                            pd.dismiss();
                            if(task.isSuccessful()){
                                Toast.makeText(Customer_registration.this, "Link send on email please verify Email", Toast.LENGTH_SHORT).show();

                                HashMap<String,Object> map=new HashMap<>();
                                map.put("Name",name);
                                map.put("Email",semail);
                                map.put("Contact",contact);
                                FirebaseDatabase.getInstance().getReference().child("Customer").setValue(map);

                                FirebaseFirestore firebaseStorage= FirebaseFirestore.getInstance();
                                DocumentReference documentReference=firebaseStorage.collection("users").document(uid);
                                documentReference.set(map);


                            }
                            else{
                                Toast.makeText(Customer_registration.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                    startActivity(new Intent(Customer_registration.this,Customer_login.class));
                }







            }

        });

    }
}