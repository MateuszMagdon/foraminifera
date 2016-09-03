package OpenGL;

import android.support.annotation.NonNull;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import Helpers.Point;
import Helpers.ReferenceSpace;
import Helpers.Vector;
import Model.Shell;

public class SphereFactory {
    private int baseSpherePointsCount;
    private FloatBuffer baseSphereVerticesBuffer;
    private LinkedList<Point> baseSpherePointsList;

    private static final int stepSize = 4;

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

    private int calculateBufferCapacity(){

        return pointsPerVertex * floatSize * pointsPerPI * 2 * pointsPerPI * 6;
    }

    private FloatBuffer prepareBuffer(int bufferCapacity) {
        ByteBuffer sphereVertexByte = ByteBuffer.allocateDirect(bufferCapacity);
        sphereVertexByte.order(ByteOrder.nativeOrder());
        return sphereVertexByte.asFloatBuffer();
    }

    private void calculateVertices() {

        double phi = -(Math.PI);
        for (int j = 0; j < pointsPerPI; j++) {
            double cosPhi = Math.cos(phi);
            double cosPhiDelta = Math.cos(phi + delta);
            double sinPhi = Math.sin(phi);
            double sinPhiDelta = Math.sin(phi + delta);

            double theta = 0.0;
            for (int i = 0; i < 2 * pointsPerPI; i++) {

                double cosTheta = (float) Math.cos(theta);
                double cosThetaDelta = (float) Math.cos(theta + delta);
                double sinTheta = (float) Math.sin(theta);
                double sinThetaDelta = (float) Math.sin(theta + delta);

                createTriangles(cosPhi, cosPhiDelta, sinPhi, sinPhiDelta, cosTheta, cosThetaDelta, sinTheta, sinThetaDelta);

                theta += delta;
            }

            phi += delta;
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

    public Sphere CreateSphere(double radius, Point center, Vector scaleRate, ReferenceSpace referenceSpace){
        Vector scalingVectorCloned = scaleRate.Clone();
        Point centerCloned = center.Clone();

        Sphere sphere = new Sphere(radius, centerCloned, scalingVectorCloned, referenceSpace, baseSpherePointsCount);

        Vector scalingWithRadiusIncluded = scalingVectorCloned.Clone().Multiply(radius);
        Vector translationVector = center.GetVector();
        LinkedList<Point> copiedPoints = transformatePoints(scalingWithRadiusIncluded, new Vector(0, 0, 0), translationVector, referenceSpace);

        sphere.points = copiedPoints;

        return sphere;
    }

    @NonNull
    private LinkedList<Point> transformatePoints(Vector scalingVector, Vector rotationVector, Vector translationVector, ReferenceSpace referenceSpace) {
        LinkedList<Point> transformatedPointsList = new LinkedList<>();

        for (Point point : baseSpherePointsList) {
            Point clonedPoint = point.Clone();
            clonedPoint.Rotate(rotationVector);
            clonedPoint.Scale(scalingVector, referenceSpace);
            clonedPoint.Translate(translationVector);
            transformatedPointsList.add(clonedPoint);
        }
        return transformatedPointsList;
    }

    public void RotateSphereToAperture(Sphere sphere, Point aperture, Vector scaleRate, ReferenceSpace referenceSpace){
        Vector scalingVector = scaleRate.Clone().Multiply(sphere.GetRadius());
        Point apertureNormalized = normalizeAperturePosition(sphere, aperture);
        Point initialAperture = new Point(0, 1, 0);
        Point apertureOnXYPlane = new Point(apertureNormalized.getX(), apertureNormalized.getY(), 0);
        double zRotation = calculateRotation(initialAperture, apertureOnXYPlane);
        double yRotation = calculateRotation(apertureOnXYPlane, apertureNormalized);

        Vector rotationVector = new Vector(0, yRotation, zRotation);
        Vector translationVector = sphere.GetCenter().GetVector();

        LinkedList<Point> transformatedPointsList = transformatePoints(scalingVector, rotationVector, translationVector, referenceSpace);

        sphere.points = transformatedPointsList;
    }

    private Point normalizeAperturePosition(Sphere sphere, Point aperture) {
        Point center = sphere.GetCenter();
        Vector returningVector = new Vector(
                -center.getX(), -center.getY(), -center.getZ());

        return aperture.Clone().Translate(returningVector).Normalize();
    }

    private double calculateRotation(Point initialApperture, Point appertureOnXYPlane) {
        double initialToXYAppertureDistance = initialApperture.GetDistance(appertureOnXYPlane);
        return 2 * Math.PI - 2 * Math.asin(0.5 * initialToXYAppertureDistance); // should be divided by radius, which in base sphere equals 1
    }

    public void CalculateTrianglesForSphere(Sphere sphere, List<Shell> previousShells){
        FloatBuffer newBuffer = prepareBuffer(bufferCapacity);
        int pointsCount = translatePointsToBuffer(sphere.points, newBuffer, previousShells);

        sphere.pointsCount = pointsCount;
        sphere.sphereVerticesBuffer = newBuffer;
    }

    private int translatePointsToBuffer(LinkedList<Point> copiedPoints, FloatBuffer newBuffer, List<Shell> previousShells) {
        int addedPoints = 0;

        int i = copiedPoints.size() - 4 * pointsPerPI;
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

            boolean p1_inside = false,
                    p2_inside = false,
                    p3_inside = false,
                    p4_inside = false;

            for (Shell shell : previousShells){
                Point shellCenter = shell.getCenter();
                double shellRadius = shell.getRadius() + shell.getThickness();
                p1_inside |= isInsideShell(p1, shellCenter, shellRadius);
                p2_inside |= isInsideShell(p2, shellCenter, shellRadius);
                p3_inside |= isInsideShell(p3, shellCenter, shellRadius);
                p4_inside |= isInsideShell(p4, shellCenter, shellRadius);
            }

            if(!(p1_inside && p2_inside && p3_inside)) {
                insertTriangleToBuffer(newBuffer, p1, p4, p2);
                addedPoints += pointsPerVertex;
            }

            if(!(p1_inside && p4_inside && p3_inside)) {
                insertTriangleToBuffer(newBuffer, p1, p3, p4);
                addedPoints += pointsPerVertex;
            }
        }

        return addedPoints;
    }

    private boolean isInsideShell(Point p, Point shellCenter, double shellRadius) {
        if (p.GetDistance(shellCenter) < shellRadius){
            return true;
        }
        return false;
    }

    private void insertTriangleToBuffer(FloatBuffer sphereVerticesBuffer, Point point1, Point point2, Point point3) {
        sphereVerticesBuffer.put(point1.AsFloatArray());
        sphereVerticesBuffer.put(point2.AsFloatArray());
        sphereVerticesBuffer.put(point3.AsFloatArray());
    }
}
