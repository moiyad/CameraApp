package com.example.pc.cameraapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.CharacterPickerDialog;
import android.view.View;

import com.example.pc.cameraapp.models.Event;

import java.util.List;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private List<Event> result;
    private Realm realm;
    RecyclerViewAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm  = Realm.getDefaultInstance();

        result = realm.where(Event.class).findAll();
        floatingActionButton =findViewById(R.id.floatingActionButton);


        recyclerView = findViewById(R.id.list_view);
        myAdapter= new RecyclerViewAdapter(this,result);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setAdapter(myAdapter);

        myAdapter.notifyDataSetChanged();


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NewEvent.class);
                startActivity(intent);
            }
        });
    }

}
