package Zork.Commands;

public class AssistCommands implements Command{
    public String command;

    public static final String ANSI_RESET =  "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";


    public AssistCommands(String command) {
        this.command = command;
    }

    public String execute() {
        if(command.equals("exit")){
            System.out.println("Exiting Game now...");
            System.exit(0);
            return "";
        } else{
            return ANSI_RED + "Commands\n" + ANSI_RESET
                    + "move" + ANSI_GREEN + " <direction>\n" + ANSI_RESET
                    + "take" + ANSI_GREEN + " <item-name>\n" + ANSI_RESET
                    + "i or inventory\n"
                    + "use" + ANSI_GREEN + " <item-name> <direction>\n" + ANSI_RESET
                    + "look";
        }
    }
}
