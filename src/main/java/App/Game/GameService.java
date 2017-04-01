package App.Game;

import App.Game.Ball.BallService;
import App.Game.Physics.PhysicsService;
import App.Game.Timer.TimerService;

import java.awt.*;

/**
 * Created by lichk on 27/03/2017.
 */
public class GameService {

    private Boolean started;//true if game started
    private Boolean finished;//true if game finished
    private Long timeLimitMs;//time in Ms the game is allowed to go on for before finishing
    private Dimension worldBounds;//boundaries of the game

    private TimerService timer;//allows access to timer service data
    private BallService ball;//allows access to ball service data
    private PhysicsService physics;

    /**
     * Game service default constructor, initializes all the services
     */
    public GameService() {
        this.timer = new TimerService();
        this.ball = new BallService();
        this.physics = new PhysicsService();

        this.physics.setWorldBounds(this.worldBounds);//set the game boundary

        this.started = false;
        this.finished = false;
        this.timeLimitMs = 0L;
        this.worldBounds = new Dimension(1024, 728);
    }

    /**
     * @return the game timer
     */
    public TimerService getTimer() {
        return this.timer;
    }

    /**
     * @return the ball
     */
    public BallService getBall() {
        return this.ball;
    }

    /**
     * @return if game started or not. true if started
     */
    public Boolean getStarted() {
        return this.started;
    }

    /**
     * @param value true if wants game to start
     */
    public void setStarted(Boolean value) {
        if (!value.equals(this.started)) {
            this.started = value;
        }
    }

    /**
     * @return if game finished or not, true if finished
     */
    public Boolean getFinished() {
        return this.finished;
    }

    /**
     * @param value true if want game to finish
     */
    public void setFinished(Boolean value) {
        if (!value.equals(this.finished)) {
            this.finished = value;
        }
    }

    /**
     * @return time in ms the game is allowed to go on for before automatically ending
     */
    public Long getTimeLimitMs() {
        return this.timeLimitMs;
    }

    /**
     * @param value sets the time in ms the game is allowed to go on for before automatically ending
     */
    public void setTimeLimitMs(Long value) {
        if (!value.equals(this.timeLimitMs)) {
            this.timeLimitMs = value;
        }
    }
}
