package com.fenda.onn.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.fenda.onn.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: david.lvfujiang
 * @Date: 2019/12/12
 * @Describe: 自定义滚动圆盘View
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class ShapeView extends View {
    private final int CIRCLE_CENTER_X = 365;
    private final int CIRCLE_CENTER_Y = 365;
    private final int CIRCLE_RADIO = 365;
    private final int PAINT_STROKE_WIDTH = 170;
    static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();
    private Paint mCirclePaint, mArcPaint;
    private int centerX, centerY;
    int dX = 0, dY = 0;
    int x = 0, y = 0;
    int single;
    boolean isShowEffects = false;
    boolean isShowArc = false;
    int[] colorArr = {Color.parseColor("#3fc9fa"), Color.parseColor("#a0a9fe"),
            Color.parseColor("#e8b7fe"), Color.parseColor("#f7603b"),
            Color.parseColor("#fea928"), Color.parseColor("#d1f0fe"),
            Color.parseColor("#a0e057"), Color.parseColor("#88fef5"),
            Color.parseColor("#3fc9fa")
    };
    private AngleCallbackListener angleCallbackListener;

    {
        //初始化圆弧画笔
        mArcPaint = new Paint();
        mArcPaint.setStrokeWidth(120);
        mArcPaint.setAntiAlias(true);
        //设置画笔形状：圆头
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setColor(Color.WHITE);
        //给画笔设置渐变效果，扫描渐变
        Shader shader1 = new SweepGradient(CIRCLE_CENTER_X, CIRCLE_CENTER_Y, colorArr, null);
        mArcPaint.setShader(shader1);

        mCirclePaint = new Paint();
        mCirclePaint.setAlpha(0);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(Color.WHITE);
        Shader shader = new RadialGradient(x, y, 180, Color.WHITE,
                0X00FFFFFF, Shader.TileMode.CLAMP);
        mCirclePaint.setShader(shader);

    }

    public ShapeView(Context context) {
        super(context);
    }

    public ShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShapeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ShapeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setAngleCallbackListener(ShapeView.AngleCallbackListener angleCallbackListener) {
        this.angleCallbackListener = angleCallbackListener;
    }

    public void setShowEffects(boolean showEffects) {
        isShowEffects = showEffects;
    }

    /**
     * 绘制圆盘
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        Log.e("TAG", "onDraw");
        super.onDraw(canvas);
        if (isShowArc) {
            //绘制圆弧
            canvas.drawArc(CIRCLE_CENTER_X - 270, CIRCLE_CENTER_Y - 270,
                    CIRCLE_CENTER_X + 270, CIRCLE_CENTER_Y + 270, 360 - single,
                    60, false, mArcPaint);
            //绘制高光圆点
            canvas.drawCircle(x, y, 180, mCirclePaint);
        }
    }

    /**
     * 点击事件、滚动事件拦截
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                isShowArc = true;
                x = (int) ev.getX();
                y = (int) ev.getY();
                calulateOffset(x, y);
                if (isShowEffects) {
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                isShowArc = false;
                invalidate();
                break;
        }

        return true;
    }

    public void calulateOffset(int x, int y) {

        if (x > CIRCLE_CENTER_X) {
            dX = x - CIRCLE_CENTER_X;
        } else {
            dX = -(CIRCLE_CENTER_X - x);
        }
        if (y > CIRCLE_CENTER_Y) {
            dY = -(y - CIRCLE_CENTER_Y);
        } else {
            dY = CIRCLE_CENTER_Y - y;
        }
        single = calulateXYAnagle(0, 0, dX, dY);
        Log.e("TAG", single + "");
        if (angleCallbackListener != null) {
            angleCallbackListener.getAngle(this, single);
        }
    }

    /**
     * 计算角度
     *
     * @param startx
     * @param starty
     * @param endx
     * @param endy
     * @return
     */
    private int calulateXYAnagle(double startx, double starty, double endx,
                                 double endy) {
        float tan = (float) (Math.atan(Math.abs((endy - starty)
                / (endx - startx))) * 180 / Math.PI);
        if (endx < startx && endy > starty)// 第二象限
        {
            return (int) (90 + (90 - tan));
        } else if (endx > startx && endy > starty)// 第一象限
        {
            return (int) tan;
        } else if (endx < startx && endy < starty)// 第三象限
        {
            return (int) (tan + 180);
        } else {
            return (int) (270 + (90 - tan));
        }
    }

    /**
     * 回调接口
     */
    public interface AngleCallbackListener {
        public void getAngle(View view, int angle);
    }
}
