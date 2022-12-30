package org.ru.vortex.commands;

import arc.util.CommandHandler;

import static org.ru.vortex.Vars.config;
import static org.ru.vortex.Vars.discordCommands;

public class DiscordCommands {
    public static void init() {
        discordCommands = new CommandHandler(config.prefix);
    }
}
