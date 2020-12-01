package com.example.huarongdao;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter  {

    private Context context;
    private int width;
    private int column;
    private String[] name=new String[]{
      "测试关卡","横刀立马","指挥若定","将拥曹营","齐头并进","兵分三路","雨声淅沥"
    ,"左右布兵","桃花园中","一路进军","一路顺风","围而不歼","捷足先登","四面楚歌","兵临曹营","插翅难飞","守口如瓶"
    ,"层层设防","水泄不通","勇闯五关"};
    private LayoutInflater mlnflater;
    public MyAdapter(Context context )
    {
        //this.context=context;
        mlnflater=LayoutInflater.from(context);
        //this.width=width;
        //Log.i("TAG", "MyPicture_Adapter: "+width);
       // this.column=column;
        //Log.i("TAG", "MyPicture_Adapter: "+columnwidth);
    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int position) {
        return name[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       View view;
        if(convertView==null)
        {
            view=mlnflater.inflate(R.layout.select_list,parent,false);
        }
        else
        {
           view=convertView;
        }
     //   view.setLayoutParams(new LinearLayout.LayoutParams(200,526));
        TextView text=view.findViewById(R.id.textView);
        //text.setLayoutParams(new LinearLayout.LayoutParams(400,526));
        text.setText(name[position]);
        //imageButton.setLayoutParams(new LinearLayout.LayoutParams(wmwidth/3,526));
        return view;
    }

}
