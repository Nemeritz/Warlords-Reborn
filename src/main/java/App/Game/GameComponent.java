package App.Game;

import App.Game.AI.AIService;
import App.Game.Ball.BallComponent;
import App.Game.Canvas.CanvasComponent;
import App.Game.Fort.FortComponent;
import App.Game.Loop.Looper;
import App.Game.Overlay.OverlayComponent;
import App.Game.Powerup.PowerupComponent;
import App.Game.StatusBar.StatusBarComponent;
import App.Shared.JFX.EventReceiver;
import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import warlordstest.IGame;
import javafx.scene.image.*;

import java.awt.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Game component contains the implementation for the game, along with associated components required for playing the
 * game. All distance measurements in the game are measured in virtual game grid 'units' where there is a 1:1 ratio
 * between units and pixels at 1024x733. All time measurements in the game are measured in seconds, unless specified.
 * Typical unit used in calculation is units/second.
 *
 * Created by Jerry Fan on 21/03/2017.
 */
public class GameComponent extends BorderPane implements IGame, EventReceiver, Looper, Observer {
    private SharedModule shared;
    private GameModule game;

    private GameService model;

    @FXML
    private StackPane gameStack;

    @FXML
    private ImageView background;

    private StatusBarComponent statusBar;
    private CanvasComponent canvas;
    private BallComponent ball;
    private ArrayList<PowerupComponent> powerups;
    private Map<Integer, AIService> ai;
    private OverlayComponent overlay;
    private Map<Integer, FortComponent> forts;

    public MediaPlayer gameMusic;

    /**
     * Conditionally spawn powerups if powerups are enabled and the spawn interval has elapsed.
     */
    private void spawnPowerupMaybe() {
        if (this.shared.getSettings().powerups) {
            if (this.model.gameTime - this.model.lastPowerupSpawn > this.model.powerupSpawnInterval) {
                this.model.lastPowerupSpawn = this.model.gameTime;

                PowerupComponent newPowerup = new PowerupComponent(this.shared, this.game);
                this.powerups.add(newPowerup);
            }
        }
    }

    /**
     * @param shared A reference to the shared module.
     */
    public GameComponent(SharedModule shared) {
        this.shared = shared;
        this.shared.getJFX().getScene().addObserver(this);
        this.game = new GameModule();
        this.model = new GameService();
        this.overlay = new OverlayComponent(this.shared, this.game);
        this.canvas = new CanvasComponent(this.shared, this.game);
        this.statusBar = new StatusBarComponent(this.shared, this.game);
        this.powerups = new ArrayList<>();
        this.gameMusic = this.shared.getJFX().loadMedia(this.getClass(), "assets/GameMusic.mp3");

        if (this.canvas.hasJFXCanvas()) {
            // Set the canvas context in the canvas service so that render functions will work.
            this.game.getCanvas().setContext(this.canvas.getGraphicsContext());
        }

        // Using treemaps for efficient self sorting maps
        this.forts = new TreeMap<>();
        this.ai = new TreeMap<>();

        this.shared.getJFX().loadFXML(this, GameComponent.class,
                "GameComponent.fxml");

        this.setTop(statusBar);
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
            // PREGAME state processing
            this.statusBar.setStatusText("PREGAME");

            if (this.game.getTimer().currentTimeMs() >= 1000) {
                // If current time is past 1 second.
                long countdownTime = this.model.lastCountdownStartMs / 1000 + 4 -
                        this.game.getTimer().currentTimeMs() / 1000;
                this.overlay.setLargeText(Long.toString(countdownTime));
                if (countdownTime == 0) {
                    this.model.gameState = GameState.GAME;
                    this.overlay.hideLargeText();
                }
            }
            else {
                // Otherwise tell players to get ready.
                this.overlay.setLargeText("GET READY!");
            }
        }
        else if (this.model.gameState.equals(GameState.GAME)) {
            // GAME state processing.
            this.model.gameTime += intervalS;
            this.game.gameTime = this.model.gameTime;

            // Track time with the statusbar.
            this.statusBar.setStatusText(Integer.toString((int) this.model.gameTime));

            this.game.getPhysics().check();

            this.ball.onGameLoop(intervalS);

            this.powerups.forEach(p -> p.onGameLoop(intervalS));

            int destroyedForts = 0;

            for (FortComponent fort : this.forts.values()) {
                fort.onGameLoop(intervalS);
                this.statusBar.setPlayerScore(fort.getPlayer(), Integer.toString(fort.getScore()));

                if (fort.isDestroyed()) {
                    destroyedForts++;
                }
            }

            // Check for win conditions here.
            boolean gameEnd = false;

            if (destroyedForts >= (this.forts.size() - this.model.fortSurvivalThreshold)) {
                // Win by kills.
                gameEnd = true;
                for (FortComponent fort : this.forts.values()) {
                    if (!fort.isDestroyed()) {
                        fort.setWinner(true);
                    }
                }
            }
            else if (this.model.gameTime >= this.model.gameTimeout) {
                // Win by timeout.
                gameEnd = true;

                TreeMap<Integer, Integer> playerScores = new TreeMap<>();

                for (FortComponent fort : this.forts.values()) {
                    if (!fort.isDestroyed()) {
                        boolean hasHigherScore = false;
                        boolean scoreIsEqual = playerScores.isEmpty();
                        for (Integer score : playerScores.values()) {
                            if (fort.getScore() > score) {
                                hasHigherScore = true;
                                break;
                            } else if (fort.getScore() == score) {
                                scoreIsEqual = true;
                                break;
                            }
                        }

                        if (hasHigherScore) {
                            playerScores.clear();
                            playerScores.put(fort.getPlayer(), fort.getScore());
                        } else if (scoreIsEqual) {
                            playerScores.put(fort.getPlayer(), fort.getScore());
                        }
                    }
                }

                playerScores.keySet().forEach(p -> this.forts.get(p).setWinner(true));
            }

            if (gameEnd) {
                this.model.gameState = GameState.END;

                ArrayList<FortComponent> winningForts = new ArrayList<>();

                // Make a list of winning forts.
                this.forts.values().forEach(f -> {
                    if (f.hasWon()) {
                        winningForts.add(f);
                    }
                });

                if (winningForts.size() == 1) {
                    // Only one fort won.
                    String playerName;

                    switch(winningForts.get(0).getPlayer()) {
                        case 1:
                            playerName = this.shared.getSettings().topLeftName;
                            break;
                        case 2:
                            playerName = this.shared.getSettings().topRightName;
                            break;
                        case 3:
                            playerName = this.shared.getSettings().botLeftName;
                            break;
                        case 4:
                            playerName = this.shared.getSettings().botRightName;
                            break;
                        default:
                            playerName = "Unknown";
                            break;
                    }
                    this.overlay.setGameEnd(playerName.toUpperCase() + " VICTORY");
                }
                else {
                    // Multiple forts won or no forts won.
                    this.overlay.setGameEnd("DRAW");
                }
                this.overlay.showGameEnd();
            }
            else {
                // If the game hasn't ended, do these things.
                this.ai.values().forEach(a -> a.onGameLoop(intervalS));
                this.spawnPowerupMaybe();
            }
        }
        else if (this.model.gameState.equals(GameState.PAUSE)) {
            // Stuff to do whilst paused.
            this.statusBar.setStatusText("PAUSE");
        }
        else if (this.model.gameState.equals(GameState.UNPAUSE)) {
            // Set a timer on unpausing so players can prepare.
            this.statusBar.setStatusText("UNPAUSE");
            long countdownTime = (this.model.lastCountdownStartMs + 4000 - this.game.getTimer().currentTimeMs()) / 1000;
            this.overlay.setLargeText(Long.toString(countdownTime));
            if (countdownTime == 0) {
                this.model.gameState = GameState.GAME;
                this.overlay.hideLargeText();
            }
        }
        else if (this.model.gameState.equals(GameState.END)) {
            // Stuff to do after game ends.
            this.statusBar.setStatusText("END");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onKeyEvent(KeyEvent event) {
        if (event.getCode() == KeyCode.P) {
            // Pause
            if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                if (this.model.gameState.equals(GameState.GAME)) {
                    this.model.gameState = GameState.PAUSE;
                    this.overlay.showPauseMenu();
                }
                else if (this.model.gameState.equals(GameState.PAUSE)) {
                    this.overlay.hidePauseMenu();
                    this.overlay.showLargeText();
                    this.model.lastCountdownStartMs = this.game.getTimer().currentTimeMs();
                    this.model.gameState = GameState.UNPAUSE;
                }
            }
        }

        if (event.getCode() == KeyCode.ESCAPE) {
            // Exit game
            if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                this.exitToMenu();
            }
        }

        if (event.getCode() == KeyCode.PAGE_DOWN) {
            // Skip to finish
            if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                this.setTimeRemaining(0);
            }
        }
    }

    /**
     * Actions to perform to setup the game with the required objects. This reads settings from the shared settings
     * service.
     */
    public void load() {
        this.statusBar.setStatusText("LOAD");
        this.overlay.setLargeText("LOADING GAME");

        // Placeholder names if no names provided.
        if (this.shared.getSettings().topLeft != 0 && this.shared.getSettings().topLeftName.isEmpty()) {
            this.shared.getSettings().topLeftName = "Zuko";
        }

        if (this.shared.getSettings().topRight != 0 && this.shared.getSettings().topRightName.isEmpty()) {
            this.shared.getSettings().topRightName = "Katara";
        }

        if (this.shared.getSettings().botLeft != 0 && this.shared.getSettings().botLeftName.isEmpty()) {
            this.shared.getSettings().botLeftName = "Toph";
        }

        if (this.shared.getSettings().botRight != 0 && this.shared.getSettings().botRightName.isEmpty()) {
            this.shared.getSettings().botRightName = "Aang";
        }

        this.statusBar.setPlayerName(1,
                (this.shared.getSettings().topLeft != 0) ? this.shared.getSettings().topLeftName : "Empty");
        this.statusBar.setPlayerName(2,
                (this.shared.getSettings().topRight != 0) ? this.shared.getSettings().topRightName : "Empty");
        this.statusBar.setPlayerName(3,
                (this.shared.getSettings().botLeft != 0) ? this.shared.getSettings().botLeftName : "Empty");
        this.statusBar.setPlayerName(4,
                (this.shared.getSettings().botRight != 0) ? this.shared.getSettings().botRightName : "Empty");

        // Initialise the ball.
        this.ball = new BallComponent(this.shared, this.game);
        this.ball.getPosition().setLocation(
                this.game.getPhysics().getWorldBounds().width / 2 - (double) this.ball.getSize().width / 2,
                this.game.getPhysics().getWorldBounds().height / 2 - (double) this.ball.getSize().height / 2
        );

        // Picking a random direction.
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

        // Create player fort and AIs to inherit the fort if needed.
        if (this.shared.getSettings().topLeft > 0) {
            FortComponent player1 = this.addPlayer(1, 1, new Point.Double(0, 0));
            if (this.shared.getSettings().topLeft < 3) {
                AIService ai1 = this.addAI(1, player1);
                if (this.shared.getSettings().topLeft == 2) {
                    ai1.setCheese(true);
                }
            }
        }
        if (this.shared.getSettings().topRight > 0) {
            FortComponent player2 = this.addPlayer(2, 2, new Point.Double(736, 0));
            if (this.shared.getSettings().topRight < 3) {
                AIService ai2 = this.addAI(2, player2);
                if (this.shared.getSettings().topRight == 2) {
                    ai2.setCheese(true);
                }
            }
        }
        if (this.shared.getSettings().botLeft > 0) {
            FortComponent player3 = this.addPlayer(3, 3, new Point.Double(0, 445));
            if (this.shared.getSettings().botLeft < 3) {
                AIService ai3 = this.addAI(3, player3);
                if (this.shared.getSettings().botLeft == 2) {
                    ai3.setCheese(true);
                }
            }
        }
        if (this.shared.getSettings().botRight > 0) {
            FortComponent player4 = this.addPlayer(4, 4, new Point.Double(736, 445));
            if (this.shared.getSettings().botRight < 3) {
                AIService ai4 = this.addAI(4, player4);
                if (this.shared.getSettings().botRight == 2) {
                    ai4.setCheese(true);
                }
            }
        }

        // Debug mode
        if (AIService.AI_DEBUG) {
            this.ai.values().forEach(a -> this.game.getCanvas().getCanvasObjects().add(a));
        }

        // Set remaining time according to match options
        this.setTimeRemaining(this.shared.getSettings().scoreWin ?
                this.shared.getSettings().maxGameTime : Integer.MAX_VALUE);

        // Register the object with broadcasters
        this.game.getLoop().getLoopers().add(this);
        this.shared.getJFX().getEventReceivers().add(this);

        this.model.lastCountdownStartMs = this.game.getTimer().currentTimeMs();

        this.model.gameState = GameState.PREGAME;

        this.game.getTimer().start();

        this.startGameMusic();
    }

    /**
     * Actions taken after game is finished to clean up and remove the game object from listener lists etc.
     */
    public void unload() {
        this.model.gameState = GameState.UNLOAD;
        this.gameMusic.stop();
        this.overlay.hidePauseMenu();
        this.overlay.hideGameEnd();
        this.ai.clear();
        this.ball.dispose();
        this.ball = null;
        this.forts.keySet().forEach(k -> {
            this.statusBar.setPlayerName(k, "Empty");
            this.statusBar.setPlayerScore(k, "0");
        });
        this.forts.values().forEach(FortComponent::dispose);
        this.forts.clear();
        this.powerups.forEach(PowerupComponent::dispose);
        this.powerups.clear();
        this.game.getLoop().getLoopers().remove(this);
        this.shared.getJFX().getEventReceivers().remove(this);
        this.game.getCanvas().clear();
        this.model = new GameService();
        this.overlay.setLargeText("LOADING GAME");
        this.overlay.showLargeText();
    }

    /**
     * Temporary public method to start the game countdown. Eventually, the scene transition to the game scene will be
     * evaluated to see if there is specific enough intent to begin the game (should be), and this method will run
     * reactively based on a scene change observable in the JFX service.
     */
    @Deprecated
    public void startGameCountdown() {
        this.load();
    }

    /**
     * Start playing music.
     */
    public void startGameMusic() {
        if (this.gameMusic != null) {
            this.gameMusic.stop();
            this.gameMusic.setVolume(this.shared.getSettings().musicVolume);
            this.gameMusic.setCycleCount(MediaPlayer.INDEFINITE);
            this.gameMusic.play();
        }
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
     * To the game component, this effectively manually rolls the clock one second forward. Game start status is NOT
     * preserved.
     */
    @Override
    public void tick() {
        this.model.gameState = GameState.GAME;
        this.game.getLoop().tick();
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Observable obs, Object arg) {
        if (this.shared.getJFX().getScene().equals(obs) &&
                this.shared.getJFX().getScene().current().getKey().equals("game")) {
            this.load();
        }
    }
}
