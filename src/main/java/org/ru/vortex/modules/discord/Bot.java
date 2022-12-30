package org.ru.vortex.modules.discord;

import arc.util.Log;
import mindustry.gen.Groups;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.dv8tion.jda.api.utils.messages.MessageRequest;

import java.util.EnumSet;

import static arc.util.Strings.format;
import static arc.util.Strings.stripColors;
import static mindustry.Vars.state;
import static net.dv8tion.jda.api.JDA.Status.CONNECTED;
import static net.dv8tion.jda.api.entities.Activity.watching;
import static net.dv8tion.jda.api.entities.Message.MentionType.CHANNEL;
import static net.dv8tion.jda.api.entities.Message.MentionType.EMOJI;
import static net.dv8tion.jda.api.requests.GatewayIntent.*;
import static net.dv8tion.jda.api.utils.MemberCachePolicy.OWNER;
import static net.dv8tion.jda.api.utils.MemberCachePolicy.VOICE;
import static org.ru.vortex.Vars.config;

public class Bot {
    public static Role adminRole;
    public static MessageChannel botChannel, adminChannel;
    private static JDA jda;

    public static void init() {
        try {
            jda = JDABuilder.createLight(config.token)
                    .disableCache(CacheFlag.ACTIVITY)
                    .setMemberCachePolicy(VOICE.or(OWNER))
                    .setChunkingFilter(ChunkingFilter.NONE)
                    .disableIntents(GUILD_MESSAGE_TYPING, GUILD_PRESENCES)
                    .setLargeThreshold(50)
                    .enableIntents(MESSAGE_CONTENT, GUILD_MEMBERS)
                    .build()
                    .awaitReady();

            adminRole = jda.getRoleById(config.adminRoleId);
            botChannel = jda.getTextChannelById(config.channelId);
            adminChannel = jda.getTextChannelById(config.adminChannelId);

            MessageRequest.setDefaultMentions(EnumSet.of(CHANNEL, EMOJI));

            jda.getGuildCache()
                    .stream()
                    .findFirst()
                    .ifPresent(guild -> guild.getSelfMember().modifyNickname(format("[@] @", config.prefix, jda.getSelfUser().getName())));

            Log.infoTag("Discord", format("Bot connected in as @", jda.getSelfUser().getAsTag()));
        } catch (InterruptedException e) {
            Log.errTag("Discord", format("Cannot connect to discord: @", e));
        }
    }

    public static boolean connected() {
        return jda != null && jda.getStatus() == CONNECTED;
    }

    public static void disconnect() {
        if (connected())
            jda.shutdown();
    }

    public static void updateStatus() {
        if (connected())
            jda.getPresence().setActivity(watching(format("at @ players on @", Groups.player.size(), stripColors(state.map.name()))));
    }

    public static void handle() {

    }
}