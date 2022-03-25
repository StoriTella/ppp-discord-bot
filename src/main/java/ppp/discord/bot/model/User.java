package ppp.discord.bot.model;

import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.message.MessageCreateEvent;
import java.util.Objects;

public class User {

    private Snowflake id;

    private String userName;

    private int points;

    public Snowflake getId() {
        return id;
    }

    public void setId(Snowflake id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int addPoints(int points) {
        this.points+=points;
        return this.points;
    }

    public int subPoints(int points) {
        this.points-=points;
        return this.points;
    }

    public User(MessageCreateEvent event) {
        this.setPoints(0);
        this.setUserName(event.getMember().get().getUsername());
        this.setId(event.getMember().get().getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
