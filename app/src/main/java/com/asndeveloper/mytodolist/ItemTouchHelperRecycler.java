package com.asndeveloper.mytodolist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class ItemTouchHelperRecycler extends  ItemTouchHelper.SimpleCallback {
    private AdapterForToDo adapter;

    //constructor and Adpater call inside this constr

    public ItemTouchHelperRecycler(AdapterForToDo adapter ) {
        super(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT);
        this.adapter=adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }


    //condition for swipe left and right and delete icon
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
    final int postion=viewHolder.getAdapterPosition();
    if (direction==ItemTouchHelper.LEFT){
        AlertDialog.Builder builder=new AlertDialog.Builder(adapter.getContext());

          builder.setTitle("Delete Task");
          builder.setMessage("Task Completed ! Are You Sure to delete this task ?");
          builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                  adapter.deleteItem(postion);
              }
          });
          builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
             adapter.notifyItemChanged(viewHolder.getAdapterPosition());
              }
          });

          AlertDialog dialog=builder.create();
          dialog.show();



    }else {
        adapter.editItem(postion);
    }
    }



    //Icon set for left and right swipe


    @Override
    public void onChildDraw( Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
         Drawable icon;
        ColorDrawable background;
        View itemView=viewHolder.itemView;
        int backGroundCornerOff=20;
       //// //right swap
        if (dX>0){
           icon= ContextCompat.getDrawable(adapter.getContext(),R.drawable.edit);
           background=new ColorDrawable(ContextCompat.getColor(adapter.getContext(),R.color.btn));
        }else {
            icon=ContextCompat.getDrawable(adapter.getContext(),R.drawable.delete);
            background=new ColorDrawable(Color.RED);
        }
        int iconMargin=(itemView.getHeight()-icon.getIntrinsicWidth())/2;
        int iconTop=itemView.getTop()+(itemView.getHeight()-icon.getIntrinsicHeight())/2;
        int iconBottom=iconTop+icon.getIntrinsicHeight();
        //swapping right
        if (dX>0){
            int iconLeft=itemView.getLeft()+iconMargin;
            int iconRight=itemView.getLeft()+iconMargin+icon.getIntrinsicWidth();
            icon.setBounds(iconLeft,iconTop,iconRight,iconBottom);
            background.setBounds(itemView.getLeft(),itemView.getTop(),itemView.getLeft()+((int)dX)+backGroundCornerOff,
                    itemView.getBottom());
        } //swipping to left
        else if (dX<0) {
            int iconLeft=itemView.getRight()-iconMargin-icon.getIntrinsicWidth();
            int iconRight=itemView.getRight()-iconMargin;
            icon.setBounds(iconLeft,iconTop,iconRight,iconBottom);
            background.setBounds(itemView.getRight()+((int)dX)-backGroundCornerOff,
                    itemView.getTop(),itemView.getRight(),itemView.getBottom());
        }
        else {
            background.setBounds(0,0,0,0);
        }
        background.draw(c);
        icon.draw(c);
    }
}
