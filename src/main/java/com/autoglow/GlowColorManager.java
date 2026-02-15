package com.autoglow;

import net.minecraft.util.Formatting;

public class GlowColorManager {

    private static Formatting currentColor = Formatting.AQUA;

    public static void setColor(Formatting color) {
        currentColor = color;
    }

    public static Formatting getFormatting() {
        return currentColor;
    }

    public static int getColorValue() {

        // Converte Formatting para RGB
        return switch (currentColor) {
            case BLACK -> 0x000000;
            case DARK_BLUE -> 0x0000AA;
            case DARK_GREEN -> 0x00AA00;
            case DARK_AQUA -> 0x00AAAA;
            case DARK_RED -> 0xAA0000;
            case DARK_PURPLE -> 0xAA00AA;
            case GOLD -> 0xFFAA00;
            case GRAY -> 0xAAAAAA;
            case DARK_GRAY -> 0x555555;
            case BLUE -> 0x5555FF;
            case GREEN -> 0x55FF55;
            case AQUA -> 0x55FFFF;
            case RED -> 0xFF5555;
            case LIGHT_PURPLE -> 0xFF55FF;
            case YELLOW -> 0xFFFF55;
            case WHITE -> 0xFFFFFF;
            default -> 0x55FFFF;
        };
    }
}
