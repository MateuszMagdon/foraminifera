package com.example.mateu_000.foraminifera;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import Helpers.SettingsContainer;
import Model.Foraminifera;
import OpenGL.MyGLRenderer;
import OpenGL.MyGLSurfaceView;

public class MainActivity extends ActionBarActivity
{
    private MyGLSurfaceView mGLSurfaceView;
    private MyGLRenderer mRenderer;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        LinearLayout layout = (LinearLayout) findViewById(R.id.activity_main);

        mGLSurfaceView = new MyGLSurfaceView(this);

        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;

        if (supportsEs2)
        {
            // Request an OpenGL ES 2.0 compatible context.
            mGLSurfaceView.setEGLContextClientVersion(2);

            final DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            Foraminifera foraminifera = buildForaminifera();
            SettingsContainer.foraminifera = foraminifera;
            float[] clipping = buildClippingVector();

            mRenderer = new MyGLRenderer(foraminifera, clipping);
            mGLSurfaceView.setRenderer(mRenderer, displayMetrics.density);
        }
        else
        {
            return;
        }

        layout.addView(mGLSurfaceView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private Foraminifera buildForaminifera() {
        Foraminifera foraminifera = new Foraminifera();

        for (int index = 1; index < SettingsContainer.numberOfChambers; index++) {
            foraminifera.addNextShell();
        }
        return foraminifera;
    }

    private float[] buildClippingVector(){
        float[] clippingVector = new float[3];
        clippingVector[0] = (float) SettingsContainer.clippingX;
        clippingVector[1] = (float) SettingsContainer.clippingY;
        clippingVector[2] = (float) SettingsContainer.clippingZ;
        return clippingVector;
    }

    public void GoToDetails(View view) {
        GoToDetails();
    }

    private void GoToDetails(){
        Intent intent = new Intent(this, DetailsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume()
    {
        // The activity must call the GL surface view's onResume() on activity onResume().
        super.onResume();
        mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause()
    {
        // The activity must call the GL surface view's onPause() on activity onPause().
        super.onPause();
        mGLSurfaceView.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_go_to_details:
                GoToDetails();
                break;
        }
        return true;
    }
}