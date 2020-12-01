package com.example.huarongdao;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EndGame extends DialogFragment {
    private Context context;
    private int count;
    //View dialogView;
    NoticeDialgoListener listener;
    public EndGame(Context context,int count)
    {
        this.context=context;
        this.count=count;
    }

    public interface NoticeDialgoListener{
        public void onDialogNextButton( );
        public void onDialogBackButton( );
        public void onDialogBackmButton();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener=(NoticeDialgoListener) context;
        }catch (ClassCastException e)
        {
            // throw new ClassCastException()
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            LayoutInflater inflater=LayoutInflater.from(context);
            View  dialogView=inflater.inflate(R.layout.dialog,null);
            ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(400,200);
           dialogView.setLayoutParams(params);
            Button next=dialogView.findViewById(R.id.nextclock);
            Button back=dialogView.findViewById(R.id.backmenu);
            Button backm=dialogView.findViewById(R.id.backmain);
            TextView textView=dialogView.findViewById(R.id.infos);
            String infos= count +"步";
               textView.setText(infos);
            builder.setView(dialogView);
            next.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    listener.onDialogNextButton();
                }
            });
            back.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    listener.onDialogBackButton();
                }
            });
            backm.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    listener.onDialogBackmButton();
                }
            });
         return builder.create();
        }

    public void SetDate(int name,int clothcount)
    {
        SQLiteDatabase db = BeginGame.db_helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select checkid,steps from rank where checkid="+(name+1), null);
        if(cursor.getCount()==0)
        {
            ContentValues values = new ContentValues();
            values.put("checkid",name+1);
            values.put("steps",String.valueOf(clothcount));
            db.insert("rank","",values);
        }
        else
        {
            String string="0";
            while (cursor.moveToNext()) {
                string = cursor.getString(cursor.getColumnIndex("steps"));
            }
            if(Integer.parseInt(string)>clothcount)
            {
                ContentValues values=new ContentValues();
                values.put("steps",String.valueOf(clothcount));
                db.update("rank",values,"checkid=?",new String[]{String.valueOf((name+1))});
            }
            //db.update()
        }
        cursor.close();
    }

}
