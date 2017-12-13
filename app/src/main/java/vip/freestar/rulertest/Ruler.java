package vip.freestar.rulertest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

/**
 * 描述：
 * 作者：一颗浪星
 * 日期：2017/12/13
 * github：
 */

public class Ruler extends ViewGroup {

    private Paint mOutLinePaint;
    private Drawable mCursorDrawable;

    //光标宽度、高度
    private int mCursorWidth = 8, mCursorHeight = 70;
    private InnerRuler mInnerRuler;

    public Ruler(Context context) {
        this(context, null);
    }

    public Ruler(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Ruler(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initDrawable();
        initInnerRuler(context);
    }

    private void initInnerRuler(Context context) {
        mInnerRuler = new InnerRuler(context, this);
        //设置全屏，加入InnerRuler
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mInnerRuler.setLayoutParams(layoutParams);
        addView(mInnerRuler);
        //设置ViewGroup可画
        setWillNotDraw(false);
    }

    //在宽高初始化之后定义光标 Drawable 的边界
    private void initDrawable() {
        mCursorDrawable = getResources().getDrawable(R.drawable.cursor_shape);
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                getViewTreeObserver().removeOnPreDrawListener(this);
                mCursorDrawable.setBounds((getWidth() - mCursorWidth) / 2, 0
                        , (getWidth() + mCursorWidth) / 2, mCursorHeight);
                return false;
            }
        });

    }

    private void initPaint() {
        mOutLinePaint = new Paint();
        mOutLinePaint.setStrokeWidth(1);
        mOutLinePaint.setColor(Color.BLUE);
    }

    // 如果不给 ViewGroup 设置一个背景，系统是不会调用onDraw时，也就是说，我们重写的onDraw是不会调用的。当设置一个背景后，onDraw就会被调用。
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("FreeStar", "Ruler→→→onDraw:" + canvas.getWidth() + "--" + getWidth());
        //画上面的轮廓线
        canvas.drawLine(0, 0, canvas.getWidth(), 0, mOutLinePaint);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        Log.e("FreeStar", "Ruler→→→dispatchDraw:");
        //画中间的选定光标，要在这里画，因为dispatchDraw()执行在onDraw()后面，这样子光标才能不被尺子的刻度遮蔽
        mCursorDrawable.draw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mInnerRuler.layout(0, 0, r - l, b - t);
    }

}
