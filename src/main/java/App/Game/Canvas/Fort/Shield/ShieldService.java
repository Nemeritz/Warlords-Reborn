package App.Game.Canvas.Fort.Shield;

import warlordstest.IPaddle;

import java.awt.*;

/**
 * Created by lichk on 30/03/2017.
 */
public class ShieldService implements IPaddle {
    private Point.Double position;
    private Point velocity;
    private Point size;

    public ShieldService() {
        this.position = new Point.Double(100, 100);
        this.velocity = new Point(0, 0);
        this.size = new Point(200, 40);
    }

    public Point.Double getPosition() {
        return this.position;
    }

    public Point getVelocity() {
        return this.velocity;
    }

    public Point getSize() { return this.size; }

    /***
     *  Set the horizontal position of the paddle to the given value.
     * @param x
     */
    public void setXPos(int x) {
        this.position.x = x;
    };

    /***
     *  Set the vertical position of the paddle to the given value.
     * @param y
     */
    public void setYPos(int y) {
        this.position.y = y;
    };
}
