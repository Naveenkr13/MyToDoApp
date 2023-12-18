package com.asndeveloper.mytodolist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterForToDo extends RecyclerView.Adapter<AdapterForToDo.ViewHolder> {
    private List<Model_for_todo> Model_List_for_todo;  //access model class here
    private MainActivity activity;     //help to call from mainactivity

    private DBHelper db;


    //constructor for calling
    public AdapterForToDo(DBHelper db,MainActivity activity) {
        this.db=db;
        this.activity=activity;

    }

     // Layout and its id call here layout- task_list_show
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(parent.getContext()).
               inflate(R.layout.task_list_show,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterForToDo.ViewHolder holder, int position)
    {
        db.OpenDatabase();          //


       // from app interface/holder you get the postion and in to do list task you search for
       //the item and work on it
        final Model_for_todo item=Model_List_for_todo.get(position);         //current postion get
        holder.task.setText(item.getTasked());
        holder.task.setChecked(toBoolean(item.getCheck_status()));
         //after DBHelper call

        // status button checked or not save in db means on Checkbox and their text
        // tick or not tick on checkbox
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                  db.UpdateStatus(item.getId(),1);
                }else {
                    db.UpdateStatus(item.getId(),0);
                }
            }

        });


    }

    // create method for change boolean value into integer
    private boolean toBoolean(int n){
        return n!=0;
    }



    //step 2
    // extends ViewHolder with RecleryView.Viewholder and
    // it automatic call its constructor
    // checked or not checked  button id find here
    public static  class ViewHolder extends RecyclerView.ViewHolder{
        //checkbox and it also considered text so i write it like this
        CheckBox task;
        //constructor and inside it hold the id of Checked box
        public ViewHolder(View view) {
            super(view);
            task=view.findViewById(R.id.checkbox);
        }
    }
    @Override
    public int getItemCount() {
        //how many item will  print decide by recycler view

//
        return Model_List_for_todo != null ? Model_List_for_todo.size() : 0;
    }
   //
    //setTask
    public void setTask(List<Model_for_todo>Model_List_for_todo){
        this.Model_List_for_todo=Model_List_for_todo;
        notifyDataSetChanged();
    }
  // return actitiy for itemtouc
    public Context getContext(){
        return activity;
    }

    //delete item for swap right in ItemTouch
    public void deleteItem(int position){
        Model_for_todo item=Model_List_for_todo.get(position);
        db.DeleteTask(item.getId());
        Model_List_for_todo.remove(position);
        notifyDataSetChanged();
    }

    // edit text when swipe left
    public void editItem(int position){
        Model_for_todo item=Model_List_for_todo.get(position);
        Bundle bundle=new Bundle();
        bundle.putInt("id",item.getId());
        bundle.putString("task",item.getTasked());
      AddNewTask fragment=new AddNewTask();
      fragment.setArguments(bundle);
      fragment.show(activity.getSupportFragmentManager(),AddNewTask.TAG);
    }

}
