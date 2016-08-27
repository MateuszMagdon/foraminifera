package Model;

import java.util.LinkedList;

import Helpers.SettingsContainer;

public class Foraminifera {
    public LinkedList<Shell> shells;

    public Foraminifera()
    {
        shells = new LinkedList<>();

        shells.add(createInitialShell());
    }

    private Shell createInitialShell(){
        return new Shell();
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
        return baseVector.clone()
                .multiply(SettingsContainer.growthFactor)
                .multiply(SettingsContainer.translationFactor)
                .deflect(SettingsContainer.deviationAngle)
                .rotate(SettingsContainer.rotationAngle, baseVector);
    }

    public void removeShell(){
        shells.removeLast();
    }

}
