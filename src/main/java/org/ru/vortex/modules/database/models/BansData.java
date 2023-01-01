package org.ru.vortex.modules.database.models;

public class BansData {
    public String uuid;
    public String reason;
    public long unbanDate;

    BansData(String uuid, String reason, long unbanDate) {
        this.uuid = uuid;
        this.reason = reason;
        this.unbanDate = unbanDate;
    }
}
