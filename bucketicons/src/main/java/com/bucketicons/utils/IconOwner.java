package com.bucketicons.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.bucketicons.Plugin;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.node.Node;

public class IconOwner {
    private final OfflinePlayer player;
    private final Plugin plugin = Plugin.getPlugin();
    private final LuckPerms luckPerms = Plugin.getLuckPerms();

    public IconOwner(OfflinePlayer player) {
        this.player = player;
    }

    public void giveIcon(Icon icon) {
        UUID playerUUID = this.player.getUniqueId();
        UserManager userManager = luckPerms.getUserManager();
        CompletableFuture<User> userFuture = userManager.loadUser(playerUUID);

        userFuture.thenAcceptAsync(user -> {
            user.data().add(icon.getOwnershipNode());

            userManager.saveUser(user);     
        });
    }

    public void removeIcon(Icon icon) {
        UUID playerUUID = this.player.getUniqueId();
        UserManager userManager = luckPerms.getUserManager();
        CompletableFuture<User> userFuture = userManager.loadUser(playerUUID);

        userFuture.thenAcceptAsync(user -> {
            user.data().remove(icon.getOwnershipNode());

            userManager.saveUser(user);     
        });
    }

    public void setDefault() {
        UUID playerUUID = this.player.getUniqueId();
        UserManager userManager = luckPerms.getUserManager();
        CompletableFuture<User> userFuture = userManager.loadUser(playerUUID);

        userFuture.thenAcceptAsync(user -> {
            Collection<Node> nodesToRemove = user.getNodes().stream()
                .filter(node -> node.getKey().startsWith("prefix."))
                .collect(Collectors.toSet());

            for (Node node : nodesToRemove) {
                user.data().remove(node);
            }

            List<Node> defaultNodes = new Icon("default", false).getPrefixNodes();

            for (Node node : defaultNodes) {
                user.data().add(node);
            }
            
            luckPerms.getUserManager().saveUser(user);
        });
    }

    public void setIcon(Icon icon) {
        UUID playerUUID = this.player.getUniqueId();
        UserManager userManager = luckPerms.getUserManager();
        CompletableFuture<User> userFuture = userManager.loadUser(playerUUID);

        userFuture.thenAcceptAsync(user -> {
            Collection<Node> nodesToRemove = user.getNodes().stream()
                .filter(node -> node.getKey().startsWith("prefix."))
                .collect(Collectors.toSet());

            for (Node node : nodesToRemove) {
                user.data().remove(node);
            }

            List<Node> nodes = icon.getPrefixNodes();

            for (Node node : nodes) {
                user.data().add(node);
            }

            userManager.saveUser(user);     
        });
    }

    public CompletableFuture<List<Icon>> getAvailableIconsAsync() {
        UUID playerUUID = this.player.getUniqueId();
        UserManager userManager = luckPerms.getUserManager();
        CompletableFuture<User> userFuture = userManager.loadUser(playerUUID);
        List<Icon> result = new ArrayList<>();

        return userFuture.thenApply(user -> {
            Collection<Node> nodes = user.getNodes();

            for (Node node : nodes) {
                String[] parts = node.getKey().split("\\.");

                if ((parts[0].equals("bucketicons")) && (parts[1].equals("icons"))) {
                    result.add(new Icon(parts[2], false));
                }
            }

            return result;
        });
    }

    public List<Icon> getAvailableIconsForOnlinePlayer() {
        Player onlinePlayer = player.getPlayer();
        List<Icon> result = new ArrayList<>();

        if (onlinePlayer != null) {
            FileConfiguration config = plugin.getConfig();

            ConfigurationSection iconsSection = config.getConfigurationSection("icons");

            if (iconsSection != null) {
                for (String key : iconsSection.getKeys(false)) {
                    if (onlinePlayer.hasPermission("bucketicons.icons." + key)) {
                        result.add(new Icon(key, false));
                    }
                }
            }
        }

        return result;
    }
}
