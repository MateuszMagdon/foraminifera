package com.example.mateu_000.foraminifera;


import Model.Point;

public class SphereVertices extends SphereFactory {

    public SphereVertices(float radius, int step) {
        super(radius, step);
    }

    protected int calculateBufferCapacity(){
        int pointsPerPI = (int)(Math.ceil(Math.PI / delta));
        return pointsPerVertex * floatSize * pointsPerPI * 2 * pointsPerPI;
    }

    protected void calculateVertices() {

        for(double phi = -(Math.PI); phi <= 0; phi+=delta) {
            double sinPhi = Math.sin(phi);
            double cosPhi = Math.cos(phi);

            for(double theta = 0.0; theta <= (Math.PI * 2); theta+=delta) {
                Point point = new Point(radius, sinPhi, cosPhi, Math.sin(theta), Math.cos(theta));
                sphereVerticesBuffer.put(point.AsFloatArray());
                pointsCount++;
            }
        }
        sphereVerticesBuffer.position(0);
    }

}

