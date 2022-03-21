package ppp.discord.bot.services;

import discord4j.core.event.domain.message.MessageUpdateEvent;
import org.springframework.stereotype.Service;
import ppp.discord.bot.config.EventListener;
import reactor.core.publisher.Mono;

@Service
public class MessageUpdateListener implements EventListener<MessageUpdateEvent> {

    @Override
    public Class<MessageUpdateEvent> getEventType() {
        return MessageUpdateEvent.class;
    }

    @Override
    public Mono<Void> execute(MessageUpdateEvent event) {
        /*return Mono.just(event)
                .filter(MessageUpdateEvent::isContentChanged)
                .flatMap(MessageUpdateEvent::getMessage)
                .flatMap(super::processCommand);*/
        return Mono.empty();
    }
}