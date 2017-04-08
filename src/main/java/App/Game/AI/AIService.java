package App.Game.AI;

import App.Game.Ball.BallComponent;
import App.Game.Fort.FortComponent;
import App.Game.Loop.Looper;

/**
 * Created by lichk on 9/04/2017.
 */
public class AIService implements Looper{
    boolean disabled;
    BallComponent ball;
    FortComponent fort;

    private void checkReady() {
        if (this.ball != null && this.fort != null) {
            this.disabled = false;
        }
    }

    public AIService() {
        this.disabled = true;
    }

    public void trackBall() {
        this.checkReady();
    }

    public void giveFort() {
        this.checkReady();
    }

    @Override
    public void onGameLoop(double intervalS) {

    }
}
