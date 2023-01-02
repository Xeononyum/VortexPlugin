package org.ru.vortex.modules.history.comp;

import arc.math.geom.Position;
import mindustry.net.Administration.PlayerInfo;
import mindustry.world.Block;
import org.ru.vortex.modules.history.comp.BlockChangeType;

public record FormatedEntry(
    BlockChangeType changeType,
    PlayerInfo participantInfo,
    Block block,
    Position position,
    long additionalData
) {
    public void applyAdditionalData() {
        changeType.applyAdditionalData.get(block, additionalData);
    };
}