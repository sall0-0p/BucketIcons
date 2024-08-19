package com.bucketicons.utils;

import org.bukkit.command.CommandSender;

public interface Command {
    void execute(CommandSender sender, String[] args) throws Exception;
}
