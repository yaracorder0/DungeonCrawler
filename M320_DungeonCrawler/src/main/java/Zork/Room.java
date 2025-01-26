package Zork;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Hashtable;

public class Room {

    private String name;
    private String desc;
    private Hashtable<String, Exit> exits;
    private HashSet<Item> contents;
    private Hashtable<String, String> roomItemDescriptions;
    private String[] initialItemNames;
    private boolean visited;

    public Room(String name) {
        this.name = name;
        this.exits = new Hashtable<>();
        this.roomItemDescriptions = new Hashtable<>();
        this.visited = false;
    }


    public Room(String name, BufferedReader reader) throws IOException {
        this.name = name;
        this.exits = new Hashtable<>();
        this.contents = new HashSet<>();
        this.roomItemDescriptions = new Hashtable<>();
        this.visited = false;
        StringBuilder descBuilder = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null && !line.equals("---")) {
                if (line.startsWith("Contents:")){
                    initialItemNames = line.substring(9).split(",");
                } else {
                    descBuilder.append(line).append("\n");
                }
            }
            this.desc = descBuilder.toString().trim();
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public void initializeContents(Dungeon dungeon) {
        if (initialItemNames != null) {
            for (String itemName : initialItemNames) {
                String[] parts = itemName.trim().split(":", 2);
                String itemPrimaryName = parts[0].trim();
                Item item = dungeon.getItem(itemPrimaryName);
                if (item != null) {
                    addItem(item);
                    if (parts.length > 1) {
                        String itemDescription = parts[1].trim();
                        roomItemDescriptions.put(itemPrimaryName, itemDescription);
                    }
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        StringBuilder description = new StringBuilder(name + "\n" + desc);
        for (Item item : contents) {
            if (roomItemDescriptions.containsKey(item.getPrimaryName())) {
                String itemDescription = roomItemDescriptions.get(item.getPrimaryName());
                description.append("\n").append(itemDescription);
            }
        }
        description.append("\nExits:");
        for (String dir : exits.keySet()) {
            description.append(" ").append(dir);
        }
        return description.toString();
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String describe(){
        if (!visited){
            visited = true;
            GameState.instance().visit(this);
            StringBuilder description = new StringBuilder(name + "\n" + desc);
            for (Item item : contents) {
                if (roomItemDescriptions.containsKey(item.getPrimaryName())) {
                    String itemDescription = roomItemDescriptions.get(item.getPrimaryName());
                    description.append("\n").append(itemDescription);
                }
            }
            description.append("\nExits:");
            for (String dir : exits.keySet()) {
                description.append(" ").append(dir);
            }
            return description.toString();
        } else {
            return name;
        }
    }

    public Item getItemNamed(String name){
        for (Item item: contents){
            if (item.goesBy(name)){
                return item;
            }
        }
        return null;
    }

    HashSet<Item> getContents(){
        return contents;
    }

    public void addExit(Exit exit){
        exits.put(exit.getDir(), exit);
    }

    public void addItem(Item item){
        contents.add(item);
    }

    public void removeItem(Item item){
        contents.remove(item);
    }

    public Room leaveBy(String dir) throws ExitLockedException {
        Exit exit = exits.get(dir);
        if (exit != null){
            if (exit.isLocked()){
                throw new ExitLockedException("The exit is locked. You need a key to open it.");
            }
            return exit.getDest();
        }
        return null;
    }

    public void unlockExit(String dir, Key key){
        Exit exit = exits.get(dir);
        if (exit != null){
            exit.unlock(key);
        }
    }

    public static class NoRoomException extends Exception {
        public NoRoomException(String errorMessage) {
            super(errorMessage);
        }
    }

    public static class ExitLockedException extends Exception {
        public ExitLockedException(String errorMessage) {
            super(errorMessage);
        }
    }
}
