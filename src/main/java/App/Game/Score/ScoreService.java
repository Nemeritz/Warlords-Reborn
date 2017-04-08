package App.Game.Score;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by lichk on 9/04/2017.
 */
public class ScoreService {
    Set<ScoreKeeper> scoreKeepers;

    public ScoreService() {
        this.scoreKeepers = new CopyOnWriteArraySet<>();
    }

    public Set<ScoreKeeper> getScoreKeepers() {
        return this.scoreKeepers;
    }

    public ScoreKeeper getScoreKeeper(Integer player) {
        for (ScoreKeeper scoreKeeper : this.scoreKeepers) {
            if (player.equals(scoreKeeper.getPlayer())) {
                return scoreKeeper;
            }
        }
        return null;
    }
}
