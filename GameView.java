package view;

import model.Player;
import model.Item;

import java.util.Scanner;

public class GameView {
    private Scanner scanner;

    public GameView() {
        scanner = new Scanner(System.in);
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayInventory(Player player) {
        displayMessage("Inventory:");
        if (player.getInventory().isEmpty()) {
            displayMessage("No items collected.");
        } else {
            for (Item item : player.getInventory()) {
                displayMessage("- " + item.getName());
            }
        }
        displayMessage("Completed Puzzles:");
        if (player.getCompletedPuzzles().isEmpty()) {
            displayMessage("No puzzles completed.");
        } else {
            for (String puzzleDescription : player.getCompletedPuzzles()) {
                displayMessage("- " + puzzleDescription);
            }
        }
    }

    public String getUserInput() {
        System.out.print("Enter command: ");
        return scanner.nextLine().trim();
    }

    public void displayHelp() {
        displayMessage("Available commands:");
        displayMessage(" - explore: Explore the current room.");
        displayMessage(" - pickup [item]: Pick up an item from the room.");
        displayMessage(" - drop [item]: Drop an item in the room.");
        displayMessage(" - inspect [item]: Inspect an item in your inventory.");
        displayMessage(" - inventory: Display your inventory.");
        displayMessage(" - solve [answer]: Solve the puzzle in the room.");
        displayMessage(" - help: Display this help message.");
        displayMessage(" - exit: Exit the game.");
    }
}