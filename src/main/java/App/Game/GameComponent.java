package App.Game;

import App.Game.Ball.BallComponent;
import App.Game.Canvas.CanvasComponent;
import App.Game.Fort.FortComponent;
import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;

/**
 * Created by lichk on 21/03/2017.
 */
public class GameComponent extends BorderPane implements Observer {
    private SharedModule shared;
    private GameService game;

    @FXML
    private Text gameTime;

    private CanvasComponent canvas;
    private BallComponent ball;
    private Map<Integer,FortComponent> forts;

    private long lastGameLoopTimeMs;

    /**
     * Controls the object interactions and positions on the game canvas. Calculation heavy code goes here. This can
     * potentially be split into multiple functions and multi-threaded for efficiency if needed.
     * @param intervalS Seconds elapsed since last gameLoop iteration.
     */
    private void gameLoop(Double intervalS) {
        if (this.game.getTimer().currentTimeMs() > 3000) {
            this.game.setStarted(true);
            this.canvas.updateObjects(intervalS);
        }
        else if (this.game.getTimer().currentTimeMs() > this.game.getTimeLimitMs()) {
            this.game.setFinished(true);
        }
    }

    /**
     * Controls the visuals as seen on the game canvas. Separated from the game loop calculation for abstraction
     * purposes and also to decouple framerate from object interaction. If game performance deteriorates, render will
     * slow, decreasing FPS.
     */
    private void renderLoop() {
        if (this.game.getStarted()) {
            this.canvas.renderObjects();
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
        this.game.ball.getPosition().setLocation(10,10);

        this.addPlayer(1);
        this.addPlayer(2);

        FortComponent player1 = this.forts.get(1);
        FortComponent player2 = this.forts.get(2);

        player1.getShield().getPosition().setLocation(300, 300);
        player2.getShield().getPosition().setLocation(600, 600);

        player1.getWall().getPosition().setLocation(200, 200);
        player2.getWall().getPosition().setLocation(500, 500);

        player1.getWarlord().getPosition().setLocation(100, 100);
        player1.getWarlord().getPosition().setLocation(400, 400);
    }

    public GameComponent(SharedModule shared) {
        this.shared = shared;
        this.game = new GameService();
        this.canvas = new CanvasComponent(this.shared, this.game);
        this.ball = new BallComponent(this.shared, this.game);
        this.forts = new TreeMap<>;
        this.shared.getJFX().loadFXML(this, GameComponent.class,
                "GameComponent.fxml");
        this.setCenter(canvas);

        this.lastGameLoopTimeMs = 0;
    }

    /**
     * Update method called when watched observables change.
     * @param obs Observable object that fired an event.
     * @param obj Any object passed by the observable object to observers.
     */
    public void update(Observable obs, Object obj) {
        // Game timer observer function, fires when each animation frame changes, which is about once per nanosecond.
        // The render loop and game loop methods are hooked here.
        if (obs == this.game.getTimer().getFrame()) {
            long currentTimeMs = this.game.getTimer().currentTimeMs();
            this.renderLoop(); // Runs every animation frame (optimally).

            long intervalMs = currentTimeMs - this.lastGameLoopTimeMs; // Calculate time since last game loop iteration.

            if (intervalMs > 0) {
                this.lastGameLoopTimeMs = currentTimeMs; // Immediately set current time as last iteration time
                this.gameLoop((double) intervalMs / 1000); // Runs every animation frame (optimally).
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
        this.game.getTimer().getFrame().addObserver(this);
        this.game.getTimer().start();
    }

    public void addPlayer(Integer player) {
        this.forts.putIfAbsent(player, new FortComponent(this.shared, this.game, player));
    }

    public Map<Integer, FortComponent> getPlayers() {
        return this.forts;
    }
}
