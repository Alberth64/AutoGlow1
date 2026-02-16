package com.autoglow;

import net.minecraft.util.Formatting;
import java.util.HashMap;
import java.util.Map;

public class GlowColorManager {

    private static int colorValue = 0xFF0000;

    private static final Map<String, Integer> NAMED_COLORS = new HashMap<>();

    static {
        NAMED_COLORS.put("red", 0xFF0000);
        NAMED_COLORS.put("green", 0x00FF00);
        NAMED_COLORS.put("blue", 0x0000FF);
        NAMED_COLORS.put("yellow", 0xFFFF00);
        NAMED_COLORS.put("cyan", 0x00FFFF);
        NAMED_COLORS.put("magenta", 0xFF00FF);
        NAMED_COLORS.put("white", 0xFFFFFF);
        NAMED_COLORS.put("black", 0x000000);
        NAMED_COLORS.put("orange", 0xFFA500);
        NAMED_COLORS.put("pink", 0xFF69B4);
        NAMED_COLORS.put("purple", 0x800080);
    }

    public static void setRGB(int r, int g, int b) {
        r = clamp(r);
        g = clamp(g);
        b = clamp(b);
        colorValue = (r << 16) | (g << 8) | b;
    }

    public static void setHex(String hex) {
        hex = hex.replace("#", "");

        if (hex.length() == 6) {
            colorValue = Integer.parseInt(hex, 16);
        }
    }

    public static boolean setNamed(String name) {
        Integer value = NAMED_COLORS.get(name.toLowerCase());
        if (value != null) {
            colorValue = value;
            return true;
        }
        return false;
    }

    public static int getColorValue() {
        return colorValue;
    }

    private static int clamp(int v) {
        return Math.max(0, Math.min(255, v));
    }
}