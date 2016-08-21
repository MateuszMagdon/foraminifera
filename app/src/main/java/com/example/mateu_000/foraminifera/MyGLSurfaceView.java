package com.example.mateu_000.foraminifera;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;


public class MyGLSurfaceView extends GLSurfaceView{
    public MyGLRenderer mRenderer;

    private float mPreviousX = 0.0f;
    private float mPreviousY = 0.0f;

    private float mDensity;

    public MyGLSurfaceView(Context context) {
        super(context);
    }

    // Hides superclass method.
    public void setRenderer(MyGLRenderer renderer, float density)
    {
        mRenderer = renderer;
        mDensity = density;
        super.setRenderer(renderer);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        int pointerCount = event.getPointerCount();

        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                float deltaX = (x - mPreviousX) / mDensity / 2f;
                float deltaY = (y - mPreviousY) / mDensity / 2f;

                if (pointerCount == 1){
                    mRenderer.mDeltaRotationX += deltaX;
                    mRenderer.mDeltaRotationY += deltaY;
                } else {
                    mRenderer.mDeltaTranslationX += deltaX/15;
                    mRenderer.mDeltaTranslationY += deltaY/15;
                }

        }
        mPreviousX = x;
        mPreviousY = y;

        return true;
    }


}
