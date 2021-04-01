package com.example111.foodshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Customer_login extends AppCompatActivity {
    private TextView customer_email;
    private TextView customer_Password;
    private TextView register;
    private Button customerLogin;
    private FirebaseAuth auth;

    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);
        customer_email = findViewById(R.id.customer_email);
        customer_Password = findViewById(R.id.customer_Password);
        customerLogin = findViewById(R.id.customerLogin);
        register = findViewById(R.id.customer_register);
        auth = FirebaseAuth.getInstance();
        TextView customerforgotPassword;
        customerforgotPassword = findViewById(R.id._customerforgotPassword);


        pd=new ProgressDialog(this);



        //Forgot password

        customerforgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRcoverPassword();

            }
        });




        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Customer_login.this, Customer_registration.class));
            }
        });

        customerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email;
                String password;
                email = customer_email.getText().toString();
                password = customer_Password.getText().toString();
                if (password.length() < 6) {
                    Toast.makeText(Customer_login.this, "Invalid password", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Customer_login.this, "Enter the Email field", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Customer_login.this, "Enter the Password field", Toast.LENGTH_SHORT).show();
                } else {

                    login(email, password);


                }


            }


            private void login(String email, String password) {
                pd.setMessage("Logging in......");
                pd.show();

                final Task<AuthResult> authResultTask = auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        pd.dismiss();
                        //Toast.makeText(Customer_login.this, "Login successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Customer_login.this, Customer_Homepage.class));
                        finish();
                    }


                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(Customer_login.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();

                    }
                });


            }


        });



    }

    private void showRcoverPassword() {



        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Forgot Password");


        LinearLayout linearLayout;
        linearLayout = new LinearLayout(this);

        final EditText editText=new EditText(this);
        editText.setHint(" Enter the Email             ");
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        linearLayout.addView(editText);

        editText.setMinEms(10);
        linearLayout.setPadding(10,10,10,10);

        builder.setView(linearLayout);


        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String email=editText.getText().toString();
                showRecovery(email);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        builder.create().show();
    }

    private void showRecovery(String email) {
        pd.setMessage("Sending link......");
        pd.show();
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                pd.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(Customer_login.this, "Reset link sent on email check email", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(Customer_login.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {


            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                pd.dismiss();
                Toast.makeText(Customer_login.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}