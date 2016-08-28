package Model;

import java.util.LinkedList;

import Helpers.SettingsContainer;
import OpenGL.SphereFactory;

public class Foraminifera {
    private final SphereFactory sphereFactory;
    private final Vector scaleVector;
    public LinkedList<Shell> shells = new LinkedList<>();

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
        return previousShell.thickness * SettingsContainer.thicknessGrowthFactor;
    }

    private double calculateRadius(Shell previousShell) {
        return previousShell.radius * SettingsContainer.growthFactor;
    }

    private Point calculateCenterPosition(Shell previousShell) {
        Vector growthVector = calculateGrowthVector(previousShell);
        return previousShell.aperturePosition.Clone().Translate(growthVector);
    }

    private Vector calculateGrowthVector(Shell previousShell) {
        Vector baseVector = previousShell.axisVector;
        return baseVector.Clone()
                .Deflect(SettingsContainer.deviationAngle)
                .Rotate(SettingsContainer.rotationAngle, baseVector)
                .Multiply(SettingsContainer.growthFactor)
                .Multiply(SettingsContainer.translationFactor);
    }

    public void removeShell(){
        shells.removeLast();
    }

}
