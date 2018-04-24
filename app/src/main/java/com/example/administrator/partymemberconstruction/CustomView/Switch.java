package com.example.administrator.partymemberconstruction.CustomView;

/**
 * Created by Administrator on 2018/4/24/024.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.administrator.partymemberconstruction.R;

/**
 * 仿IOS选择开关
 * @author mufeng
 *
 */
public class Switch extends View implements OnClickListener{

    private Context mContext;
    private Paint mPaint;
    private float width,height;
    private boolean isOpen=true;
    private RectF oval;

    public Switch(Context context) {
        super(context);
        init(context);
    }

    public Switch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Switch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

//    public Switch(Context context, AttributeSet attrs, int defStyleAttr,
//                  int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init(context);
//    }

    private void init(Context context) {
        mContext=context;
        //设置宽高
        width=DensityUtils.dip2px(mContext, 60);
        height=width/2;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setOnClickListener(this);
        oval = new RectF(0, 0, width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);//设置填充样式为描边
        paint.setColor(getResources().getColor(R.color.actionsheet_gray));//设置颜色为灰色
        paint.setStrokeWidth(2);//设置笔触宽度为2像素
        //根据控件当前状态设置画笔颜色
        if(isOpen){
            mPaint.setColor(getResources().getColor(R.color.select));
        }else{
            mPaint.setColor(getResources().getColor(R.color.actionsheet_gray));
        }
        mPaint.setStyle(Paint.Style.FILL);// 充满
        //画底层圆角矩形
        canvas.drawRoundRect(oval,height/2 , width/4, mPaint);
    if(isOpen)
        canvas.drawRoundRect(oval,height/2 , width/4, mPaint);// 第二个参数是x半径，第三个参数是y半径
        //画开关圆圈 将画笔颜色设为白色
        mPaint.setColor(Color.WHITE);
        //根据控件当前状态判断将圆圈画在左边还是右边
        if(isOpen){
            canvas.drawCircle(width/4*3, height/2, height/2-DensityUtils.dip2px(mContext, 1), mPaint);
        }else{
            canvas.drawCircle(width/4, height/2, height/2-DensityUtils.dip2px(mContext, 1), mPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //重新测量控件的大小，主要在控件宽高属性设置为wrap_content时，将控件大小设置为实际大小
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    //测量宽度
    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = (int) DensityUtils.dip2px(mContext, 80) + getPaddingLeft() + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        return result;
    }

    //测量高度
    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = (int) DensityUtils.dip2px(mContext, 80)/2 + getPaddingTop() + getPaddingBottom();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * 点击切换isOpen值，调用postInvalidate重绘view,可用于异步线程
     */
    @Override
    public void onClick(View view) {
        isOpen=!isOpen;
        postInvalidate();
    }

    /**
     * 打开控件开关
     */
    public void open(){
        if(!isOpen){
            isOpen=true;
            postInvalidate();
        }
    }

    /**
     * 关闭控件开关
     */
    public void close(){
        if(isOpen){
            isOpen=false;
            postInvalidate();
        }
    }

    /**
     * 获取控件状态
     * @return
     */
    public boolean isOpen(){
        return isOpen;
    }
  static    class DensityUtils {

        /**
         * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
         */
        public static int dip2px(Context context, float dpValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        }

        /**
         * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
         */
        public static int px2dip(Context context, float pxValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5f);
        }
    }
}
