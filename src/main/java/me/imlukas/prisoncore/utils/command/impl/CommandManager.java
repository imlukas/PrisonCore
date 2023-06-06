package me.imlukas.prisoncore.utils.command.impl;

import me.imlukas.prisoncore.PrisonCore;
import me.imlukas.prisoncore.utils.command.BaseCommand;
import me.imlukas.prisoncore.utils.command.SimpleCommand;
import me.imlukas.prisoncore.utils.command.comparison.ComparisonResult;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class CommandManager {

    private static CommandMap commandMap;
    private static Constructor<PluginCommand> pluginCommandConstructor;

    static {
        try {
            Server server = Bukkit.getServer();
            Field commandMapField = server.getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);

            commandMap = (CommandMap) commandMapField.get(server);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            pluginCommandConstructor = PluginCommand.class.getDeclaredConstructor(String.class,
                    Plugin.class);
            pluginCommandConstructor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private final Set<String> registeredBaseCommands = new HashSet<>();
    private final Map<String, SimpleCommand> commands = new HashMap<>();
    private final PrisonCore main;

    public CommandManager(PrisonCore main) {
        this.main = main;
    }

    public void register(SimpleCommand command) {
        if (command.getIdentifier() == null) {
            System.err.println("Command " + command.getClass().getSimpleName() + " has no identifier!");
            return;
        }


        if (command.getIdentifier().startsWith("*")) {
            throw new IllegalArgumentException("Command identifier cannot start with *");
        }

        int wildcardCount = 0;

        for (char c : command.getIdentifier().toCharArray()) {
            if (c == '*') {
                wildcardCount++;
            }
        }

        for (String alias : command.getAliases()) {
            if (alias.startsWith("*")) {
                throw new IllegalArgumentException("Command alias cannot start with *");
            }

            int aliasWildcardCount = 0;

            for (char c : alias.toCharArray()) {
                if (c == '*') {
                    aliasWildcardCount++;
                }
            }

            if (aliasWildcardCount != wildcardCount) {
                throw new IllegalArgumentException("Command alias " + alias + " does not have the same amount of wildcards as the command identifier " + command.getIdentifier());
            }
        }

        registerCommand(command.getIdentifier(), command);

        for (String alias : command.getAliases()) {
            registerCommand(alias, command);
        }
    }

    private void registerCommand(String identifier, SimpleCommand command) {
        commands.put(identifier, command);

        String base = getBaseCommand(identifier);

        if (!registeredBaseCommands.contains(base)) {
            try {
                PluginCommand pluginCommand = pluginCommandConstructor.newInstance(base, main);

                BaseCommand baseCommand = new BaseCommand(main);

                pluginCommand.setExecutor(baseCommand);
                pluginCommand.setTabCompleter(baseCommand);

                commandMap.register(base, pluginCommand);

            } catch (InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                e.printStackTrace();
            }

            System.out.println(base + " was registered as a command");
            registeredBaseCommands.add(base);
        }
    }

    public SimpleCommand get(String identifier) {
        String[] split = identifier.split("\\.");
        String base = split[0];

        if (split.length == 1) {
            return commands.get(base);
        }

        SimpleCommand command = commands.get(base);

        if (command != null) {
            return command;
        }

        for (String key : commands.keySet()) {
            if (key.startsWith(base + ".")) {
                String[] splitKey = key.split("\\.");
                String[] splitIdentifier = identifier.split("\\.");

                boolean matches = true;

                for (int i = 0; i < splitKey.length; i++) {
                    if (splitKey[i].equals("*")) {
                        continue;
                    }

                    if (splitKey[i].equals(splitIdentifier[i])) {
                        continue;
                    }

                    matches = false;
                    break;
                }

                if (matches) {
                    return commands.get(key);
                }
            }
        }

        return null;
    }

    public List<String> tabComplete(String identifier) {

        ComparisonResult result = new ComparisonResult(commands);
        return result.tabComplete(identifier);
    }

    public SimpleCommand get(String name, String... args) {
        String identifier = String.join(".", name, String.join(".", args));

        return get(identifier);
    }

    private String getBaseCommand(String identifier) {
        int index = identifier.indexOf(".");
        return index == -1 ? identifier : identifier.substring(0, index);
    }

}
