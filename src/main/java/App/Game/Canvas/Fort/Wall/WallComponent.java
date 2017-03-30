package App.Game.Canvas.Fort.Wall;

import App.Game.GameService;
import App.Shared.SharedModule;
import App.Game.Canvas.Ball.BallService;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


/**
 * Created by pie on 28/03/17.
 */
public class WallComponent {
    private SharedModule shared;
    private Image image;
    private GameService game;


    public WallComponent(SharedModule shared, GameService game) {
        this.shared = shared;
        this.game = game;
        this.image = this.shared.getJFX().loadImage(
                this.getClass(), "WallComponent.png"
        );
    }

    public void checkCollisionWall(BallService ball) {

    }

    public void renderOnContext(GraphicsContext context) {
        context.drawImage(this.image, this.game.getWall().getXpos(), this.game.getWall().getYpos(),
                this.game.getWall().getSize(), this.game.getWall().getSize());
    }
}
