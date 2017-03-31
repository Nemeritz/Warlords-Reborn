package App.Game;

import App.Game.Ball.BallService;
import App.Game.Physics.PhysicsService;
import App.Game.Timer.TimerService;

import java.awt.*;

/**
 * Created by lichk on 27/03/2017.
 */
public class GameService {

    private Boolean started;
    private Boolean finished;
    private Long timeLimitMs;
    private Dimension worldBounds;

    private TimerService timer;
    private BallService ball;
    private PhysicsService physics;

    public GameService() {
        this.timer = new TimerService();
        this.ball = new BallService();
        this.physics = new PhysicsService();

        this.physics.setWorldBounds(this.worldBounds);

        this.started = false;
        this.finished = false;
        this.timeLimitMs = 0L;
        this.worldBounds = new Dimension(1024, 728);
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
