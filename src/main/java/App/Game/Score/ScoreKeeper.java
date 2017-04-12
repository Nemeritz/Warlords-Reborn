package App.Game.Score;

/**
 * Interface for classes implementing a scorekeeping method.
 * Created by Jerry Fan on 9/04/2017.
 */
public interface ScoreKeeper {

    /**
     * Increases score by a given value.
     * @param value Value to increase score by.
     */
    public void increaseScore(int value);

    /**
     * @return The player number the scorekeeper is tracking for.
     */
    public int getPlayer();
}
