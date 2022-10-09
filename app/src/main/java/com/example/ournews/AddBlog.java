package com.example.ournews;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Objects;

public class AddBlog extends AppCompatActivity {

    EditText Title, Des;
    Button Upload;
    ImageView Image;
    Uri image_uri = null;
    private  static final int Gallery_Image_Code = 100;
    private  static final int Camera_Image_Code = 101;
    FirebaseAuth auth ;
    ProgressDialog pd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blog);

        //ActionBar actionBar = getSupportActionBar();
        //noinspection ConstantConditions
        //actionBar.setTitle("Add Blog");
        //actionBar.setDisplayShowHomeEnabled(true);
        //actionBar.setDisplayHomeAsUpEnabled(true);

        Title = findViewById(R.id.ptitle);
        Des = findViewById(R.id.pdes);
        Upload = findViewById(R.id.pupload);
        Image = findViewById(R.id.imagep);
        auth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);

        Image.setOnClickListener(v -> imagePickDialog());
        Upload.setOnClickListener(v -> {
            String title = Title.getText().toString();
            String des = Des.getText().toString();

            if (TextUtils.isEmpty(title)){
                Title.setError("Title is required");
            }
            else if (TextUtils.isEmpty(des)){
                Des.setError("Description is required");
            }
            else {
                uploadData(title , Des);
            }
        });
    }


    private void uploadData(String title, EditText Des) {
        pd.setMessage("Publising post");
        pd.show();
        //here we will upload the data to firebase
        //firt we will get the time when user upload the post
        final String timeStamp = String.valueOf(System.currentTimeMillis());
        //here we will set the filepath of our image
        String filepath = "Posts/"+"post_"+timeStamp;

        if (Image.getDrawable() != null) {
            //getImage from Image view ;
            Bitmap bitmap = ((BitmapDrawable) Image.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] data = baos.toByteArray();

            // now we will creat the referense of storage in firebase as we have al ready added the linraries
            StorageReference reference = FirebaseStorage.getInstance().getReference().child(filepath);
            reference.putBytes(data)
                    .addOnSuccessListener(taskSnapshot -> {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        //here uri.Tast is not success to end the while loop so put not equal to sing !
                        //noinspection StatementWithEmptyBody
                        while (!uriTask.isSuccessful()) ;

                        String downloadUri = uriTask.getResult().toString();

                        if (uriTask.isSuccessful()) {
                            //uri is recieved post is publised to database
                            //now we will upload the daata to firebase database for
                            FirebaseUser user = auth.getCurrentUser();

                            HashMap<String, Object> hashMap = new HashMap<>();

                            hashMap.put("uid", Objects.requireNonNull(user).getUid());
                            hashMap.put("uEmail", user.getEmail());
                            hashMap.put("pId", timeStamp);
                            hashMap.put("pTitle", title);
                            hashMap.put("pImage", downloadUri);
                            hashMap.put("pDescription", Des);
                            hashMap.put("pTime", timeStamp);

                            //now we will pust the data to firebase database
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
                            ref.child(timeStamp).setValue(hashMap)
                                    .addOnSuccessListener(aVoid -> {
                                        pd.dismiss();
                                        Toast.makeText(AddBlog.this, "Post Published", Toast.LENGTH_SHORT).show();
                                        Title.setText("");
                                        Des.setText("");
                                        Image.setImageURI(null);
                                        image_uri = null;

                                        //when post is publised user must go to home activity means main dashboad
                                        startActivity(new Intent(AddBlog.this, FrontPage.class));


                                    }).addOnFailureListener(e -> {
                                pd.dismiss();
                                Toast.makeText(AddBlog.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });


                        }

                    }).addOnFailureListener(e -> {
                Toast.makeText(AddBlog.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                pd.dismiss();
            });
        }
    }

    private void imagePickDialog() {
        String[] options = {"Gallery","Camera"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image from");
        builder.setItems(options, (dialog, which) -> {
            if (which == 0){
                gallerypick();
            }
            if (which == 1){
                camerapick();
            }
        });
        builder.create().show();
    }

    private void camerapick() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "Temp Pick");
        contentValues.put(MediaStore.Images.Media.TITLE, "Temp desc");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        //noinspection deprecation
        startActivityForResult(intent, Camera_Image_Code);
    }

    private void gallerypick() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        //noinspection deprecation
        startActivityForResult(intent, Gallery_Image_Code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            //noinspection ConstantConditions
            if (resultCode == Camera_Image_Code){
                image_uri = data.getData();
                Image.setImageURI(image_uri);
            }
            //noinspection ConstantConditions
            if (resultCode == Gallery_Image_Code){
                image_uri = data.getData();
                Image.setImageURI(image_uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}