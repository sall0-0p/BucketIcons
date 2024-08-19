package com.bucketicons.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.bucketicons.Plugin;

public class IconManager {
    private static final Plugin plugin = Plugin.getPlugin();

    public static String findIconNameBySymbol(String symbol) {
        FileConfiguration config = plugin.getConfig();
        ConfigurationSection iconsSection = config.getConfigurationSection("icons");
    
        if (iconsSection == null) {
            return null;
        }
    
        for (String key : iconsSection.getKeys(false)) {
            ConfigurationSection itemSection = iconsSection.getConfigurationSection(key);
    
            if (itemSection == null) {
                continue;
            }
    
            String itemSymbol = itemSection.getString("symbol");
    
            if (symbol.equals(itemSymbol)) {
                return key;
            }
        }
    
        return null;
    }

    public static int getWorldCount() {
        FileConfiguration config = plugin.getConfig();
        
        ConfigurationSection worldsSection = config.getConfigurationSection("servers");

        if (worldsSection != null) {
            return worldsSection.getKeys(false).size();
        } else {
            return 0;
        }
    }

    public static Set<String> getWorlds() {
        FileConfiguration config = plugin.getConfig();

        ConfigurationSection worldsSection = config.getConfigurationSection("servers");

        if (worldsSection != null) {
            return worldsSection.getKeys(false);
        }

        return null;
    }

    public static List<Icon> getAllIcons() {
        FileConfiguration config = plugin.getConfig();
        List<Icon> result = new ArrayList<>();

        ConfigurationSection iconsSection = config.getConfigurationSection("icons");

        if (iconsSection != null) {
            for (String key : iconsSection.getKeys(false)) {
                result.add(new Icon(key, false));
            }
        }

        return result;
    }

    public static List<String> getAllSymbols() {
        FileConfiguration config = plugin.getConfig();
        List<String> result = new ArrayList<>();

        ConfigurationSection iconsSection = config.getConfigurationSection("icons");

        if (iconsSection != null) {
            for (String key : iconsSection.getKeys(false)) {
                result.add(new Icon(key, false).getSymbol());
            }
        }

        return result;
    }
}
