package com.mrj.mvpproject;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;



/**
 * Created by MrJ on 2018/11/23.
 */

public class CustomSelect extends  LinearLayout    {
    int [] drawables;
    Context context;
    Boolean isOpen;
    ImageView select_imageview;
    int ROW_HEIGHT = 150;
    LinearLayout dialog_select_linearlayout,iconLinearLayout;
    IconSelectListener listener =null;


    public CustomSelect(Context context) {
        super(context);
    }
    interface IconSelectListener {
        void  onOpen();
        void   onSelected(int iconIndex);
        void  onCancel();
    }


    private void setting(){
        View.inflate(context, R.layout.custom_selector,this);
        select_imageview = findViewById(R.id.select_imageview);
        dialog_select_linearlayout = findViewById(R.id.dialog_select_linearlayout);
        iconLinearLayout = findViewById(R.id.iconLinearLayout);
        //繪製
        drawButton();
        drawDialog();
        displayDialog(false);

        //clickButton
        select_imageview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //+→x
                rotateSelectImageView();

                displayDialog(!isOpen);

                //callback
                if(isOpen){
                    listener.onOpen();
                }else{
                    listener.onCancel();
                }
                isOpen = !isOpen;
            }
        });

    }

    private  void rotateSelectImageView(){
        Float fromDegree;
        Float toDegree;

        if(isOpen){
            fromDegree = -45.0f;
            toDegree =0.0f;
        }else{
            fromDegree = 0.0f;
            toDegree =-45.0f;
        }
        RotateAnimation rotateAnimation = new RotateAnimation(fromDegree, toDegree,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(300);
        //不會返回原來位子
        rotateAnimation.setFillAfter(true);
        select_imageview.startAnimation(rotateAnimation);
    }

    private void dismissDialog(){
        isOpen  =false;
        rotateSelectImageView();
        displayDialog(isOpen);
    }

    public  void setSelectIcon(int [] drawables){
        this.drawables = drawables;
        setting();

    }

    //是否顯示dialog
    private void displayDialog(boolean display) {
        Float fromScale  = 0.0f;
        Float toScale  = 1.0f;

        if(display){
            fromScale = 0.0f;
            toScale = 1.0f;
            this.dialog_select_linearlayout.setVisibility(View.VISIBLE);
            //??
            this.dialog_select_linearlayout.bringToFront();
        }else{
            fromScale = 1.0f;
            toScale = 0.0f;
            this.dialog_select_linearlayout.setVisibility(View.GONE);
        }
        ScaleAnimation anim = new ScaleAnimation(
                fromScale, toScale,
                fromScale, toScale,
                Animation.RELATIVE_TO_SELF,  0.5f,
                Animation.RELATIVE_TO_SELF,1f);
        anim.setDuration(300);
        this.dialog_select_linearlayout.startAnimation(anim);


    }

    //繪製dialog
    private void drawDialog() {
        int trianglewith = 50;
        int triangleHeight = 40;
        //icon 數量
        int imagePerRow = 3;

        int imageRows = drawables.length/imagePerRow;

        if(drawables.length% imagePerRow > 0){
            imageRows++;
        }
        if (imageRows == 0){
            imageRows = 1;
        }

        int dialogwidth =500;
        int dialogheight =ROW_HEIGHT*imageRows + triangleHeight;
        int dialogImageheight = ROW_HEIGHT* imageRows;

        for(int i =0; i<imageRows;i++){
            LinearLayout imagelayout = new LinearLayout(this.context);
            LayoutParams imageParam = new LinearLayout.LayoutParams(0, dialogwidth/6,1.0f);

            for(int j = 0; j<imagePerRow;i++){
                final int nums = i*imagePerRow  + j;
                if(nums <drawables.length){
                    ImageView imageView = new ImageView(this.context);
                    imageView.setImageResource(drawables[nums]);
                    imageView.setLayoutParams(imageParam);

                    imageView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dismissDialog();
                            listener.onSelected(nums);
                        }
                    });
                    imagelayout.addView(imageView);
                }
            }
            iconLinearLayout.addView(imagelayout);
        }

        //繪製dialog外觀

        Bitmap bitmap = Bitmap.createBitmap(dialogwidth, dialogheight, Bitmap.Config.ARGB_8888);

        //creat paint

        Paint p = new Paint();
        p.setStrokeWidth(2f);
        p.setColor(Color.WHITE);
        p.setStyle(Paint.Style.FILL);
        p.setAntiAlias(true);
        p.setShadowLayer(5f,2f,2f,Color.LTGRAY);
        p.setStrokeJoin(Paint.Join.ROUND);
        p.setStrokeCap(Paint.Cap.ROUND);
        p.setPathEffect(new CornerPathEffect(10f));

        //draw dialog

        Canvas canvas = new Canvas(bitmap);

        int leftx =  0;
        int lefty = 0;

        int centerx = (dialogwidth-leftx)/2+leftx;
        Path path = new Path();

        path.moveTo(leftx,lefty);
        path.lineTo(dialogwidth,lefty);
        path.lineTo(dialogwidth, dialogImageheight);
        path.lineTo((centerx + trianglewith), dialogImageheight);
        path.lineTo(centerx,(dialogImageheight + triangleHeight));
        path.lineTo((centerx - trianglewith), dialogImageheight);
        path.lineTo(leftx, dialogImageheight);
        path.close();

        canvas.drawPath(path,p);

        Resources resources = getResources();
        BitmapDrawable drawable = new BitmapDrawable(resources,bitmap);
        this.dialog_select_linearlayout.setBackground(drawable);


    }

    //繪製按鈕
    private void drawButton() {
        Bitmap bitmap = Bitmap.createBitmap(50,50,Bitmap.Config.ARGB_8888);

        //產生paint
        Paint p = new Paint();
        p.setStrokeWidth(2f);
        p.setStyle(Paint.Style.FILL);
        p.setAntiAlias(true);
        p.setColor(Color.parseColor("#419DFF"));

        //畫圓
        Canvas canvas = new Canvas(bitmap);
        canvas.drawCircle(25f,25f,25f, p);

        int circlePadding = 12;
        p.setColor(Color.WHITE);

        //劃十字
        RectF rect1 = new RectF((25-3),circlePadding,(25+3),(50-circlePadding));
        canvas.drawRect(rect1,p);

        RectF rect2 = new RectF(circlePadding, (25-3), (50-circlePadding),(25+3));
        canvas.drawRect(rect2,p);

        select_imageview.setImageBitmap(bitmap);

    }

    public   void setListener( IconSelectListener listener){
        this.listener = listener;

    }


    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }
}


