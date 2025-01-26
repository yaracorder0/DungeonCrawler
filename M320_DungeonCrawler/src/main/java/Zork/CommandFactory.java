package Zork;

import Zork.Commands.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class CommandFactory {

    private static CommandFactory instance;
    private static String REGEX = "\\s+";

    private final Map<String, Function<String[], Command>> commandRegistry;

    private CommandFactory() {
        commandRegistry = new HashMap<>();

        commandRegistry.put("move", cm -> new MovementCommand(cm.length > 1 ? cm[1] : ""));
        commandRegistry.put("look", cm -> new LookCommand(cm.length > 1 ? cm[1] : ""));
    }

    public static CommandFactory instance() {
        if (instance == null) {
            instance = new CommandFactory();
        }
        return instance;
    }


    public Command parse(String commandString) {
        commandString = commandString.trim();

        String[] splitCommand = commandString.split(REGEX);

        if(splitCommand.length > 0){
            String commandKey = splitCommand[0].toLowerCase();
            if(commandRegistry.containsKey(commandKey)){
                return commandRegistry.get(commandKey).apply(splitCommand);
            }
        }

        if (splitCommand.length > 1) {
            String verb = splitCommand[0];
            String itemName = splitCommand[1];

            if(isValidItemSpecificCommand(verb, itemName)){
                return new ItemSpecificCommand(verb, itemName);
            }
        }

        return new UnknownCommand(commandString);
    }

    private boolean isValidItemSpecificCommand(String verb, String itemName) {
        GameState gameState = GameState.instance();
        Set<Item> inventory = new HashSet<>(gameState.getInventory());
        Room currentRoom = gameState.getAdventurersCurrentRoom();
        Set<Item> roomItems = gameState.getItemsInRoom(currentRoom);

        if (roomItems == null) {
            roomItems = new HashSet<>();
        }

        // Check both inventory and current room for the item
        for (Item item : inventory) {
            if (item.goesBy(itemName) && item.getMessageForVerb(verb) != null) {
                return true;
            }
        }

        for (Item item : roomItems) {
            if (item.goesBy(itemName) && item.getMessageForVerb(verb) != null) {
                return true;
            }
        }

        return false;
    }
}

