package ppp.discord.bot.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Component
public class CommandFactory {

    private Map<String, Command> commands;

    @Autowired
    public CommandFactory(Set<Command> commandSet){
        setupFactory(commandSet);
    }

    public Optional<Command> findCommand(String commandPrefix){
        return Optional.ofNullable(commands.get(commandPrefix));
    }

    private void setupFactory(Set<Command> commandSet){
        commands = new HashMap<>();
        commandSet.forEach(command -> commands.put(command.getCommandPrefix(), command));
    }
}
