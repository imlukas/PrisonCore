package me.imlukas.prisoncore.utils.messages;

import me.imlukas.prisoncore.PrisonCore;
import me.imlukas.prisoncore.utils.storage.YMLBase;
import me.imlukas.prisoncore.utils.text.Placeholder;
import me.imlukas.prisoncore.utils.text.TextUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang3.StringEscapeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessagesFile extends YMLBase {

    private final String prefix, arrow;
    protected boolean usePrefixConfig, useActionBar, isLessIntrusive;
    private String msg;

    public MessagesFile(PrisonCore plugin) {
        super(plugin, new File(plugin.getDataFolder(), "messages.yml"), true);

        prefix = StringEscapeUtils.unescapeJava(getConfiguration().getString("messages.prefix"));
        arrow = StringEscapeUtils.unescapeJava(getConfiguration().getString("messages.arrow"));
        usePrefixConfig = getConfiguration().getBoolean("messages.use-prefix");
        useActionBar = getConfiguration().getBoolean("messages.use-actionbar");
        isLessIntrusive = getConfiguration().getBoolean("messages.less-intrusive");
        writeUnsetValues();

    }

    private String setMessage(String name, Function<String, String> action) {
        if (!getConfiguration().contains("messages." + name)) {
            return "";
        }

        msg = getMessage(name).replaceAll("%prefix%", prefix);

        if (usePrefixConfig) {
            msg = prefix + " " + getMessage(name);
        }

        msg = action.apply(msg);
        return TextUtils.color(msg);
    }

    public void sendMessage(CommandSender sender, String name) {
        sendMessage(sender, name, (s) -> s);
    }

    @SafeVarargs
    public final <T extends CommandSender> void sendMessage(T sender, String name, Placeholder<T>... placeholders) {
        sendMessage(sender, name, List.of(placeholders));
    }

    public final <T extends CommandSender> void sendMessage(T sender, String name, Collection<Placeholder<T>> placeholders) {
        sendMessage(sender, name, text -> {
            for (Placeholder<T> placeholder : placeholders) {
                text = placeholder.replace(text, sender);
            }

            return text;
        });
    }


    public void sendMessage(CommandSender sender, String name, UnaryOperator<String> action) {
        if (getConfiguration().isList("messages." + name)) {
            for (String message : getConfiguration().getStringList("messages." + name)) {
                msg = message.replace("%prefix%", prefix);
                msg = TextUtils.color(action.apply(msg));
                sender.sendMessage(msg);
            }
            return;
        }

        msg = setMessage(name, action);
        sender.sendMessage(msg);
    }

    public String getMessage(String name) {
        return getConfiguration().getString("messages." + name);
    }

    public String getMessage(String name, Placeholder<CommandSender>... placeholders) {
        String message = getMessage(name);

        if (message == null) {
            return null;
        }

        for (Placeholder<CommandSender> placeholder : placeholders) {
            message = placeholder.replace(message, null);
        }

        return message;
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

