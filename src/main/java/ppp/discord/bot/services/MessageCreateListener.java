package ppp.discord.bot.services;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ppp.discord.bot.commands.LanguageCommand;
import ppp.discord.bot.commands.PingPongCommand;
import ppp.discord.bot.config.EventListener;
import reactor.core.publisher.Mono;

@Service
public class MessageCreateListener implements EventListener<MessageCreateEvent> {

    //Keys used to run the command
    @Value("${prefix}")
    private String prefix;
    @Value("${pingKey}")
    private String pingKey;
    @Value("${langKey}")
    private String langKey;

    @Autowired
    PingPongCommand pingPongCommand;

    @Autowired
    LanguageCommand languageCommand;

    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        Message message = event.getMessage();
        if (!message.getContent().contains(prefix)) {
            return Mono.empty();
        }

        if (message.getContent().contains(pingKey)) {
            return this.pingPongCommand.execute(event);
        } else if (message.getContent().contains(langKey)) {
            return this.languageCommand.execute(event);
        }

        return Mono.empty();
    }

}