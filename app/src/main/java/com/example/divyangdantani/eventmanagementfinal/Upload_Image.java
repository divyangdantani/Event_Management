package com.example.divyangdantani.eventmanagementfinal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.IOException;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Upload_Image extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.imageName) EditText _imageName;
    @BindView(R.id.uploadImageView) ImageView _uploadImageView;
    @BindView(R.id.btn_browse) Button _btnBrowse;
    @BindView(R.id.btn_upload) Button _btnUpload;
    private static final int STORAGE_PERMISSION_CODE = 2342;
    private static final int IMG_REQUEST = 22;
    private Bitmap bitmap;
    private Uri filepath;
    private static String TAG = "Uploading";
    private static final String UPLOAD_URL = "http://192.168.43.46/android/UploadExample/upload.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        ButterKnife.bind(this);
        _btnBrowse.setOnClickListener(this);
        _btnUpload.setOnClickListener(this);
        requestStoragePermission();

    }

    private void requestStoragePermission()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        ActivityCompat.requestPermissions( this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == STORAGE_PERMISSION_CODE)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this,"Permission not Granted",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View v) {

        if(v == _btnBrowse){
           browseImage();
        }
       if(v == _btnUpload){
            uploadImage();
        }
       /* else {
            Toast.makeText(getApplicationContext(),"Button Not Working!!!",Toast.LENGTH_SHORT).show();
        }*/
    }

    public void browseImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image"),IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMG_REQUEST && resultCode == RESULT_OK && data!=null && data.getData()!=null)
        {
            filepath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
                _uploadImageView.setImageBitmap(bitmap);
                _uploadImageView.setVisibility(View.VISIBLE);
                _imageName.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getPath(Uri uri)
    {
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        cursor.moveToFirst();

        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":")+1);
        cursor.close();

        cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,MediaStore.Images.Media._ID + "= ?",new String[]{document_id},null
        );
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }


    private void uploadImage(){
        Log.d(TAG,"**********************************************************");
        String name = _imageName.getText().toString().trim();
        String path = getPath(filepath);
        Log.d(TAG,path);
        try {
            String uploadid = UUID.randomUUID().toString();
            Log.d(TAG,"********************************************************** 111");
            new MultipartUploadRequest(this,uploadid,UPLOAD_URL)
                    .addFileToUpload(path, "image")
                    .addParameter("name",name)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload();
            Log.d(TAG,"********************************************************** 222");
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
