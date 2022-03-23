package ppp.discord.bot.model;

import discord4j.core.event.domain.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public interface Command <T extends Event>  {

    Mono<Void> execute(T event);

    String getCommandPrefix();
}
