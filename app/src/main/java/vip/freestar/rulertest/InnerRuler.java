package vip.freestar.rulertest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.Log;
import android.view.View;

/**
 * 描述：
 * 作者：一颗浪星
 * 日期：2017/12/13
 * github：
 */

public class InnerRuler extends View {

    private Ruler mParent;
    private Paint mSmallScalePaint;
    private Paint mBigScalePaint;
    private Paint mTextPaint;
    private int mDrawOffset;

    public InnerRuler(Context context, Ruler ruler) {
        super(context);
        mParent = ruler;
        init(context);
    }

    private void init(Context context) {
        initPaints();
        mDrawOffset = 10 * 18 / 2;
        checkAPILevel();
    }

    //API小于18则关闭硬件加速，否则setAntiAlias()方法不生效
    private void checkAPILevel() {
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(LAYER_TYPE_NONE, null);
        }
    }

    private void initPaints() {
        mSmallScalePaint = new Paint();
        mSmallScalePaint.setStrokeWidth(5);
        mSmallScalePaint.setColor(Color.RED);
        mSmallScalePaint.setStrokeCap(Paint.Cap.ROUND);

        mBigScalePaint = new Paint();
        mBigScalePaint.setColor(Color.RED);
        mBigScalePaint.setStrokeWidth(8);
        mBigScalePaint.setStrokeCap(Paint.Cap.ROUND);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.RED);
        mTextPaint.setTextSize(22);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("FreeStar", "InnerRuler→→→onDraw:" + mDrawOffset);

        for (float i = 500; i <= 600; i++) {
            float locationX = (i - 500) * 18;

            if (locationX > getScrollX() - mDrawOffset && locationX < (getScrollX() + canvas.getWidth() + mDrawOffset)) {
                if (i % 10 == 0) {
                    canvas.drawLine(locationX, 0, locationX, 60, mBigScalePaint);
                    canvas.drawText(String.valueOf(i / 10), locationX, 100, mTextPaint);
                } else {
                    canvas.drawLine(locationX, 0, locationX, 30, mSmallScalePaint);
                }
            }
        }
    }
}
