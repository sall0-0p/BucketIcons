package com.bucketicons.utils;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.bucketicons.commands.GiveCommand;
import com.bucketicons.commands.ReloadCommand;
import com.bucketicons.commands.RemoveCommand;
import com.bucketicons.commands.SwapCommand;

import net.kyori.adventure.text.minimessage.MiniMessage;

public class CommandHandler implements CommandExecutor {
    private final Map<String, Command> commands = new HashMap<>();
    private static final MiniMessage mm = MiniMessage.miniMessage();

    public CommandHandler() {
        commands.put("give", new GiveCommand());
        commands.put("remove", new RemoveCommand());
        commands.put("swap", new SwapCommand());
        commands.put("reload", new ReloadCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        try {
            if (args.length == 0) {
                throw new Exception("Usage: /bi <sub-arguments>");
            }
            
            StringBuilder subCommandPath = new StringBuilder(args[0]);
            Command command = commands.get(subCommandPath.toString());

            int index = 1;
            while (command == null && index < args.length) {
                subCommandPath.append(" ").append(args[index]);
                command = commands.get(subCommandPath.toString());
                index++;
            }
    
            if (command == null) {
                sender.sendMessage("Unknown command. Type \"/help\" for help.");
                return true;
            }
    
            // Create a new array for the remaining arguments
            String[] subCommandArgs = new String[args.length - index];
            System.arraycopy(args, index, subCommandArgs, 0, args.length - index);
    
            command.execute(sender, subCommandArgs);

            return true;
        } catch (Exception e) {
            sender.sendMessage(mm.deserialize("<red>| " + e.getMessage()));

            return true;
        }
    }
}
