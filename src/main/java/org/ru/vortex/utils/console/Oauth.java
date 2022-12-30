package org.ru.vortex.utils.console;

import arc.struct.Seq;
import mindustry.gen.Player;

import static org.ru.vortex.Vars.discordAuthString;

/**
 * Class contains static OauthMethods
 */
public class Oauth {

    /**
     * Generate auth link using player uuid
     *
     * @param p player who need auth url
     * @return http string ready-to-use
     * @see org.ru.vortex.Vars#discordAuthString
     */
    public static String getAuthLink(Player p) {
        return discordAuthString + p.uuid();
    }

    /**
     * Check player's authorization
     *
     * @param p target player
     * @return boolean value of player's authorization
     */
    public static boolean isAuthorized(Player p) {
        // TODO: 12/30/22 Mongo reference
        return false;
    }

    /**
     * Returns list of players who authed with current discord id
     *
     * @param discordId discord id for search
     * @return Seq<Player> of founded players, size = 0 means no players founded
     */
    public static Seq<Player> getAuthorizedByDISCORDID(Long discordId) {
        Seq<Player> players = new Seq<>();
        // TODO: 12/30/22 Mongo reference
        return players;
    }

    /**
     * Finding discord id for player
     *
     * @param p target player
     * @return ID of this player, -1 if not authorized
     * @see #getDISCORDID(String)
     */
    public static Long getDISCORDID(Player p) {
        return getDISCORDID(p.uuid());
    }

    /**
     * Find discord id for uuid string
     *
     * @param uuid string(player uuid)
     * @return ID associated with this UUID, -1 if not authorized
     */
    public static Long getDISCORDID(String uuid) {
        // TODO: 12/30/22 Mongo reference
        return -1L;
    }
}
