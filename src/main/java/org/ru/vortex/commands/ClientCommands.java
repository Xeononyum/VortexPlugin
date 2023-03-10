package org.ru.vortex.commands;

import arc.Events;
import arc.util.CommandHandler.CommandRunner;
import arc.util.Strings;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import org.ru.vortex.modules.Bundler;
import org.ru.vortex.utils.Timeouts;

import static mindustry.gen.Call.openURI;
import static org.ru.vortex.Vars.*;
import static org.ru.vortex.modules.history.History.enabledHistory;

public class ClientCommands {
    public static void init() {
        register("discord", (args, player) -> openURI(player.con, serverLink));

        register("rtv", (args, player) -> {
            if (player.admin()) {
                rtvEnabled = args.length != 1 || !args[0].equals("off");
            }

            if (!rtvEnabled) {
                Bundler.sendLocalized(player, "commands.rtv.disabled");
                return;
            }

            rtvVotes.add(player.uuid());

            int cur = rtvVotes.size();
            int req = (int) Math.ceil(rtvRatio * Groups.player.size());

            Bundler.sendLocalizedAll("commands.rtv.change-map", player.name, cur, req);

            if (cur < req) return;
            rtvVotes.clear();

            Bundler.sendLocalizedAll("commands.rtv.vote-passed");
            Events.fire(new EventType.GameOverEvent(Team.crux));
        });

        register("history", (args, player) -> {
            if (enabledHistory.contains(player)) {
                enabledHistory.remove(player);
                return;
            };

            enabledHistory.add(player);
        });
    }

    private static void register(String name, CommandRunner<Player> runner) {
        clientCommands.<Player>register(
                name,
                Bundler.getLocalized(Strings.format("commands.@.parameters", name)),
                Bundler.getLocalized(Strings.format("commands.@.description", name)),
                (args, player) -> {
                    if (Timeouts.hasTimeout(player, name)) return;
                    runner.accept(args, player);
                    Timeouts.timeout(player, name);
                });

    }
}
