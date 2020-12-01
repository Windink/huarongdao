package com.example.huarongdao;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import androidx.activity.ComponentActivity;
import androidx.appcompat.widget.SwitchCompat;

public class BeginGame extends ComponentActivity {

    public static float switchcker=0f;
    public static DataHelper db_helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.begin);

        db_helper=new DataHelper(this);
        Button button=findViewById(R.id.button);
        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BeginGame.this,SelectGame.class);
                startActivity(intent);
            }
        });
        Button button1=findViewById(R.id.button2);
        button1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Dialog builder= new Dialog(BeginGame.this);
                LayoutInflater inflater=LayoutInflater.from(BeginGame.this);
                View dialogView=inflater.inflate(R.layout.music_dialog,null);
               // ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(400,200);
                Switch plan=dialogView.findViewById(R.id.switch1);
                if (switchcker!=0)
                {
                    plan.setChecked(true);
                }
                else
                {
                    plan.setChecked(false);
                }
               plan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                   @Override
                   public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                       if(isChecked)
                       {
                           switchcker=0.5f;
                       }
                       else
                       {
                           switchcker=0;
                       }
                   }
               });
                builder.setContentView(dialogView);
                builder.show();
            }
        });
        Button button2=findViewById(R.id.button3);
        button2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BeginGame.this,RandGame.class);
                startActivity(intent);
            }
        });
    }
}
