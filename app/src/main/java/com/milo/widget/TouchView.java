package com.milo.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * 支持点击事件的可拖动视图
 * The view can touch and support click event
 *
 * @author Milo
 *         Email:303767416@qq.com
 * @version 2018/4/20
 */
public class TouchView extends View {
    private static final String TAG = "TouchView";

    /**
     * 记录X轴最后位置
     */
    int lastX = 0;
    /**
     * 记录Y轴最后位置
     */
    int lastY = 0;
    /**
     * 记录Action_Down - X轴位置
     */
    int downX = 0;
    /**
     * 记录Action_Down - Y轴位置
     */
    int downY = 0;
    /**
     * 记录Action_Down 时间
     */
    long lastDownInMills;

    final int DEFAULT_LIMITLEFT = 0;
    final int DEFAULT_LIMITTOP = 0;

    int limitLeft;
    int limitTop;
    int limitRight;
    int limitBottom;

    int screenWidth;
    int screenHeight;

    private OnClickListener l;

    public TouchView(Context context) {
        super(context);
    }

    public TouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        setLimitAuto();
    }

    public TouchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 自适应边界
     */
    private void setLimitAuto() {
        setLimitAuto(null);
    }

    /**
     * 根据parentView自适应边界
     *
     * @param parentView
     */
    public void setLimitAuto(ViewGroup parentView) {
        if (parentView == null) {
            this.limitLeft = DEFAULT_LIMITLEFT;
            this.limitTop = DEFAULT_LIMITTOP;
            this.limitRight = screenWidth;
            this.limitBottom = screenHeight;
        } else {
            this.limitLeft = parentView.getLeft();
            this.limitTop = parentView.getTop();
            this.limitRight = parentView.getRight();
            this.limitBottom = parentView.getBottom();
        }
    }

    /**
     * 设定自定义边界
     *
     * @param limitLeft
     * @param limitTop
     * @param limitRight
     * @param limitBottom
     */
    public void setLimitParams(int limitLeft, int limitTop, int limitRight, int limitBottom) {
        this.limitLeft = limitLeft;
        this.limitTop = limitTop;
        this.limitRight = limitRight;
        this.limitBottom = limitBottom;
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        this.l = l;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastDownInMills = System.currentTimeMillis();
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                downX = (int) event.getRawX();
                downY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;
                int left = getLeft() + dx;
                int top = getTop() + dy;
                int right = getRight() + dx;
                int bottom = getBottom() + dy;

                if (left < limitLeft) {
                    left = limitLeft;
                    right = left + getWidth();
                }
                if (top < limitTop) {
                    top = limitTop;
                    bottom = top + getHeight();
                }
                if (right > limitRight) {
                    right = limitRight;
                    left = limitRight - getWidth();
                }
                if (bottom > limitBottom) {
                    bottom = limitBottom;
                    top = limitBottom - getHeight();
                }

                layout(left, top, right, bottom);
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                dx = (int) event.getRawX() - downX;
                dy = (int) event.getRawY() - downY;
                if (dx + dy == 0 && System.currentTimeMillis() - lastDownInMills < 500) {
                    //TODO x + y移动距离小于2px 且 触碰时间小于500ms 触发点击事件
                    if (l != null) {
                        l.onClick(this);
                    }
                }
                break;
        }
        return true;
    }

}
