package Zork.Commands;

import Zork.GameState;

public class InventoryCommand implements Command {

    @Override
    public String execute() {
        if(GameState.instance().getInventory().isEmpty()){
            return "You're are empty handed.";
        }
        return GameState.instance().getAllItemsInInventory();
    }
}
