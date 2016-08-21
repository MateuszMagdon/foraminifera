package com.example.mateu_000.foraminifera;


import Model.Point;

public class SphereTriangles extends SphereFactory{

    public SphereTriangles(float radius, int step) {
        super(radius, step);
    }

    protected int calculateBufferCapacity(){
        int pointsPerPI = (int)(Math.ceil(Math.PI / delta));
        return pointsPerVertex * floatSize * pointsPerPI * 2 * pointsPerPI * 6;
    }

    public void calculateVertices() {

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

                Point point1 = new Point(radius, sinPhi, cosPhi, sinTheta, cosTheta);
                Point point2 = new Point(radius, sinPhi, cosPhi, sinThetaDelta, cosThetaDelta);
                Point point3 = new Point(radius, sinPhiDelta, cosPhiDelta, sinTheta, cosTheta);
                Point point4 = new Point(radius, sinPhiDelta, cosPhiDelta, sinThetaDelta, cosThetaDelta);

                sphereVerticesBuffer.put(point1.AsFloatArray());
                sphereVerticesBuffer.put(point2.AsFloatArray());
                sphereVerticesBuffer.put(point3.AsFloatArray());

                sphereVerticesBuffer.put(point3.AsFloatArray());
                sphereVerticesBuffer.put(point2.AsFloatArray());
                sphereVerticesBuffer.put(point4.AsFloatArray());

                pointsCount+=6;
            }
        }
        sphereVerticesBuffer.position(0);
    }

}
