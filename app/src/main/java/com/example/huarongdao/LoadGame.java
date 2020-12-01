package com.example.huarongdao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class LoadGame {

    private Context context;
    private int checkmun;
    private ArrayList<Bitmap> caocaoPicture;
    private ArrayList<Bitmap> bingPicture;
    private ArrayList<Bitmap> gyPicture;
    private ArrayList<Bitmap> mcPicture;
    private ArrayList<Bitmap> zyPicture;
    private ArrayList<Bitmap> zfPicture;
    private ArrayList<Bitmap> hzPicture;
    private ArrayList<Bitmap> picture;
    private Sizes screenSize;//屏幕大小
    private Sizes layerSize;
    private int[] picthre={R.drawable.caocao,R.drawable.guangyu1,R.drawable.huangzhong1,R.drawable.machao1,
            R.drawable.zhaoyun1,R.drawable.zhangfei1};
    private int[] picthres={R.drawable.bing,R.drawable.guangyu2,R.drawable.huangzhong2,R.drawable.machao2,
            R.drawable.zhaoyun2,R.drawable.zhangfei2};

    public ArrayList<Bitmap> getPicture() {
        return picture;
    }

    public Sizes getLayerSize() {
        return layerSize;
    }

    public LoadGame(Context context, int checkmun)
    {
        this.context=context;
        this.checkmun=checkmun;
    }

    private void InitializPicture()
    {
        caocaoPicture=new ArrayList<>();
        bingPicture=new ArrayList<>();
        gyPicture=new ArrayList<>();
        mcPicture=new ArrayList<>();
        zyPicture=new ArrayList<>();
        zfPicture=new ArrayList<>();
        hzPicture=new ArrayList<>();
         for (int i=0;i<6;i++)
         {
           Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(),picthre[i]);
           Bitmap bitmap1=BitmapFactory.decodeResource(context.getResources(),picthres[i]);
             if (i==0)
             {
                 caocaoPicture.addAll(CutPicure(bitmap,2,2));
                 bingPicture.addAll(CutPicure(bitmap1,1,1));
             }
             else if (i==1)
             {
                 gyPicture.addAll(CutPicure(bitmap, 2, 1));
                 gyPicture.addAll(CutPicure(bitmap1, 1, 2));
             }
             else if(i==2)
             {
                 hzPicture.addAll(CutPicure(bitmap, 2, 1));
                 hzPicture.addAll(CutPicure(bitmap1, 1, 2));
             }
             else if(i==3)
             {
                 mcPicture.addAll(CutPicure(bitmap, 2, 1));
                 mcPicture.addAll(CutPicure(bitmap1, 1, 2));
             }
             else if(i==4)
             {
                 zyPicture.addAll(CutPicure(bitmap, 2, 1));
                 zyPicture.addAll(CutPicure(bitmap1, 1, 2));
             }
             else if(i==5)
             {
                 zfPicture.addAll(CutPicure(bitmap, 2, 1));
                 zfPicture.addAll(CutPicure(bitmap1, 1, 2));
             }
    }
        AddPicture();
    }
    private ArrayList<Bitmap> CutPicure(Bitmap bitmap,int row,int col)
    {
        bitmap=Bitmap.createScaledBitmap(bitmap,layerSize.width*row, layerSize.height*col,true);
        ArrayList<Bitmap> bitmapTiles=new ArrayList<>();
        for(int j=0;j<col;j++)
        {
            for(int i=0;i<row;i++)
            {
                bitmapTiles.add(Bitmap.createBitmap(bitmap,
                        i*layerSize.width,
                        j*layerSize.height,
                        layerSize.width,layerSize.height));
            }
        }
          return bitmapTiles;
    }
    private void AddPicture()
    {
        String[] string = context.getResources().getStringArray(R.array.CheackPoint);
        picture=new ArrayList<>();
        int caonum = 0;//曹操初始化图片下标
        int huangnum =0;//黄下标初始由开始
        int zhaonum =0;//zhao下标初始由开始
        int manum =0;//ma下标初始由开始
        int zhangnum =0;//zhang下标初始由开始
        int guannum = 0;//关羽下标

        for (int i = 0; i < 20; i++) {
            int num = Integer.parseInt(String.valueOf(string[checkmun].charAt(i)));
            if (num == 0) {
                picture.add(caocaoPicture.get(caonum));
                caonum++;
            } else if (num == 3) {
                if(i+1<20&&Integer.parseInt(String.valueOf(string[checkmun].charAt(i+1)))!=3&&guannum==0)
                {
                    guannum=2;
                }
                picture.add(gyPicture.get(guannum));
                guannum++;
            } else if (num == 6) {
                picture.add(bingPicture.get(0));
            } else if (num == 7) {
                picture.add(null);
            }
            else if (num == 1) {
                if(i+1<20&&Integer.parseInt(String.valueOf(string[checkmun].charAt(i+1)))!=1&&huangnum==0)
                {
                    huangnum=2;
                }
                picture.add(hzPicture.get(huangnum));
                huangnum++;
            }
            else if (num == 2) {
                if(i+1<20&&Integer.parseInt(String.valueOf(string[checkmun].charAt(i+1)))!=2&&manum==0)
                {
                    manum=2;
                }
                picture.add(mcPicture.get(manum));
                manum++;
            }
            else if (num == 4) {
                if(i+1<20&&Integer.parseInt(String.valueOf(string[checkmun].charAt(i+1)))!=4&&zhangnum==0)
                {
                    zhangnum=2;
                }
                picture.add(zfPicture.get(zhangnum));
                zhangnum++;
            }
            else if (num == 5) {
                if(i+1<20&&Integer.parseInt(String.valueOf(string[checkmun].charAt(i+1)))!=5&&zhaonum==0)
                {
                    zhaonum=2;
                }
                picture.add(zyPicture.get(zhaonum));
                zhaonum++;
            }
//
        }
    }
    public Bitmap InitializGame()
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        int mun=(int) (20 * scale + 0.5f);
         Point point= new Point();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        assert wm != null;
        wm.getDefaultDisplay().getSize(point);
        screenSize=new Sizes(point.x,point.y);
        layerSize=new Sizes((point.x-mun)/4,(point.y-mun)/4*3/5);
        Bitmap bitmap=Bitmap.createBitmap(point.x+20,point.y/4*3,Bitmap.Config.ARGB_8888);
        Paint paint=new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        Canvas canvas=new Canvas(bitmap);
        canvas.drawRect(mun/4f,0,bitmap.getWidth()-mun/4f,bitmap.getHeight()-5, paint);
        paint.setColor(Color.WHITE);
        canvas.drawLine(bitmap.getWidth()/4f+mun/4f,bitmap.getHeight()-5,bitmap.getWidth()/4f*3f-mun/4f,bitmap.getHeight()-5,paint);
        InitializPicture();
        return bitmap;
    }
    public void loadRand(TextView textView,int checkmun)
    {
        SQLiteDatabase db = BeginGame.db_helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select checkid,steps from rank where checkid="+String.valueOf(checkmun+1), null);
        if(cursor.getCount()!=0)
        {
            String str="0";
            while (cursor.moveToNext()){
                str=cursor.getString(cursor.getColumnIndex("steps"));
            }
            textView.setText(str);
        }
        cursor.close();
    }
    public void loadmusic(Switch swit)
    {
        if (BeginGame.switchcker!=0)
        {
            swit.setChecked(true);
        }
        else
        {
            swit.setChecked(false);
        }
        swit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    BeginGame.switchcker=0.5f;
                }
                else
                {
                    BeginGame.switchcker=0;
                }
            }
        });
    }
    public class Sizes{
        private int height;
        private int width;
        public Sizes(int width,int height)
        {
            this.height=height;
            this.width=width;
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }
    }

}


