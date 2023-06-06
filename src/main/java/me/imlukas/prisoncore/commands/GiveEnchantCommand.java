package me.imlukas.prisoncore.commands;

import me.imlukas.prisoncore.PrisonCore;
import me.imlukas.prisoncore.enchantments.Enchantment;
import me.imlukas.prisoncore.enchantments.registry.EnchantmentRegistry;
import me.imlukas.prisoncore.items.BaseItem;
import me.imlukas.prisoncore.items.EnchantableItem;
import me.imlukas.prisoncore.items.registry.PrisonItemRegistry;
import me.imlukas.prisoncore.utils.NBTUtil;
import me.imlukas.prisoncore.utils.command.SimpleCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GiveEnchantCommand implements SimpleCommand {
    private final PrisonItemRegistry prisonItemRegistry;
    private final EnchantmentRegistry enchantmentRegistry;

    public GiveEnchantCommand(PrisonCore plugin) {
        this.prisonItemRegistry = plugin.getPrisonItemRegistry();
        this.enchantmentRegistry = plugin.getEnchantmentRegistry();
    }

    @Override
    public Map<Integer, List<String>> tabCompleteWildcards() {
        return Map.of(1, enchantmentRegistry.getEnchantmentIdentifiers());
    }

    @Override
    public String getIdentifier() {
        return "pcore.enchant.*.*";
    }

    @Override
    public void execute(CommandSender sender, String... args) {
        Player player = (Player) sender;
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

        if (itemInMainHand.getType().isAir()) {
            return;
        }

        UUID itemId = NBTUtil.getItemId(itemInMainHand);

        if (itemId == null) {
            player.sendMessage("This item does not have an identifier!");
            return;
        }

        BaseItem baseItem = prisonItemRegistry.getItem(itemId);

        if (baseItem == null) {
            player.sendMessage("This item does not have a PrisonItem!");
            return;
        }

        if (!(baseItem instanceof EnchantableItem enchantableItem)) {
            player.sendMessage("This item is not enchantable!");
            return;
        }

        String enchantmentName = args[0];

        int level = 1;
        if (!args[1].isEmpty()) {
            level = Integer.parseInt(args[1]);
        }

        Enchantment enchantment = enchantmentRegistry.getEnchantment(enchantmentName, level);

        if (enchantment == null) {
            player.sendMessage("This enchantment does not exist!");
            return;
        }

        enchantableItem.addEnchantment(enchantment);
    }
}
