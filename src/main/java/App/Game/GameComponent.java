package App.Game;

import App.Game.Canvas.CanvasComponent;
import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by lichk on 21/03/2017.
 */
public class GameComponent extends BorderPane implements Observer {
    private SharedModule shared;
    private GameService game;

    @FXML
    private Text gameTime;

    private CanvasComponent canvas;

    private boolean started;

    private boolean finished;

    private long lastGameLoopTimeMs;

    /**
     * Controls the object interactions and positions on the game canvas. Calculation heavy code goes here. This can
     * potentially be split into multiple functions and multi-threaded for efficiency if needed.
     * @param intervalMs Milliseconds elapsed since last gameLoop iteration.
     */
    private void gameLoop(long intervalMs) {
        if (this.game.getTimer().currentTimeMs() > 3000) {
            this.started = true;
        }
        else if (this.game.getTimer().currentTimeMs() > 120000) {
            this.finished = true;
        }
    }

    /**
     * Controls the visuals as seen on the game canvas. Separated from the game loop calculation for abstraction
     * purposes and also to decouple framerate from object interaction. If game performance deteriorates, render will
     * slow, decreasing FPS.
     */
    private void renderLoop() {
        if (this.started) {
            this.canvas.renderGameObjects();
        }

        Double timeS = (double) (this.game.getTimer().currentTimeMs() / 1000);
        this.gameTime.setText(timeS.toString());
    }

    public GameComponent(SharedModule shared) {
        this.shared = shared;
        this.game = new GameService();
        this.canvas = new CanvasComponent(this.shared, this.game);
        this.shared.getJFX().loadFXML(this, GameComponent.class,
                "GameComponent.fxml");
        this.setCenter(canvas);

        this.lastGameLoopTimeMs = 0;
        this.started = false;
        this.finished = false;
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
            this.renderLoop(); // Runs every nanosecond (optimally).

            long intervalMs = currentTimeMs - this.lastGameLoopTimeMs; // Calculate time since last game loop iteration.

            if (intervalMs > 0) {
                this.lastGameLoopTimeMs = currentTimeMs; // Immediately set current time as last iteration time
                this.gameLoop(intervalMs); // Runs every millisecond (optimally).
            }
        }
    }


    /**
     * Temporary public method to start the game countdown. Eventually, the scene transition to the game scene will be
     * evaluated to see if there is specific enough intent to begin the game (should be), and this method will run
     * reactively based on a scene change observable in the JFX service.
     */
    public void startGameCountdown() {
        this.game.getTimer().getFrame().addObserver(this);
        this.game.getTimer().start();
    }
}
