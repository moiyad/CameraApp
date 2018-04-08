package com.example.pc.cameraapp;

import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.util.LinkedList;

public class EventActivity extends AppCompatActivity {
    public static final int REQUEST_CAPTURE = 1;
    public static final int REQUEST_RECORD = 100;

    LinkedList<String> stringLinkedList = new LinkedList<String>();


    Button imageB, videoB;
    EditText eventName;
    private int imageCounter = 0, videoCounter = 0;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Intent intent= getIntent();
        String event = intent.getExtras().getString("name");

        imageB = findViewById(R.id.take_picture);
        videoB = findViewById(R.id.take_video);
        eventName = findViewById(R.id.editText);

        Log.d("moiyad", "onCreate: "+event);

        eventName.setText(event);



        videoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                startActivityForResult(intent, REQUEST_RECORD);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, filePath(eventName.getText().toString(), false));


            }
        });


        imageB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CAPTURE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        data.putExtra(MediaStore.EXTRA_OUTPUT, filePath(eventName.getText().toString(), true));

    }

    // True == image , False == video
    public Uri filePath(String event, boolean type) {


        Uri fileUrl;
        File folder = new File("sdcard/" + event);

        if (!folder.exists()) {
            folder.mkdir();
        }
        if (type) {
            File image = new File(folder, event + "_image_" + imageCounter + ".jpg");
            fileUrl = Uri.fromFile(image);
            imageCounter++;
        } else {
            File video = new File(folder, "videos_" + videoCounter + ".mp4");
            fileUrl = Uri.fromFile(video);
            videoCounter++;
        }
        Log.d("moiyad", "onClick: folder " + fileUrl);

        return fileUrl;
    }
}
