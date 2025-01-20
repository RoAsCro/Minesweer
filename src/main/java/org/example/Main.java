package org.example;

public class Main {
    public static void main(String[] args) {
        UserInterface userInterface;
        if (args.length == 0) {
            userInterface = new GUIInterface();
        } else {
            userInterface = switch (args[0]) {
                case "console" -> new ConsoleInterface();
                case "gui" -> new GUIInterface();
                default -> new GUIInterface();
            };
        }
        Game game = new Game(userInterface);
        game.setUp();
    }
}