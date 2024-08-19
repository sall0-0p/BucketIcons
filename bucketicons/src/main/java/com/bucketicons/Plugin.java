package com.bucketicons;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.bucketicons.utils.CommandHandler;
import com.bucketicons.utils.Messages;
import com.bucketicons.utils.TabComplete;

import net.luckperms.api.LuckPerms;

/**
 * Hello world!
 *
 */
public class Plugin extends JavaPlugin {
    private static Plugin plugin;
    private static LuckPerms luckPerms;

    @Override
    public void onEnable() {
        plugin = this;
        Logger logger = getLogger();

        // Initialise config
        saveDefaultConfig();
        Messages.load();

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        this.getCommand("bucketicons").setExecutor(new CommandHandler());
        this.getCommand("bucketicons").setTabCompleter(new TabComplete());
        
        if (provider != null) {
            luckPerms = provider.getProvider();
        } else {
            logger.severe("Failed to enable! You need luckperms to use this plugin!");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {

    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public static LuckPerms getLuckPerms() {
        return luckPerms;
    }
}
