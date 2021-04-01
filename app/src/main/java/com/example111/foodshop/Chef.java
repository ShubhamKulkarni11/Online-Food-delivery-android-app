package com.example111.foodshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class Chef extends AppCompatActivity {
    private ImageView sImage;
    private TextView sFoodName;
    private TextView sPrice;
    private TextView sDescription;
    private final static int pick_image_request = 1;
    private Uri sImageUri;
    String UrlImage;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef);

        sImage = findViewById(R.id.chefImage);
        sFoodName = findViewById(R.id.foodItemName);
        sPrice = findViewById(R.id.foodItemPrice);
        sDescription = findViewById(R.id.foodItemDescription);
        Button selectImage = findViewById(R.id.selectImage);
        //Button uploadItems = findViewById(R.id.uploadItem);


        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageFromDevice();
            }
        });




    }

    private void uploadFoodItem() {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Food Item");
        progressDialog.setMessage("Uploading......");
        progressDialog.show();

        StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("FoodItems").child(sImageUri.getLastPathSegment());
        storageReference.putFile(sImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                    while(!uriTask.isComplete());
                    Uri urlImage=uriTask.getResult();



                UrlImage= urlImage.toString();
                uploadItemDetail();
                progressDialog.dismiss();
                Toast.makeText(Chef.this, "Food Item Uploaded Successfully", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void selectImageFromDevice() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, pick_image_request);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == pick_image_request && resultCode == RESULT_OK && data != null && data.getData() != null) {
            sImageUri = data.getData();

            sImage.setImageURI(sImageUri);

        }
    }

    public  void uploadItemDetail(){


        FirebaseUser user;
        user=FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        FoodData foodData=new FoodData(
                sFoodName.getText().toString().toLowerCase(),
                sDescription.getText().toString(),
                sPrice.getText().toString(),
                UrlImage
        );




        String dateTime= DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        final Task<Void> foodItems1 = FirebaseDatabase.getInstance().getReference().child("FoodItems").child(dateTime).setValue(foodData);

        final Task<Void> foodItems = FirebaseDatabase.getInstance().getReference().child("FoodItems").child(uid).child(dateTime).setValue(foodData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Chef.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                
            }
        });

        

    }


    public void uploadFoodItem(View view) {
        uploadFoodItem();

    }
}