package com.example.mateu_000.foraminifera;


import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

//public class Sphere {
//    public void draw(GL10 gl, float xOffset) {
//
//        float angleA, angleB;
//        float cos, sin;
//        float r1, r2;
//        float h1, h2;
//        float step = 2.0f;
//        float[][] v = new float[32][3];
//        ByteBuffer vbb;
//        FloatBuffer vBuf;
//
//        vbb = ByteBuffer.allocateDirect(v.length * v[0].length * 4);
//        vbb.order(ByteOrder.nativeOrder());
//        vBuf = vbb.asFloatBuffer();
//
//        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
//
//        for (angleA = -90.0f; angleA < 90.0f; angleA += step) {
//            int n = 0;
//
//            r1 = (float) Math.cos(angleA * Math.PI / 180.0);
//            r2 = (float) Math.cos((angleA + step) * Math.PI / 180.0);
//            h1 = (float) Math.sin(angleA * Math.PI / 180.0);
//            h2 = (float) Math.sin((angleA + step) * Math.PI / 180.0);
//
//            // Fixed latitude, 360 degrees rotation to traverse a weft
//            for (angleB = 0.0f; angleB <= 360.0f; angleB += step) {
//
//                cos = (float) Math.cos(angleB * Math.PI / 180.0);
//                sin = -(float) Math.sin(angleB * Math.PI / 180.0);
//
//                v[n][0] = (r2 * cos) + xOffset;
//                v[n][1] = (h2) + xOffset;
//                v[n][2] = (r2 * sin) + xOffset;
//                v[n + 1][0] = (r1 * cos);
//                v[n + 1][1] = (h1);
//                v[n + 1][2] = (r1 * sin);
//
//                vBuf.put(v[n]);
//                vBuf.put(v[n + 1]);
//
//                n += 2;
//
//                if (n > 31) {
//                    vBuf.position(0);
//
//                    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vBuf);
//                    gl.glNormalPointer(GL10.GL_FLOAT, 0, vBuf);
//                    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);
//
//                    n = 0;
//                    angleB -= step;
//                }
//
//            }
//            vBuf.position(0);
//
//            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vBuf);
//            gl.glNormalPointer(GL10.GL_FLOAT, 0, vBuf);
//            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);
//        }
//
//        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
//        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
//    }
public class Sphere {

    static private FloatBuffer sphereVertex;
    static private ByteBuffer sphereVertexByte;
    static private FloatBuffer sphereNormal;
    static float sphere_parms[]=new float[3];

    double mRaduis;
    double mStep;
    float mVertices[];
    private static double DEG = Math.PI/180;
    int mPoints;

    /**
     * The value of step will define the size of each facet as well as the number of facets
     *
     * @param radius
     * @param step
     */

    public Sphere( float radius, double step) {
        this.mRaduis = radius;
        this.mStep = step;
        sphereVertexByte = ByteBuffer.allocateDirect(40000);
        sphereVertexByte.order(ByteOrder.nativeOrder());
        sphereVertex = sphereVertexByte.asFloatBuffer();
        mPoints = build();
        Log.d("ALIS CHECK!!!!!!", " COUNT:" + mPoints);
    }

    public void draw(GL10 gl) {
        gl.glFrontFace(GL10.GL_CW);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, sphereVertex);

        gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
        gl.glDrawArrays(GL10.GL_POINTS, 0, mPoints);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }

    private int build() {

        /**
         * x = p * sin(phi) * cos(theta)
         * y = p * sin(phi) * sin(theta)
         * z = p * cos(phi)
         */
        double dTheta = mStep * DEG;
        double dPhi = dTheta;
        int points = 0;

        for(double phi = -(Math.PI); phi <= 0; phi+=dPhi) {
            //for each stage calculating the slices
            for(double theta = 0.0; theta <= (Math.PI * 2); theta+=dTheta) {
                sphereVertex.put((float) (mRaduis * Math.sin(phi) * Math.cos(theta)) );
                sphereVertex.put((float) (mRaduis * Math.sin(phi) * Math.sin(theta)) );
                sphereVertex.put((float) (mRaduis * Math.cos(phi)) );
                points++;

            }
        }
        sphereVertex.position(0);
        return points;
    }
}

