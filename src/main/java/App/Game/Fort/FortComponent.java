package App.Game.Fort;

import App.Game.Fort.Shield.ShieldComponent;
import App.Game.Fort.Wall.WallComponent;
import App.Game.Fort.Warlord.WarlordComponent;
import App.Game.GameService;
import App.Game.Physics.Physical;
import App.Shared.SharedModule;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Jerry Fan on 31/03/2017.
 */
public class FortComponent {
    private SharedModule shared;
    private GameService game;
    private FortService fort;

    private WarlordComponent warlord;
    private List<WallComponent> walls;
    private ShieldComponent shield;

    private void setup(Integer orientation) {
        switch (orientation) {
            case 2:
                this.fort.mirrorX = true;
                break;
            case 3:
                this.fort.mirrorY = true;
                break;
            case 4:
                this.fort.mirrorX = true;
                this.fort.mirrorY = true;
                break;
            default:
                break;
        }

        this.walls = new ArrayList<>();

        Point.Double fortPosition = this.fort.getPosition();
        Point.Double position = new Point.Double();
        Dimension size = new Dimension(0, 0);
        position.setLocation(fortPosition);
        WallComponent newWall;

        // Building a pixel castle is hard work apparently
        newWall = new WallComponent(this.shared, this.game, this.fort, 6);
        newWall.getPosition().setLocation(position);
        size.width += newWall.getSize().width;
        position.x += newWall.getSize().width;
        this.walls.add(newWall);
        for (int i = 0; i < 6; i++) {
            newWall = new WallComponent(this.shared, this.game, this.fort, 1);
            newWall.getPosition().setLocation(position);
            size.width += newWall.getSize().width;
            position.x += newWall.getSize().width;
            this.walls.add(newWall);
        }

        newWall = new WallComponent(this.shared, this.game, this.fort, 7);
        newWall.getPosition().setLocation(position);
        size.width += newWall.getSize().width;
        size.height += newWall.getSize().height;
        position.y += newWall.getSize().height;
        this.walls.add(newWall);
        for (int i = 0; i < 6; i++) {
            newWall = new WallComponent(this.shared, this.game, this.fort, 2);
            newWall.getPosition().setLocation(position);
            size.height += newWall.getSize().height;
            position.y += newWall.getSize().height;
            this.walls.add(newWall);
        }

        newWall = new WallComponent(this.shared, this.game, this.fort, 5);
        newWall.getPosition().setLocation(position);
        size.height += newWall.getSize().height;
        position.x -= newWall.getSize().width;
        this.walls.add(newWall);
        for (int i = 0; i < 6; i++) {
            newWall = new WallComponent(this.shared, this.game, this.fort, 1);
            newWall.getPosition().setLocation(position);
            position.x -= newWall.getSize().width;
            this.walls.add(newWall);
        }

        newWall = new WallComponent(this.shared, this.game, this.fort, 4);
        newWall.getPosition().setLocation(position);
        position.y -= newWall.getSize().height;
        this.walls.add(newWall);
        for (int i = 0; i < 6; i++) {
            newWall = new WallComponent(this.shared, this.game, this.fort, 3);
            newWall.getPosition().setLocation(position);
            position.y -= newWall.getSize().height;
            this.walls.add(newWall);
        }

        this.warlord = new WarlordComponent(this.shared, this.game, this.fort); // creates a warlord
        this.warlord.getPosition().setLocation(
                fortPosition.x + (size.width / 2) - ((double) this.warlord.getSize().width) / 2,
                fortPosition.y + (size.height / 2) - ((double) this.warlord.getSize().height) / 2
        );


        this.shield = new ShieldComponent(this.shared, this.game, this.fort); // creates a shield

        this.shield.getPosition().setLocation(
                fortPosition.x + size.width * (this.fort.mirrorX ? 0 : 1) -
                        (((double) this.walls.get(15).getSize().width) /  2 +
                        ((double) this.shield.getSize().width) * (this.fort.mirrorX ? 0 : 1) / 2),
                fortPosition.y + size.height * (this.fort.mirrorY ? 0 : 1) -
                        (((double) this.walls.get(15).getSize().height) / 2 +
                        ((double) this.shield.getSize().height) * (this.fort.mirrorY ? 0 : 1) / 2)
        );

        double shieldWallDiffW = ((double) this.shield.getSize().width) / 2 -
                ((double) this.walls.get(15).getSize().width) / 2;
        double shieldWallDiffH = ((double) this.shield.getSize().height) / 2 -
                ((double) this.walls.get(15).getSize().height) / 2;

        size.width += shieldWallDiffW;
        size.height += shieldWallDiffH;
        fortPosition.setLocation(
                position.x + (this.fort.mirrorX ? -shieldWallDiffW : 0),
                position.y + (this.fort.mirrorY ? -shieldWallDiffH : 0)
        );
        this.fort.getSize().setSize(size);
    }

    /**
     * Contructor for fort
     * @param shared shared module controlling all scenes
     * @param game current game containing all services
     */
    public FortComponent(SharedModule shared, GameService game, Integer player,
                         Integer orientation, Point.Double position) {
        this.shared = shared;
        this.game = game;
        this.fort = new FortService(player);
        this.fort.getPosition().setLocation(position);
        this.setup(orientation);
    }

    /**
     * @param intervalS time between last frame and current so velocities stay consistent
     */
    public void updateObject(Double intervalS) {
        this.warlord.update(intervalS);
        this.walls.forEach((w) -> w.update(intervalS));
        this.shield.update(intervalS);

        if (warlord.isDead()) {
            this.fort.destroyed = true;
        }
    }

    /**
     * @return Set of physical components belonging to the fort.
     */
    public HashSet<Physical> getPhysicalComponents() {
        HashSet<Physical> components =  new HashSet<>();
        components.add(this.warlord);
        components.addAll(this.walls);
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
    public List<WallComponent> getWalls() {
        return this.walls;
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
