package com.example.huarongdao;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DataAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> checkid;
    private ArrayList<String> steps;
    private LayoutInflater inflater;
    public DataAdapter(Context context)
    {
        this.context=context;
        inflater=LayoutInflater.from(context);
        checkid=new ArrayList<>();
        checkid.add("关卡号");
        steps=new ArrayList<>();
        steps.add("最佳步数");
        loadData();
    }
    public void loadData()
    {
        SQLiteDatabase db = BeginGame.db_helper.getReadableDatabase();
        Cursor cursor = //db.rawQuery("select * from rank order by checkid",null);
        db.query("rank", new String[]{"checkid", "steps"},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("checkid"));
            String name = cursor.getString(cursor.getColumnIndex("steps"));
            checkid.add(id);
            steps.add(name);

            System.out.println("book_id: " + id + ", name: " + name);

        }
        cursor.close();
    }



    @Override
    public int getCount() {
        return checkid.size();
    }

    @Override
    public Object getItem(int position) {
        return checkid.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView==null) {
            view = inflater.inflate(R.layout.rand_dialog, parent, false);
        }
        else {
            view=convertView;
        }
        TextView textView=view.findViewById(R.id.textView4);
        textView.setText(checkid.get(position));
        System.out.println(checkid.get(position));
        TextView textView1=view.findViewById(R.id.textView6);
        textView1.setText(steps.get(position));
        return view;
    }
}
