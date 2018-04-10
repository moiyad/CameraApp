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

import com.example.pc.cameraapp.models.Event;

import java.io.File;
import java.util.LinkedList;

import io.realm.Realm;
import io.realm.RealmResults;

public class EventActivity extends AppCompatActivity {
    public static final int REQUEST_CAPTURE = 1;
    public static final int REQUEST_RECORD = 100;


    Realm realm;

    Button imageB, videoB;
    EditText eventName;
    private int imageCounter = 0, videoCounter = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        realm  = Realm.getDefaultInstance();

        RealmResults<Event> result = realm.where(Event.class).findAll();
        Log.d("moiyad", "execute: "+result.size());

        imageB = findViewById(R.id.take_picture);
        videoB = findViewById(R.id.take_video);
        eventName = findViewById(R.id.editText);



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

        return fileUrl;
    }

    public void deleteFromDB(final int i) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Event> result = realm.where(Event.class).findAll();
                Log.d("moiyad", "execute: "+result.size());

                if (i == 0)
                    result.deleteAllFromRealm();
                else {
                    Event e = result.get(i);
                    e.deleteFromRealm();
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d("moiyad", "onSuccess: Delete ");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Log.d("moiyad", "onError: Delete " + error);
            }
        });


    }
}
