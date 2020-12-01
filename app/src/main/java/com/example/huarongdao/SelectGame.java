package com.example.huarongdao;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.Nullable;

public class SelectGame extends Activity implements GridView.OnItemClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_game);

        MyAdapter adapter=new MyAdapter(this);
        GridView gridView=findViewById(R.id.Cheacklager);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(this,StartGame.class);
        Adapter adapter=parent.getAdapter();
        intent.putExtra("checknum",position);
        intent.putExtra("checkname",adapter.getItem(position).toString());
        startActivity(intent);
    }
}
