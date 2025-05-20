import model.Player;
import view.GameView;
import controller.GameController;
import model.InvalidRoomException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Player player = new Player("item.txt", "map.txt", "puzzle.txt");
            GameView view = new GameView();
            GameController controller = new GameController(player, view);
            controller.startGame();
        } catch (InvalidRoomException | IOException e) {
            e.printStackTrace();
        }
    }
}