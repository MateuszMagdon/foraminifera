package Model;

import java.util.LinkedList;

import Helpers.SettingsContainer;

public class Foraminifera {
    private Point initialShellCenter;
    private double initialThickness;
    private double initialRadius;
    private Point initialAperturePosition;

    public LinkedList<Shell> shells;

    public Foraminifera()
    {
        shells = new LinkedList<>();

        initialShellCenter = new Point(0, 0, 0);
        initialThickness = 0.02d;
        initialRadius = 1.0d;
        initialAperturePosition = new Point(0, 1, 0);

        shells.add(createInitialShell());
    }

    private Shell createInitialShell(){
        return new Shell(initialShellCenter, initialRadius, initialThickness, initialAperturePosition);
    }

    public void addNextShell() {
        Shell previousShell = shells.getLast();

        Point nextCenter = calculateCenterPosition(previousShell);
        double nextRadius = calculateRadius(previousShell);
        double nextThickness = calculateThickness(previousShell);

        Shell nextShell = new Shell(nextCenter, nextRadius, nextThickness, previousShell);
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
        return previousShell.aperturePosition.addVector(growthVector);
    }

    private Vector calculateGrowthVector(Shell previousShell) {
        Vector baseVector = previousShell.axisVector;
        return baseVector.multiply(SettingsContainer.growthFactor)
                .multiply(SettingsContainer.translationFactor)
                .deflect(SettingsContainer.deviationAngle)
                .rotate(SettingsContainer.rotationAngle)
                .clone();
    }

    public void removeShell(){
        shells.removeLast();
    }

}
