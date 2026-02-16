package com.autoglow;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Formatting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GlowConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH =
            FabricLoader.getInstance().getConfigDir().resolve("autoglow.json");

    public int color = 0x00FFFF; // padrão aqua
    public boolean enable = true;

    private static GlowConfig INSTANCE;

    public static GlowConfig get() {
        if (INSTANCE == null) {
            load();
        }
        return INSTANCE;
    }

    public static void load() {
        try {
            if (Files.exists(CONFIG_PATH)) {
                String json = Files.readString(CONFIG_PATH);

                GlowConfig loaded = GSON.fromJson(json, GlowConfig.class);

                // Evita null caso JSON esteja corrompido
                if (loaded != null) {
                    INSTANCE = loaded;
                } else {
                    System.out.println("[Glow] Config inválido, recriando...");
                    INSTANCE = new GlowConfig();
                    save();
                }

            } else {
                INSTANCE = new GlowConfig();
                save();
            }

        } catch (Exception e) {
            System.out.println("[Glow] Erro ao carregar config, recriando...");
            e.printStackTrace();

            INSTANCE = new GlowConfig();
            save();
        }
    }


    public static void save() {
        try {
            Files.writeString(CONFIG_PATH, GSON.toJson(INSTANCE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
