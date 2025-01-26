package Zork.Commands;

import Zork.GameState;
import Zork.Item;
import Zork.Room;

public class LookCommand implements Command {

    private String itemName;

    public LookCommand(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public String execute() {
        if (itemName.equals("")){
            Room currentRoom = GameState.instance().getAdventurersCurrentRoom();
            return currentRoom.getDesc();
        } else {
            try {
                Item item = GameState.instance().getItemFromInventoryNamed(itemName);
                return item.getDescription();
            } catch (Item.NoItemException e){
                return itemName + " is not in your inventory";
            }
        }
    }
}
