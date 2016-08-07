package com.example.mateu_000.foraminifera;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity
{
    private MyGLSurfaceView mGLSurfaceView2;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mGLSurfaceView2 = new MyGLSurfaceView(this);
        mGLSurfaceView2.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        MyGLRenderer renderer = new MyGLRenderer();
        mGLSurfaceView2.setRenderer(renderer);
        mGLSurfaceView2.mRenderer = renderer;
        setContentView(mGLSurfaceView2);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        //mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        //mGLSurfaceView.onPause();
    }
}