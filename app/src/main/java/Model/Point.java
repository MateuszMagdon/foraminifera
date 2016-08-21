package Model;

/**
 * Created by mateu_000 on 2015-02-09.
 */
public class Point {
    public double x;
    public double y;
    public double z;

    public Point() {
    }

    public Point(double radius, double phi, double theta) {
        double sinPhi = Math.sin(phi);
        double cosPhi = Math.cos(phi);
        double cosTheta = Math.cos(theta);
        double sinTheta = Math.sin(theta);

        CalculatePosition(radius, sinPhi, cosPhi, sinTheta, cosTheta);
    }

    public Point(double radius, double sinPhi, double cosPhi, double sinTheta, double cosTheta){
        CalculatePosition(radius, sinPhi, cosPhi, sinTheta, cosTheta);
    }

    private void CalculatePosition(double radius, double sinPhi, double cosPhi, double sinTheta, double cosTheta) {
        x = radius * sinPhi * cosTheta;
        y = radius * sinPhi * sinTheta;
        z = radius * cosPhi;
    }

    public float[] AsFloatArray(){
        float[] result = new float[3];
        result[0] = (float) x;
        result[1] = (float) y;
        result[2] = (float) z;

        return result;
    }
}
