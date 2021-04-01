package com.example111.foodshop;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.ExceptionCatchingInputStream;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import java.text.DateFormat;
import java.util.Calendar;
public class foodItemDescription extends AppCompatActivity {
    private ImageView img;
    private TextView name, price, description, quantity,total;
    private Button buyNow, addToCart;
    String imageName;

    DatabaseReference reference;
    private cartItems cartItems;
    int num;
    int num2;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item_description);

        img = findViewById(R.id.cfoodImage);
        name = findViewById(R.id.cFoodName);
        price = findViewById(R.id.cFoodPrice);
        description = findViewById(R.id.cFooddescri);
        quantity = findViewById(R.id.cFoodQuantity);
        buyNow = findViewById(R.id.buyNow);
        addToCart = findViewById(R.id.addToCart);
        total=findViewById(R.id.totalPrice);



        // add to cart
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadItemDetail();


            }
        });


        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(foodItemDescription.this, BuyFoodItem.class));
            }
        });

        // reference= FirebaseDatabase.getInstance().getReference().child("FoodItems");

        //String Image=getIntent().getStringExtra("Image");

        Picasso.get().load(getIntent().getStringExtra("Image")).into(img);
        //img.setImageResource(getIntent().getIntExtra("Image",0));
        name.setText(getIntent().getStringExtra("FoodName"));
        description.setText(getIntent().getStringExtra("FoodDesci"));
        String itemprice= getIntent().getStringExtra("FoodPrice");
        price.setText("Rs " + getIntent().getStringExtra("FoodPrice"));
        num=Integer.parseInt(itemprice);
        quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String quant=quantity.getText().toString();

                try {
                    num2 = Integer.parseInt(quant);

                }
                finally {
                    int foodtotal=num*num2;
                    String sum=String.valueOf(foodtotal);
                    total.setText(sum);
                }

            }
        });


    }

    public void uploadItemDetail() {


        FirebaseUser user;
        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        cartItems cartItems = new cartItems(
                name.getText().toString().toLowerCase(),
                description.getText().toString(),
                price.getText().toString(), imageName, quantity.getText().toString(),total.getText().toString()


        );


        String dateTime = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        final Task<Void> Customer = FirebaseDatabase.getInstance().getReference().child("CustomerCart").child(uid).child(dateTime).setValue(cartItems).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(foodItemDescription.this, "Added to cart", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(foodItemDescription.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}