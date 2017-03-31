package App.Game.Physics;

import java.awt.*;

/**
 * Created by lichk on 31/03/2017.
 */
public interface Physical {
    public Point.Double getPosition();

    public Dimension getSize();

    public void onCollision(Point.Double hitBoxCenter, Point.Double intersectionCenter, Physical object);
}
