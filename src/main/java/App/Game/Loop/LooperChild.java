package App.Game.Loop;

/**
 * Interface for objects implementing the game loop as a child of a looper object (hooked on to another object's game
 * loop).
 * Created by Jerry Fan on 10/04/2017.
 */
public interface LooperChild {

    /**
     * Method called when game loop is triggered on parent.
     * @param intervalS Time to simulate for.
     */
    public void onGameLoop(Double intervalS);
}
