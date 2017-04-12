package App.Game;

import App.Game.Canvas.CanvasService;
import App.Game.Loop.LoopService;
import App.Game.Physics.PhysicsService;
import App.Game.Score.ScoreService;
import App.Game.Timer.TimerService;

import java.awt.*;

/**
 * This class provides game services to game components.
 * Created by Jerry Fan on 27/03/2017.
 */
public class GameModule {
    public double gameTime;
    private ScoreService score;
    private TimerService timer;
    private PhysicsService physics;
    private CanvasService canvas;
    private LoopService loop;

    /**
     * Game service default constructor, initializes all the services.
     */
    public GameModule() {
        this.timer = new TimerService();
        this.physics = new PhysicsService();
        this.canvas = new CanvasService();
        this.score = new ScoreService();
        this.loop = new LoopService();
        this.gameTime = 0;
        this.loop.setMasterTimer(this.timer);
        this.physics.setWorldBounds(new Dimension(1024, 733)); // Set bounds to fit with the status bar.
    }

    /**
     * @return A service to manage scorekeepers.
     */
    public ScoreService getScore() {
        return this.score;
    }

    /**
     * @return the game timer.
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

    /**
     * @return The canvas service.
     */
    public CanvasService getCanvas() {
        return this.canvas;
    }

    /**
     * @return The game loop service.
     */
    public LoopService getLoop() {
        return this.loop;
    }
}
