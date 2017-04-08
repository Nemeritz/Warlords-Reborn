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
public class GameModule {
    private TimerService timer;
    private PhysicsService physics;
    private CanvasService canvas;

    /**
     * Game service default constructor, initializes all the services
     */
    public GameModule() {
        this.timer = new TimerService();
        this.physics = new PhysicsService();
        this.canvas = new CanvasService();

        this.physics.setWorldBounds(new Dimension(1024, 768));
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
}
