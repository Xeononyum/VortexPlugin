package org.ru.vortex.modules.database;

import arc.util.Strings;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.reactivestreams.client.*;
import mindustry.gen.Player;
import org.ru.vortex.modules.database.models.BansData;
import org.ru.vortex.modules.database.models.PlayerData;
import reactor.core.publisher.Mono;

import static arc.util.Log.errTag;
import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static org.bson.codecs.pojo.PojoCodecProvider.builder;
import static org.ru.vortex.Vars.config;

public class Database {
    public static MongoClient client;
    public static MongoDatabase database;

    public static MongoCollection<PlayerData> playersCollection;
    public static MongoCollection<BansData> bansCollection;

    public static void connect() {
        try {
            client = MongoClients.create(config.mongoUrl);
            database = client.getDatabase("vortex")
                    .withCodecRegistry(fromRegistries(getDefaultCodecRegistry(), fromProviders(builder()
                            .automatic(true)
                            .build())));

            playersCollection = database.getCollection("players", PlayerData.class);
            bansCollection = database.getCollection("bans", BansData.class);
        } catch (Exception e) {
            errTag("Database", Strings.format("Unable connect to database: @", e));
        }
    }

    public static Mono<PlayerData> getPlayerData(Player player) {
        return getPlayerData(player.uuid());
    }

    public static Mono<PlayerData> getPlayerData(String uuid) {
        return Mono.from(playersCollection.find(eq("uuid", uuid))).defaultIfEmpty(new PlayerData(uuid));
    }

    public static Mono<UpdateResult> setPlayerData(PlayerData data) {
        return Mono.from(playersCollection.replaceOne(eq("uuid", data.uuid), data, new ReplaceOptions().upsert(true)));
    }
}
