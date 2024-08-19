package com.bucketicons.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import com.bucketicons.utils.Command;
import com.bucketicons.utils.Icon;
import com.bucketicons.utils.IconOwner;
import com.bucketicons.utils.Messages;

import net.kyori.adventure.text.minimessage.MiniMessage;

public class GiveCommand implements Command {
    private static final MiniMessage mm = MiniMessage.miniMessage();

    @Override
    public void execute(CommandSender sender, String[] args) throws Exception {
        if (!sender.hasPermission("bucketicons.give")) {
            throw new Exception("You do not have permission to use this command!");
        }

        if (args.length < 2) {
            throw new Exception("You need to provide player and icon!");
        }

        IconOwner target = new IconOwner(Bukkit.getOfflinePlayer(args[0]));

        target.giveIcon(new Icon(args[1], true));

        sender.sendMessage(mm.deserialize(Messages.getString("give_success")));
    }
}
