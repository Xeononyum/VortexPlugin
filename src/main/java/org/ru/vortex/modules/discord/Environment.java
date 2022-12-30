package org.ru.vortex.modules.discord;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;

public record Environment(MessageReceivedEvent event, Message message) {
    public Environment(MessageReceivedEvent event) {
        this(event, event.getMessage());
    }

    public MessageCreateAction info(String raw) {
        return message.reply(raw);
    }
}
