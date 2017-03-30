package App.Game;

import App.Game.Canvas.Ball.BallService;
import App.Game.Timer.TimerService;

import java.awt.*;

/**
 * Created by lichk on 27/03/2017.
 */
public class GameService {
    private Boolean started;
    private Boolean finished;

    TimerService timer;
    BallService ball;

    public GameService() {
        this.timer = new TimerService();
        this.ball = new BallService();
    }

    public TimerService getTimer() {
        return this.timer;
    }

    public BallService getBall() {
        return this.ball;
    }

    public Boolean getStarted() {
        return this.started;
    }

    public void setStarted(Boolean value) {
        if (!value.equals(this.started)) {
            this.started = started;
        }
    }

    public Boolean getFinished() {
        return this.finished;
    }

    public void setFinished(Boolean value) {
        if (!value.equals(this.finished)) {
            this.finished = finished;
        }
    }
}
