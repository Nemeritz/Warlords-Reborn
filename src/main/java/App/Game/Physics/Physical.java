package App.Game.Physics;

import java.awt.*;

/**
 * Interface for classes implementing physical interactions.
 * Created by Jerry Fan on 31/03/2017.
 */
public interface Physical {

    /**
     * @return Position of the object in units on the virtual game grid.
     */
    public Point.Double getPosition();

    /**
     * @return the dimensions of the object containing width and height in units
     */
    public Dimension getSize();

    /**
     * Method is called when a registered physical object collides with another registered physical object. Any sort
     * of reaction from collisions with other objects should be placed here.
     *
     * @param hitBoxCenter Center of the hit box for this object.
     * @param intersectionCenter The center of the collision box.
     * @param object The opposing object in the collision.
     */
    public void onCollision(Point.Double hitBoxCenter, Point.Double intersectionCenter, Physical object);
}
