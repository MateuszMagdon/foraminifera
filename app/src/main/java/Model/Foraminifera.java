package Model;

import java.util.LinkedList;

import Helpers.Point;
import Helpers.SettingsContainer;
import Helpers.Vector;
import OpenGL.SphereFactory;

public class Foraminifera {
    private final SphereFactory sphereFactory;
    private final Vector scaleVector;
    private LinkedList<Shell> shells = new LinkedList<>();

    public Foraminifera()
    {
        sphereFactory = new SphereFactory();
        scaleVector = new Vector(SettingsContainer.scaleX, SettingsContainer.scaleY, SettingsContainer.scaleZ);

        shells.add(createInitialShell());
    }

    private Shell createInitialShell(){
        return new Shell(sphereFactory);
    }

    public void addNextShell() {
        Shell previousShell = shells.getLast();

        Point nextCenter = calculateCenterPosition(previousShell);
        double nextRadius = calculateRadius(previousShell);
        double nextThickness = calculateThickness(previousShell);

        Shell nextShell = new Shell(nextCenter, nextRadius, nextThickness, previousShell, scaleVector, sphereFactory);
        shells.add(nextShell);
    }

    private double calculateThickness(Shell previousShell) {
        return previousShell.getThickness() * SettingsContainer.thicknessGrowthFactor;
    }

    private double calculateRadius(Shell previousShell) {
        return previousShell.getRadius() * SettingsContainer.growthFactor;
    }

    private Point calculateCenterPosition(Shell previousShell) {
        Vector growthVector = calculateGrowthVector(previousShell);
        return previousShell.getAperturePosition().Translate(growthVector);
    }

    private Vector calculateGrowthVector(Shell previousShell) {
        Vector baseVector = previousShell.getNextShellGrowthAxis();
        return baseVector.Multiply(previousShell.getRadius())
                .Deflect(SettingsContainer.deviationAngle)
                .Rotate(SettingsContainer.rotationAngle, baseVector)
                .Multiply(SettingsContainer.growthFactor)
                .Multiply(SettingsContainer.translationFactor);
    }

    public void removeShell(){
        shells.removeLast();
    }

    public LinkedList<Shell> getShells() {
        return (LinkedList<Shell>) shells.clone();
    }
}
