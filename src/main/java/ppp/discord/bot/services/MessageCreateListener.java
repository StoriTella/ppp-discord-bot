package ppp.discord.bot.services;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ppp.discord.bot.config.EventListener;
import ppp.discord.bot.model.Command;
import ppp.discord.bot.model.CommandFactory;
import ppp.discord.bot.model.PingPongCommand;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class MessageCreateListener implements EventListener<MessageCreateEvent> {

    private String prefix;

    private final CommandFactory factory;

    @Autowired
    public MessageCreateListener(@Value("${prefix}") String prefix, CommandFactory factory) {
        this.prefix = prefix;
        this.factory = factory;
    }

    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        Message message = event.getMessage();

        if(message.getAuthor().map(user -> !user.isBot()).orElse(false) &&
                message.getContent().startsWith(prefix)){
            Optional<Command> command = factory.findCommand(message.getContent().split(" ")[0].substring(prefix.length())); //gets the command alias, there's probably a cleaner way =)
            return command.map(c -> c.execute(event))
                    .orElse(Mono.just(event.getMessage())
                            .flatMap(Message::getChannel)
                            .flatMap(channel -> channel.createMessage("Unknow command :cold_sweat:"))
                            .then());
        }
        return Mono.empty();
    }
}