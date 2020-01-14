package com.fenda.onn.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @Author: david.lvfujiang
 * @Date: 2019/12/12
 * @Describe: 自定义滚动圆盘View
 */
public class ShapeView extends View {
    private final int CIRCLE_CENTER_X = 365;
    private final int CIRCLE_CENTER_Y = 365;
    private final int CIRCLE_RADIO = 365;
    private final int PAINT_STROKE_WIDTH = 170;
    static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();
    private Paint paint;
    private int centerX, centerY;
    int dX = 0, dY = 0;
    int x = 0, y = 0;
    boolean isShowEffects = false;
    private AngleCallbackListener angleCallbackListener;

    public void setAngleCallbackListener(ShapeView.AngleCallbackListener angleCallbackListener) {
        this.angleCallbackListener = angleCallbackListener;
    }

    public void setShowEffects(boolean showEffects) {
        isShowEffects = showEffects;
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

    /**
     * 绘制圆盘
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        Log.e("TAG", "onDraw");
        super.onDraw(canvas);
        if (isShowEffects){
            Paint paint = new Paint();
            paint.setAlpha(0);
            paint.setAntiAlias(true);
            paint.setColor(Color.WHITE);
            Shader shader = new RadialGradient(x, y, 180, Color.WHITE,
                    0X00FFFFFF, Shader.TileMode.CLAMP);
            paint.setShader(shader);
            canvas.drawCircle(x, y, 180, paint);
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
        x = (int) ev.getX();
        y = (int) ev.getY();

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
        int single = calulateXYAnagle(0, 0, dX, dY);
        Log.e("TAG", single + "");
        if (angleCallbackListener != null) {
            angleCallbackListener.getAngle(this, single);
        }
        if (isShowEffects){
            invalidate();
        }
        return true;
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
