package App.Game.Fort;

import App.Game.Fort.Shield.ShieldComponent;
import App.Game.Fort.Wall.WallComponent;
import App.Game.Fort.Warlord.WarlordComponent;
import App.Game.GameService;
import App.Game.Physics.Physical;
import App.Shared.SharedModule;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by lichk on 31/03/2017.
 */
public class FortComponent {
    private SharedModule shared;
    private GameService game;
    private FortService model;

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
        this.model = new FortService(player);

        this.warlord = new WarlordComponent(this.shared, this.game);//creates a warlord
        this.wall = new WallComponent(this.shared, this.game);//creates a wall
        this.shield = new ShieldComponent(this.shared, this.game);//creates a shield
    }

    public void updateObject(Double intervalS) {
        this.warlord.update(intervalS);
        this.wall.update(intervalS);
        this.shield.update(intervalS);
    }

    public void renderOnContext(GraphicsContext context) {
        this.warlord.renderOnContext(context);
        this.wall.renderOnContext(context);
        this.shield.renderOnContext(context);
    }

    public HashSet<Physical> getPhysicalComponents() {
        HashSet<Physical> components =  new HashSet<>();
        components.add(this.warlord);
        components.add(this.wall);
        components.add(this.shield);
        return components;
    }

    public Integer getPlayer() {
        return this.model.player;
    }

    public void setPlayer(Integer value) {
        if (!value.equals(this.model.player)) {
            this.model.player = value;
        }
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
     * @return true if fort won, false if fort didn't
     */
    public Boolean getWon() {
        return this.model.won;
    }

    /**
     * @param value true if the fort is last alive or highest score
     */
    public void setWon(Boolean value) {
        if (!value.equals(this.model.won)) {
            this.model.won = value;
            this.warlord.setWon(value);
        }
    }

    /**
     * @param intervalS time between last frame and current so velocities stay consistent
     */
    public void updateObject(Double intervalS) {
        this.warlord.update(intervalS);//check if hit by ball
        this.wall.update();//check if hit by ball
        this.shield.update(intervalS);//check if hit by ball, also check if arrow keys are pressed
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
}
