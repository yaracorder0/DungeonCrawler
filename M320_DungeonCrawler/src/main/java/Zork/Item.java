package Zork;


import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;

public class Item {

    private String primaryName;
    private String description;
    private int weight;
    private Hashtable <String, String> messages;
    private HashSet<String> aliases;

    public Item(String primaryName,String description, int weight) {
        this.primaryName = primaryName;
        this.description = description;
        this.weight = weight;
        this.messages = new Hashtable<>();
        this.aliases = new HashSet<>();
    }

    public Item(Scanner scan) {
        String[] splitName = scan.nextLine().split(",");
        this.primaryName = splitName[0].trim();
        this.aliases = new HashSet<>();
        for (int i = 1; i < splitName.length; i++){
            this.aliases.add(splitName[i].trim());
        }

        this.description = scan.nextLine().trim();
        this.weight = Integer.parseInt(scan.nextLine().trim());
        this.messages = new Hashtable<>();

        String line;
        while (scan.hasNextLine() && !(line = scan.nextLine().trim()).equals("---")){
            String[] parts = line.split(":");
            if (parts.length == 2){
                messages.put(parts[0].trim(), parts[1].trim());
            }
        }
    }

    public boolean goesBy(String name){
        return primaryName.equalsIgnoreCase(name) || aliases.contains(name.toLowerCase());
    }

    public String getPrimaryName() {
        return primaryName;
    }

    public String getDescription() {
        return description;
    }

    public String getMessageForVerb(String verb){
        return messages.get(verb);
    }

    public boolean isKey(){
        return false;
    }

    public String toString(){
        return primaryName;
    }

    public int getWeight() {
        return weight;
    }

    public static class NoItemException extends Exception {
        public NoItemException(String errorMessage) {
            super(errorMessage);
        }
    }
}
