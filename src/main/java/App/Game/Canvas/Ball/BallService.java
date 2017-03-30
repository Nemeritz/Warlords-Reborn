package App.Game.Canvas.Ball;

import warlordstest.IBall;

import java.awt.*;

/**
 * Created by lichk on 28/03/2017.
 */
public class BallService implements IBall {
    private Point position;
    private Point velocity;

    public BallService() {
        this.position = new Point(0, 0);
        this.velocity = new Point(0, 0);
    }

    public Point getPosition() {
        return this.position;
    }

    public Point getVelocity() {
        return this.velocity;
    }

    /***
     *  Set the horizontal position of the ball to the given value.
     * @param x
     */
    public void setXPos(int x) {
        this.position.setLocation(x, this.position.getY());
    }

    /***
     * Set the vertical position of the ball to the given value.
     * @param y
     */
    public void setYPos(int y) {
        this.position.setLocation(this.position.getX(), y);
    }

    /***
     * @return the horizontal position of the ball.
     */
    public int getXPos() {
        return ((int) this.position.getX());
    }

    /***
     * @return the vertical position of the ball.
     */
    public int getYPos() {
        return ((int) this.position.getY());
    }

    /***
     *  Set the horizontal velocity of the ball to the given value.
     * @param dX
     */
    public void setXVelocity(int dX) {
        this.velocity.setLocation(dX, this.velocity.getY());
    }

    /***
     *  Set the vertical velocity of the ball to the given value.
     * @param dY
     */
    public void setYVelocity(int dY) {
        this.velocity.setLocation(this.velocity.getX(), dY);
    };

    /***
     * @return the horizontal velocity of the ball.
     */
    public int getXVelocity() {
        return ((int) this.velocity.getX());
    }

    /***
     * @return the vertical velocity of the ball.
     */
    public int getYVelocity() {
        return ((int) this.velocity.getY());
    }
}