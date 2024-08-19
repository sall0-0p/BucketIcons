package com.bucketicons.commands;

import org.bukkit.command.CommandSender;

import com.bucketicons.Plugin;
import com.bucketicons.utils.Command;
import com.bucketicons.utils.Messages;

import net.kyori.adventure.text.minimessage.MiniMessage;

public class ReloadCommand implements Command {
    private static final MiniMessage mm = MiniMessage.miniMessage();
    private static final Plugin plugin = Plugin.getPlugin();

    @Override
    public void execute(CommandSender sender, String[] args) throws Exception {
        if (!sender.hasPermission("bucketicons.reload")) {
            throw new Exception("You do not have permission to use this command!");
        }

        plugin.reloadConfig();
        Messages.reloadConfig();
        
        sender.sendMessage(mm.deserialize(Messages.getString("reloaded")));
    }
}
