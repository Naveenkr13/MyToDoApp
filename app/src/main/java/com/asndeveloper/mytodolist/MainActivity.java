package com.asndeveloper.mytodolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  DialogCloseListener {
    AdView adView;
    private DBHelper db;
    private AdapterForToDo adapter;
    private RecyclerView recyclerView_task;
    private FloatingActionButton floatButton;
    private List<Model_for_todo> tasklist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().hide();
        db=new DBHelper(this);
        db.OpenDatabase();
        adView=findViewById(R.id.adview);
        recyclerView_task=findViewById(R.id.RecylcerViewtask);
        floatButton=findViewById(R.id.floatbtn);


        //banner id
        MobileAds.initialize(this);
        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        //banner id
        //recycler
        recyclerView_task.setLayoutManager(new LinearLayoutManager(this));
        adapter=new AdapterForToDo(db,MainActivity.this);
        recyclerView_task.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new ItemTouchHelperRecycler(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView_task);
        //actual data get and store
        tasklist=db.getAllTask();
        Collections.reverse(tasklist);
        adapter.setTask(tasklist);

        // float btn listener
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.addNewTask_Instance().show(getSupportFragmentManager(),AddNewTask.TAG);
            }
        });


    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        tasklist=db.getAllTask();
        Collections.reverse(tasklist);
        adapter.setTask(tasklist);
        adapter.notifyDataSetChanged();
    }



}