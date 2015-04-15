package com.example.mateu_000.foraminifera;

import android.content.Context;
import android.opengl.EGLConfig;
import android.opengl.GLU;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by mateu_000 on 2015-04-15.
 */
public class Renderer {
    /**
     * These values are used to rotate the image by a certain value
     */
    private float xRot;
    private float yRot;

    private Sphere mSphere;

    public void setxRot(float xRot) {
        this.xRot += xRot;
    }

    public void setyRot(float yRot) {
        this.yRot += yRot;
    }

    public Renderer(Context ctx) {
        //initialize our 3D triangle here
        mSphere = new Sphere(1, 25);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //initialize all the things required for openGL configurations
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
    }

    public void onDrawFrame(GL10 gl) {
        //write the drawing code needed
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();

        /**
         * Change this value in z if you want to see the image zoomed in
         */
        gl.glTranslatef(0.0f, 0.0f, -5.0f);

        gl.glRotatef(xRot, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(yRot, 1.0f, 0.0f, 0.0f);
        mSphere.draw(gl);
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if(height == 0) {
            height = 1;
        }

        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();

        //Calculate The Aspect Ratio Of The Window
        GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 100.0f);

        gl.glMatrixMode(GL10.GL_MODELVIEW);     //Select The Modelview Matrix
        gl.glLoadIdentity();                    //Reset The Modelview Matrix
    }
}
