package com.example.huarongdao;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;

public class RandGame extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rand_list);

        DataAdapter adapter=new DataAdapter(this);
        ListView listView=findViewById(R.id.randlist);
        listView.setAdapter(adapter);

        Button back=findViewById(R.id.backs);
        back.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                RandGame.this.finish();
            }
        });
    }
}
