package com.example.asus.cameraapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;

import java.io.File;

public class MainActivity extends Activity {
    ImageButton cameraButton, galleryButton;
    ImageButton tempButton;

    static final int CAM_REQUEST = 1;
    static int countImage = 0;

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_main);
        cameraButton = (ImageButton) findViewById(R.id.cameraButton);
        galleryButton = (ImageButton) findViewById(R.id.galleryButton);
        tempButton = (ImageButton) findViewById(R.id.tempButton);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = getFile();
                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(camera_intent, CAM_REQUEST);
                addImageGallery(file);
            }
        });
        galleryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
                startActivity(intent);
            }
        });
        tempButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditPhotoActivity.class);
                startActivity(intent);
            }
        });
    }

    private File getFile() {
        File folder = new File("sdcard/camera_app");
            if(!folder.exists()) {
                folder.mkdir();
            }
        File image_file = new File(folder,"cam_image"+countImage+".jpg");
        return image_file;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String path = "sdcard/camera_app/cam_image"+countImage+".jpg";
    }

    private void addImageGallery( File file ) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        countImage++;
    }
}
