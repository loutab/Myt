package com.example.administrator.partymemberconstruction.CustomView;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.partymemberconstruction.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/27/027.
 */

public class MTabHost extends RadioGroup {
    private int one = 0;

    /**
     * 文字在图片的上面
     */
    public static final int TOP_TEXTPOSITION = 0;

    /**
     * 文字在图片的下面
     */
    public static final int BOTTOM_TEXTPOSITION = 1;

    /**
     * 文字在图片的左边
     */
    public static final int LEFT_TEXTPOSITION = 2;

    /**
     * 文字在图片的右边
     */
    public static final int RIGHT_TEXTPOSITION = 3;

    /**
     * 默认文字是在图片上面的
     */
    private int textPosition = TOP_TEXTPOSITION;
    private FragmentTransaction fragmentTransaction;

    public MTabHost(Context context) {
        this(context, null);
    }

    public MTabHost(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //===================== 共有方法 start ==========================================

    /**
     * @param norImageRid
     * @param selectedImageRid
     * @param text
     */
    public void addTab(int norImageRid, int selectedImageRid, String text, int weight, int type, int id) {

        //创建单选按钮
        RadioButton rb = new RadioButton(getContext());
        rb.setId(id);
        //取消圆点
        rb.setButtonDrawable(android.R.color.transparent);
        //rb.setButtonDrawable(null);
        //设置内容居中
        rb.setGravity(Gravity.CENTER);
        //声明布局参数对象
        LayoutParams lp;
        StateListDrawable drawable = new StateListDrawable();
        //添加默认的item
        drawable.addState(new int[]{-android.R.attr.state_checked},
                new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), norImageRid)));
        //添加选择后的item
        drawable.addState(new int[]{android.R.attr.state_checked},
                new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), selectedImageRid)));
        //设置大小
        int i = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics());
        drawable.setBounds(0, 0, i, i);
        //获取组建的朝向
        int orientation = getOrientation();
        //如果是水平的
        int j = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
        if (orientation == LinearLayout.HORIZONTAL) {
            //水平的时候创建一个高度填充,但是宽度平分的布局参数
            lp = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, weight);
            //判断文字是不是在图片下面
            if (textPosition == BOTTOM_TEXTPOSITION) {
                rb.setCompoundDrawables(null, drawable, null, null);
            } else { //文字再图片上面
                rb.setCompoundDrawables(null, null, null, drawable);
            }
        } else { //否则就是竖直的
            //竖直的时候创建一个宽度填充,但是高度平分的布局参数
            lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
            //判断文字是不是在图片右边
            if (textPosition == RIGHT_TEXTPOSITION) {
                rb.setCompoundDrawables(drawable, null, null, null);
            } else { //文字在图片左边
                rb.setCompoundDrawables(null, null, drawable, null);
            }
        }
        rb.setCompoundDrawablePadding(j);
        rb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        //设置布局参数
        rb.setLayoutParams(lp);
        //设置文本
        rb.setText(text);
        setSelectorColor(rb);
        if (type == 0) {
            rb.setVisibility(INVISIBLE);
        }
        //添加到组中
        this.addView(rb);
        //请求重新布局
        requestLayout();
        Log.d("p", rb.getId() + "");
        if (one == 0)
            check(rb.getId());
        if (one == 0)
            one = 1;
        rb.setButtonDrawable(null);
        //fragments.add(fragment);
        radioButtons.add(rb);
    }

    private List<RadioButton> radioButtons = new ArrayList<>();

    public RadioButton getRadioButton(int i) {
        RadioButton radioButton = radioButtons.get(i);
        return radioButton;
    }

    /**
     * 设置文字的方位
     * 在方法{@link MTabHost#addTab(int, int, String, int, int)}之前调用
     * <p/>
     * {@link MTabHost#TOP_TEXTPOSITION}
     * {@link MTabHost#BOTTOM_TEXTPOSITION}
     * {@link MTabHost#LEFT_TEXTPOSITION}
     * {@link MTabHost#RIGHT_TEXTPOSITION}
     *
     * @param textPosition
     */
    public void setTextPosition(int textPosition) {
        this.textPosition = textPosition;
    }

    public void setSelectorColor(RadioButton radioButton) {

        int[] colors = new int[]{Color.rgb(84, 82, 84), Color.rgb(233, 74, 78)};

        int[][] states = new int[2][];

        states[0] = new int[]{-android.R.attr.state_checked};

        states[1] = new int[]{android.R.attr.state_checked};


        ColorStateList colorStateList = new ColorStateList(states, colors);

        radioButton.setTextColor(colorStateList);

    }


}
