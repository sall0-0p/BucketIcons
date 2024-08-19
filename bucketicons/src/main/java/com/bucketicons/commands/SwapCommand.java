package com.bucketicons.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bucketicons.utils.Command;
import com.bucketicons.utils.Icon;
import com.bucketicons.utils.IconOwner;
import com.bucketicons.utils.Messages;

import net.kyori.adventure.text.minimessage.MiniMessage;

public class SwapCommand implements Command {
    private static final MiniMessage mm = MiniMessage.miniMessage();
        
    @Override
    public void execute(CommandSender sender, String[] args) throws Exception {
        if (!sender.hasPermission("bucketicons.swap")) {
            throw new Exception("You do not have permission to use this command!");
        }

        if (args.length < 1) {
            throw new Exception("You need to provide player and icon!");
        }

        if (!sender.hasPermission("bucketicons.icons." + new Icon(args[0], true).getName())) {
            throw new Exception("You do not have access to this icon!");
        }

        if (args.length == 2) {
            if (!sender.hasPermission("bucketicons.swap.others")) {
                throw new Exception("You do not have permission to use this command in this way!");
            }
            IconOwner target = new IconOwner(Bukkit.getOfflinePlayer(args[1]));
            target.setIcon(new Icon(args[0], true));

            sender.sendMessage(mm.deserialize(Messages.getString("swap_success")));
        } else if (args.length == 1) {
            if (!(sender instanceof Player)) {
                throw new Exception("This command can be used only as player!");
            }
            IconOwner target = new IconOwner(Bukkit.getOfflinePlayer(sender.getName()));
            target.setIcon(new Icon(args[0], true));

            sender.sendMessage(mm.deserialize(Messages.getString("swap_success")));
        } else {
            throw new Exception("Wrong amount of arguments!");
        }
    }
}
