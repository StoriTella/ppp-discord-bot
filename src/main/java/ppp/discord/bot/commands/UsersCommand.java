package ppp.discord.bot.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Component;
import ppp.discord.bot.model.User;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component("usersCommand")
public class UsersCommand implements Command<MessageCreateEvent>{

    private List<User> listUsers = new ArrayList<User>();

    public List<User> getListUsers() {
        return listUsers;
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        Message eventMessage = event.getMessage();
        boolean flag = true;
        for (User u: listUsers) {
            if(event.getMember().get().getId().equals(u.getId())) {
                flag = false;
                break;
            }
        }
        if (flag) {
            User u = new User(event);
            this.listUsers.add(u);
        }
        return Mono.empty();
    }
}
