package ppp.discord.bot.config;

import discord4j.core.object.entity.Message;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Mono;

public abstract class MessageListener {

    @Value("${symbol}")
    private String symbol;

    public Mono<Void> processCommand(Message eventMessage) {
        return Mono.just(eventMessage)
          .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
          .filter(message -> message.getContent().equalsIgnoreCase(symbol + "ping"))
          .flatMap(Message::getChannel)
          .flatMap(channel -> channel.createMessage("pong"))
          .then();
    }
}