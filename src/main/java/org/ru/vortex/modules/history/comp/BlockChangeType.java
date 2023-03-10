package org.ru.vortex.modules.history.comp;

import java.util.function.Function;

import arc.func.Cons2;
import arc.math.geom.Position;
import mindustry.net.Administration.PlayerInfo;
import mindustry.world.Block;

public enum BlockChangeType {
    Destroyed(),
    Builded(),
    PayloadDrop(),
    Pickup();

    public final byte id;
    public final Function<Block, Long> packageData;
    public Cons2<Block, Long> applyAdditionalData;

    BlockChangeType() {
        this(
            (block) -> 0L,
            (block, packagedData) -> {}
        );
    }

    BlockChangeType(Function<Block, Long> packageData, Cons2<Block, Long> applyAditionalData) {
        this.id = (byte) (StaticFields.lastID += 1);
        this.packageData = packageData;
        this.applyAdditionalData = applyAditionalData;
    }

    public FormatedEntry formateEntry(
        PlayerInfo participantInfo,
        Block block,
        Position position,
        long additionalData
    ) {
        return new FormatedEntry(this, participantInfo, block, position, additionalData);
    }

    public FormatedEntry formateEntry(
        PlayerInfo participantInfo,
        Block block,
        Position position
    ) {
        return formateEntry(participantInfo, block, position, packageData.apply(block));
    }

    public static BlockChangeType getByID(byte id) {
        for (BlockChangeType changeType : BlockChangeType.values()) {
            if (changeType.id == id)
                return changeType;
        }

        return null;
    }

    public static final class StaticFields {
        public static int lastID = -1;
    }
}
