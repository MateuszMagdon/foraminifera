package com.example.mateu_000.foraminifera;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by mateu_000 on 2015-02-09.
 */
public class Stage extends GLSurfaceView{

    public Stage(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setEGLConfigChooser(8 , 8, 8, 8, 16, 0);
        setRenderer(new MyRenderer());
    }


    private class MyRenderer implements Renderer{

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            // Set up alpha blending
            gl.glEnable(GL10.GL_ALPHA_TEST);
            gl.glEnable(GL10.GL_BLEND);

            // We will discuss this line later along with textures
            gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);

            // We are in 2D. Who needs depth?
            gl.glDisable(GL10.GL_DEPTH_TEST);

            // Enable vertex arrays (we'll use them to draw primitives).
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

            // Enable texture coordinate arrays.
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            gl.glClearColor(0, 0, 0, 1);

            float w, h;

            if(width > height) {
                h = 600;
                w = width * h / height;
            } else {
                w = 600;
                h = height * w / width;
            }

            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();
            gl.glOrthof(0, w, h, 0, -1, 1);
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();
        }

        @Override
        public void onDrawFrame(GL10 gl) {

        }
    }
}
