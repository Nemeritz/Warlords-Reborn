package App.Game;

import App.Game.Canvas.CanvasService;
import App.Game.Physics.PhysicsService;
import App.Game.Timer.TimerService;

import java.awt.*;

/**
 * This class provides a model and game services to game components. Will eventually be split into a module and
 * service to separate the model and service containers.
 *
 * Created by Jerry Fan on 27/03/2017.
 */
public class GameService {

    boolean started; // true if game started
    boolean finished; // true if game finished
    int fortSurvivalThreshold;
    double schedulerInterval;
    private Long timeLimitMs; // time in Ms the game is allowed to go on for before finishing
    private Dimension worldBounds; // boundaries of the game

    private TimerService timer;
    private PhysicsService physics;
    private CanvasService canvas;

    /**
     * Game service default constructor, initializes all the services
     */
    public GameService() {
        this.timer = new TimerService();
        this.physics = new PhysicsService();
        this.canvas = new CanvasService();

        this.started = false;
        this.finished = false;
        this.timeLimitMs = 180000L;
        this.fortSurvivalThreshold = 1; // One player remaining threshold by default
        this.schedulerInterval = 1.0/60;
        this.worldBounds = new Dimension(1024, 768);

        this.physics.setWorldBounds(this.worldBounds);
    }

    /**
     * @return the game timer
     */
    public TimerService getTimer() {
        return this.timer;
    }

    /**
     * @return The physics service.
     */
    public PhysicsService getPhysics() {
        return this.physics;
    }

    public CanvasService getCanvas() {
        return this.canvas;
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
