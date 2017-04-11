package App.Game.AI;

import App.Game.Ball.BallComponent;
import App.Game.Fort.FortComponent;
import App.Game.Fort.Shield.ShieldComponent;
import App.Game.Fort.Warlord.WarlordComponent;
import App.Game.Loop.LooperChild;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Created by lichk on 9/04/2017.
 */
public class AIService implements LooperChild {
    boolean disabled;
    Rectangle.Double bounds;
    BallComponent ball;
    FortComponent fort;

    private Point.Double getIntersect(Line2D.Double line1, Line2D.Double line2) {
        // Construct line equations ax + by = c.
        double a1 = line1.y1 - line1.y2;
        double a2 = line2.y1 - line2.y2;
        double b1 = line1.x2 - line1.x1;
        double b2 = line2.x2 - line2.x1;
        double c1 = -(line1.x1 * line1.y2 - line1.x2 * line1.y1);
        double c2 = -(line2.x1 * line2.y2 - line2.x2 * line2.y1);

        // Use line intersection algorithm to determine intersection point.
        double delta = a1 * b2 - a2 * b1;

        if (!(delta == 0)) {
            return new Point.Double(
                    (b2 * c1 - b1 * c2) / delta,
                    (a1 * c2 - a2 * c1) / delta
            );
        }
        return null;
    }

    private void checkReady() {
        if (this.ball != null && this.fort != null && this.bounds != null) {
            this.disabled = false;
        }
    }

    public AIService() {
        this.disabled = true;
    }

    public void trackBall(BallComponent ball) {
        this.ball = ball;
        this.checkReady();
    }

    public void giveFort(FortComponent fort) {
        this.fort = fort;
        this.fort.setAIControl(true);
        this.checkReady();
    }

    public void setBounds(Rectangle.Double bounds) {
        this.bounds = bounds;
        this.checkReady();
    }

    @Override
    public void onGameLoop(Double intervalS) {
        if (!this.disabled && this.fort.isAIControlled()) {
            WarlordComponent warlord = this.fort.getWarlord();

            if (!this.fort.isDestroyed() || warlord.isGhost()) {
                ShieldComponent shield = this.fort.getShield();

                // Start off with rail speed 0
                shield.setRailSpeed(0);

                // Check if the ball is getting closer
                double currentDistance = warlord.getPosition().distance(this.ball.getPosition());
                double nextDistance = warlord.getPosition().distance(
                        this.ball.getPosition().x + this.ball.getVelocity().x,
                        this.ball.getPosition().y + this.ball.getVelocity().y
                );

                if (currentDistance > nextDistance) {
                    double dXToBounds = (this.bounds.width * (this.ball.getVelocity().x < 0 ? 0 : 1) -
                            this.ball.getPosition().x) / this.ball.getVelocity().x;
                    double dYToBounds = (this.bounds.height * (this.ball.getVelocity().y < 0 ? 0 : 1) -
                            this.ball.getPosition().y) / this.ball.getVelocity().y;

                    Line2D.Double ballTrajectory;
                    if (Math.abs(dXToBounds) < Math.abs(dYToBounds)) {
                        double posY2 = this.ball.getPosition().y + dXToBounds * this.ball.getVelocity().y;
                        // Hits X bounds first
                        if (dXToBounds < 0) {
                            // Hits left bound
                            ballTrajectory = new Line2D.Double(
                                        this.ball.getPosition().x, this.ball.getPosition().y,
                                        this.bounds.x, posY2
                                    );
                        }
                        else {
                            // Hits right bound
                            ballTrajectory = new Line2D.Double(
                                    this.ball.getPosition().x, this.ball.getPosition().y,
                                    this.bounds.width, posY2
                            );
                        }
                    }
                    else {
                        // Hits Y bounds first
                        double posX2 = this.ball.getPosition().x + dYToBounds * this.ball.getVelocity().x;
                        if (dYToBounds < 0) {
                            // Hits top bound
                            ballTrajectory = new Line2D.Double(
                                    this.ball.getPosition().x, this.ball.getPosition().y,
                                    posX2, this.bounds.y
                            );
                        }
                        else {
                            // Hits bottom bound
                            ballTrajectory = new Line2D.Double(
                                    this.ball.getPosition().x, this.ball.getPosition().y,
                                    posX2, this.bounds.height
                            );
                        }
                    }

                    // Check if fort is in the way
                    Rectangle.Double fortHitbox = new Rectangle.Double(
                            this.fort.getPosition().x, this.fort.getPosition().y,
                            this.fort.getSize().width, this.fort.getSize().height
                    );

                    Rectangle.Double shieldHitbox = new Rectangle.Double(
                            shield.getPosition().x, shield.getPosition().y,
                            shield.getSize().height, shield.getSize().width
                    );

                    boolean intersectsFort = fortHitbox.intersectsLine(ballTrajectory);

                    if (intersectsFort) {
                        Point.Double intersection = null;
                        Point.Double intersectionX;
                        Point.Double intersectionY;

                        if (this.fort.getMirrorX()) {
                            // Check left wall
                            intersectionX = this.getIntersect(new Line2D.Double(
                                    fortHitbox.x, fortHitbox.y,
                                    fortHitbox.x, fortHitbox.height
                            ), ballTrajectory);
                        }
                        else {
                            // Check right wall
                            intersectionX = this.getIntersect(new Line2D.Double(
                                    fortHitbox.width, fortHitbox.y,
                                    fortHitbox.width, fortHitbox.height
                            ), ballTrajectory);
                        }

                        if (this.fort.getMirrorY()) {
                            // Check top wall
                            intersectionY = this.getIntersect(new Line2D.Double(
                                    fortHitbox.x, fortHitbox.y,
                                    fortHitbox.width, fortHitbox.y
                            ), ballTrajectory);
                        }
                        else {
                            // Check bottom wall
                            intersectionY = this.getIntersect(new Line2D.Double(
                                    fortHitbox.x, fortHitbox.y,
                                    fortHitbox.width, fortHitbox.y
                            ), ballTrajectory);
                        }

                        if (intersectionX == null) {
                            intersection = intersectionY;
                        }
                        else if (intersectionY == null) {
                            intersection = intersectionX;
                        }
                        else {
                            if (intersectionX.distance(this.ball.getPosition()) <
                                    intersectionY.distance(this.ball.getPosition())) {
                                intersection = intersectionX;
                            }
                            else {
                                intersection = intersectionY;
                            }
                        }

                        if (intersection != null) {
                            Point.Double shieldCenter = new Point.Double(
                                    shieldHitbox.getCenterX(),
                                    shieldHitbox.getCenterY()
                            );

                            if (shieldCenter.distance(intersection) > shieldHitbox.width / 4) {
                                // TODO: Determine where the intersection point is relative to paddle.
                                if (intersection.y < shieldCenter.y || intersection.x < shieldCenter.x) {
                                    shield.setRailSpeed(-400);
                                }
                                else {
                                    shield.setRailSpeed(400);
                                }
                            }
                        }
                    }

                }
            }
            else {
                this.disabled = true;
            }
        }
    }
}
