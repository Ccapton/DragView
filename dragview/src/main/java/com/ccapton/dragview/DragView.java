package com.ccapton.dragview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class DragView extends RelativeLayout {


    private int defaultAlignDistance = 40;

    private float alignDistance ;

    private boolean leftAlign = true;
    private boolean rightAlign = true;
    private boolean topAlign = true;
    private boolean bottomAlign = true;

    private OnDragViewClickListener onDragViewClickListener;


    public  interface OnDragViewClickListener{
        void onDragViewClick(View view);
    }

    public float getAlignDistance() {
        return alignDistance;
    }

    public void setAlignDistance(float alignDistance) {
        this.alignDistance = alignDistance;
    }

    public boolean isLeftAlign() {
        return leftAlign;
    }

    public void setLeftAlign(boolean leftAlign) {
        this.leftAlign = leftAlign;
    }

    public boolean isRightAlign() {
        return rightAlign;
    }

    public void setRightAlign(boolean rightAlign) {
        this.rightAlign = rightAlign;
    }

    public boolean isTopAlign() {
        return topAlign;
    }

    public void setTopAlign(boolean topAlign) {
        this.topAlign = topAlign;
    }

    public boolean isBottomAlign() {
        return bottomAlign;
    }

    public void setBottomAlign(boolean bottomAlign) {
        this.bottomAlign = bottomAlign;
    }

    public OnDragViewClickListener getOnDragViewClickListener() {
        return onDragViewClickListener;
    }

    public void setOnDragViewClickListener(OnDragViewClickListener onDragViewClickListener) {
        this.onDragViewClickListener = onDragViewClickListener;
    }

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

        leftAlign = typedArray.getBoolean(R.styleable.DragView_leftAlign,true);
        rightAlign = typedArray.getBoolean(R.styleable.DragView_rightAlign,true);
        topAlign = typedArray.getBoolean(R.styleable.DragView_topAlign,true);
        bottomAlign = typedArray.getBoolean(R.styleable.DragView_bottomAlign,true);

        alignDistance = typedArray.getDimension(R.styleable.DragView_alignDistance,defaultAlignDistance);

        typedArray.recycle();

    }

    protected float dp2px(float dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private float oldX,oldY;

    private int pressCount = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                pressCount ++;
                oldX = event.getX();
                oldY = event.getY();
                return  super.onTouchEvent(event);
            case MotionEvent.ACTION_MOVE:
                pressCount ++;
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

            case MotionEvent.ACTION_UP:
                if (pressCount == 1) {
                    if (this.onDragViewClickListener != null)
                        this.onDragViewClickListener.onDragViewClick(this);
                }
                pressCount = 0;

                if (getX() <= alignDistance) {
                    if (leftAlign)
                        this.layout(0, (int) getY(), getWidth(), (int) getY() + getHeight());
                } else if (getX() + getWidth() >= ((ViewGroup)getParent()).getWidth() - alignDistance) {
                    if (rightAlign)
                        this.layout(((ViewGroup) getParent()).getWidth() - getWidth(), (int) getY(), ((ViewGroup) getParent()).getWidth(), (int) getY() + getHeight());
                }
                if (getY() <= alignDistance) {
                    if (topAlign)
                        this.layout((int) getX(), 0, (int) getX() + getWidth(), getHeight());
                }
                else if ( getY() + getHeight() >= ((ViewGroup)getParent()).getHeight() - alignDistance) {
                    if (bottomAlign)
                        this.layout((int) getX(), ((ViewGroup) getParent()).getHeight() - getHeight(), (int) getX() + getWidth(), ((ViewGroup) getParent()).getHeight());
                }
                break;
        }
        return true;
    }
}
