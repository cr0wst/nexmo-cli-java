package com.nexmo.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.nexmo.cli.insight.AdvancedCommand;
import com.nexmo.cli.insight.BasicCommand;
import com.nexmo.cli.sms.SendCommand;

import java.util.HashMap;
import java.util.Map;

public class App {
    private static final Map<String, Command> COMMAND_MAP = new HashMap<>();

    public static void main(String... args) {
        loadCommands();
        JCommander commander = initializeCommander();

        try {
            commander.parse(args);
            Command command = COMMAND_MAP.get(commander.getParsedCommand());
            if (command != null) {
                command.execute();
            } else {
                commander.usage();
            }
        } catch (ParameterException pe) {
            System.out.println(pe.getMessage());
            pe.usage();
        } catch (CommandFailedException cfe) {
            System.out.println(cfe.getMessage());
            commander.usage(commander.getParsedCommand());
        }
    }

    private static void loadCommands() {
        COMMAND_MAP.put("sms:send", new SendCommand());
        COMMAND_MAP.put("insight:basic", new BasicCommand());
        COMMAND_MAP.put("insight:advanced", new AdvancedCommand());
    }

    private static JCommander initializeCommander() {
        JCommander.Builder builder = JCommander.newBuilder();
        COMMAND_MAP.forEach((name, command) -> {
            builder.addCommand(name, command);
        });

        return builder.build();
    }
}
