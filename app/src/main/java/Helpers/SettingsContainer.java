package Helpers;

public class SettingsContainer {
    public static double numberOfChambers;

    public static double translationFactor; //przesunięcie po growth axis srodka kolejnej sfery wzgledem srodka poprzedniej
        //0 to centrum kolejnej sfery w apperture kolejnej
        //1 to stykające się sfery - v = newRadius

    public static double growthFactor; //scaling rate of growth vector and radius
    public static double thicknessGrowthFactor; //scaling rate of thickness

    public static double deviationAngle;
    public static double rotationAngle;

    //additional scaling;
    public static double scaleX = 1.0;
    public static double scaleY = 1.0;
    public static double scaleZ = 1.0;

    //clipping panes positions
    public static double clippingX;
    public static double clippingY;
    public static double clippingZ;
}
