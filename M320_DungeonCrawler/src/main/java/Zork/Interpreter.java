package Zork;

import Zork.Commands.Command;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

class Interpreter {
    private static JTextArea gameOutput;
    private static JTextField inputField;

    public static void main(String[] args) throws IOException, Dungeon.IllegalDungeonFormatException{
        String filepath = showFileChooser();

        if (filepath != null){
            try {
                Dungeon dungeon = new Dungeon(filepath);
                GameState.instance().initialize(dungeon);

                createGameWindow(dungeon);
            }catch (IOException e){
                throw new IOException(e.getMessage());
            }
        }else {
            System.out.println("No file selected. Exiting Game now.");
        }
    }

    private static void createGameWindow(Dungeon dungeon) {
        JFrame frame = new JFrame("Zork Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Set dark mode colors
        Color backgroundColor = new Color(30, 30, 30);
        Color textColor = new Color(200, 200, 200);
        Color inputBackgroundColor = new Color(45, 45, 45);

        // Main game panel
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(backgroundColor);

        // Text area to display game output
        gameOutput = new JTextArea();
        gameOutput.setEditable(false);
        gameOutput.setBackground(backgroundColor);
        gameOutput.setForeground(textColor);
        gameOutput.setFont(new Font("Consolas", Font.PLAIN, 14));

        // Add a small margin around the text
        gameOutput.setMargin(new Insets(10, 10, 10, 10)); // Top, Left, Bottom, Right

        JScrollPane scrollPane = new JScrollPane(gameOutput);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Input panel for commands
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(backgroundColor);

        // Input prefix (">")
        JLabel inputPrefix = new JLabel("> ");
        inputPrefix.setForeground(textColor);
        inputPrefix.setFont(new Font("Consolas", Font.PLAIN, 14));

        // Text field for input
        inputField = new JTextField();
        inputField.setBackground(inputBackgroundColor);
        inputField.setForeground(textColor);
        inputField.setCaretColor(textColor);
        inputField.setFont(new Font("Consolas", Font.PLAIN, 14));
        inputField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Add action listener to handle command input
        inputField.addActionListener(e -> processCommand(dungeon));

        inputPanel.add(inputPrefix, BorderLayout.WEST);
        inputPanel.add(inputField, BorderLayout.CENTER);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);

        // Display the initial room description
        Room currentRoom = GameState.instance().getAdventurersCurrentRoom();
        appendToGameOutput(currentRoom.getDesc());
    }


    private static void processCommand(Dungeon dungeon) {
        String input = inputField.getText().trim();
        if (!input.isEmpty()) {
            inputField.setText("");

            try {
                Command command = CommandFactory.instance().parse(input);
                String result = command.execute();
                appendToGameOutput("> " + input + "\n" + result);
            } catch (Exception e) {
                appendToGameOutput("Error: " + e.getMessage());
            }
        }
    }

    private static void appendToGameOutput(String text) {
        gameOutput.append(text + "\n");
        gameOutput.setCaretPosition(gameOutput.getDocument().getLength());
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