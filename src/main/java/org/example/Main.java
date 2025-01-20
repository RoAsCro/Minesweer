package org.example;

public class Main {
    public static void main(String[] args) {
        UserInterface userInterface;
        // TODO Make switch
        if (args.length == 0) {
            userInterface = new GUIInterface();
        } else if (args[0].equals("console")) {
            userInterface = new ConsoleInterface();
        } else {
            userInterface = new GUIInterface();
        }
        Game game = new Game(userInterface);
        game.setUp();
    }
}