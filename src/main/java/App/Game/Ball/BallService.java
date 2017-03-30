package App.Game.Ball;

import warlordstest.IBall;

import java.awt.*;

/**
 * Created by lichk on 28/03/2017.
 */
public class BallService implements IBall {
    private Point.Double position;
    private Point velocity;
    private Point size;

    public BallService() {
        this.position = new Point.Double(0, 0);
        this.velocity = new Point(10, 10);
        this.size = new Point(20, 20);
    }

    public Point.Double getPosition() {
        return this.position;
    }

    public Point getVelocity() {
        return this.velocity;
    }

    public Point getSize() { return this.size; }

    /***
     *  Set the horizontal position of the ball to the given value.
     * @param x
     */
    public void setXPos(int x) {
        this.position.x = x;
    }

    /***
     * Set the vertical position of the ball to the given value.
     * @param y
     */
    public void setYPos(int y) {
        this.position.y = y;
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
        this.velocity.x = dX;
    }

    /***
     *  Set the vertical velocity of the ball to the given value.
     * @param dY
     */
    public void setYVelocity(int dY) {
        this.velocity.y = dY;
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
