package ppp.discord.bot.commands;

import com.austinv11.servicer.Service;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ppp.discord.bot.utils.LanguageUtil;
import reactor.core.publisher.Mono;

@Component("pingPongCommand")
public class PingPongCommand implements Command<MessageCreateEvent>{

    //Keys to output
    private String pongStr = "operations-pong";

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        Message eventMessage = event.getMessage();
        return Mono.just(eventMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(LanguageUtil.getString(pongStr)))
                .then();
    }
}
