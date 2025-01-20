package org.example;

public class Main {
    public static void main(String[] args) {
        UserInterface userInterface;
        if (args.length == 0) {
            userInterface = new GUIInterface();
        } else {
            switch (args[0]) {
                case "console":
                    userInterface = new ConsoleInterface();
                    break;
                case "gui":
                    userInterface = new GUIInterface();
                    break;
                default:
                    userInterface = new GUIInterface();
            }
        }
        Game game = new Game(userInterface);
        game.setUp();
    }
}