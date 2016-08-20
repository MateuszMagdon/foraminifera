package com.example.mateu_000.foraminifera;


import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class SphereVertices {

    private static final int pointsPerVertex = 3;
    private double degree;

    public FloatBuffer sphereVertex;
    private ByteBuffer sphereVertexByte;

    public int pointsCount = 0;

    private double radius;
    private int stepSize;


    public SphereVertices(float radius, int step) {
        this.radius = radius;
        this.stepSize = step;

        degree = Math.ceil(100 * Math.PI/180) / 100;

        double delta = stepSize * degree;
        int pointsPerPI = (int)(Math.ceil(Math.PI / delta));
        int capacity = pointsPerVertex * Float.SIZE / Byte.SIZE * pointsPerPI * 2 * pointsPerPI;

        sphereVertexByte = ByteBuffer.allocateDirect(capacity);
        sphereVertexByte.order(ByteOrder.nativeOrder());
        sphereVertex = sphereVertexByte.asFloatBuffer();

        calculateVertices();
    }

    private void calculateVertices() {

        /**
         * x = p * sin(phi) * cos(theta)
         * y = p * sin(phi) * sin(theta)
         * z = p * cos(phi)
         */
        double delta = stepSize * degree;

        for(double phi = -(Math.PI); phi <= 0; phi+=delta) {
            //for each stage calculating the slices
            for(double theta = 0.0; theta <= (Math.PI * 2); theta+=delta) {

                sphereVertex.put((float) (radius * Math.sin(phi) * Math.cos(theta)) );
                sphereVertex.put((float) (radius * Math.sin(phi) * Math.sin(theta)) );
                sphereVertex.put((float) (radius * Math.cos(phi)) );
                pointsCount++;
            }
        }
        sphereVertex.position(0);
    }
}

