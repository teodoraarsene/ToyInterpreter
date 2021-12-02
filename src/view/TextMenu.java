package view;

import view.commands.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class TextMenu {
    private final Map<String, Command> commands;

    public TextMenu() {
        commands = new HashMap<>();
    }

    public void addCommand(Command cmd) {
        commands.put(cmd.getKey(), cmd);
    }

    private void printMenu() {
        TreeMap<Integer, Command> sorted = new TreeMap<>();
        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            int converted = Integer.parseInt(entry.getKey());
            sorted.put(converted, entry.getValue());
        }
        for (Command cmd : sorted.values()) {
            String line = String.format("%4s : %s", cmd.getKey(), cmd.getDescription());
            System.out.println(line);
        }
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.print("input your option: ");
            String key = scanner.nextLine();
            Command command = commands.get(key);
            if (command == null) {
                System.out.println("invalid option!");
                continue;
            }
            try {
                command.execute();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
