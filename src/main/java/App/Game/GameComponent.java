package App.Game;

import App.Game.AI.AIService;
import App.Game.Ball.BallComponent;
import App.Game.Canvas.CanvasComponent;
import App.Game.Fort.FortComponent;
import App.Game.Loop.Looper;
import App.Game.Overlay.OverlayComponent;
import App.Game.Powerup.PowerupComponent;
import App.Shared.JFX.EventReceiver;
import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import warlordstest.IGame;
import javafx.scene.media.MediaPlayer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Game component contains the implementation for the game, along with associated components required for playing the
 * game. All distance measurements in the game are measured in virtual game grid 'units' where there is a 1:1 ratio
 * between units and pixels at 1024x768. All time measurements in the game are measured in seconds, unless specified.
 * Typical unit used in calculation is units/second.
 *
 * Created by Jerry Fan on 21/03/2017.
 */
public class GameComponent extends BorderPane implements IGame, EventReceiver, Looper {
    private SharedModule shared;
    private GameModule game;

    private GameService model;

    @FXML
    private Text statusText;

    @FXML
    private StackPane gameStack;

    private CanvasComponent canvas;
    private BallComponent ball;
    private ArrayList<PowerupComponent> powerups;
    private Map<Integer, AIService> ai;
    private OverlayComponent overlay;
    private Map<Integer, FortComponent> forts;

    public MediaPlayer gameMusic;

    private void spawnPowerupMaybe() {
        if (this.shared.getSettings().powerups) {
            if (this.model.gameTime - this.model.lastPowerupSpawn > this.model.powerupSpawnInterval) {
                this.model.lastPowerupSpawn = this.model.gameTime;

                PowerupComponent newPowerup = new PowerupComponent(this.shared, this.game);
                this.powerups.add(newPowerup);
            }
        }
    }

    public GameComponent(SharedModule shared) {
        this.shared = shared;
        this.game = new GameModule();
        this.model = new GameService();
        this.overlay = new OverlayComponent(this.shared, this.game);
        this.canvas = new CanvasComponent(this.shared, this.game);
        this.powerups = new ArrayList<>();
        this.gameMusic = this.shared.getJFX().loadMedia(this.shared.getClass(), "MenuMusicBackup.mp3");

        if (this.canvas.hasJFXCanvas()) {
            this.game.getCanvas().setContext(this.canvas.getGraphicsContext());
        }

        this.forts = new TreeMap<>();
        this.ai = new TreeMap<>();
        this.shared.getJFX().loadFXML(this, GameComponent.class,
                "GameComponent.fxml");

        if (this.gameStack != null) {
            this.gameStack.getChildren().add(canvas);
            this.gameStack.getChildren().add(overlay);
        }
    }

    /**
     * Controls the object interactions and positions on the game canvas. Calculation heavy code goes here. This can
     * potentially be split into multiple functions and multi-threaded for efficiency if needed.
     * @param intervalS Seconds to calculate for. Objects will use this time to determine positions etc.
     */
    @Override
    public void onGameLoop(Double intervalS) {
        if (this.model.gameState.equals(GameState.PREGAME)) {
            this.statusText.setText("PREGAME");
            if (this.game.getTimer().currentTimeMs() >= 1000) {
                long countdownTime = this.model.lastCountdownStartMs / 1000 + 4 -
                        this.game.getTimer().currentTimeMs() / 1000;
                this.overlay.setCountdown(countdownTime);
                if (countdownTime == 0) {
                    this.model.gameState = GameState.GAME;
                    this.overlay.hideCountdown();
                }
            }
        }
        else if (this.model.gameState.equals(GameState.GAME)) {
            this.model.gameTime += intervalS;

            if (this.statusText != null) {
                this.statusText.setText(Integer.toString((int) this.model.gameTime));
            }

            this.game.getPhysics().check();

            this.ball.onGameLoop(intervalS);

            this.powerups.forEach(p -> p.onGameLoop(intervalS));

            int destroyedForts = 0;

            for (FortComponent fort : this.forts.values()) {
                fort.onGameLoop(intervalS);

                if (fort.isDestroyed()) {
                    destroyedForts++;
                }
            }

            boolean gameEnd = false;

            if (destroyedForts >= (this.forts.size() - this.model.fortSurvivalThreshold)) {
                gameEnd = true;
                for (FortComponent fort : this.forts.values()) {
                    fort.setWinner(true);
                }
            }

            if (this.model.gameTime >= this.model.gameTimeout) {
                gameEnd = true;

                // Placeholder for real win condition under timeout - will be based on score.
                for (FortComponent fort : this.forts.values()) {
                    fort.setWinner(true);
                }
            }

            if (gameEnd) {
                this.model.gameState = GameState.END;
            }
            else {
                this.ai.values().forEach(a -> a.onGameLoop(intervalS));
                this.spawnPowerupMaybe();
            }
        }
        else if (this.model.gameState.equals(GameState.PAUSE)) {
            this.statusText.setText("PAUSE");
        }
        else if (this.model.gameState.equals(GameState.UNPAUSE)) {
            this.statusText.setText("UNPAUSE");
            long countdownTime = (this.model.lastCountdownStartMs + 4000 - this.game.getTimer().currentTimeMs()) / 1000;
            this.overlay.setCountdown(countdownTime);
            if (countdownTime == 0) {
                this.model.gameState = GameState.GAME;
                this.overlay.hideCountdown();
            }
        }
        else if (this.model.gameState.equals(GameState.END)) {
            if (this.statusText != null) {
                this.statusText.setText("END");
            }
        }
    }

    @Override
    public void onKeyEvent(KeyEvent event) {
        if (event.getCode() == KeyCode.F9) {
            if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                if (this.model.gameState.equals(GameState.GAME)) {
                    this.model.gameState = GameState.PAUSE;
                    this.overlay.showPauseMenu();
                }
                else if (this.model.gameState.equals(GameState.PAUSE)) {
                    this.overlay.hidePauseMenu();
                    this.overlay.showCountdown();
                    this.model.lastCountdownStartMs = this.game.getTimer().currentTimeMs();
                    this.model.gameState = GameState.UNPAUSE;
                }
            }
        }
    }

    /**
     * Eventually this method will use the settings service from the shared module to generate the game layout at the
     * start of each game. For now, it's just a convenient way to load in anything that needs to be done prior to
     * game start (like manually testing the object rendering).
     */
    public void load() {
        this.startGameMusic();

        this.ball = new BallComponent(this.shared, this.game);
        this.ball.getPosition().setLocation(
                this.game.getPhysics().getWorldBounds().width / 2 - (double) this.ball.getSize().width / 2,
                this.game.getPhysics().getWorldBounds().height / 2 - (double) this.ball.getSize().height / 2
        );
        int direction = ThreadLocalRandom.current().nextInt(1, 5);

        switch(direction) {
            case 1:
                this.ball.getVelocity().set(
                        this.shared.getSettings().ballSpeed,
                        -this.shared.getSettings().ballSpeed
                );
                break;
            case 2:
                this.ball.getVelocity().set(
                        this.shared.getSettings().ballSpeed,
                        this.shared.getSettings().ballSpeed
                );
                break;
            case 3:
                this.ball.getVelocity().set(
                        -this.shared.getSettings().ballSpeed,
                        this.shared.getSettings().ballSpeed
                );
                break;
            case 4:
                this.ball.getVelocity().set(
                        -this.shared.getSettings().ballSpeed,
                        -this.shared.getSettings().ballSpeed
                );
                break;
        }

        FortComponent player1 = this.addPlayer(1, 1, new Point.Double(0, 0));
        FortComponent player2 = this.addPlayer(2, 2, new Point.Double(736, 0));
        FortComponent player3 = this.addPlayer(3, 3, new Point.Double(0, 480));
        FortComponent player4 = this.addPlayer(4, 4, new Point.Double(736, 480));

        this.addAI(3, player3);

        this.game.getLoop().getLoopers().add(this);
        this.shared.getJFX().getEventReceivers().add(this);

        this.overlay.showCountdown();
        this.model.lastCountdownStartMs = this.game.getTimer().currentTimeMs();

        this.model.gameState = GameState.PREGAME;

        this.game.getTimer().start();
    }

    public void unload() {
        this.model.gameState = GameState.UNLOAD;
        this.ai.clear();
        this.ball.dispose();
        this.ball = null;
        this.forts.values().forEach(FortComponent::dispose);
        this.forts.clear();
        this.powerups.forEach(PowerupComponent::dispose);
        this.powerups.clear();
        this.game.getLoop().getLoopers().remove(this);
        this.shared.getJFX().getEventReceivers().remove(this);
        this.game.getCanvas().clear();
        this.model = new GameService();
    }

    /**
     * Temporary public method to start the game countdown. Eventually, the scene transition to the game scene will be
     * evaluated to see if there is specific enough intent to begin the game (should be), and this method will run
     * reactively based on a scene change observable in the JFX service.
     */
    public void startGameCountdown() {
        this.load();
    }

    public void startGameMusic() {
        this.gameMusic.setVolume(this.shared.getSettings().musicVolume);
        this.gameMusic.setCycleCount(MediaPlayer.INDEFINITE);
        this.gameMusic.play();
    }

    public void exitToMenu() {
        this.unload();
        this.shared.getJFX().setScene("menu");
    }

    /**
     * Adds a player to the game by giving them a fort.
     * @param player Player's number, should be positive.
     * @param wallTopLeftPos
     * @return
     */
    public FortComponent addPlayer(Integer player, Integer orientation, Point.Double wallTopLeftPos) {
        FortComponent fort = new FortComponent(this.shared, this.game, player, orientation, wallTopLeftPos);
        this.forts.putIfAbsent(player, fort);
        return fort;
    }

    public AIService addAI(Integer player, FortComponent playerFort) {
        AIService ai = new AIService();
        ai.trackBall(this.ball);
        ai.giveFort(playerFort);
        ai.setBounds(this.game.getPhysics().getWorldBounds());
        this.ai.putIfAbsent(player, ai);
        return ai;
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
        GameState originalStarted = this.model.gameState;
        this.model.gameState = GameState.GAME;
        this.game.getLoop().tick();
        this.model.gameState = originalStarted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTimeRemaining(int seconds) {
        this.model.gameTimeout = seconds;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFinished() {
        return this.model.gameState.equals(GameState.END);
    }


    /**
     * @return Gets the ball component from the game. Only used for testing.
     */
    public BallComponent getBall() {
        return this.ball;
    }
}
