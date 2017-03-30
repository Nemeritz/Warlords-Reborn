package App.Game;

import App.Game.Ball.BallService;
import App.Game.Timer.TimerService;

/**
 * Created by lichk on 27/03/2017.
 */
public class GameService {
    private Boolean started;
    private Boolean finished;
    private Long timeLimitMs;

    TimerService timer;
    BallService ball;

    public GameService() {
        this.timer = new TimerService();
        this.ball = new BallService();

        this.started = false;
        this.finished = false;
        this.timeLimitMs = 0L;
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
            this.started = value;
        }
    }

    public Boolean getFinished() {
        return this.finished;
    }

    public void setFinished(Boolean value) {
        if (!value.equals(this.finished)) {
            this.finished = value;
        }
    }

    public Long getTimeLimitMs() {
        return this.timeLimitMs;
    }

    public void setTimeLimitMs(Long value) {
        if (!value.equals(this.timeLimitMs)) {
            this.timeLimitMs = value;
        }
    }
}
