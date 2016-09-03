package Helpers;

public class ReferenceSpace {
    private Vector x;
    private Vector y;
    private Vector z;

    public ReferenceSpace(Vector x, Vector y, Vector z){

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector getX() {
        return x;
    }

    public Vector getY() {
        return y;
    }

    public Vector getZ() {
        return z;
    }
}
