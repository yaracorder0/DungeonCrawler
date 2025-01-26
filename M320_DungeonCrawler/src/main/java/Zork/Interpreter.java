package Zork;

import Zork.Commands.Command;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

class Interpreter {
    public static void main(String[] args) throws IOException, Dungeon.IllegalDungeonFormatException{
        String filepath = showFileChooser();

        if (filepath != null){
            try {
                Dungeon dungeon = new Dungeon(filepath);
                GameState.instance().initialize(dungeon);

                Room currentRoom = GameState.instance().getAdventurersCurrentRoom();
                System.out.println(currentRoom.getDesc());


                Scanner scan = new Scanner(System.in);

                while (true){
                    System.out.print("> ");
                    String input = scan.nextLine();
                    Command command = CommandFactory.instance().parse(input);
                    String result = command.execute();
                    System.out.println(result);
                }
            }catch (IOException e){
                throw new IOException(e.getMessage());
            }
        }else {
            System.out.println("No file selected. Exiting Game now.");
        }
    }


    private static String showFileChooser(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("C:\\TBZ\\DungeonCrawler\\M320_DungeonCrawler\\src\\main\\java\\GameFiles"));
        fileChooser.setDialogTitle("Select Zork Game File");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Zork File", "zork");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION){
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        } else {
            return null;
        }
    }

}