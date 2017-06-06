package com.example.administrator.guaguademo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;

/**
 * 作者：国富小哥
 * 日期：2017/6/6
 * Created by Administrator
 */

public class MyView extends SurfaceView{
    private Bitmap mBitmapBg;//被覆盖的内容图层
    private Bitmap mBitmapbp;//用来覆盖的图层
    private Canvas mCanvas;//用来覆盖的画布
    private Paint mPaint;//模拟手指刮开路径的画笔
    private Path mPath;//手指刮开的路径

    private Paint mPaintContent;//用来做覆盖的图层的文字画笔
    private String content="刮刮抽大奖";//用来做覆盖的图层的文字内容
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //刮开路经的画笔
        mPaint=new Paint();
        mPaint.setAlpha(0);
        //只绘制图形轮廓（描边）
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(50);
        //取两层绘制交吉
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        //初始化模拟手指头刮开的路径
        mPath=new Path();
        //初始化被覆盖的内容bitmap
        mBitmapBg= BitmapFactory.decodeResource(getResources(),R.drawable.bg);
        //初始化用来覆盖的内容bitmap
        mBitmapbp=Bitmap.createBitmap(mBitmapBg.getWidth(),mBitmapBg.getHeight(), Bitmap.Config.ARGB_8888);

        //初始化用来覆盖的画布
        mCanvas=new Canvas(mBitmapbp);
        //初始化内容画笔
        mPaintContent=new Paint();
        mPaintContent.setColor(Color.WHITE);
        mPaintContent.setTextSize(100);
        mPaintContent.setStrokeWidth(20);

        //设置用来做覆盖的画布颜色（灰色）
        mCanvas.drawColor(Color.GRAY);
        mCanvas.drawText(content,mCanvas.getWidth()/4,mCanvas.getHeight()/2,mPaintContent);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //清空画笔
                mPath.reset();
                //原点移至手指的触摸点
                mPath.moveTo(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(event.getX(),event.getY());
                break;
        }
        //模拟刮刮效果
        mCanvas.drawPath(mPath,mPaint);
        invalidate();

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制两个图层
        canvas.drawBitmap(mBitmapBg,0,0,null);
        canvas.drawBitmap(mBitmapbp,0,0,null);
    }
}
