package Helpers;

public class SettingsContainer {
    public static double translationFactor; //przesunięcie po growth axis srodka kolejnej sfery wzgledem srodka poprzedniej
        //0 to centrum kolejnej sfery w apperture kolejnej
        //1 to stykające się sfery - v = newRadius
    public static double growthFactor; //radius times this = new radius
    public static double deviationAngle; //odgiecie od osi wzrostu
    public static double rotationAngle; //-180 - 180 rotacja odgietej osi
    public static double thicknessGrowthFactor; //thickness times this = new thickness

    //wszystkie scale >1
    public static double scaleX;
    public static double scaleY;
    public static double scaleZ;
}
