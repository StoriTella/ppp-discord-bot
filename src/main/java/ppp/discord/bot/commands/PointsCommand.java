package ppp.discord.bot.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ppp.discord.bot.model.User;
import ppp.discord.bot.utils.LanguageUtil;
import reactor.core.publisher.Mono;

@Component("pointsCommand")
public class PointsCommand implements Command<MessageCreateEvent> {

    @Autowired
    UsersCommand usersCommand;

    //Keys used to run the command
    @Value("${pointKeyGet}")
    private String pointKeyGet;
    @Value("${minimalPoints}")
    private String minimalPointsStr;
    @Value("${maximumPoints}")
    private String maximumPointsStr;

    //Keys to output
    private static String getStr = "points-get";
    private static String pointsStr = "points-point";

    private int minimalPoints = -5;
    private int maximumPoints = 10;

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        minimalPoints = Integer.valueOf(minimalPointsStr);
        maximumPoints = Integer.valueOf(maximumPointsStr);
        Message eventMessage = event.getMessage();
        this.passivePoints(event);
        if (isGetPoints(eventMessage)) {
            return getPoints(event);
        }

        return Mono.empty();
    }

    public Mono<Void> passivePoints(MessageCreateEvent event) {
        if (this.usersCommand.getListUsers().contains(new User(event))) {
            int pointsToAdd = (int) ((Math.random() * (maximumPoints - minimalPoints)) + minimalPoints);
            if (getUser(event) != null) {
                getUser(event).addPoints(pointsToAdd);
            }
        }
        return Mono.empty();
    }

    public User getUser(MessageCreateEvent event) {
        if (this.usersCommand.getListUsers().contains(new User(event))) {

            return this.usersCommand.getListUsers().get(this.usersCommand.getListUsers().indexOf(new User(event)));
        }

        return null;
    }
    public boolean isGetPoints(Message message) {
        return message.getContent().contains(pointKeyGet);
    }

    public Mono<Void> addPoints(MessageCreateEvent event) {
        return Mono.empty();
    }

    public Mono<Void> getPoints(MessageCreateEvent event) {
        Message eventMessage = event.getMessage();
        if (getUser(event) != null) {
            return Mono.just(eventMessage)
                    .flatMap(Message::getChannel)
                    .flatMap(channel -> channel.createMessage(LanguageUtil.getString(getStr) + getUser(event).getPoints() + LanguageUtil.getString(pointsStr)))
                    .then();
        }
        return Mono.empty();
    }
}
