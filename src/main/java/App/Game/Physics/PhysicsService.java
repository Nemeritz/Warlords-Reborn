package App.Game.Physics;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by lichk on 31/03/2017.
 */
public class PhysicsService {
    private Dimension worldBounds;
    private Set<Physical> statics;
    private Set<Physical> kinetics;

    private Rectangle.Double getHitBox(Point.Double position, Dimension size) {
        return new Rectangle.Double(
                position.x, position.y,
                size.width, size.height
        );
    }

    public PhysicsService() {
        this.worldBounds = new Dimension();
        this.statics = new CopyOnWriteArraySet<>();
        this.kinetics = new CopyOnWriteArraySet<>();
    }

    public void setWorldBounds(Dimension d) {
        this.worldBounds.setSize(d);
    }

    public Set<Physical> getStatics() {
        return this.statics;
    }

    public Set<Physical> getKinetics() {
        return this.kinetics;
    }

    public void collisionCheck() {
        for (Physical k: this.kinetics) {
            Rectangle.Double kHitBox = this.getHitBox(
                    k.getPosition(), k.getSize()
            );

            for (Physical s : this.statics) {
                Rectangle.Double sHitBox = this.getHitBox(
                        s.getPosition(), s.getSize()
                );

                Rectangle2D intersection = kHitBox.createIntersection(sHitBox);

                // Check if there is an intersection.
                if (intersection.getHeight() > 0 && intersection.getWidth() > 0) {
                    Point.Double intersectionCenter = new Point.Double(
                            intersection.getCenterX(), intersection.getCenterY()
                    );
                    k.onCollision(
                            new Point.Double(kHitBox.getCenterX(), kHitBox.getCenterY()),
                            intersectionCenter,
                            s
                    );
                    s.onCollision(
                            new Point.Double(sHitBox.getCenterX(), sHitBox.getCenterY()),
                            intersectionCenter,
                            k
                    );
                }
            }

        }
    }
}
