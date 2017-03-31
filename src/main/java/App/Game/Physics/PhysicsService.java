package App.Game.Physics;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Created by lichk on 31/03/2017.
 */
public class PhysicsService {
    private Dimension worldBounds;
    private ArrayList<Destructible> destructibles;
    private ArrayList<Destructor> destructors;

    private Rectangle.Double getHitBox(Point.Double position, Dimension size) {
        return new Rectangle.Double(
                position.x, position.y,
                size.width, size.height
        );
    }

    public PhysicsService() {
        this.worldBounds = new Dimension();
    }

    public void setWorldBounds(Dimension d) {
        this.worldBounds.setSize(d);
    }

    public void collisionCheck() {
        for (Destructor destructor: this.destructors) {
            Rectangle.Double destructorHitBox = this.getHitBox(
                    destructor.getPosition(), destructor.getSize()
            );

            for (Destructible destructible : this.destructibles) {
                Rectangle.Double destructibleHitBox = this.getHitBox(
                        destructible.getPosition(), destructible.getSize()
                );

                Rectangle2D intersection = destructorHitBox.createIntersection(destructibleHitBox);
                System.out.print(intersection.isEmpty());
                if (intersection.getHeight() > 0 && intersection.getWidth() > 0) {
                    destructor.collisionAction(
                            new Point.Double(destructorHitBox.getCenterX(), destructorHitBox.getCenterY()),
                            new Point.Double(intersection.getCenterX(), intersection.getCenterY())
                    );
                    destructible.setDestroyed(true);
                }
            }

        }
    }
}
