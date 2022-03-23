package ppp.discord.bot.model;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PingPongCommand implements Command<MessageCreateEvent>{

    private final String prefix;

    @Autowired
    public PingPongCommand(@Value("${PingPong}") String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        Message eventMessage = event.getMessage();
        return Mono.just(eventMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("pong"))
                .then();
    }

    @Override
    public String getCommandPrefix() {
        return prefix;
    }
}
