package com.mrj.mvpproject;


import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity    {
    public  boolean islike;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView selectResult = findViewById(R.id.selectResult);
        /*
        //動畫集合
        final AnimationSet animationSet = new AnimationSet(true);
        final ImageView fb_like = findViewById(R.id.fb_like);
        ImageView img = findViewById(R.id.img);
        final TextView selectResult = findViewById(R.id.selectResult);

        //動畫
        Animation scale = new ScaleAnimation(1.0f,1.2f,1.0f,1.2f,Animation.RELATIVE_TO_SELF, 1f,Animation.RELATIVE_TO_SELF,1f);
        scale.setDuration(200);
        Animation rotate = new RotateAnimation(0.0f,-20f,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        rotate.setDuration(200);
        animationSet.addAnimation(scale);
        animationSet.addAnimation(rotate);

        //左右搖擺
        Keyframe k1 = Keyframe.ofFloat(0f,0f);
        Keyframe k2 = Keyframe.ofFloat(0.1f,-30f);
        Keyframe k3 = Keyframe.ofFloat(0.3f,30f);
        Keyframe k4 = Keyframe.ofFloat(0.6f,-30f);
        Keyframe k5 = Keyframe.ofFloat(0.8f,30f);
        Keyframe k6 = Keyframe.ofFloat(1f,0f);
        PropertyValuesHolder propertyValuesHolder =PropertyValuesHolder.ofKeyframe("rotation", k1,k2,k3,k4,k5,k6);
        final ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(img,propertyValuesHolder);
        objectAnimator.setDuration(500);


        fb_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(islike){
                    islike=!islike;
                    fb_like.setImageResource(R.drawable.fblike_notselect);

                }else{
                    islike=!islike;
                    fb_like.setImageResource(R.drawable.fblike_select);
                }

                fb_like.startAnimation(animationSet);
                objectAnimator.start();

            }
        });
        */
        int[] drawables  = {R.mipmap.icon1,R.mipmap.icon2,R.mipmap.icon3};

        com.mrj.mvpproject.CustomSelect selectDialog = findViewById(R.id.selectDialog);
        selectDialog.setSelectIcon(drawables);

        selectDialog.setListener(new CustomSelect.IconSelectListener() {
            @Override
            public void onOpen() {
                selectResult.setText("");
            }

            @Override
            public void onSelected(int iconIndex) {
                selectResult.setText("Select icon: $iconIndex");
            }

            @Override
            public void onCancel() {
                selectResult.setText("Cancel");
            }
        });

    }
}
