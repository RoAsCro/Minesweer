package org.example;

public class Main {
    public static void main(String[] args) {
        UserInterface console = new ConsoleInterface();
        UserInterface GUI = new GUIInterface();
        Game game = new Game(GUI);
        game.setUp();
//        GUIInterface.createAndShowGUI();
    }
}