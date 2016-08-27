package OpenGL;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import OpenGL.MyGLRenderer;


public class MyGLSurfaceView extends GLSurfaceView{
    public MyGLRenderer mRenderer;

    private float mPreviousX = 0.0f;
    private float mPreviousY = 0.0f;

    private float mScaleFactor = 1.0f;

    private float mDensity;

    private ScaleGestureDetector mScaleGestureDetector;

    public MyGLSurfaceView(Context context) {
        super(context);
        mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
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

        mScaleGestureDetector.onTouchEvent(event);

        float x = event.getX(0);
        float y = event.getY(0);

        int pointerCount = event.getPointerCount();

        if (!mScaleGestureDetector.isInProgress()) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_MOVE:
                    float deltaX = (x - mPreviousX) / mDensity / 2.0f;
                    float deltaY = (y - mPreviousY) / mDensity / 2.0f;

                    if (pointerCount == 1) {
                        mRenderer.mDeltaRotationX += deltaX;
                        mRenderer.mDeltaRotationY += deltaY;
                    } else {
                        mRenderer.mDeltaTranslationX += deltaX / 15;
                        mRenderer.mDeltaTranslationY += deltaY / 15;
                    }

            }
        }

        mPreviousX = x;
        mPreviousY = y;

        return true;
    }


    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();

            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

            mRenderer.mScaleFactor = mScaleFactor;

            return true;
        }
    }
}

