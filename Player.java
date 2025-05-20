package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private Room currentRoom;
    private GameMap map;
    private List<Item> inventory; // Player's inventory
    private List<String> completedPuzzles; // List of completed puzzle descriptions and answers
    public static final int MAX_PUZZLE_ATTEMPTS = 3;

    // Default constructor
    public Player() throws InvalidRoomException, FileNotFoundException, IOException {
        map = new GameMap(); // Load the game map using the default constructor
        currentRoom = map.getRoom("1"); // Start the player in Room 1
        inventory = new ArrayList<>(); // Initialize inventory
        completedPuzzles = new ArrayList<>(); // Initialize completed puzzles list
    }

    // New constructor that accepts file paths
    public Player(String itemsFilePath, String roomsFilePath, String puzzlesFilePath) throws InvalidRoomException, FileNotFoundException, IOException {
        map = new GameMap(roomsFilePath, itemsFilePath, puzzlesFilePath); // Load the game map with specified file paths
        currentRoom = map.getRoom("1"); // Start the player in Room 1
        inventory = new ArrayList<>(); // Initialize inventory
        completedPuzzles = new ArrayList<>(); // Initialize completed puzzles list
    }

    public String getCurrentRoomName() {
        return currentRoom.getName(); // Return the room name instead of the room ID
    }

    public String getCurrentRoomDescription() {
        return currentRoom.getDescription();
    }

    public boolean isCurrentRoomVisited() {
        return currentRoom.isVisited();
    }

    public void markCurrentRoomVisited() {
        currentRoom.setVisited(true);
    }

    public void moveTo(String nextRoomID) throws InvalidRoomException {
        if (nextRoomID == null || nextRoomID.equals("0")) {
            throw new InvalidRoomException("Oh dear! Alice wandered off the map and vanished into butterflies!");
        }
        currentRoom = map.getRoom(nextRoomID);
        if (!currentRoom.isVisited()) {
            currentRoom.setVisited(true);
        } else {
            System.out.println();
            System.out.println("This looks awfully familiar...");
        }
        // Reset puzzle attempts when moving to a new room
        currentRoom.resetPuzzleAttempts();
    }

    public String getNextRoomID(String direction) {
        switch (direction.toLowerCase()) {
            case "north":
            case "n":
                return currentRoom.getNorthRoomID();
            case "east":
            case "e":
                return currentRoom.getEastRoomID();
            case "south":
            case "s":
                return currentRoom.getSouthRoomID();
            case "west":
            case "w":
                return currentRoom.getWestRoomID();
            default:
                return null;
        }
    }

    public void explore() {
        List<Item> items = currentRoom.getItems();
        if (items.isEmpty()) {
            System.out.println("The room is empty. No items to explore.");
        } else {
            System.out.println("Items available in the room:");
            for (Item item : items) {
                System.out.println("- " + item.getName());
            }
        }
    }

    // Method to pick up an item
    public void pickUpItem(String itemName) {
        List<Item> items = currentRoom.getItems();
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                inventory.add(item);
                currentRoom.removeItem(item); // Use the removeItem method
                System.out.println("Picked up: " + item.getName() + " from " + currentRoom.getName() + " room.");
                return;
            }
        }
        System.out.println("Item not found in the room.");
    }

    // Method to get the inventory
    public List<Item> getInventory() {
        return inventory;
    }

    // Method to get the list of completed puzzles
    public List<String> getCompletedPuzzles() {
        return completedPuzzles;
    }

    // Method to use an item
    public void useItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                System.out.println("Using item: " + item.getName());
                // Handle item usage logic here
                return;
            }
        }
        System.out.println("Item not found in inventory.");
    }

    // Method to inspect an item in the inventory
    public void inspectItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                System.out.println("Inspecting item: " + item.getName());
                System.out.println(item.getDescription());
                return;
            }
        }
        System.out.println("Item not found in inventory.");
    }

    // Method to drop an item in the current room
    public void dropItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                inventory.remove(item);
                currentRoom.addItem(item);
                System.out.println("Dropped: " + item.getName() + " and placed in " + currentRoom.getName() + " room.");
                return;
            }
        }
        System.out.println("Item not found in inventory.");
    }

    // Method to get the current room
    public Room getCurrentRoom() {
        return currentRoom;
    }

    // Method to handle puzzle logic with attempt limit
    public void handlePuzzle(String answer) {
        if (currentRoom.hasPuzzle()) {
            if (currentRoom.solvePuzzle(answer)) {
                completedPuzzles.add(currentRoom.getPuzzle().getDescription() + " - " + answer); // Add to completed puzzles list with answer
                currentRoom.addPuzzle(null); // Clear the puzzle from the room
                System.out.println("Correct! The puzzle is solved.");
            } else {
                int remainingAttempts = MAX_PUZZLE_ATTEMPTS - currentRoom.getPuzzleAttempts();
                if (remainingAttempts <= 0) {
                    System.out.println("You have reached the maximum number of attempts for this puzzle.");
                } else {
                    System.out.println("Incorrect answer. " + remainingAttempts + " attempts remaining. Try again.");
                }
            }
        } else {
            System.out.println("There is no puzzle in this room.");
        }
    }
}