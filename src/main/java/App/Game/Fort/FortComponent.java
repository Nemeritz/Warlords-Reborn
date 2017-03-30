package App.Game.Fort;

import App.Game.Fort.Shield.ShieldComponent;
import App.Game.Fort.Wall.WallComponent;
import App.Game.Fort.Warlord.WarlordComponent;
import App.Game.GameService;
import App.Shared.SharedModule;
import javafx.scene.canvas.GraphicsContext;

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

    public FortComponent(SharedModule shared, GameService game, Integer player) {
        this.shared = shared;
        this.game = game;
        this.model = new FortService(player);

        this.warlord = new WarlordComponent(this.shared, this.game);
        this.wall = new WallComponent(this.shared, this.game);
        this.shield = new ShieldComponent(this.shared, this.game);
    }

    public Integer getPlayer() {
        return this.model.player;
    }

    public void setPlayer(Integer value) {
        if (!value.equals(this.model.player)) {
            this.model.player = value;
        }
    }

    public WarlordComponent getWarlord() {
        return this.warlord;
    }

    public WallComponent getWall() {
        return this.wall;
    }

    public ShieldComponent getShield() {
        return this.shield;
    }

    public Boolean getWon() {
        return this.model.won;
    }

    public void setWon(Boolean value) {
        if (!value.equals(this.model.won)) {
            this.model.won = value;
            this.warlord.setWon(value);
        }
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
}
