package Zork;

import java.util.Scanner;

public class Key extends Item{
    public Key(String primaryName, String description, int weight) {
        super(primaryName, description, weight);
    }

    @Override
    public boolean isKey() {
        return true;
    }

    public Key(Scanner scan) {
        super(scan);
    }
}
