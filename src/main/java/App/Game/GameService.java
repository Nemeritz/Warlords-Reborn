package App.Game;

/**
 * Game model used for the GameComponent.
 * Created by Jerry Fan on 8/04/2017.
 */
public class GameService {
    public int fortSurvivalThreshold; // How many forts remaining for win
    public double gameTime;
    public double gameTimeout; // Time limit for the game
    public GameState gameState;
    public long lastCountdownStartMs;
    public double lastPowerupSpawn;
    public int powerupSpawnInterval;

    public GameService() {
        this.gameState = GameState.LOAD;
        this.gameTime = 0;
        this.gameTimeout = 180;
        this.fortSurvivalThreshold = 1; // One player remaining threshold by default
        this.lastCountdownStartMs = 0L;
        this.lastPowerupSpawn = 0L;
        this.powerupSpawnInterval = 20;
    }
}
