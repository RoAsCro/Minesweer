package org.example;

public interface UserInterface {
    int getInt(String prompt);
    int getInt(String prompt, int max);
    String getCoordinate(String prompt);
    void display(String prompt);
}
