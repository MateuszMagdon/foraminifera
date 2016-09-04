package Metrics;

import Helpers.Point;

public class RandomPointFactory {

    private final double minX;
    private final double maxX;
    private final double minY;
    private final double maxY;
    private final double minZ;
    private final double maxZ;

    public RandomPointFactory(double minX, double maxX, double minY, double maxY, double minZ, double maxZ) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.minZ = minZ;
        this.maxZ = maxZ;
    }

    public Point GenerateRandomPoint(){
        double x = minX + (Math.random() * (maxX - minX));
        double y = minY + (Math.random() * (maxY - minY));
        double z = minZ + (Math.random() * (maxZ - minZ));

        return new Point(x, y, z);
    }
}
