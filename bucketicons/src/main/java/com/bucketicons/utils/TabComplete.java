package com.bucketicons.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class TabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        try {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if ((args.length) > 1) {
                    if (args[0].equals("give")) {
                        switch (args.length) {
                            case 2:
                                return null;
                            case 3:
                                return IconManager.getAllSymbols();
                        }

                        return new ArrayList<>();
                    }

                    if (args[0].equals("remove")) {
                        switch (args.length) {
                            case 2:
                                return null;
                            case 3:
                                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);

                                if (target.isOnline()) {
                                    IconOwner owner = new IconOwner(target);
                                    List<String> result = new ArrayList<>();

                                    for (Icon icon : owner.getAvailableIconsForOnlinePlayer()) {
                                        result.add(icon.getSymbol());
                                    }

                                    return result;
                                } else {
                                    return IconManager.getAllSymbols();
                                }
                        }

                        return new ArrayList<>();
                    }

                    if (args[0].equals("swap")) {
                        switch (args.length) {
                            case 2:
                                OfflinePlayer target = Bukkit.getOfflinePlayer(player.getName());

                                IconOwner owner = new IconOwner(target);
                                List<String> result = new ArrayList<>();

                                for (Icon icon : owner.getAvailableIconsForOnlinePlayer()) {
                                    result.add(icon.getSymbol());
                                }

                                return result;
                            
                            case 3:
                                if (sender.hasPermission("bucketicons.swap.others")) {
                                    return null;
                                } else {
                                    return new ArrayList<>();
                                }
                        }

                        return new ArrayList<>();
                    }

                    return new ArrayList<>();
                } else {
                    List<String> result = new ArrayList<>();

                    addIfHasPermission(player, result, "give", "bucketicons.give");
                    addIfHasPermission(player, result, "remove", "bucketicons.remove");
                    addIfHasPermission(player, result, "swap", "bucketicons.swap");
                    addIfHasPermission(player, result, "reload", "bucketicons.reload");

                    return result;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private void addIfHasPermission(Player player, List<String> list, String command, String permission) {
        if (player.hasPermission(permission)) {
            list.add(command);
        }
    }
}
