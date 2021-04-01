package com.example111.foodshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class ShopMenuPage extends AppCompatActivity {
    private Button chooseFile;
    private  Button uploadedFile;
    private TextView title;
    private  Button uploadFile;
    private ImageView image;
    private ProgressBar progressBar;
    private final static  int pick_image_request=1;

    private Uri imageUri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private StorageTask mupload;
    public TextView shopkeeperName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_menu_page);

        chooseFile=findViewById(R.id.chooseFile);
         uploadFile = findViewById(R.id.uploadFile);
        title=findViewById(R.id.fileName);
        uploadedFile=findViewById(R.id.uploadView);
        image=findViewById(R.id.imageView);
        progressBar=findViewById(R.id.progressBar2);
        shopkeeperName=findViewById(R.id.shopkeeperName);

        storageReference= FirebaseStorage.getInstance().getReference("Upload");
        databaseReference= FirebaseDatabase.getInstance().getReference("Upload");

        uploadedFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageView();
            }
        });

        chooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooseUser();

            }


        });

        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mupload !=null && mupload.isInProgress()){
                    Toast.makeText(ShopMenuPage.this, "process in progress", Toast.LENGTH_SHORT).show();
                }
                else {
                    uploadfile();
                }

            }
        });
    }

    private void openImageView() {
        startActivity(new Intent(this,UploadedViewImage.class));
    }

    private String getStringExtension(Uri uri){
        ContentResolver CR= getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(CR.getType(uri));
    }

    private void uploadfile() {
        final String user=shopkeeperName.getText().toString();
        if(imageUri !=null){
            StorageReference filereferance=storageReference.child(System.currentTimeMillis()+"."+getStringExtension(imageUri));
            mupload=filereferance.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler=new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0);
                                }
                            }, 300);
                            Toast.makeText(ShopMenuPage.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                            //Upload upload=new Upload(title.getText().toString().trim(), taskSnapshot.getUploadSessionUri().toString());
                            String uploadId=databaseReference.push().getKey();

                            FirebaseDatabase.getInstance().getReference().child("Restaurant").child(user).push().setValue(imageUri);

                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                    double progress=(100.0*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                    progressBar.setProgress((int) progress );

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ShopMenuPage.this,e.getMessage(), Toast.LENGTH_LONG).show();

                }
            });

        }
        else{
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }

    }

    private void openFileChooseUser() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,pick_image_request);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==pick_image_request && resultCode==RESULT_OK && data !=null && data.getData()!=null){
            imageUri=data.getData();

            image.setImageURI(imageUri);

        }
    }
}