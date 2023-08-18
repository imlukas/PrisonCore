package me.imlukas.prisoncore.modules.items.items.command;

import me.imlukas.prisoncore.PrisonCore;
import me.imlukas.prisoncore.modules.items.ItemModule;
import me.imlukas.prisoncore.modules.items.items.cache.PrisonItemCache;
import me.imlukas.prisoncore.modules.items.items.registry.PrisonItemRegistry;
import me.imlukas.prisoncore.modules.items.items.impl.PrisonItem;
import me.imlukas.prisoncore.utils.command.SimpleCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class GiveItemCommand implements SimpleCommand {

    private final PrisonCore plugin;
    private final PrisonItemRegistry prisonItemRegistry;
    private final PrisonItemCache prisonItemCache;

    public GiveItemCommand(ItemModule itemModule) {
        this.plugin = itemModule.getPlugin();
        this.prisonItemRegistry = itemModule.getPrisonItemRegistry();
        this.prisonItemCache = itemModule.getPrisonItemCache();
    }

    @Override
    public String getIdentifier() {
        return "pcore.give.*";
    }

    @Override
    public Map<Integer, List<String>> tabCompleteWildcards() {
        return Map.of(1, prisonItemRegistry.getItemIdentifiers());
    }

    @Override
    public void execute(CommandSender sender, String... args) {
        Player player = (Player) sender;

        if (args[0].isEmpty()) {
            return;
        }

        String identifier = args[0];
        PrisonItem prisonItem = prisonItemRegistry.get(identifier);

        if (prisonItem == null) {
            player.sendMessage("This item does not exist!");
            return;
        }

        prisonItemCache.put(prisonItem);
        player.getInventory().addItem(prisonItem.getDisplayItem());
        player.sendMessage("You have been given the item " + identifier);

    }
}
