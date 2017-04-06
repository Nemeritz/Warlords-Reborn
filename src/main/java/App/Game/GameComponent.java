package App.Game;

import App.Game.Ball.BallComponent;
import App.Game.Canvas.CanvasComponent;
import App.Game.Fort.FortComponent;
import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import warlordstest.IGame;

import java.awt.*;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;

/**
 * Game component contains the implementation for the game, along with associated components required for playing the
 * game. All distance measurements in the game are measured in virtual game grid 'units' where there is a 1:1 ratio
 * between units and pixels at 1024x768. All time measurements in the game are measured in seconds, unless specified.
 * Typical unit used in calculation is units/second.
 *
 * Created by Jerry Fan on 21/03/2017.
 */
public class GameComponent extends BorderPane implements IGame, Observer {
    private SharedModule shared;
    private GameService game;

    @FXML
    private Text gameTime;

    private CanvasComponent canvas;
    private BallComponent ball;
    private Map<Integer,FortComponent> forts;

    private long lastGameLoopTimeMs;

    /**
     * Divide the intervals if they are larger than the scheduler intervals, and iteratively progress the game using
     * game loops of interval size + remainder.
     * @param intervalS Seconds elapsed since last gameLoop iteration.
     */
    private void gameLoopScheduler(Double intervalS) {
        if (this.game.started && !this.game.finished) {
            double remainder;

            if (intervalS > this.game.schedulerInterval) {
                int iterations = (int) Math.floor(intervalS / this.game.schedulerInterval);

                for (int i = 0; i < iterations; i++) {
                    this.gameLoop(this.game.schedulerInterval);
                }

                remainder = intervalS % this.game.schedulerInterval;
            }
            else {
                remainder = intervalS;
            }

            this.gameLoop(remainder);
        }
    }

    /**
     * Controls the object interactions and positions on the game canvas. Calculation heavy code goes here. This can
     * potentially be split into multiple functions and multi-threaded for efficiency if needed.
     * @param intervalS Seconds to calculate for. Objects will use this time to determine positions etc.
     */
    private void gameLoop(Double intervalS) {
        this.game.getPhysics().check();

        this.ball.updateObject(intervalS);

        int destroyedForts = 0;

        for (FortComponent fort : this.forts.values()) {
            fort.updateObject(intervalS);

            if (fort.isDestroyed()) {
                destroyedForts++;
            }
        }

        boolean gameEnd = false;

        if (destroyedForts >= (this.forts.size() - this.game.fortSurvivalThreshold)) {
            gameEnd = true;
            for (FortComponent fort : this.forts.values()) {
                fort.setWinner(true);
            }
        }

        if (this.game.getTimer().currentTimeMs() >= this.game.getTimeLimitMs()) {
            gameEnd = true;

            // Placeholder for real win condition under timeout - will be based on score.
            for (FortComponent fort : this.forts.values()) {
                fort.setWinner(true);
            }
        }

        if (gameEnd) {
            this.game.finished = true;
        }
    }

    /**
     * Controls the visuals as seen on the game canvas. Separated from the game loop calculation for abstraction
     * purposes and also to decouple framerate from object interaction. If game performance deteriorates, render will
     * slow, decreasing FPS.
     */
    private void renderLoop() {
        if (this.game.started) {
            this.game.getCanvas().clear();
            this.game.getCanvas().render();
        }

        Double timeS = (double) (this.game.getTimer().currentTimeMs() / 1000);
        this.gameTime.setText(timeS.toString());
    }

    /**
     * Eventually this method will use the settings service from the shared module to generate the game layout at the
     * start of each game. For now, it's just a convenient way to load in anything that needs to be done prior to
     * game start (like manually testing the object rendering).
     */
    private void setup() {
        this.ball.getPosition().setLocation(500,500);
        this.ball.getVelocity().set(0, 0);

        FortComponent player1 = this.addPlayer(1, new Point.Double(0, 0));
        FortComponent player2 = this.addPlayer(2, new Point.Double(736, 480));

        player1.getShield().getPosition().setLocation(300, 300);
        player2.getShield().getPosition().setLocation(700, 600);
    }

    public GameComponent(SharedModule shared) {
        this.shared = shared;
        this.game = new GameService();
        this.canvas = new CanvasComponent(this.shared, this.game);
        this.game.getCanvas().setContext(this.canvas.getGraphicsContext());
        this.ball = new BallComponent(this.shared, this.game);
        this.forts = new TreeMap<>();
        this.shared.getJFX().loadFXML(this, GameComponent.class,
                "GameComponent.fxml");
        this.setCenter(canvas);
        this.lastGameLoopTimeMs = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Observable obs, Object obj) {
        // Game timer observer function, fires when each animation frame changes, which is about once per nanosecond.
        // The render loop and game loop methods are hooked here.
        if (obs == this.game.getTimer().getFrame()) {
            long currentTimeMs = this.game.getTimer().currentTimeMs();
            this.renderLoop(); // Runs every animation frame (optimally).

            long intervalMs = currentTimeMs - this.lastGameLoopTimeMs; // Calculate time since last game loop iteration.

            if (intervalMs > 0) {
                this.lastGameLoopTimeMs = currentTimeMs; // Immediately set current time as last iteration time
                this.gameLoopScheduler((double) intervalMs / 1000); // Runs every animation frame (optimally).
            }
        }
    }

    /**
     * Temporary public method to start the game countdown. Eventually, the scene transition to the game scene will be
     * evaluated to see if there is specific enough intent to begin the game (should be), and this method will run
     * reactively based on a scene change observable in the JFX service.
     */
    public void startGameCountdown() {
        this.setup();
        this.game.started = true;
        this.game.getTimer().getFrame().addObserver(this);
        this.game.getTimer().start();
    }


    /**
     * Adds a player to the game by giving them a fort.
     * @param player Player's number, should be positive.
     * @return
     */
    public FortComponent addPlayer(Integer player, Point.Double position) {
        FortComponent fort = new FortComponent(this.shared, this.game, player, position);
        this.forts.putIfAbsent(player, fort);
        return fort;
    }


    /**
     * Gets the forts (players) in the game.
     */
    public Map<Integer, FortComponent> getPlayers() {
        return this.forts;
    }

    /**
     * {@inheritDoc}
     * To the game component, this effectively manually rolls the clock one second forward. Game start status is
     * preserved.
     */
    @Override
    public void tick() {
        boolean originalStarted = this.game.started;
        this.game.started = true;
        this.gameLoopScheduler(1.0);
        this.game.started = originalStarted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTimeRemaining(int seconds) {
        this.game.setTimeLimitMs((long) seconds * 1000);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFinished() {
        return this.game.finished;
    }


    /**
     * @return Gets the ball component from the game. Only used for testing.
     */
    public BallComponent getBall() {
        return this.ball;
    }
}
