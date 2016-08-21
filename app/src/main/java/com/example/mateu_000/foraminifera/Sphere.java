package com.example.mateu_000.foraminifera;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.LinkedList;
import java.util.List;

import Model.Point;

public class Sphere {
    public int pointsCount = 0;
    public FloatBuffer sphereVerticesBuffer;
    public List<Point> points = new LinkedList<>();

    private static final int pointsPerVertex = 3;
    private static final int floatSize = Float.SIZE / Byte.SIZE;
    private static final double degree = Math.ceil(100 * Math.PI/180) / 100;

    private final Point center;
    private double radius;
    private double delta;

    public Sphere(double radius, int stepSize, Point center) {
        this.radius = radius;
        this.center = center;
        delta = stepSize * degree;

        int bufferCapacity = calculateBufferCapacity();
        ByteBuffer sphereVertexByte = ByteBuffer.allocateDirect(bufferCapacity);
        sphereVertexByte.order(ByteOrder.nativeOrder());
        sphereVerticesBuffer = sphereVertexByte.asFloatBuffer();

        calculateVertices();
    }

    private int calculateBufferCapacity(){
        int pointsPerPI = (int)(Math.ceil(Math.PI / delta));
        return pointsPerVertex * floatSize * pointsPerPI * 2 * pointsPerPI * 6;
    }

    private void calculateVertices() {

        for (double phi = -(Math.PI); phi <= 0; phi += delta) {
            double cosPhi = Math.cos(phi);
            double cosPhiDelta = Math.cos(phi + delta);
            double sinPhi = Math.sin(phi);
            double sinPhiDelta = Math.sin(phi + delta);

            for (double theta = 0.0; theta <= (Math.PI * 2); theta += delta) {

                double cosTheta = (float) Math.cos(theta);
                double cosThetaDelta = (float) Math.cos(theta + delta);
                double sinTheta = (float) Math.sin(theta);
                double sinThetaDelta = (float) Math.sin(theta + delta);

                Point point1 = new Point(radius, sinPhi, cosPhi, sinTheta, cosTheta, center);
                Point point2 = new Point(radius, sinPhi, cosPhi, sinThetaDelta, cosThetaDelta, center);
                Point point3 = new Point(radius, sinPhiDelta, cosPhiDelta, sinTheta, cosTheta, center);
                Point point4 = new Point(radius, sinPhiDelta, cosPhiDelta, sinThetaDelta, cosThetaDelta, center);

                sphereVerticesBuffer.put(point1.AsFloatArray());
                sphereVerticesBuffer.put(point2.AsFloatArray());
                sphereVerticesBuffer.put(point3.AsFloatArray());

                sphereVerticesBuffer.put(point3.AsFloatArray());
                sphereVerticesBuffer.put(point2.AsFloatArray());
                sphereVerticesBuffer.put(point4.AsFloatArray());

                pointsCount+=6;

                points.add(point1);
            }
        }
        sphereVerticesBuffer.position(0);
    }

}
