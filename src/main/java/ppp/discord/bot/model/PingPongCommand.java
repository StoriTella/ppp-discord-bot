package ppp.discord.bot.model;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

public class PingPongCommand implements Command<MessageCreateEvent>{
    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        Message eventMessage = event.getMessage();
        return Mono.just(eventMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("pong"))
                .then();
    }
}
