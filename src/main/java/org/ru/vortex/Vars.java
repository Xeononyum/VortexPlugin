package org.ru.vortex;

import arc.util.CommandHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.ru.vortex.modules.Config;

import java.util.HashSet;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_DASHES;

public class Vars {
    public static final String serverLink = "";
    public static final Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(LOWER_CASE_WITH_DASHES)
            .setPrettyPrinting()
            .serializeNulls()
            .disableHtmlEscaping()
            .create();
    public static final String discordAuthString = "https://discord.com/api/oauth2/authorize?client_id=1058095954097610794&redirect_uri=http%3A%2F%2Flocalhost%3A3000%2Fredirect&response_type=code&scope=identify&state=";
    public static final String outlinePassword = "hentai";
    public static Config config;
    public static CommandHandler clientCommands, serverCommands, discordCommands, outlineCommands;
    public static double rtvRatio = 0.6;
    public static HashSet<String> rtvVotes = new HashSet<>();
    public static boolean rtvEnabled = true;
}
