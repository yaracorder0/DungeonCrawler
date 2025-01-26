package Zork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

public class Dungeon {

    private Room entry;
    private String dungeonName;
    private Hashtable<String, Room> rooms;
    private Hashtable<String, Item> items;
    private List<ExitInfo> exits;
    private String filepath;

    public Dungeon(Room entry, String dungeonName) {
        this.entry = entry;
        this.dungeonName = dungeonName;
        rooms = new Hashtable<>();
        items = new Hashtable<>();
    }

    public Dungeon(String filepath) throws IllegalDungeonFormatException, IOException {
        this.filepath = filepath;
        rooms = new Hashtable<>();
        items = new Hashtable<>();
        exits = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            this.dungeonName = reader.readLine();
            String line = reader.readLine();

            if (!line.equals("===")) {
                throw new IllegalDungeonFormatException("Missing '===' after title.");
            }

            line = reader.readLine().trim();
            if (!line.equals("Items:")) {
                throw new IllegalDungeonFormatException("Missing 'Items:' after '==='.");
            }

            while ((line = reader.readLine()) != null && !line.equals("===")) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String itemType = line.trim();
                if (itemType.equals("Key")){
                    line = reader.readLine().trim();
                }
                StringBuilder itemData = new StringBuilder(line + "\n" + reader.readLine() + "\n" + reader.readLine() + "\n");
                while (!(line = reader.readLine()).equals("---")) {
                    itemData.append(line + "\n");
                }
                itemData.append(line + "\n");
                Scanner scan = new Scanner(itemData.toString());

                Item item;
                if (itemType.equals("Key")){
                    item = new Key(scan);
                }else {
                    item = new Item(scan);
                }
                addItem(item);
            }

            line = reader.readLine().trim();
            if (!line.equals("Rooms:")) {
                throw new IllegalDungeonFormatException("Missing 'Rooms:' after '==='.");
            }

            while ((line = reader.readLine()) != null && !line.equals("===")) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                Room room = new Room(line.trim(), reader);
                addRoom(room);
                if (entry == null) {
                    entry = room;
                }
            }

            line = reader.readLine();
            if (line != null && line.trim().equals("Exits:")) {
                while ((line = reader.readLine()) != null && !line.equals("===")) {
                    if (line.trim().isEmpty() || line.trim().equals("---")) {
                        continue;
                    }
                    String srcName = line.trim();
                    String dir = reader.readLine().trim();
                    String destName = reader.readLine().trim();
                    line = reader.readLine().trim();
                    String keyName = line.equals("---") ? "null" : line;
                    exits.add(new ExitInfo(srcName, dir, destName, keyName));
                }
            }

            for (Room room : rooms.values()) {
                room.initializeContents(this);
            }

        } catch (IOException e) {
            throw new IOException(e);
        }

        for (ExitInfo exitInfo : exits) {
            Room src = getRoom(exitInfo.srcName);
            Room dest = getRoom(exitInfo.destName);
            if (src != null && dest != null) {
                if (exitInfo.keyName.isEmpty() || exitInfo.keyName.equals("null")) {
                    src.addExit(new Exit(exitInfo.dir, src, dest));
                } else {
                    Item item = getItem(exitInfo.keyName);
                    if (item instanceof Key) {
                        src.addExit(new Exit(exitInfo.dir, src, dest, (Key) item));
                    } else {
                        throw new IllegalDungeonFormatException("The item " + exitInfo.keyName + " is not a key.");
                    }
                }
            } else {
                throw new IllegalDungeonFormatException("Exit references non-existent room: " + exitInfo.srcName + " or " + exitInfo.destName);
            }
        }
    }


    public Room getEntry() {
        return entry;
    }

    public String getTitle() {
        return dungeonName;
    }

    public void addRoom (Room room){
        rooms.put(room.getName(), room);
    }

    public void addItem (Item item){
        items.put(item.getPrimaryName(), item);
    }

    public Room getRoom (String roomname){
        return rooms.get(roomname);
    }

    public Item getItem(String itemName){
        return items.get(itemName);
    }

    public class IllegalDungeonFormatException extends Exception {
        public IllegalDungeonFormatException(String errorMessage) {
            super(errorMessage);
        }
    }

    private static class ExitInfo {
        String srcName;
        String dir;
        String destName;
        String keyName;

        ExitInfo(String srcName, String dir, String destName, String keyName) {
            this.srcName = srcName;
            this.dir = dir;
            this.destName = destName;
            this.keyName = keyName;
        }
    }
}



