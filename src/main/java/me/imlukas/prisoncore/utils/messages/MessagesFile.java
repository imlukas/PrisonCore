package me.imlukas.prisoncore.utils.messages;

import me.imlukas.prisoncore.PrisonCore;
import me.imlukas.prisoncore.utils.storage.YMLBase;
import me.imlukas.prisoncore.utils.text.Placeholder;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang3.StringEscapeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Collection;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessagesFile extends YMLBase {

    private final Pattern pattern;
    private final String prefix, arrow;
    protected boolean usePrefixConfig, useActionBar, isLessIntrusive;
    private String msg;

    public MessagesFile(PrisonCore plugin) {
        super(plugin, new File(plugin.getDataFolder(), "messages.yml"), true);
        pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        prefix = StringEscapeUtils.unescapeJava(getConfiguration().getString("messages.prefix"));
        arrow = StringEscapeUtils.unescapeJava(getConfiguration().getString("messages.arrow"));
        usePrefixConfig = getConfiguration().getBoolean("messages.use-prefix");
        useActionBar = getConfiguration().getBoolean("messages.use-actionbar");
        isLessIntrusive = getConfiguration().getBoolean("messages.less-intrusive");
        writeUnsetValues();

    }

    public String setColor(String message) {
        String[] split = Bukkit.getBukkitVersion().split("-")[0].split("\\.");
        int minorVer = Integer.parseInt(split[1]);

        if (minorVer >= 16) {
            Matcher matcher = pattern.matcher(message);

            while (matcher.find()) {
                String color = message.substring(matcher.start(), matcher.end());
                message = message.replace(color, net.md_5.bungee.api.ChatColor.of(color) + "");
                matcher = pattern.matcher(message);
            }
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private String setMessage(String name) {
        return setMessage(name, (s) -> s);
    }

    private String setMessage(String name, Function<String, String> action) {
        if (!getConfiguration().contains("messages." + name))
            return "";
        msg = getMessage(name);
        if (usePrefixConfig && !isLessIntrusive) {
            msg = prefix + " " + arrow + " " + getMessage(name);
        } else {
            msg = msg.replace("%prefix%", prefix);
        }
        msg = action.apply(msg);
        return setColor(msg);
    }

    public void sendStringMessage(CommandSender player, String msg) {
        player.sendMessage(setColor(msg));
    }

    public void sendMessage(CommandSender sender, String name) {
        sendMessage(sender, name, (s) -> s);
    }


    @SafeVarargs
    public final <T extends CommandSender> void sendMessage(T sender, String name, Placeholder<T>... placeholders) {
        sendMessage(sender, name, (text) -> {
            for (Placeholder<T> placeholder : placeholders) {
                text = placeholder.replace(text, sender);
            }

            return text;
        });
    }

    public final <T extends CommandSender> void sendMessage(T sender, String name, Collection<Placeholder<T>> placeholders) {
        sendMessage(sender, name, (text) -> {
            for (Placeholder<T> placeholder : placeholders) {
                text = placeholder.replace(text, sender);
            }

            return text;
        });
    }


    public void sendMessage(CommandSender sender, String name, Function<String, String> action) {
        if (getConfiguration().isList("messages." + name)) {
            for (String str : getConfiguration().getStringList("messages." + name)) {
                msg = StringEscapeUtils.unescapeJava(str.replace("%prefix%", prefix));
                msg = action.apply(msg);
                sender.sendMessage(setColor(msg));
            }
            return;
        }

        msg = setMessage(name, action);
        if (useActionBar && sender instanceof Player player && !isLessIntrusive) {
            sendActionbarStringMessage(player, msg);
            return;
        }
        sender.sendMessage(msg);
    }

    public void sendActionbarStringMessage(Player player, String msg) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(msg));
    }

    public void sendActionBarMessage(Player player, String key) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(setMessage(key)));
    }

    public void sendActionBarMessage(Player player, String key, Function<String, String> action) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(setMessage(key, action)));
    }

    public String getMessage(String name) {
        return getConfiguration().getString("messages." + name);
    }


    public boolean togglePrefix() {
        return toggle("prefix", usePrefixConfig);
    }

    public boolean toggleActionBar() {
        return toggle("actionbar", useActionBar);
    }

    public boolean toggleLessIntrusive() {
        return toggle("less-intrusive", isLessIntrusive);
    }

    public boolean toggle(String type, boolean isEnabled) {
        if (isEnabled) {
            getConfiguration().set("messages.use-" + type, false);
        } else {
            getConfiguration().set("messages.use-" + type, true);
        }

        save();
        return !isEnabled;
    }
}

