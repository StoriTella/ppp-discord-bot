package ppp.discord.bot.commands;

import com.austinv11.servicer.Service;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ppp.discord.bot.utils.LanguageUtil;
import reactor.core.publisher.Mono;

@Component("languageCommand")
public class LanguageCommand implements Command<MessageCreateEvent> {

    //Keys used to run the command
    @Value("${langKey}")
    private String langKey;
    @Value("${langKeyGreet}")
    private String langKeyGreet;
    @Value("${langKeyChange}")
    private String langKeyChange;

    //Keys to output
    private static String greetStr = "lang-greet";
    private static String changeStr = "lang-change";
    private static String successStr = "operations-success";
    private static String errorStr = "operations-error";
    private static String notSuportedStr = "operations-notSuported";

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        Message eventMessage = event.getMessage();
        if (isGreet(eventMessage)) {
            return greet(event);
        } else if (isChangeLanguage(eventMessage)) {
            return changeLanguage(event);
        }
        return Mono.empty();
    }

    public boolean isChangeLanguage(Message message) {
        return message.getContent().contains(langKeyChange);
    }

    public boolean isGreet(Message message) {
        return message.getContent().contains(langKeyGreet);
    }

    public Mono<Void> changeLanguage(MessageCreateEvent event) {
        Message eventMessage = event.getMessage();
        if (LanguageUtil.changeLanguage(eventMessage.getContent().split(" ")[2])) {
            return Mono.just(eventMessage)
                    .flatMap(Message::getChannel)
                    .flatMap(channel -> channel.createMessage(LanguageUtil.getString(changeStr)))
                    .flatMap(Message::getChannel)
                    .flatMap(channel -> channel.createMessage(LanguageUtil.getString(LanguageUtil.defaultLanguageSigla)))
                    .then();
        } else {
            return Mono.just(eventMessage)
                    .flatMap(Message::getChannel)
                    .flatMap(channel -> channel.createMessage(LanguageUtil.getString(notSuportedStr)))
                    .then();
        }
    }

    public Mono<Void> greet(MessageCreateEvent event) {
        Message eventMessage = event.getMessage();
        return Mono.just(eventMessage)
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(LanguageUtil.getString(greetStr)))
                .then();
    }

}