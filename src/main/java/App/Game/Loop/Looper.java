package App.Game.Loop;

/**
 * The interface for objects implementing the game loop.
 * Created by Jerry Fan on 9/04/2017.
 */
public interface Looper {
    /**
     * Method called when the game loop is ready.
     * @param intervalS Time to simulate for.
     */
    public void onGameLoop(Double intervalS);
}
