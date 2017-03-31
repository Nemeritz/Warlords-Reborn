package App.Game.Fort.Shield;

import App.Game.GameService;
import App.Shared.SharedModule;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import warlordstest.IPaddle;
import javafx.scene.Node;

import java.awt.*;
import javafx.event.*;

import javax.swing.plaf.basic.BasicSplitPaneUI;

/**
 * Created by lichk on 30/03/2017.
 */
public class ShieldComponent implements IPaddle {
    private SharedModule shared;
    private GameService game;
    private Image image;
    private ShieldService model;
    public boolean leftIsPressed;//indicates if left arrow is pressed
    public boolean rightIsPressed;//indicates if right arrow is pressed

    public ShieldComponent(SharedModule shared, GameService game) {
        this.leftIsPressed = false;
        this.rightIsPressed = false;
        this.shared = shared;
        this.game = game;
        this.image = this.shared.getJFX().loadImage(
                this.getClass(), "barrier_shield.png"
        );
        this.model = new ShieldService();
    }

    public void update(Double intervalS) {
        Point.Double position = this.model.getPosition();
        Point velocity = this.model.getVelocity();

        EventHandler<KeyEvent> keyEvent = new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {//when listener on Key triggers event
                if (event.getCode() == KeyCode.LEFT && event.getEventType() == KeyEvent.KEY_PRESSED) {
                    leftIsPressed = true;//if left arrow key is pressed
                }
                else if (event.getCode() == KeyCode.LEFT && event.getEventType() == KeyEvent.KEY_RELEASED) {
                    leftIsPressed = false;//if left arrow key is released
                }
                else if (event.getCode() == KeyCode.RIGHT && event.getEventType() == KeyEvent.KEY_PRESSED) {
                    rightIsPressed = true;//if right arrow key is pressed
                }
                else if (event.getCode() == KeyCode.RIGHT && event.getEventType() == KeyEvent.KEY_RELEASED) {
                    rightIsPressed = false;//if right arrow key is released
                }
            }
        };
        if(this.leftIsPressed == true){

            position.setLocation(position.getX() - velocity.getX() * intervalS, position.getY());//moves slider to the left
        }
        else if(this.rightIsPressed == true){
            position.setLocation(position.getX() + velocity.getX() * intervalS, position.getY());//moves slider to the right
        }
        this.shared.getJFX().getScene("game").getValue()
                .addEventHandler(KeyEvent.KEY_PRESSED,keyEvent);//adds event handler to the game scene on keys pressed
        this.shared.getJFX().getScene("game").getValue()
                .addEventHandler(KeyEvent.KEY_RELEASED,keyEvent);//adds event handler to the game scene on keys released
    }

    public void renderOnContext(GraphicsContext context) {
        Point.Double position = this.model.getPosition();
        Dimension size = this.model.getSize();
        context.drawImage(this.image,
                position.x,
                position.y,
                size.width, size.height
        );
    }

    public Point.Double getPosition() {
        return this.model.getPosition();
    }

    public Point getVelocity() {
        return this.model.getVelocity();
    }

    public Dimension getSize() { return this.model.getSize(); }

    /***
     *  Set the horizontal position of the paddle to the given value.
     * @param x
     */
    public void setXPos(int x) {
        this.model.getPosition().x = x;
    };

    /***
     *  Set the vertical position of the paddle to the given value.
     * @param y
     */
    public void setYPos(int y) {
        this.model.getPosition().y = y;
    };
}
