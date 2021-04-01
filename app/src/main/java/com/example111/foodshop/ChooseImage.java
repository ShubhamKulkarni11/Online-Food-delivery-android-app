package com.example111.foodshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;
import java.util.UUID;

public class ChooseImage extends AppCompatActivity {
    private final static int PICK_IMAGE_REQUEST=1;
    private Button chooseFile;

    private TextView foodName;
    private TextView price;
    private TextView back;
    private ImageView mImageView;
    private Button Upload;
    private StorageTask mupload;
    private ProgressBar progressBar;

    private FirebaseStorage storage;
    private  StorageReference storageReference;
    private DatabaseReference mDatabase;


    private Uri mImageuri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_image);

        chooseFile=findViewById(R.id.chooseFile);
       Upload =findViewById(R.id.uploadItem);
        foodName=findViewById(R.id.FoodName);
        price=findViewById(R.id.foodPrice);
        mImageView=findViewById(R.id.choosedImage);
        back=findViewById(R.id.back);
        progressBar=findViewById(R.id.progressBar2);
       storage=FirebaseStorage.getInstance();
       storageReference=storage.getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();



        chooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();

            }
        });

        //back to home page
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseImage.this,ShopHomePage.class));
                finish();
            }
        });
        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Uploading imaage into firebase
                uploadImageFile();

            }
        });

    }

    private void uploadImageFile() {
        String mName=foodName.getText().toString();
        if(mImageuri!=null){
            // uploading data into database
            HashMap<String,Object> map=new HashMap<>();
            map.put(" Food Name",foodName.getText().toString());
            map.put("Price ",price.getText().toString());
            map.put("ImageUrl",mImageuri);
            FirebaseDatabase.getInstance().getReference().child("mName").setValue(map);


            final  ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setTitle("Uploading........");
            progressDialog.show();

            mDatabase.child(foodName.getText().toString()).setValue(foodName.getText().toString());
           mDatabase.child(foodName.getText().toString()).push().setValue(price.getText().toString());
           mDatabase.child(foodName.getText().toString()).push().setValue(mImageuri);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("message");

            myRef.setValue("Hello, World!");

            StorageReference reference=storageReference.child("images/"+ UUID.randomUUID().toString());
            reference.putFile(mImageuri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            progressDialog.dismiss();
                            Toast.makeText(ChooseImage.this, "Image uploaded", Toast.LENGTH_SHORT).show();






                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    progressDialog.show();

                    double progress=(100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                    progressDialog.setMessage("Uploaded "+(int)progress+"%");


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }



    }

    private String getStringExtension(Uri uri){
        ContentResolver CR= getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(CR.getType(uri));
    }

    // mUpload method for uploading Image into Firebase





    //choosing image from device
    private void openFileChooser() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST  && resultCode == RESULT_OK && data !=null && data.getData() !=null){
            mImageuri=data.getData();
            Glide.with(this).load(mImageuri).into(mImageView);

        }

    }
}