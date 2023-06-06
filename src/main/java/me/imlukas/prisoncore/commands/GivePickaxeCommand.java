package me.imlukas.prisoncore.commands;

import me.imlukas.prisoncore.PrisonCore;
import me.imlukas.prisoncore.items.BaseItem;
import me.imlukas.prisoncore.items.registry.ItemParser;
import me.imlukas.prisoncore.items.registry.PrisonItemRegistry;
import me.imlukas.prisoncore.utils.command.SimpleCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class GivePickaxeCommand implements SimpleCommand {
    private final PrisonItemRegistry prisonItemRegistry;
    private final ItemParser itemParser;

    public GivePickaxeCommand(PrisonCore plugin) {
        this.prisonItemRegistry = plugin.getPrisonItemRegistry();
        this.itemParser = plugin.getItemParser();
    }

    @Override
    public Map<Integer, List<String>> tabCompleteWildcards() {
        return Map.of(1, itemParser.getItemIdentifiers());
    }

    @Override
    public String getIdentifier() {
        return "pcore.give.*";
    }

    @Override
    public void execute(CommandSender sender, String... args) {
        Player player = (Player) sender;

        if (args[0].isEmpty()) {
            return;
        }

        String identifier = args[0];

        BaseItem baseItem = itemParser.getItem(player, identifier);

        if (baseItem == null) {
            player.sendMessage("Item not found!");
            return;
        }

        baseItem.giveItem();
        prisonItemRegistry.registerItem(player.getUniqueId(), baseItem);

    }
}
