package Zork.Commands;

import Zork.GameState;
import Zork.Item;

public class ItemSpecificCommand implements Command {

    public String verb;
    public String itemName;

    public ItemSpecificCommand(String verb, String item) {
        this.verb = verb;
        this.itemName = item;
    }

    public String execute() {
        try {
            Item item = GameState.instance().getItemFromInventoryNamed(itemName);
            return item.getMessageForVerb(verb);
        } catch (Item.NoItemException e) {
            return "You don't have a " + itemName + " in your inventory.";
        }
    }
}
