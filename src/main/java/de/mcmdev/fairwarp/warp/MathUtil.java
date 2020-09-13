package de.mcmdev.fairwarp.warp;

public class MathUtil {

    public static double scale(double value, double minOrig, double maxOrig, double minScaled, double maxScaled)   {
        return (maxScaled - minScaled) * (value - minOrig) / (maxOrig - minOrig) + minScaled;
    }

}
