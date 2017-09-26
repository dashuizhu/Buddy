package com.example.administrator.buddy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.example.administrator.buddy.R;

/**
 * 自定义view ， 顺时针方向， 依次添加子view在圆环上
 * Created by zhuj on 2017/6/7 下午2:20.
 */
public class BuddyCircleGroupView extends ViewGroup {

  /**
   * 第一个item开始的角度
   * 角度，右边是0 ， top是90 lift 180  bottom 270
   */
  private int startAngle;

  /**
   * item之间间隔角度
   */
  private int intervalAngle;

  /**
   * 半径， 正常应该是view 的 width height中 比较小的值的 1/2, 再减去圆环宽度
   */
  private float radius;

  /**
   * 圆环颜色
   */
  private int circleColor;

  /**
   * 画笔
   */
  private Paint mPaint;

  /**
   * 圆心x， 圆心y ， 默认等于view的 （width/2, height/2);
   */
  private float circleCenterX, circleCenterY;

  /**
   * view的宽度 高度
   */
  private float mHeight, mWidth;

  /**
   * 圆环宽度
   */
  //private float ringRadius;

  public BuddyCircleGroupView(Context context) {
    super(context);
  }

  public BuddyCircleGroupView(Context context, AttributeSet attrs) {
    super(context, attrs);
    TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.BuddyCircleGroupView);
    startAngle = ta.getInteger(R.styleable.BuddyCircleGroupView_startAngle, 270);
    intervalAngle = ta.getInteger(R.styleable.BuddyCircleGroupView_intervalAngle, 30);
    circleColor = ta.getColor(R.styleable.BuddyCircleGroupView_circleColor, Color.CYAN);
    radius = ta.getDimension(R.styleable.BuddyCircleGroupView_radius, 100);
    //ringRadius = ta.getDimension(R.styleable.BuddyCircleGroupView_ringRadius, 10);
    ta.recycle();
    //默认viewGroup不设置背景，是不会执行onDraw的，这里无论设置无论是否有背景都要执行onDraw
    setWillNotDraw(false);
  }
//设置大小
  @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
    //寻找圆心
    circleCenterX = getWidth() / 2;
    circleCenterY = (getHeight() + radius) /2 ;
    //
    int count = getChildCount();
    int cl = 0, ct = 0, cr = 0, cb = 0;
    int cWidth = 0;
    int cHeight = 0;
    for (int i = 0; i < count; i++) {
      View childView = getChildAt(i);
      //子view的宽高度
      cWidth = childView.getMeasuredWidth();
      cHeight = childView.getMeasuredHeight();
      //通过三角函数， 获得对应角度， 相对半径radius的偏移量x 和 y
      double x = radius * Math.cos(Math.toRadians(startAngle - i * intervalAngle));
      double y = radius * Math.sin(Math.toRadians(startAngle - i * intervalAngle));
      //设置4个边界坐标
      //(r - l)/2 是横坐标居中,  加上 x偏移量， 再减去半个子view宽度
      cl = (int) ((r - l) / 2 + x) - cWidth / 2;
      cr = cl + cWidth;
      //(b - t)/2 是纵坐标居中,  y偏移量， 再减去半个子view宽度
      ct = (int) ((b - t)  - y - cHeight);
      cb = ct + cHeight;
      childView.layout(cl, ct, cr, cb);
    }
  }
//设置子控件
  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    measureChildren(widthMeasureSpec, heightMeasureSpec);

    int count = getChildCount();
    int cWidth = 0;
    int cHeight = 0;
    //获得子view的 最大高宽度， 因为上下左右边缘，要能放的下子view，不被遮住
    for (int i = 0; i < count; i++) {
      View childView = getChildAt(i);
      //子view的宽高度
      cWidth = Math.max(cWidth, childView.getMeasuredWidth());
      cHeight = Math.max(cHeight, childView.getMeasuredHeight());
    }

    int specModeWidth = MeasureSpec.getMode(widthMeasureSpec);
    int specModeHeight = MeasureSpec.getMode(heightMeasureSpec);
    int width = 0;
    int height = 0;
    switch (specModeWidth) {
      case MeasureSpec.AT_MOST:
        width = (int) (radius * 2) + cWidth;
        break;
      case MeasureSpec.EXACTLY:
      case MeasureSpec.UNSPECIFIED:
        width = MeasureSpec.getSize(widthMeasureSpec);
        radius = (width- cWidth) /2 ;
        break;
    }
    switch (specModeHeight) {
      case MeasureSpec.AT_MOST:
        height = (int) (radius ) + cHeight;
        break;
      case MeasureSpec.EXACTLY:
      case MeasureSpec.UNSPECIFIED:
        height = MeasureSpec.getSize(heightMeasureSpec);
        break;
    }
    setMeasuredDimension(width, height);
    //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }
//绘制view内容
  @Override protected void onDraw(Canvas canvas) {
    //if (mPaint == null) {
    //  mPaint = new Paint();
    //}
    ////限制最大半径
    //int min = Math.min(getWidth(), getHeight());
    //if (radius > min / 2) {
    //  radius = min;
    //}
    ////设置画笔
    //mPaint.setColor(circleColor);
    //mPaint.setStyle(Paint.Style.STROKE);//设置空心
    //mPaint.setStrokeWidth(ringRadius);//边框宽度
    //canvas.drawCircle(circleCenterX, circleCenterY, radius, mPaint);
    super.onDraw(canvas);
  }
}
