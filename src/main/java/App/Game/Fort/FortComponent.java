package App.Game.Fort;

import App.Game.Fort.Shield.ShieldComponent;
import App.Game.Fort.Wall.WallComponent;
import App.Game.Fort.Warlord.WarlordComponent;
import App.Game.GameService;
import App.Game.Physics.Physical;
import App.Shared.SharedModule;
import javafx.scene.canvas.GraphicsContext;

import java.util.HashSet;

/**
 * Created by Jerry Fan on 31/03/2017.
 */
public class FortComponent {
    private SharedModule shared;
    private GameService game;
    private FortService fort;

    private WarlordComponent warlord;
    private WallComponent wall;
    private ShieldComponent shield;


    /**
     * Contructor for fort
     * @param shared shared module controlling all scenes
     * @param game current game containing all services
     */
    public FortComponent(SharedModule shared, GameService game, Integer player) {
        this.shared = shared;
        this.game = game;
        this.fort = new FortService(player);

        this.warlord = new WarlordComponent(this.shared, this.game, this.fort); // creates a warlord
        this.wall = new WallComponent(this.shared, this.game); // creates a wall
        this.shield = new ShieldComponent(this.shared, this.game, this.fort); // creates a shield
    }

    /**
     * @param intervalS time between last frame and current so velocities stay consistent
     */
    public void updateObject(Double intervalS) {
        this.warlord.update(intervalS);
        this.wall.update(intervalS);
        this.shield.update(intervalS);

        if (warlord.isDead()) {
            this.fort.destroyed = true;
        }
    }

    /**
     * @param context the 2Dgraphics context in the canvas
     *                Function renders all components in the fort
     */
    public void renderOnContext(GraphicsContext context) {
        this.warlord.renderOnContext(context);
        this.wall.renderOnContext(context);
        this.shield.renderOnContext(context);
    }


    /**
     * @return Set of physical components belonging to the fort.
     */
    public HashSet<Physical> getPhysicalComponents() {
        HashSet<Physical> components =  new HashSet<>();
        components.add(this.warlord);
        components.add(this.wall);
        components.add(this.shield);
        return components;
    }

    /**
     * @return warlord of the fort
     */
    public WarlordComponent getWarlord() {
        return this.warlord;
    }

    /**
     * @return wall of the fort
     */
    public WallComponent getWall() {
        return this.wall;
    }

    /**
     * @return shield of the fort
     */
    public ShieldComponent getShield() {
        return this.shield;
    }

    /**
     * @return True if this fort has been destroyed.
     */
    public boolean isDestroyed() {
        return this.fort.destroyed;
    }

    /**
     * @param value true if the fort is last alive or highest score
     */
    public void setWinner(Boolean value) {
        this.fort.winner = value;
        this.warlord.setWinner(value);
    }
}
