package com.ccapton.dragview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class DragView extends RelativeLayout {

    protected final int DEFAULT_CIRCLERADIUS = 0;
    protected int circleRadius;
    protected int ciccleColor;

    public DragView(Context context) {
        this(context,null);
    }

    public DragView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupStyleable(context,attrs);
        setFocusableInTouchMode(true);
        setClickable(true);
        setFocusable(true);
    }


    public void setupStyleable(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DragView);

        circleRadius = (int) typedArray.getDimension(R.styleable.DragView_circleRadius, dp2px(DEFAULT_CIRCLERADIUS));

        int circleColorDefault = context.getResources().getColor(R.color.circle_color_default);
        ciccleColor = typedArray.getColor(R.styleable.DragView_circleColor, circleColorDefault);

        typedArray.recycle();

    }

    protected float dp2px(float dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return ev.getAction() != MotionEvent.ACTION_DOWN;
//    }

    private float oldX,oldY;


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                oldX = event.getX();
                oldY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float distanceX =  event.getX() - oldX;
                float distanceY =  event.getY() - oldY;
                if (Math.abs(distanceX) >= 5 || Math.abs(distanceY) >= 5)
                {
                    int l = (int) (getLeft() + distanceX);
                    int t = (int) (getTop() + distanceY);
                    int r = l + getWidth();
                    int b = t + getHeight();



                    if (l <= 0){
                        l = 0;
                        r = getWidth();
                    } else if (r > ((ViewGroup)getParent()).getWidth()){
                        r = ((ViewGroup)getParent()).getWidth();
                        l = ((ViewGroup)getParent()).getWidth() - getWidth();
                    }
                    if (t <= 0){
                        t = 0;
                        b = getHeight();
                    }else if (b > ((ViewGroup)getParent()).getHeight()){
                        b = ((ViewGroup)getParent()).getHeight();
                        t = ((ViewGroup)getParent()).getHeight() - getHeight();
                    }
                    this.layout(l,t,r,b);
                }
                break;
        }
        return true;
    }
}
