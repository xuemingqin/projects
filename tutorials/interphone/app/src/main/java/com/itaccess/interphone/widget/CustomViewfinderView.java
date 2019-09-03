package com.itaccess.interphone.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.ViewfinderView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linxi on 2017/12/14.
 */

public class CustomViewfinderView extends ViewfinderView {

    public static final long CUSTOME_ANIMATION_DELAY = 16;

    /**
     * 角線の長さ
     */
    public float mLineRate = 0.1F;

    /**
     * 角線の高さ
     */
    public float mLineDepth =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());

    /**
     * 角線の色
     */
    public int mLineColor = Color.WHITE;

    /**
     * スキャンナーのスタート
     */
    public int mScanLinePosition = 0;

    /**
     * スキャンナー線の幅
     */
    public float mScanLineDepth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());

    /**
     * スキャンナーの移動距離
     */
    public float mScanLineDy = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());

    public LinearGradient mLinearGradient;

    public float[] mPositions = new float[]{0f, 0.5f, 1f};

    public int[] mScanLineColor = new int[]{0x00FFFFFF, Color.WHITE, 0x00FFFFFF};


    public CustomViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onDraw(Canvas canvas) {
        refreshSizes();
        if (framingRect == null || previewFramingRect == null) {
            return;
        }

        Rect frame = framingRect;
        Rect previewFrame = previewFramingRect;

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        //角線の描き
        paint.setColor(mLineColor);
        canvas.drawRect(frame.left, frame.top, frame.left + frame.width() * mLineRate, frame.top + mLineDepth, paint);
        canvas.drawRect(frame.left, frame.top, frame.left + mLineDepth, frame.top + frame.height() * mLineRate, paint);

        canvas.drawRect(frame.right - frame.width() * mLineRate, frame.top, frame.right, frame.top + mLineDepth, paint);
        canvas.drawRect(frame.right - mLineDepth, frame.top, frame.right, frame.top + frame.height() * mLineRate, paint);

        canvas.drawRect(frame.left, frame.bottom - mLineDepth, frame.left + frame.width() * mLineRate, frame.bottom, paint);
        canvas.drawRect(frame.left, frame.bottom - frame.height() * mLineRate, frame.left + mLineDepth, frame.bottom, paint);

        canvas.drawRect(frame.right - frame.width() * mLineRate, frame.bottom - mLineDepth, frame.right, frame.bottom, paint);
        canvas.drawRect(frame.right - mLineDepth, frame.bottom - frame.height() * mLineRate, frame.right, frame.bottom, paint);

        // Draw the exterior (i.e. outside the framing rect) darkened
        paint.setColor(resultBitmap != null ? resultColor : maskColor);
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);

        if (resultBitmap != null) {
            // Draw the opaque result bitmap over the scanning rectangle
            paint.setAlpha(CURRENT_POINT_OPACITY);
            canvas.drawBitmap(resultBitmap, null, frame, paint);
        } else {
            mScanLinePosition += mScanLineDy;
            if(mScanLinePosition > frame.height()){
                mScanLinePosition = 0;
            }
            mLinearGradient = new LinearGradient(frame.left, frame.top + mScanLinePosition, frame.right, frame.top + mScanLinePosition, mScanLineColor, mPositions, Shader.TileMode.CLAMP);
            paint.setShader(mLinearGradient);
            canvas.drawRect(frame.left, frame.top + mScanLinePosition, frame.right, frame.top + mScanLinePosition + mScanLineDepth, paint);
            paint.setShader(null);

            float scaleX = frame.width() / (float) previewFrame.width();
            float scaleY = frame.height() / (float) previewFrame.height();

            List<ResultPoint> currentPossible = possibleResultPoints;
            List<ResultPoint> currentLast = lastPossibleResultPoints;
            int frameLeft = frame.left;
            int frameTop = frame.top;
            if (currentPossible.isEmpty()) {
                lastPossibleResultPoints = null;
            } else {
                possibleResultPoints = new ArrayList<>(5);
                lastPossibleResultPoints = currentPossible;
                paint.setAlpha(CURRENT_POINT_OPACITY);
                paint.setColor(resultPointColor);
                for (ResultPoint point : currentPossible) {
                    canvas.drawCircle(frameLeft + (int) (point.getX() * scaleX),
                            frameTop + (int) (point.getY() * scaleY),
                            POINT_SIZE, paint);
                }
            }
            if (currentLast != null) {
                paint.setAlpha(CURRENT_POINT_OPACITY / 2);
                paint.setColor(resultPointColor);
                float radius = POINT_SIZE / 2.0f;
                for (ResultPoint point : currentLast) {
                    canvas.drawCircle(frameLeft + (int) (point.getX() * scaleX),
                            frameTop + (int) (point.getY() * scaleY),
                            radius, paint);
                }
            }
        }

        // Request another update at the animation interval, but only repaint the laser line,
        // not the entire viewfinder mask.
        postInvalidateDelayed(CUSTOME_ANIMATION_DELAY,
                frame.left,
                frame.top,
                frame.right,
                frame.bottom);
    }
}
