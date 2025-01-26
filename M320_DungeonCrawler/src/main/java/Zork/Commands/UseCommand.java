package Zork.Commands;

import Zork.GameState;
import Zork.Item;
import Zork.Key;
import Zork.Room;

public class UseCommand implements Command {

    private String key;
    private String dir;

    public UseCommand(String key, String dir) {
        this.key = key;
        this.dir = dir;
    }

    @Override
    public String execute() {
        try {
            Key keyItem = (Key) GameState.instance().getItemFromInventoryNamed(key);
            GameState.instance().unlockExit(dir, keyItem);
            GameState.instance().removeFromInventory(keyItem);
            return "Exit to " + dir + " is unlocked.";
        } catch (Item.NoItemException e) {
            return "You need the right key to open this.";
        } catch (Room.ExitLockedException e) {
            return "The room is locked.";
        }

    }
}
