package com.example.huarongdao;

import android.content.Context;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.IOException;

public class SwapAnim implements Animation.AnimationListener {

    private ImageView simageView;
    private ImageView eimageView;
    private ImageView yimageView;
    private Context context;
    public static MediaPlayer mediaPlayer;
    private ConstraintLayout constraintLayout;
    private LoadGame.Sizes layerSize;
    private static boolean isFinsh=true;

    public static void setIsFinsh(boolean boo)
    {
        isFinsh=boo;
    }
    public static boolean getIsFinsh()
    {
        return isFinsh;
    }
    public SwapAnim(ImageView imageView, ImageView imageView1, ConstraintLayout constraintLayout , LoadGame.Sizes layerSize, Context context)
   {
      // isAnimation=false;
       simageView=imageView;
       eimageView=imageView1;
       this.constraintLayout=constraintLayout;
       this.layerSize=layerSize;
       this.context=context;
   }

   private void bulidView()
   {
       final float scale = context.getResources().getDisplayMetrics().density;
       int mun=(int) (10 * scale + 0.5f);
       yimageView=new ImageView(context);
       yimageView.setAlpha(simageView.getAlpha());
       yimageView.setImageAlpha(simageView.getImageAlpha());
       yimageView.setImageDrawable(simageView.getDrawable());
       yimageView.setScaleType(ImageView.ScaleType.FIT_START);
           ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(layerSize.getWidth(),layerSize.getHeight());
           yimageView.setLayoutParams(params);
       yimageView.setX(simageView.getX()+mun);
       yimageView.setY(simageView.getY()+mun);
   }


   public void exchangeImage()
   {
       bulidView();
       isFinsh=false;
       simageView.setImageDrawable(null);
       eimageView.setImageDrawable((null));
       constraintLayout.addView(yimageView);
       yimageView.startAnimation(MoveAnimation(eimageView.getX()-simageView.getX(),eimageView.getY()-simageView.getY()));
   }

    private TranslateAnimation MoveAnimation(float twidth,float theight)
    {
        TranslateAnimation   animator=new TranslateAnimation(0,twidth,
                0,theight);
        animator.setDuration(200);
        animator.setAnimationListener(this);
        return animator;
    }

    @Override
    public void onAnimationStart(Animation animation) {
        if(!mediaPlayer.isPlaying()) {
            mediaPlayer.setLooping(false);
            mediaPlayer.setVolume(BeginGame.switchcker, BeginGame.switchcker);
            mediaPlayer.start();
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        eimageView.setImageDrawable(yimageView.getDrawable());
        constraintLayout.removeView(yimageView);
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        isFinsh=true;
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
