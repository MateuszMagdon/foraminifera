package com.example.mateu_000.foraminifera;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public abstract class SphereFactory {
    protected static final int pointsPerVertex = 3;
    protected static final double degree = Math.ceil(100 * Math.PI/180) / 100;
    protected static final int floatSize = Float.SIZE / Byte.SIZE;

    public FloatBuffer sphereVerticesBuffer;

    public int pointsCount = 0;

    protected double radius;
    protected int stepSize;

    protected double delta;

    public SphereFactory(double radius, int stepSize) {
        this.radius = radius;
        this.stepSize = stepSize;
        delta = stepSize * degree;

        int bufferCapacity = calculateBufferCapacity();
        ByteBuffer sphereVertexByte = ByteBuffer.allocateDirect(bufferCapacity);
        sphereVertexByte.order(ByteOrder.nativeOrder());
        sphereVerticesBuffer = sphereVertexByte.asFloatBuffer();

        calculateVertices();
    }

    protected abstract void calculateVertices();

    protected abstract int calculateBufferCapacity();
}
