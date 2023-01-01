package org.ru.vortex.modules.database.models;

public class PlayerData {
    public String uuid;
    public long discord = 0L;


    @SuppressWarnings("unused")
    public PlayerData() {
    }

    public PlayerData(String uuid) {
        this.uuid = uuid;
    }
}
