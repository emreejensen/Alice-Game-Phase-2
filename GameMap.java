package model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameMap {
    private ArrayList<Room> rooms;
    private Map<String, Item> items;
    private Map<String, Puzzle> puzzles;  // Add a map for puzzles

    // Default constructor
    public GameMap() throws IOException, InvalidRoomException {
        this("map.txt", "Item.txt", "Puzzle.txt");
    }

    // Constructor that accepts file paths
    public GameMap(String roomFilePath, String itemFilePath, String puzzleFilePath) throws IOException, InvalidRoomException {
        rooms = new ArrayList<>();
        items = new HashMap<>();
        puzzles = new HashMap<>();  // Initialize the puzzles map
        loadItems(itemFilePath);
        loadPuzzles(puzzleFilePath);
        readRooms(roomFilePath);
    }

    public Room getRoom(String roomID) throws InvalidRoomException {
        for (Room room : rooms) {
            if (roomID.equals(room.getRoomID())) {
                return room;
            }
        }
        throw new InvalidRoomException("Room not found: " + roomID);
    }

    private void loadItems(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("~");
                if (parts.length >= 2) {
                    String itemName = parts[0];
                    String itemDescription = parts[1];
                    Item item = new Item(itemName, itemDescription);
                    items.put(itemName, item);
                } else {
                    System.err.println("Invalid item data: " + line);
                }
            }
        }
    }

    private void loadPuzzles(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("~");
                if (parts.length >= 3) {
                    String puzzleID = parts[0];
                    String description = parts[1];
                    String solution = parts[2];
                    Puzzle puzzle = new Puzzle(puzzleID, description, solution);
                    puzzles.put(puzzleID, puzzle);
                } else {
                    System.err.println("Invalid puzzle data: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading puzzles file: " + fileName);
            // Handle empty or missing puzzle file gracefully
        }
    }

    private void readRooms(String fileName) throws IOException, InvalidRoomException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("~");
                if (parts.length < 7) {
                    throw new InvalidRoomException("Invalid room data: " + line);
                }

                String roomID = parts[0];
                boolean isVisited = Boolean.parseBoolean(parts[1]);
                String roomName = parts[2];
                String description = parts[3];
                String itemID = parts[4];
                String puzzleID = parts[5];
                String[] connections = parts[6].split(",");

                if (connections.length < 4) {
                    throw new InvalidRoomException("Invalid connections data: " + parts[6]);
                }

                String north = connections[0];
                String east = connections[1];
                String south = connections[2];
                String west = connections[3];

                Room room = new Room(roomID, roomName, isVisited, description, north, east, south, west);

                if (!itemID.equals("0")) {
                    Item item = items.get(itemID);
                    if (item != null) {
                        room.addItem(item);
                    } else {
                        System.err.println("Item ID not found: " + itemID);
                    }
                }

                if (!puzzleID.equals("0")) {
                    Puzzle puzzle = puzzles.get(puzzleID);
                    if (puzzle != null) {
                        room.addPuzzle(puzzle);
                    } else {
                        System.err.println("Puzzle ID not found: " + puzzleID);
                    }
                }

                rooms.add(room);
            }
        }
    }
}