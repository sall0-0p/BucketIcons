package com.bucketicons.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;

import com.bucketicons.Plugin;

import net.luckperms.api.context.DefaultContextKeys;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.types.PrefixNode;

public class Icon {
    Plugin plugin = Plugin.getPlugin();

    private final String name;
    private final String symbol;
    private final List<Node> prefixNodes;
    private final Node ownershipNode;
    private final int priority;

    public Icon(String input, boolean fromSymbol) {
        FileConfiguration config = plugin.getConfig();

        if (fromSymbol) {
            this.name = IconManager.findIconNameBySymbol(input);
            this.symbol = input;
        } else {
            this.name = input;
            this.symbol = config.getString("icons." + this.name + ".symbol");
        }

        this.priority = config.getInt("icons." + this.name + ".priority");
        this.ownershipNode = Node.builder("bucketicons.icons." + this.name).build();
        this.prefixNodes = buildNodes();
    }

    public String getName() {
        return this.name;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public List<Node> getPrefixNodes() {
        return prefixNodes;
    }

    public Node getOwnershipNode() {
        return ownershipNode;
    }

    public int getPriority() {
        return priority;
    }

    private List<Node> buildNodes() {
        FileConfiguration config = plugin.getConfig();
        List<Node> result = new ArrayList<>();
        Set<String> worlds = IconManager.getWorlds();

        if (config.getBoolean("multi-server")) {
            for (String world : worlds) {
                String worldSymbol = config.getString("servers." + world + ".symbol");

                PrefixNode node = PrefixNode.builder(worldSymbol + this.symbol, this.priority)
                    .withContext(DefaultContextKeys.SERVER_KEY, world)
                    .build();

                result.add(node);
            }
        } else {
            PrefixNode node = PrefixNode.builder(this.symbol, this.priority).build();
            result.add(node);
        }
        return result;
    }
}
