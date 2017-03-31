package App.Game.Physics;

import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by lichk on 31/03/2017.
 */
public class PhysicsService {
    private Rectangle.Double worldBounds;
    private Set<Physical> statics;
    private Set<Physical> kinetics;

    private Rectangle.Double getHitBox(Point.Double position, Dimension size) {
        return new Rectangle.Double(
                position.x, position.y,
                size.width, size.height
        );
    }

    private void collisionCheck() {
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

    private void boundsCheck() {
        for (Physical k: this.kinetics) {
            Rectangle.Double kHitBox = this.getHitBox(
                    k.getPosition(), k.getSize()
            );

            Rectangle2D union = this.worldBounds.createUnion(kHitBox);

            if (!union.equals(worldBounds)) {
                Rectangle.Double intersection = new Rectangle.Double();

                if (union.getX() < 0) {
                    intersection.x = union.getX();
                    intersection.width = -union.getX();
                }
                else if (union.getWidth() > this.worldBounds.width) {
                    intersection.x = this.worldBounds.width;
                    intersection.width = union.getWidth() - this.worldBounds.width;
                }
                else {
                    intersection.x = kHitBox.x;
                    intersection.width = kHitBox.width;
                }

                if (union.getY() < 0) {
                    intersection.y = union.getY();
                    intersection.height = -union.getY();
                }
                else if (union.getHeight() > this.worldBounds.height) {
                    intersection.y = this.worldBounds.height;
                    intersection.height = union.getWidth() - this.worldBounds.height;
                }
                else {
                    intersection.y = kHitBox.y;
                    intersection.height = kHitBox.height;
                }

                k.onCollision(
                        new Point.Double(kHitBox.getCenterX(), kHitBox.getCenterY()),
                        new Point.Double(intersection.getCenterX(), intersection.getCenterY()),
                        null
                );
            }
        }
    }

    public PhysicsService() {
        this.worldBounds = new Rectangle.Double();
        this.statics = new CopyOnWriteArraySet<>();
        this.kinetics = new CopyOnWriteArraySet<>();
    }

    public void setWorldBounds(Dimension d) {
        this.worldBounds.setRect(0, 0, d.width, d.height);
    }

    public Set<Physical> getStatics() {
        return this.statics;
    }

    public Set<Physical> getKinetics() {
        return this.kinetics;
    }

    public void check() {
        this.boundsCheck();
        this.collisionCheck();
    }
}
