package controller;

import model.Player;
import model.InvalidRoomException;
import view.GameView;

public class GameController {
    private Player player;
    private GameView view;

    public GameController(Player player, GameView view) {
        this.player = player;
        this.view = view;
    }

    public void startGame() {
        while (true) {
            view.displayMessage("");
            view.displayMessage("Alice wanders into the " + player.getCurrentRoomName() + "...");
            view.displayMessage(player.getCurrentRoomDescription());
            view.displayMessage("");

            // Handle puzzle logic if the room has a puzzle and attempts are not exceeded
            if (player.getCurrentRoom().hasPuzzle() && player.getCurrentRoom().getPuzzleAttempts() < Player.MAX_PUZZLE_ATTEMPTS) {
                view.displayMessage("There is a puzzle in this room!");
                view.displayMessage(player.getCurrentRoom().getPuzzle().getDescription());
            } else if (player.getCurrentRoom().hasPuzzle()) {
                view.displayMessage("You have reached the maximum number of attempts for this puzzle.");
            }

            // Display help offer and user prompt
            view.displayMessage("Type 'help' for a list of available commands.");
            String input = view.getUserInput();

            if (input.equalsIgnoreCase("exit")) {
                view.displayMessage("");
                view.displayMessage("Alice drifts out of Wonderland and returns to the real world...");
                view.displayMessage("(Exiting Game)");
                break;
            }

            if (input.equalsIgnoreCase("explore")) {
                player.explore();
                continue;
            }

            if (input.equalsIgnoreCase("inventory")) {
                view.displayInventory(player);
                continue;
            }

            if (input.equalsIgnoreCase("help")) {
                view.displayHelp();
                continue;
            }

            if (input.startsWith("pickup ")) {
                String itemName = input.substring(7).trim();
                player.pickUpItem(itemName);
                continue;
            }

            if (input.startsWith("drop ")) {
                String itemName = input.substring(5).trim();
                player.dropItem(itemName);
                continue;
            }

            if (input.startsWith("inspect ")) {
                String itemName = input.substring(8).trim();
                player.inspectItem(itemName);
                continue;
            }

            if (input.startsWith("solve ")) {
                String answer = input.substring(6).trim();
                player.handlePuzzle(answer);
                continue;
            }

            // Handle the case where the input might be a puzzle answer directly
            if (player.getCurrentRoom().hasPuzzle() && player.getCurrentRoom().getPuzzleAttempts() < Player.MAX_PUZZLE_ATTEMPTS) {
                player.handlePuzzle(input);
                continue;
            }

            String nextRoomID = player.getNextRoomID(input);

            if (nextRoomID == null) {
                view.displayMessage("");
                view.displayMessage("Invalid direction. Please enter North, East, South, West, or exit.");
                continue;
            }

            try {
                player.moveTo(nextRoomID);
            } catch (InvalidRoomException e) {
                view.displayMessage(e.getMessage());
            }
        }
    }
}