package org.ru.vortex.utils;

import mindustry.net.NetConnection;
import org.ru.vortex.modules.Bundler;

import java.util.Locale;

import static org.ru.vortex.Vars.serverLink;

public class Administration {
    public static void kick(NetConnection con, long duration, String key, String locale, Object... objects) {
        var reason = Bundler.getLocalized(Locale.of(locale), key, objects);

        if (duration > 0) reason += Bundler.getLocalized(Locale.of(locale), "kick.time", duration);
        reason += Bundler.getLocalized(Locale.of(locale), "kick.disclaimer", serverLink);

        con.kick(reason, duration);
    }
}
