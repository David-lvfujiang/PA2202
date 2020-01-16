package com.fenda.onn.ui.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import com.fenda.onn.R;

/**
 * @author David-lvfujiang
 * @time 2019/12/30 17:18
 * desc  FM刻度尺
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class RulerView extends View {
    /**
     * 最大的滑动速度
     */
    private static final int MAX_FLING_SPEED = 500;
    private static final String KG = "MHZ";
    private static final int ONE_KG = 1000;
    private static final int MAX_FLING_WEIGHT = 1050;
    private final int TWO = 2;
    private final int THREE = 3;
    /**
     * 最大的滑动动画持续时间
     */
    private static final int MAX_FLING_WEIGHT_DURATION = 2000;
    private LinearOutSlowInInterpolator mLinearOutSlowInInterpolator
            = new LinearOutSlowInInterpolator();
    /**
     * FM最小频率
     */
    private int minWeight = 87500;
    /**
     * FM最大频率
     */
    private int maxWeight = 108100;
    /**
     * 默认频率
     */
    private int bodyWeight = 95500;
    /**
     * FM的文字大小
     */
    private int textSizeWeight = sp2pix(48);
    /**
     * MHZ字的文字大小
     */
    private int textSizeKg = sp2pix(20);
    /**
     * 提示的文字大小
     */
    private int textPrompt = sp2pix(18);
    /**
     * 刻度基线的宽度
     */
    private int lineWidthBase = dp2pix(1);
    /**
     * 刻度线的宽度
     */
    private int lineWidthG = dp2pix(2);
    /**
     * 长刻度线的高度
     */
    private int lineHeightG = dp2pix(20);
    /**
     * 短刻度线的宽度
     */
    private int lineWidthKg = dp2pix(4);
    /**
     * 中心线
     */
    private int lineHeightKg = dp2pix(40);
    /**
     * 每个的刻度线数
     */
    private int scaleTableGNum = 5;
    /**
     * 每格的宽度
     */
    private int scaleTableGWidth = dp2pix(10);
    /**
     * FM频率值
     */
    String weightStr = "95.5";
    String result;
    private Paint mWeightTextPain;
    private Paint mScaleLinePain;
    private Paint mScaleWeightTextPain;
    /**
     * 遮罩的画笔
     */
    private Paint mForegroundPaint;
    private Paint mRoundRectPait;
    private Paint mRoundRectBorderPait;
    /**
     * 手势识别器
     */
    private GestureDetector mGesture;
    /**
     * 平滑动画
     */
    private ObjectAnimator mFlingAnim;
    private FlingAnimUpdateListener mFlingAnimUpdateListener = new FlingAnimUpdateListener();
    private FmRateUpdateListener fmRateUpdateListener;
    private BodyWeightUpdateListener mBodyWeightUpdateListener;

    //构造代码块，初始化画笔
    {
        mWeightTextPain = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWeightTextPain.setColor(getResources().getColor(R.color.fd_text_3c88d4));
        mWeightTextPain.setStrokeWidth(lineWidthKg);

        mScaleLinePain = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScaleLinePain.setColor(Color.BLACK);

        mScaleWeightTextPain = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScaleWeightTextPain.setColor(Color.BLACK);
        mScaleWeightTextPain.setTextSize(sp2pix(18));
        mScaleWeightTextPain.setTextAlign(Paint.Align.CENTER);
        mScaleWeightTextPain.setAntiAlias(true);

        mRoundRectPait = new Paint();
        mRoundRectPait.setColor(getResources().getColor(R.color.fd_text_3c88d4, null));
        mRoundRectPait.setStrokeWidth(lineWidthKg);
        mRoundRectPait.setAntiAlias(true);

        mRoundRectBorderPait = new Paint();
        mRoundRectBorderPait.setColor(getResources().getColor(R.color.fd_text_3c88d4, null));
        mRoundRectBorderPait.setStrokeWidth(15);
        mRoundRectBorderPait.setStyle(Paint.Style.STROKE);
        mRoundRectBorderPait.setAntiAlias(true);

        mGesture = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (mFlingAnim != null) {
                    mFlingAnim.cancel();
                }
                bodyWeight += distanceX * scaleTableGWidth;
                if (bodyWeight >= maxWeight) {
                    bodyWeight = maxWeight;
                } else if (bodyWeight <= minWeight) {
                    bodyWeight = minWeight;
                }
                invalidate();
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                int target;
                if (velocityX > 0) {
                    target = bodyWeight - getTargetWeightByVelocityX(velocityX);
                } else {
                    target = bodyWeight + getTargetWeightByVelocityX(velocityX);
                }
                return true;
            }
        });
        mGesture.setIsLongpressEnabled(false);
    }

    public RulerView(Context context) {
        this(context, null);
    }

    public RulerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RulerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 事件拦截
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGesture.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (mFlingAnim != null && !mFlingAnim.isRunning()) {
                startSmoothAnim(revisedTarget(bodyWeight), 100);
            }
            if (fmRateUpdateListener != null) {
                //返回角度
                fmRateUpdateListener.callbackFmRate(weightStr);
            }
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //计算当前的FM
        int saved = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
        StringBuffer floattStr = new StringBuffer(String.valueOf(bodyWeight % ONE_KG));
        if (floattStr.length() < TWO) {
            result = String.valueOf(bodyWeight / ONE_KG) + ".00" + floattStr;
        } else if (floattStr.length() >= TWO && floattStr.length() < THREE) {
            result = String.valueOf(bodyWeight / ONE_KG) + ".0" + floattStr;
        } else {
            result = String.valueOf(bodyWeight / ONE_KG) + "." + floattStr;
        }
        double f = Double.valueOf(result.trim());
        Log.e("TAG", result);
        //得到角度
        weightStr = String.format("%.1f", f);
        if (fmRateUpdateListener != null) {
            //返回角度
            fmRateUpdateListener.callbackFmRate(weightStr);
        }

        int canvasWidth = canvas.getWidth();
        int centerX = canvasWidth / TWO;
        int centerY = canvas.getHeight() / THREE;
        //画刻度基准线
        mWeightTextPain.setColor(getResources().getColor(R.color.fd_text_3c88d4, null));
        mScaleLinePain.setStrokeWidth(lineWidthBase);
        canvas.drawLine(0, centerY, canvasWidth, centerY, mScaleLinePain);

        //画刻度线
        int everyScaleG = ONE_KG / scaleTableGNum;
        float offset = bodyWeight % everyScaleG;
        float handOffset = offset / everyScaleG * scaleTableGWidth;
        float currentLeftHandWeight;
        float currentRightHandWeight;
        float leftLineX;
        float rightLineX;
        if (offset == 0) {
            float lineX = centerX;
            float currentHandWeight = bodyWeight;
            drawScaleTable(canvas, centerY, lineX, currentHandWeight);
            currentLeftHandWeight = bodyWeight - everyScaleG;
            currentRightHandWeight = bodyWeight + everyScaleG;
            leftLineX = lineX - scaleTableGWidth;
            rightLineX = lineX + scaleTableGWidth;

        } else {
            currentLeftHandWeight = bodyWeight - offset % everyScaleG;
            currentRightHandWeight = bodyWeight + everyScaleG - offset % everyScaleG;
            leftLineX = centerX - handOffset;
            rightLineX = centerX + scaleTableGWidth - handOffset;
        }
        while (rightLineX < canvasWidth + TWO * scaleTableGWidth) {
            //从中开始向左画指针
            if (currentLeftHandWeight >= minWeight) {
                drawScaleTable(canvas, centerY, leftLineX, currentLeftHandWeight);
                drawScaleBottomTable(canvas, centerX, centerY, leftLineX + dp2pix(100), currentLeftHandWeight);
            }
            //从中开始向右画指针
            if (currentRightHandWeight <= maxWeight) {
                drawScaleTable(canvas, centerY, rightLineX, currentRightHandWeight);
                drawScaleBottomTable(canvas, centerX, centerY, rightLineX - dp2pix(100), currentRightHandWeight);
            }
            currentLeftHandWeight -= everyScaleG;
            currentRightHandWeight += everyScaleG;
            leftLineX -= scaleTableGWidth;
            rightLineX += scaleTableGWidth;
        }
        //画出中间的指针
        canvas.drawLine(centerX, centerY + dp2pix(30), centerX, centerY - lineHeightKg, mWeightTextPain);
        //绘制遮罩层，用来实现刻度线俩边遮罩效果
        if (mForegroundPaint == null) {
            initForegroundPaint(canvasWidth);
        }
        canvas.drawRect(0, dp2pix(150), canvas.getWidth(), canvas.getHeight(), mForegroundPaint);
        canvas.restoreToCount(saved);
        drawScaleBottomTableShader(canvas, centerX, centerY);
    }

    private void drawScaleBottomTableShader(Canvas canvas, int centerX, int centerY) {
        //给画笔设置渐变效果，中间透明
        mRoundRectPait.setShader(new LinearGradient(centerX - dp2pix(100), (lineHeightKg + dp2pix(20)) / 2 + centerY + dp2pix(30),
                centerX, (lineHeightKg + dp2pix(20)) / 2 + centerY + dp2pix(30), getResources()
                .getColor(R.color.fd_text_3c88d4), 0X00FFFFFF, Shader.TileMode.MIRROR));
        //绘制底部的滑动刻度表遮罩矩形
        canvas.drawRoundRect(centerX - dp2pix(100), centerY + dp2pix(30),
                centerX + dp2pix(100), centerY + lineHeightKg + dp2pix(50), 50, 50, mRoundRectPait);
        //绘制底部的滑动刻度表的矩形边框
        canvas.drawRoundRect(centerX - dp2pix(100), centerY + dp2pix(30),
                centerX + dp2pix(100), centerY + lineHeightKg + dp2pix(50),
                50, 50, mRoundRectBorderPait);
    }

    /**
     * 绘制底部的滑动刻度表
     *
     * @param canvas
     * @param centerX
     * @param centerY
     * @param lineX
     * @param currentHandWeight
     */
    private void drawScaleBottomTable(Canvas canvas, int centerX, int centerY, float lineX, float currentHandWeight) {
        if (lineX >= centerX - dp2pix(90) && lineX <= centerX + dp2pix(90)) {
            canvas.drawLine(lineX, centerY + dp2pix(35), lineX,
                    centerY + lineHeightKg + dp2pix(45), mWeightTextPain);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initForegroundPaint(w);
    }

    private void drawScaleTable(Canvas canvas, int centerY, float lineX, float currentHandWeight) {
        if (currentHandWeight % ONE_KG == 0) {
            mScaleLinePain.setStrokeWidth(lineWidthKg);
            canvas.drawLine(lineX, centerY, lineX, centerY - lineHeightKg, mScaleLinePain);
            canvas.drawText(String.valueOf(currentHandWeight / ONE_KG), lineX,
                    centerY + dp2pix(20), mScaleWeightTextPain);
        } else {
            mScaleLinePain.setStrokeWidth(lineWidthG);
            canvas.drawLine(lineX, centerY, lineX, centerY - lineHeightG, mScaleLinePain);
        }
    }


    public int getBodyWeight() {
        return bodyWeight;
    }

    public void setBodyWeight(int bodyWeight) {
        Log.e("tag", bodyWeight + "");
        if (bodyWeight >= minWeight - 10 && bodyWeight <= maxWeight) {
            this.bodyWeight = bodyWeight;
            invalidate();
        }
    }

    /**
     * @param anim 是否进行平滑动画
     */
    public void setBodyWeight(int bodyWeight, boolean anim) {
        if (anim) {
            startSmoothAnim(bodyWeight, MAX_FLING_WEIGHT_DURATION);
        } else {
            setBodyWeight(bodyWeight);
        }
    }

    /**
     * 开始一个平滑动画
     */
    private void startSmoothAnim(int targetWeight, int duration) {
        if (targetWeight >= maxWeight) {
            targetWeight = maxWeight;
        } else if (targetWeight <= minWeight) {
            targetWeight = minWeight;
        } else {
            targetWeight = revisedTarget(targetWeight);
        }
        if (mFlingAnim != null) {
            mFlingAnim.cancel();
            mFlingAnim = null;
        }
        mFlingAnim = ObjectAnimator.ofInt(RulerView.this,
                "bodyWeight", this.bodyWeight, targetWeight);
        mFlingAnim.setInterpolator(mLinearOutSlowInInterpolator);
        mFlingAnim.setDuration(duration);
        mFlingAnim.addUpdateListener(mFlingAnimUpdateListener);
        mFlingAnim.start();
    }

    /**
     * 对体重值进行修正，以符合刻度
     */
    private int revisedTarget(int targetWeight) {
        int oneScaleBox = ONE_KG / scaleTableGNum;
        int offset = targetWeight % oneScaleBox;
        int half = TWO;
        if (offset != 0) {
            if (offset > oneScaleBox / half) {
                targetWeight += oneScaleBox - offset;
            } else {
                targetWeight -= offset;
            }
        }
        return targetWeight;
    }

    /**
     * 通过滑动速率来得到滑动动画的目标体重值
     */
    private int getTargetWeightByVelocityX(float velocityX) {
        return (int) (MAX_FLING_WEIGHT * mLinearOutSlowInInterpolator
                .getInterpolation(Math.abs(velocityX / MAX_FLING_SPEED)));
    }

    /**
     * 通过滑动速率来得到滑动动画持续时间
     */
    private int getDurationByVelocityX(float velocityX) {
        return (int) (MAX_FLING_WEIGHT_DURATION * mLinearOutSlowInInterpolator
                .getInterpolation(Math.abs(velocityX / MAX_FLING_SPEED)));
    }

    private int dp2pix(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    private int sp2pix(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp
                , Resources.getSystem().getDisplayMetrics());
    }

    public BodyWeightUpdateListener getBodyWeightUpdateListener() {
        return mBodyWeightUpdateListener;
    }

    public void setBodyWeightUpdateListener(BodyWeightUpdateListener bodyWeightUpdateListener) {
        mBodyWeightUpdateListener = bodyWeightUpdateListener;
    }

    public String getWeightStr() {
        return weightStr;
    }

    private void initForegroundPaint(int w) {
        mForegroundPaint = new Paint();
        mForegroundPaint.setShader(new LinearGradient(0, 0, w / TWO, 0,
                0X00FFFFFF, 0XFFFFFFFF, Shader.TileMode.MIRROR));
        mForegroundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
    }

    private class FlingAnimUpdateListener implements ValueAnimator.AnimatorUpdateListener {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            if (mBodyWeightUpdateListener != null) {
                mBodyWeightUpdateListener.update((Integer) animation.getAnimatedValue());
            }
        }
    }

    public void setFmRateUpdateListener(FmRateUpdateListener fmRateUpdateListener) {
        this.fmRateUpdateListener = fmRateUpdateListener;
    }

    public interface BodyWeightUpdateListener {
        void update(int bodyWeight);
    }

    public interface FmRateUpdateListener {
        void callbackFmRate(String rate);
    }
}
