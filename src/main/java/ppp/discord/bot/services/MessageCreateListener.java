package ppp.discord.bot.services;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ppp.discord.bot.config.EventListener;
import ppp.discord.bot.model.Command;
import ppp.discord.bot.model.PingPongCommand;
import reactor.core.publisher.Mono;

@Service
public class MessageCreateListener implements EventListener<MessageCreateEvent> {

    @Value("${prefix}")
    private String prefix;

    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        Message message = event.getMessage();

        if(message.getAuthor().map(user -> !user.isBot()).orElse(false) &&
                message.getContent().startsWith(prefix) &&
                message.getContent().substring(prefix.length(), message.getContent().length()).equals("ping")){
            Command command = new PingPongCommand();
            return command.execute(event);
        }
        return Mono.empty();
    }
}