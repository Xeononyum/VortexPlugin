package org.ru.vortex.modules;

import arc.files.Fi;
import arc.util.Log;
import arc.util.Strings;

import static mindustry.Vars.dataDirectory;
import static mindustry.net.Administration.Config.*;
import static org.ru.vortex.Vars.config;
import static org.ru.vortex.Vars.gson;
import static org.ru.vortex.modules.Config.Gamemode.hub;
import static org.ru.vortex.modules.Config.Gamemode.survival;

public class Config {
    public String mongoUrl = "";
    public String token = "";
    public String prefix = "";
    public String channelId = "";
    public String adminRoleId = "";
    public String adminChannelId = "";
    public Gamemode gamemode = survival;

    public static void init() {
        Fi file = dataDirectory.child("config.json");

        if (file.exists()) {
            config = gson.fromJson(file.reader(), Config.class);
        } else {
            file.writeString(gson.toJson(config = new Config()));
            Log.infoTag("Config", Strings.format("Config generated in @", file.absolutePath()));
        }

        autoPause.set(config.gamemode.isDefault());
        enableVotekick.set(config.gamemode != hub);

        motd.set("off");
        showConnectMessages.set(false);
        logging.set(true);
        strict.set(true);
        antiSpam.set(true);

        interactRateWindow.set(1);
        interactRateLimit.set(15);
        messageRateLimit.set(1);
        packetSpamLimit.set(250);

        interactRateKick.set(15);
        messageSpamKick.set(5);

        snapshotInterval.set(250);
    }

    public enum Gamemode {
        attack, hub, pvp, sandbox, survival;

        public boolean isDefault() {
            return this == attack || this == pvp || this == sandbox || this == survival;
        }
    }
}
