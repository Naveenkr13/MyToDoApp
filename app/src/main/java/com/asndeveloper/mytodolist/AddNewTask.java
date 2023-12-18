package com.asndeveloper.mytodolist;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTask extends BottomSheetDialogFragment {
    //this is for bottom task that add new task refer to new task add.xml


    // tag helps to call in adpaterclass and in Mainactivity
    public static final String TAG="ActionButtonDialog";

    private EditText NewTextTask_Edittext;
    private Button SaveButton;
    private DBHelper db;

    //consturctor create helps to call
    public static AddNewTask addNewTask_Instance(){
        return new AddNewTask();
    }
    //


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL,R.style.DilogStyle);
    }

    // put layout new_task.xml here
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.new_task_add,container,false);
       //dialogBox Adujust automatic
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }


    // here id of Edittext and BUtton insdie of newTaskadd.xml define
    @Override
    public void onViewCreated( View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NewTextTask_Edittext=getView().findViewById(R.id.ed_add_new_task);
        SaveButton=getView().findViewById(R.id.new_task_savebutton);
        //DBHalper class Call
        db=new DBHelper(getActivity());
        db.OpenDatabase();

        //task is goint to save or being updated
        boolean isUpdate=false;
        final Bundle bundle=getArguments();
        //get data from adaptor and pass it
         if (bundle!=null){
             isUpdate=true;
             String task=bundle.getString("task");
            NewTextTask_Edittext.setText(task);
            assert task!=null;
            if (task.length()>0) {
                SaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.btn));
            }
             db=new DBHelper(getActivity());
             db.OpenDatabase();
         }

         //Listener for Edt TextTask and Save Button
        //check new task is empty or not then text and button color also change
        NewTextTask_Edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               if (s.toString().equals("")){
                   SaveButton.setEnabled(false);
                   SaveButton.setTextColor(Color.GRAY);
               }else {
                   SaveButton.setEnabled(true);
                   SaveButton.setTextColor(ContextCompat.getColor(getContext(),R.color.btn));
               }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

         //SavedButton pe ClickListener

        final boolean finalIsUpdate=isUpdate;
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=NewTextTask_Edittext.getText().toString();
                if (finalIsUpdate){
                    db.UpdateTask(bundle.getInt("id"),text);
                }else {
                    Model_for_todo task= new Model_for_todo();
                    task.setTasked(text);
                    task.setCheck_status(1);
                    db.insertTask(task);
                }
                dismiss();
            }
        });
    }

    ///
       @Override
        public void onDismiss(@NonNull DialogInterface dialog){
            Activity activity=getActivity();
            if (activity instanceof DialogCloseListener){
                ((DialogCloseListener) activity).handleDialogClose(dialog);
                }
            }
        }



