package App.Game.Fort.Warlord;

/**
 * Created by pie on 30/03/17.
 */

import App.Game.Canvas.Ball.BallService;
import App.Game.GameService;
import App.Shared.SharedModule;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;


/**
 * Created by pie on 28/03/17.
 */
public class WarlordComponent {
    private SharedModule shared;
    private Image image;
    private GameService game;
    private WarlordService model;


    public WarlordComponent(SharedModule shared, GameService game) {
        this.shared = shared;
        this.game = game;
        this.image = this.shared.getJFX().loadImage(
                this.getClass(), "WarlordComponent.png"
        );
        this.model = new WarlordService();
    }

    public void update(Double intervalS) {

    }

    public void checkCollisionWarlord(BallService ball) {
    }

    public void renderOnContext(GraphicsContext context) {
<<<<<<< Updated upstream:src/main/java/App/Game/Canvas/Fort/Warlord/WarlordComponent.java
        context.drawImage(this.image, this.game.getWarlord().getXPos(), this.game.getWarlord().getYPos(),
                this.game.getWarlord().getSize(), this.game.getWarlord().getSize());
=======
        Point.Double position = this.model.getPosition();
        Point size = this.model.getSize();
        context.drawImage(this.image,
                position.getX() - size.getX() / 2,
                position.getY() - size.getY() / 2,
                size.getX(), size.getY()
        );
>>>>>>> Stashed changes:src/main/java/App/Game/Fort/Warlord/WarlordComponent.java
    }
}