package ppp.discord.bot.commands;

import discord4j.core.event.domain.Event;
import reactor.core.publisher.Mono;

public interface Command <T extends Event>  {

    Mono<Void> execute(T event);
}
