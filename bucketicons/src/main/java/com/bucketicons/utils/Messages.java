package com.bucketicons.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.bucketicons.Plugin;

public class Messages {
    private static final Plugin plugin = Plugin.getPlugin();
    private static final Logger logger = plugin.getLogger();

    private static File customConfigFile;
    private static FileConfiguration customConfig;

    public static void load() {
        customConfigFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            plugin.saveResource("messages.yml", false);
        }

        customConfig = new YamlConfiguration();

        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            logger.severe(e.getMessage());
        }
    } 

    public static String getString(String key) {
        return customConfig.getString(key);
    }

    public static void reloadConfig() throws InvalidConfigurationException {
        try {
            customConfig.load(customConfigFile);
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }
}
