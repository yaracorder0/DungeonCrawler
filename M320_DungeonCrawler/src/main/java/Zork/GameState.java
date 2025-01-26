package Zork;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

public class GameState {

    private static GameState Instance;
    private Room currentRoom;
    private HashSet<Room> visited;
    private ArrayList<Item> inventory;
    private Hashtable<Room, HashSet<Item>> roomContents;
    private Dungeon dungeon;

    private GameState() {
        visited = new HashSet<>();
        inventory = new ArrayList<>();
        roomContents = new Hashtable<>();
    }

    public static GameState instance() {
        if (Instance == null) {
            Instance = new GameState();
        }
        return Instance;
    }

    public void initialize(Dungeon dungeon) {
        this.dungeon = dungeon;
        this.currentRoom = dungeon.getEntry();
    }

    public Room getAdventurersCurrentRoom() {
        return currentRoom;
    }

    public void setAdventurersCurrentRoom(Room room){
        this.currentRoom = room;
    }

    public Dungeon getDungeon(){
        return dungeon;
    }

    public void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public void visit(Room room){
        visited.add(room);
    }

    public boolean hasBeenVisited(Room room){
        return visited.contains(room);
    }

    public ArrayList<Item> getInventory(){
        return inventory;
    }

    public String getAllItemsInInventory(){
        StringBuilder allItems = new StringBuilder("You have");
        for(Item item : inventory){
            allItems.append("\n" + item.getPrimaryName());
        }
        return allItems.toString();
    }
    public void addToInventory(Item item){
        inventory.add(item);
    }

    public void removeFromInventory(Item item){
        inventory.remove(item);
    }

    public HashSet<Item> getItemsInRoom(Room room){
        return roomContents.get(room);
    }

    public void addItemToRoom(Item item, Room room){
        roomContents.computeIfAbsent(room, k -> new HashSet<>()).add(item);
    }

    public void removeItemFromRoom(Item item, Room room){
        HashSet<Item> items = room.getContents();
        if (items.contains(item)){
            items.remove(item);
        }
    }

    public Item getItemFromInventoryNamed(String name) throws Item.NoItemException {
        for (Item item: inventory){
            if (item.goesBy(name)){
                return item;
            }
        }
        throw new Item.NoItemException(name + " not found in your inventory.");
    }

    public Item getItemInVicinityNamed(String name) throws Item.NoItemException {
        Room currentRoom = getAdventurersCurrentRoom();
        Item item = currentRoom.getItemNamed(name);
        if (item == null) {
            throw new Item.NoItemException(name + " not found in your vicinity.");
        }
        return item;
    }

    public void unlockExit(String dir, Key key) throws Room.ExitLockedException{
        Room currentRoom = getAdventurersCurrentRoom();
        currentRoom.unlockExit(dir, key);
    }

    public class IllegalSaveFormatException extends Exception {
        public IllegalSaveFormatException(String errorMessage) {
            super(errorMessage);
        }
    }
}
