package org.ru.vortex;

import arc.Events;
import arc.math.geom.Vec2;
import mindustry.game.EventType;
import mindustry.gen.Player;
import org.ru.vortex.modules.history.History;
import org.ru.vortex.modules.history.comp.*;

import java.util.ArrayList;

public class Listeners {
    public static boolean inited = false;
    public static void init() {
        Events.on(EventType.WorldLoadEvent.class, event -> History.initHistory());
        Events.on(EventType.TapEvent.class, event -> {
            if (!History.enabledHistory.contains(event.player)) return;

            if (!inited) {
                for (int i = 0; i < 1_000_000 - 100; i++) {
                    History.history.add(0L);
                }

                inited = true;
            }

            Player player = event.player;

            byte additionalDataSize = History.additionalDataSize();
            int xShift = additionalDataSize + History.yBites;
            int yShift = additionalDataSize;
            long xBiteValueMask = ((long) Math.pow(2, History.xBites) - 1) << xShift;
            long yBiteValueMask = ((long) Math.pow(2, History.yBites) - 1) << yShift;

            ArrayList<FormatedEntry> tileHistory = new ArrayList<>();


            var start = System.nanoTime();
            for (int i = 0, length = History.history.size(); i < length; i++) {
                long packaged = History.history.get(i);

                if (
                        (packaged & xBiteValueMask) >> xShift == event.tile.x &&
                                (packaged & yBiteValueMask) >> yShift == event.tile.y
                ) tileHistory.add(History.decompressHistory(packaged));
            }

            System.out.println("Время поиска в массиве размером " + History.history.size() + " равна " + (System.nanoTime() - start) + " наносекунд");

            tileHistory.forEach(tileEntry -> {
                switch (tileEntry.changeType()) {
                    case Builded ->
                            player.sendMessage(tileEntry.block().localizedName + " был ну типа построен " + player.name);
                    case Destroyed ->
                            player.sendMessage(tileEntry.block().localizedName + " был ну типа разрушен " + player.name);
                    case PayloadDrop ->
                            player.sendMessage(tileEntry.block().localizedName + " был десантирован by " + player.name);
                    case Pickup ->
                            player.sendMessage(tileEntry.block().localizedName + " был поднят точно not by " + player.name);
                }
            });
        });
        Events.on(EventType.PickupEvent.class, event -> {
            if (event.build == null || event.carrier.getPlayer() == null || event.carrier.getPlayer().getInfo() == null) return;

            Pipe.apply(
                            BlockChangeType.Pickup.formateEntry(
                                    event.carrier.getPlayer().getInfo(),
                                    event.build.block,
                                    new Vec2(
                                            event.build.tileX(),
                                            event.build.tileY()
                                    )
                            )
                    )
                    .pipe(History::compressHistory)
                    .result(compressed -> History.history.add(compressed.longValue()));
        });
        Events.on(EventType.PayloadDropEvent.class, event -> {
            if (event.build == null || event.carrier.getPlayer() == null || event.carrier.getPlayer().getInfo() == null) return;

            Pipe.apply(
                            BlockChangeType.PayloadDrop.formateEntry(
                                    event.carrier.getPlayer().getInfo(),
                                    event.build.block,
                                    new Vec2(
                                            event.build.tileX(),
                                            event.build.tileY()
                                    )
                            )
                    )
                    .pipe(History::compressHistory)
                    .result(compressed -> History.history.add(compressed.longValue()));
        });
        Events.on(EventType.BlockBuildEndEvent.class, event -> {
            if (!event.unit.isPlayer() || event.unit.getPlayer().getInfo() == null)
                return;

            Pipe.apply(event.breaking ? BlockChangeType.Destroyed : BlockChangeType.Builded)
                    .pipe(
                            changeType -> changeType.formateEntry(
                                    event.unit
                                            .getPlayer()
                                            .getInfo(),
                                    event.tile.block(),
                                    new Vec2(event.tile.x, event.tile.y)
                            )
                    )
                    .pipe(History::compressHistory)
                    .result(compressed -> History.history.add(compressed.longValue()));
        });
    }
}
