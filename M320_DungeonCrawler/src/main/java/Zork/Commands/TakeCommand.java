package Zork.Commands;

import Zork.GameState;
import Zork.Item;

public class TakeCommand implements Command{

    private final String itemName;

    public TakeCommand(String itemName) {
        this.itemName = itemName;
    }

    public String execute() {
        if (itemName.contains("take")){
            return "hahha\nima take your mama.";
        }

        try {
            Item item = GameState.instance().getItemInVicinityNamed(itemName);
            if (item == null){
                return "There isn't a " + itemName + " here";
            }
            GameState.instance().addToInventory(item);
            GameState.instance().removeItemFromRoom(item, GameState.instance().getAdventurersCurrentRoom());
            return  "Your are now in possesion of a " + itemName;
        } catch (Item.NoItemException e) {
            return "There is no such thing in this room.";
        }
    }
}
