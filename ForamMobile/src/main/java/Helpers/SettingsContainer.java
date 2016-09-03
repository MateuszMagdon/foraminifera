package Helpers;

public class SettingsContainer {
    public static int numberOfChambers = 3;

    public static double translationFactor = 0.8d; //przesunięcie po growth axis srodka kolejnej sfery wzgledem srodka poprzedniej
        //0 to centrum kolejnej sfery w apperture kolejnej
        //1 to stykające się sfery - v = newRadius

    public static double growthFactor = 1.4d; //scaling rate of growth vector and radius
    public static double thicknessGrowthFactor = 1.1d; //scaling rate of thickness

    public static double deviationAngle = 30;
    public static double rotationAngle = 50;

    //additional scaling;
    public static double scaleX = 1.0;
    public static double scaleY = 1.0;
    public static double scaleZ = 1.0;

    //clipping panes positions
    public static double clippingX = 100;
    public static double clippingY = 100;
    public static double clippingZ = 100;
}
