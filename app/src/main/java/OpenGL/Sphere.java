package OpenGL;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.LinkedList;
import java.util.List;

import Helpers.PointFactory;
import Model.Point;

public class Sphere {
    private final PointFactory pointFactory;
    public int pointsCount = 0;
    public FloatBuffer sphereVerticesBuffer;
    public List<Point> points = new LinkedList<>();

    private static final int stepSize = 10;

    private static final int pointsPerVertex = 3;
    private static final int floatSize = Float.SIZE / Byte.SIZE;
    private static final double degree = Math.ceil(100 * Math.PI/180) / 100;

    private final Point center;
    private double radius;
    private double delta;

    public Sphere(double radius, Point center) {
        this.radius = radius;
        this.center = center;
        pointFactory = new PointFactory(radius, center);
        
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

                createTriangles(cosPhi, cosPhiDelta, sinPhi, sinPhiDelta, cosTheta, cosThetaDelta, sinTheta, sinThetaDelta);
            }
        }
        sphereVerticesBuffer.position(0);
    }

    private void createTriangles(double cosPhi, double cosPhiDelta, double sinPhi, double sinPhiDelta, double cosTheta, double cosThetaDelta, double sinTheta, double sinThetaDelta) {
        Point point1 = pointFactory.CreatePoint(sinPhi, cosPhi, sinTheta, cosTheta);
        Point point2 = pointFactory.CreatePoint(sinPhi, cosPhi, sinThetaDelta, cosThetaDelta);
        Point point3 = pointFactory.CreatePoint(sinPhiDelta, cosPhiDelta, sinTheta, cosTheta);
        Point point4 = pointFactory.CreatePoint(sinPhiDelta, cosPhiDelta, sinThetaDelta, cosThetaDelta);

        insertTriangleToBuffer(point1, point2, point3);
        insertTriangleToBuffer(point3, point2, point4);

        points.add(point1);
    }

    private void insertTriangleToBuffer(Point point1, Point point2, Point point3) {
        sphereVerticesBuffer.put(point1.AsFloatArray());
        sphereVerticesBuffer.put(point2.AsFloatArray());
        sphereVerticesBuffer.put(point3.AsFloatArray());

        pointsCount+=3;
    }

}
