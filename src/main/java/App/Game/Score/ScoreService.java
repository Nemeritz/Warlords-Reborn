package App.Game.Score;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * A service to keep track of scorekeepers.
 * Created by Jerry Fan on 9/04/2017.
 */
public class ScoreService {
    Set<ScoreKeeper> scoreKeepers;

    public ScoreService() {
        this.scoreKeepers = new CopyOnWriteArraySet<>();
    }

    /**
     * @return The set of scorekeeper objects.
     */
    public Set<ScoreKeeper> getScoreKeepers() {
        return this.scoreKeepers;
    }

    /**
     * Get the scorekeeper for a player.
     * @param player Player number.
     * @return The player scorekeeper.
     */
    public ScoreKeeper getScoreKeeper(Integer player) {
        for (ScoreKeeper scoreKeeper : this.scoreKeepers) {
            if (player.equals(scoreKeeper.getPlayer())) {
                return scoreKeeper;
            }
        }
        return null;
    }
}
