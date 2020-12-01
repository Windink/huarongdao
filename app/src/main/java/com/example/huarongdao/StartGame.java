package com.example.huarongdao;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PointF;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class StartGame extends FragmentActivity implements ImageView.OnTouchListener,EndGame.NoticeDialgoListener {

    private LoadGame loadGame;
    TextView cloth;
    private  int colthcoun = 0;
     private int checknum;
    CueGame cueGame;
    private ArrayList<Integer> general;
    private ArrayList<Integer> backgeneral;
    private boolean isOpen=true;
    private ConstraintLayout constraintLayout;
    private final int[] layerid = new int[]{R.id.layer01, R.id.layer02, R.id.layer03, R.id.layer04, R.id.layer05, R.id.layer06
            , R.id.layer07, R.id.layer08, R.id.layer09, R.id.layer10, R.id.layer11, R.id.layer12, R.id.layer13, R.id.layer14, R.id.layer15, R.id.layer16
            , R.id.layer17, R.id.layer18, R.id.layer19, R.id.layer20};

    @Override
    protected void onRestart() {
        super.onRestart();
        SwapAnim.setIsFinsh(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_main);

        Intent intent=getIntent();
        String checkname=intent.getStringExtra("checkname");
         checknum=intent.getIntExtra("checknum",0);

       TextView textView=findViewById(R.id.mosttext);

        loadGame = new LoadGame(this,checknum);
        loadGame.loadRand(textView,checknum);
        SwapAnim.mediaPlayer=MediaPlayer.create(this, R.raw.movemusic);
        constraintLayout=findViewById(R.id.layer);
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageBitmap(loadGame.InitializGame());
        TextView checknames=findViewById(R.id.textView3);
        checknames.setText(checkname);
        cloth=findViewById(R.id.clothtext);

        //初始集合
        openGame(checknum);

        //提示按钮
         Button cue=findViewById(R.id.cue);
        cue.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!SwapAnim.getIsFinsh())
                {
                    return ;
                }
                if(cueGame==null)
                {
                    cueGame=new CueGame(general);
                }
               for (int i=0;i<cueGame.getFullResult().size();i++)
               {
                   if(cueGame.getFullResult().get(i).equals(general.toString()))
                   {
                       moveStep(i);
                       return;
                   }
               }
                cueGame=new CueGame(general);
               moveStep(0);
            }
        });


        //两个按钮
        Button reset=findViewById(R.id.reset);
        reset.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                openGame(checknum);
                colthcoun=0;
                cloth.setText(String.valueOf(colthcoun));
            }
        });
        Button goback=findViewById(R.id.goback);
                goback.setOnClickListener(new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(StartGame.this,SelectGame.class);
                        startActivity(intent);
                    }
                });
         //音效开关
        Switch swit=findViewById(R.id.musicye);
        loadGame.loadmusic(swit);
    }
    //棋盘初始化方法
    private void openGame(int checkunm) {
        String[] string = getResources().getStringArray(R.array.CheackPoint);
        backgeneral=new ArrayList<>();
       for (int i=0;i<layerid.length;i++)
        {
            int num=Integer.parseInt(String.valueOf(string[checkunm].charAt(i)));
            backgeneral.add(num);
         ImageView imageView=findViewById(layerid[i]);
         imageView.setImageBitmap(loadGame.getPicture().get(i));
         imageView.setOnTouchListener(this);
        }
        general=new ArrayList<Integer>();
        general.addAll(backgeneral);
        Collections.reverse(backgeneral);
    }

    private PointF mouserDown;//点击的坐标
    private PointF mouserUp;//松开的坐标
    //移动方法
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(!SwapAnim.getIsFinsh())
        {
            return false;
        }
        if(!isOpen)
        {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mouserDown=new PointF(event.getX(),event.getY());
                return true;

            case MotionEvent.ACTION_MOVE:
                return true;


            case MotionEvent.ACTION_UP:
                mouserUp=new PointF(event.getX(),event.getY());

                break;
            default:
                return false;
        }

        int idNews=v.getId();
        int number=linearSearch(idNews);//点击图片的序列号
        int flag=general.get(number);
        String direc=judgeDirection(number,flag);
        if(flag==0&&general.lastIndexOf(0)==18&&direc.equals("down"))
        {
            EndGame endGame=new EndGame(this,colthcoun);
            endGame.show(getSupportFragmentManager(),"EndGame");
            endGame.SetDate(checknum,colthcoun);
            isOpen=false;
            colthcoun=0;
            return false;
        }
        countMoveOne(flag,direc,number);
        return false;
    }
    //方向判断方法
    protected String judgeDirection(int number,int flag)
    {
        if(mouserDown==null||mouserUp==null)
        {
            return null;
        }
        if(Math.abs(mouserDown.x-mouserUp.x)<loadGame.getLayerSize().getWidth()/2f)
        {
           if(mouserUp.y>mouserDown.y&&number/4<4)
           {
               return "down";
           }
           else if(mouserUp.y<=mouserDown.y&&number/4>0)
           {
               return "up";
           }
        }
        else if(Math.abs(mouserDown.y-mouserUp.y)<loadGame.getLayerSize().getHeight()/2f)
        {
            if(mouserUp.x>mouserDown.x&&(number+1)%4!=0)
            {
                return "right";
            }
            else if(mouserUp.x<=mouserDown.x&&(number+4)%4!=0)
            {
                return "left";
            }
        }
        return "";
    }
    //遍历点击对象方法
    protected int linearSearch(int i)
    {
        for(int j=0;j< layerid.length;j++)
        {
            if(layerid[j]==i)
            {
                return j;
            }
        }
        return -1;
    }
    //棋子类型判断方法One
    protected void countMoveOne(int flag,String direc,int number)
    {
        switch (direc)
        {
            case "down":
               countMoveTwo(flag,backgeneral,number,4);
                break;
            case "up":
                countMoveTwo(flag,general,number,-4);
                break;
            case "right":
                countMoveTwo(flag,backgeneral,number,1);
                break;
            case "left":
                countMoveTwo(flag,general,number,-1);
                break;
            default:
                break;
        }
    }
    //棋子类型判断方法Two
    protected void countMoveTwo(int flag,ArrayList<Integer> order ,int number,int count)
    {
                     int mun = order.indexOf(flag);
                     int counts=Math.abs(count)==4 ? 3:-3;
                     try {
                         if(flag==6) {
                                 if(general.get(number+count)==7)
                                 {
                                     moveLayer(flag, number, count, general);
                                 }
                              return;
                         }
                         if(order.get(mun-Math.abs(count))==7) {
                             if(flag!=0&&mun+Math.abs(count)<order.size()&&order.get(mun+Math.abs(count))==flag)//最后一个判断顺序不可改
                             {
                                 moveLayer(flag, mun, count, order);
                             }
                             if(order.get(mun-counts)==7)
                             {
                                 moveLayer(flag, mun, count, order);
                             }
                         }
                     }
                     catch (Exception e)
                     {
                         //e.printStackTrace();
                     }
    }
    //棋子移动遍历方法
    protected void  moveLayer(int flag,int star,int end,ArrayList<Integer> array)
    {
        switch(flag)
        {
            case 0: case 3: case 1: case 2: case 4: case 5:
                ArrayList<Integer> geners = new ArrayList<>(array);
                while (true)
                {
                    int open=lookUpList(geners,flag,star);
                   if(open==-1)
                   {
                       break;
                   }
                   if(array==general)
                   {
                       movePicture( open, open + end);
                   }else {
                       movePicture(19 - open, 19 - open + end);
                   }
                   star=open+1;
                }

                break;
            case 6:
                       movePicture(star,star+end);
                break;

            default:
                return;
        }
        colthcoun++;
        cloth.setText(String.valueOf(colthcoun));
    }
    //图片移动方法

    protected void movePicture(int star,int end)
    {
        ImageView imageView=findViewById(layerid[star]);
        ImageView imageView1=findViewById(layerid[end]);
        SwapAnim swapAnim=new SwapAnim(imageView,imageView1,constraintLayout,loadGame.getLayerSize(),this);
        swapAnim.exchangeImage();
        int mun=general.get(star);
        general.set(star,general.get(end));
        general.set(end,mun);
        backgeneral.clear();
        backgeneral.addAll(general);
        Collections.reverse(backgeneral);
    }
    //目标链表遍历方法
    private int lookUpList(ArrayList<Integer> arrayList,int name,int index)
    {
        for (int i=index;i<arrayList.size();i++)
        {
            if(arrayList.get(i)==name)
            {
                return i;

            }
        }
        return -1;
    }

    //置换成移动规则
    protected void moveStep(int index)
    {
        if(index==cueGame.getFullResult().size()-1)
        {
            return;
        }
        for (int i=1;i<cueGame.getFullResult().get(index).length();i+=3)
        {
            if(cueGame.getFullResult().get(index).charAt(i)!=cueGame.getFullResult().get(index+1).charAt(i))
            {
                char a=cueGame.getFullResult().get(index).charAt(i);
                for (int j=i;j<cueGame.getFullResult().get(index+1).length();j+=3)
                {
                    if(a==cueGame.getFullResult().get(index+1).charAt(j))
                    {
                        int count=(j-i)/3;
                        int num=Integer.parseInt(String.valueOf(a));
                        if(cueGame.getFullResult().get(index).charAt(j)==cueGame.getFullResult().get(index+1).charAt(j))
                        {
                            continue;
                        }
                        switch (count)
                        {
                            case 4:
                                if(num==6)
                                {
                                    moveLayer(num,(i-1)/3,count,general);
                                    return;
                                }
                                if(num==7&&cueGame.getFullResult().get(index+1).charAt(i)=='6')
                                {
                                    a = cueGame.getFullResult().get(index + 1).charAt(i);
                                    num=Integer.parseInt(String.valueOf(a));
                                    moveLayer(num,(i-1)/3+4,-count,general);
                                    return;
                                }
                                if(num!=7&&cueGame.getFullResult().get(index).charAt(i+12)!='6')
                                {
                                    moveLayer(num, backgeneral.indexOf(num), count, backgeneral);
                                    return;
                                }
                                if(num==7)
                                {
                                    a = cueGame.getFullResult().get(index + 1).charAt(i);
                                    num=Integer.parseInt(String.valueOf(a));
                                    moveLayer(num,(i-1)/3+4,-count,general);
                                    return;
                                }
                            case 1:
                                if(num==6)
                                {
                                    moveLayer(num,(i-1)/3,count,general);
                                    return;
                                }
                                if(num!=7&&cueGame.getFullResult().get(index+1).charAt(i)=='7')
                                {
                                    moveLayer(num, backgeneral.indexOf(num), count, backgeneral);
                                    return;
                                }

                                if(num==7&&cueGame.getFullResult().get(index+1).charAt(i)=='6')
                                {
                                    a = cueGame.getFullResult().get(index + 1).charAt(i);
                                    num=Integer.parseInt(String.valueOf(a));
                                    moveLayer(num,(i-1)/3+1,-count,general);
                                }
                                if(num==7&&cueGame.getFullResult().get(index+1).charAt(i)!='6')
                                {
                                    a = cueGame.getFullResult().get(index + 1).charAt(i);
                                    num=Integer.parseInt(String.valueOf(a));
                                    moveLayer(num,general.indexOf(num),-count,general);
                                }
                                return;
                            case 2:
                                if(num==7) {
                                    a = cueGame.getFullResult().get(index + 1).charAt(i);
                                    num = Integer.parseInt(String.valueOf(a));
                                    moveLayer(num, general.indexOf(num), -1, general);
                                }
                                else
                                {
                                    moveLayer(num, backgeneral.indexOf(num), 1, backgeneral);
                                }
                                return;
                            case 8:
                                if(num==7) {
                                    a = cueGame.getFullResult().get(index + 1).charAt(i);
                                    num = Integer.parseInt(String.valueOf(a));
                                    moveLayer(num, general.indexOf(num), -4, general);
                                }
                                else
                                {
                                    moveLayer(num, backgeneral.indexOf(num), 4, backgeneral);
                                }
                                return;
                            default:
                                break;
                        }

                    }
                }
            }
        }
    }


    @Override
    public void onDialogNextButton() {
        MyAdapter adapter=new MyAdapter(this);
        if(adapter.getCount()==checknum+1)
        {
            Toast.makeText(this,"这是最后一关了！",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent=new Intent(this,StartGame.class);
        intent.putExtra("checknum",checknum+1);
        intent.putExtra("checkname",adapter.getItem(checknum+1).toString());
        startActivity(intent);
    }

    @Override
    public void onDialogBackButton() {
        Intent intent=new Intent(this,SelectGame.class);
        startActivity(intent);
    }

    @Override
    public void onDialogBackmButton() {
        Intent intent=new Intent(this,BeginGame.class);
        startActivity(intent);
    }

}

