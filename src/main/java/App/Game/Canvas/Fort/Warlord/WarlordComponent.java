package App.Game.Canvas.Fort.Warlord;

/**
 * Created by pie on 30/03/17.
 */

import App.Game.Canvas.Ball.BallService;
import App.Game.GameService;
import App.Shared.SharedModule;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


/**
 * Created by pie on 28/03/17.
 */
public class WarlordComponent {
    private SharedModule shared;
    private Image image;
    private GameService game;


    public WarlordComponent(SharedModule shared, GameService game) {
        this.shared = shared;
        this.game = game;
        this.image = this.shared.getJFX().loadImage(
                this.getClass(), "WarlordComponent.png"
        );
    }

    public void checkCollisionWarlord(BallService ball) {
    }

    public void renderOnContext(GraphicsContext context) {
        context.drawImage(this.image, this.game.getWarlord().getXPos(), this.game.getWarlord().getYPos(),
                this.game.getWarlord().getSize(), this.game.getWarlord().getSize());
    }
}