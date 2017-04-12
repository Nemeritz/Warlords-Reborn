package App.Game.Powerup;

import App.Game.Ball.BallComponent;
import App.Game.Canvas.CanvasObject;
import App.Game.GameModule;
import App.Game.Loop.LooperChild;
import App.Game.Physics.Physical;
import App.Shared.Interfaces.Disposable;
import App.Shared.SharedModule;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by lichk on 9/04/2017.
 */
public class PowerupComponent implements Physical, CanvasObject, Disposable, LooperChild {

    private MediaPlayer hitSound;
    private SharedModule shared;
    private GameModule game;
    private PowerupService model;
    private Image image;

    public PowerupComponent(SharedModule shared, GameModule game) {
        this.shared = shared;
        this.game = game;
        this.model = new PowerupService();

        this.hitSound = this.shared.getJFX().loadMedia(this.getClass(), "assets/impact.mp3");

        Power[] powerList = Power.values();
        this.model.setPower(
                Power.values()[ThreadLocalRandom.current().nextInt(0, powerList.length)]
        );

        int spawnPosition = ThreadLocalRandom.current().nextInt(0, 4);

        Rectangle.Double bounds = this.game.getPhysics().getWorldBounds();

        double modX = (double) this.model.getSize().width / 2;
        double modY = (double) this.model.getSize().height / 2 ;

        switch(spawnPosition) {
            case 0:
                this.model.getPosition().setLocation(
                        bounds.width / 2 - modX,
                        bounds.height / 4 - modY
                );
                break;
            case 1:
                this.model.getPosition().setLocation(
                        bounds.width * 3 / 4 - modX,
                        bounds.height / 2 - modY
                );
                break;
            case 2:
                this.model.getPosition().setLocation(
                        bounds.width / 2 - modX,
                        bounds.height * 3 / 4 - modY
                );
                break;
            case 3:
                this.model.getPosition().setLocation(
                        bounds.width / 4 - modX,
                        bounds.height / 2 - modY
                );
                break;
            default:
                this.model.getPosition().setLocation(
                        bounds.width / 2 - modX,
                        bounds.height / 2 - modY
                );
                break;
        }

        this.image = this.shared.getJFX().loadImage(
                this.getClass(), "assets/powerup-" + Integer.toString(this.model.getPower().number) + ".png"
        );

        this.game.getPhysics().getStatics().add(this);
        this.game.getCanvas().getCanvasObjects().add(this);
    }

    @Override
    public Point.Double getPosition() {
        return this.model.getPosition();
    }

    @Override
    public Dimension getSize() {
        return this.model.getSize();
    }

    public Power getPower() {
        return this.model.getPower();
    }

    @Override
    public void onCollision(Point.Double hitBoxCenter, Point.Double intersectionCenter, Physical object) {
        if (BallComponent.class.isInstance(object)) {
            this.hitSound.stop();
            this.hitSound.play();

            BallComponent ball = (BallComponent) object;
            ball.addPower(this.getPower());
            this.dispose();
        }
    }

    @Override
    public void renderOnContext(GraphicsContext context) {
        Point.Double position = this.model.getPosition();
        Dimension size = this.model.getSize();
        context.drawImage(this.image,
                position.x,
                position.y,
                size.width, size.height
        );
    }

    @Override
    public void onGameLoop(Double intervalS) {
        this.model.timeSinceSpawn += intervalS;

        if (this.model.timeSinceSpawn >= 20) {
            this.dispose();
        }
    }

    @Override
    public void dispose() {
        this.game.getPhysics().getStatics().remove(this);
        this.game.getCanvas().getCanvasObjects().remove(this);
        this.game.getLoop().getLoopers().remove(this);
    }
}
