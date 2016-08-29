package OpenGL;

import android.support.annotation.NonNull;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.LinkedList;

import Helpers.Point;
import Helpers.ReferenceSpace;
import Helpers.Vector;

public class SphereFactory {
    private int baseSpherePointsCount;
    private FloatBuffer baseSphereVerticesBuffer;
    private LinkedList<Point> baseSpherePointsList;

    private static final int stepSize = 10;

    private static final int pointsPerVertex = 3;
    private static final int floatSize = Float.SIZE / Byte.SIZE;
    private static final double degree = Math.ceil(100 * Math.PI/180) / 100;

    private double delta;
    private final int pointsPerPI;
    private final int bufferCapacity;

    public SphereFactory() {
        baseSpherePointsCount = 0;
        baseSpherePointsList = new LinkedList<>();

        delta = stepSize * degree;
        pointsPerPI = (int)(Math.ceil(Math.PI / delta));

        bufferCapacity = calculateBufferCapacity();
        baseSphereVerticesBuffer = prepareBuffer(bufferCapacity);
        calculateVertices();
    }

    public Sphere CreateSphere(double radius, Point center, Vector scaleRate, ReferenceSpace referenceSpace){
        Vector scalingVectorCloned = scaleRate.Clone();
        Point centerCloned = center.Clone();

        Sphere sphere = new Sphere(radius, centerCloned, scalingVectorCloned, referenceSpace, baseSpherePointsCount);

        Vector scalingWithRadiusIncluded = scalingVectorCloned.Clone().Multiply(radius);
        LinkedList<Point> copiedPoints = transformatePoints(centerCloned, scalingWithRadiusIncluded, referenceSpace);

        sphere.points = copiedPoints;

        return sphere;
    }

    public void CalculateTrianglesForSphere(Sphere sphere){
        FloatBuffer newBuffer = prepareBuffer(bufferCapacity);
        translatePointsToBuffer(sphere.points, newBuffer);

        sphere.sphereVerticesBuffer = newBuffer;
    }

    @NonNull
    private LinkedList<Point> transformatePoints(Point center, Vector scalingVector, ReferenceSpace referenceSpace) {
        LinkedList<Point> transformatedPointsList = new LinkedList<>();

        Vector translationVector = center.GetVector();
        for (Point point : baseSpherePointsList) {
            Point clonedPoint = point.Clone();
            clonedPoint.Rotate();//TODO rotate to have properly placed apperture
            clonedPoint.Scale(scalingVector, referenceSpace);
            clonedPoint.Translate(translationVector);
            transformatedPointsList.add(clonedPoint);
        }
        return transformatedPointsList;
    }

    private void translatePointsToBuffer(LinkedList<Point> copiedPoints, FloatBuffer newBuffer) {
        int i = copiedPoints.size() - 2 * pointsPerPI;
        for (int index = 0; index < i; index++){
            Point p1 = copiedPoints.get(index);
            Point p3 = copiedPoints.get(2*pointsPerPI + index);

            Point p2;
            Point p4;

            if ((index+1) % (2*pointsPerPI) == 0){
                p2 = copiedPoints.get(index+1 - 2*pointsPerPI);
                p4 = copiedPoints.get(index+1);
            } else {
                p2 = copiedPoints.get(index+1);
                p4 = copiedPoints.get(2*pointsPerPI + index+1);
            }

            insertTriangleToBuffer(newBuffer, p1, p4, p2);
            insertTriangleToBuffer(newBuffer, p1, p3, p4);
        }
    }

    private FloatBuffer prepareBuffer(int bufferCapacity) {
        ByteBuffer sphereVertexByte = ByteBuffer.allocateDirect(bufferCapacity);
        sphereVertexByte.order(ByteOrder.nativeOrder());
        return sphereVertexByte.asFloatBuffer();
    }

    private int calculateBufferCapacity(){

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
        baseSphereVerticesBuffer.position(0);
    }

    private void createTriangles(double cosPhi, double cosPhiDelta, double sinPhi, double sinPhiDelta, double cosTheta, double cosThetaDelta, double sinTheta, double sinThetaDelta) {
        Point point1 = new Point(sinPhi, cosPhi, sinTheta, cosTheta);
        Point point2 = new Point(sinPhi, cosPhi, sinThetaDelta, cosThetaDelta);
        Point point3 = new Point(sinPhiDelta, cosPhiDelta, sinTheta, cosTheta);
        Point point4 = new Point(sinPhiDelta, cosPhiDelta, sinThetaDelta, cosThetaDelta);

        insertTriangleToBuffer(baseSphereVerticesBuffer, point1, point2, point3);
        insertTriangleToBuffer(baseSphereVerticesBuffer, point3, point2, point4);

        baseSpherePointsCount +=6;

        baseSpherePointsList.add(point1);
    }

    private void insertTriangleToBuffer(FloatBuffer sphereVerticesBuffer, Point point1, Point point2, Point point3) {
        sphereVerticesBuffer.put(point1.AsFloatArray());
        sphereVerticesBuffer.put(point2.AsFloatArray());
        sphereVerticesBuffer.put(point3.AsFloatArray());
    }
}
