package Model;

/**
 * Created by mateu_000 on 2015-03-08.
 */
public class Engine {

    private Point firstShellCenter;

    private double translationFactor; //TF
    private double growthFactor; //GF
    private double thicknessGrowthFactor; //TGF
    private double deflectionAngle;
    private double rotationAngle;
    private double initialThickness;
    private Point initialApperturePostion;
    private double initialRadius;
    private Point initialCenter;

    public Point getFirstShellCenter() {
        return firstShellCenter;
    }

    public void setFirstShellCenter(Point firstShellCenter) {
        this.firstShellCenter = firstShellCenter;
    }

    public double getTranslationFactor() {
        return translationFactor;
    }

    public void setTranslationFactor(double translationFactor) {
        this.translationFactor = translationFactor;
    }

    public double getGrowthFactor() {
        return growthFactor;
    }

    public void setGrowthFactor(double growthFactor) {
        this.growthFactor = growthFactor;
    }

    public double getDeflectionAngle() {
        return deflectionAngle;
    }

    public void setDeflectionAngle(double deflectionAngle) {
        this.deflectionAngle = deflectionAngle;
    }

    public double getRotationAngle() {
        return rotationAngle;
    }

    public void setRotationAngle(double rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    public double getThicknessGrowthFactor() {
        return thicknessGrowthFactor;
    }

    public void setThicknessGrowthFactor(double thicknessGrowthFactor) {
        this.thicknessGrowthFactor = thicknessGrowthFactor;
    }

    public double getInitialThickness() {
        return initialThickness;
    }

    public void setInitialThickness(double initialThickness) {
        this.initialThickness = initialThickness;
    }

    public Point getInitialApperturePostion() {
        return initialApperturePostion;
    }

    public void setInitialApperturePostion(Point initialApperturePostion) {
        this.initialApperturePostion = initialApperturePostion;
    }

    public double getInitialRadius() {
        return initialRadius;
    }

    public void setInitialRadius(double initialRadius) {
        this.initialRadius = initialRadius;
    }

    public Point getInitialCenter() {
        return initialCenter;
    }

    public void setInitialCenter(Point initialCenter) {
        this.initialCenter = initialCenter;
    }

    public Engine(Point firstShellCenter) {
        this.firstShellCenter = firstShellCenter;
    }

    public Foraminifera createForamWithShells(int numberOfShells){
        Foraminifera createdForaminifera = new Foraminifera();

        addInitialShell(createdForaminifera);

        for (int i = 2; i <= numberOfShells; i++){
            addShell(createdForaminifera, i);
        }
        return createdForaminifera;
    }

    public void addInitialShell(Foraminifera foraminifera){
        Shell createdShell = new Shell(1);

        createdShell.setApperturePosition(initialApperturePostion);
        createdShell.setCenter(initialCenter);
        createdShell.setRadius(initialRadius);
        createdShell.setThickness(initialThickness);

        foraminifera.shells.add(createdShell);
    }

    public void addShell(Foraminifera foraminifera, int shellNumber){
        Shell createdShell = new Shell(shellNumber);

        createdShell.setApperturePosition(calculateApperturePosition(foraminifera));
        createdShell.setCenter(calculateCenter(foraminifera));
        createdShell.setRadius(calculateRadius(foraminifera));
        createdShell.setThickness(calculateThickness(foraminifera));

        foraminifera.shells.add(createdShell);
    }

    private double calculateRadius(Foraminifera foraminifera){
        Shell lastShell = foraminifera.shells.getLast();

        return lastShell.getRadius() * growthFactor;
    }

    private Point calculateCenter(Foraminifera foraminifera){
        throw new UnsupportedOperationException();
    }

    private Point calculateApperturePosition(Foraminifera foraminifera){
        throw new UnsupportedOperationException();
    }

    private double calculateThickness(Foraminifera foraminifera) {
        Shell lastShell = foraminifera.shells.getLast();

        return lastShell.getRadius() * thicknessGrowthFactor;
    }
}
