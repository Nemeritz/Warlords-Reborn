package App.Game;

/**
 * Created by Jerry Fan on 7/04/2017.
 */
public enum GameState {
    LOAD(0),
    PREGAME(1),
    GAME(2),
    PAUSE(3),
    UNPAUSE(4),
    END(5),
    UNLOAD(6);

    public int number;

    GameState(int number) {
        this.number = number;
    }
}
