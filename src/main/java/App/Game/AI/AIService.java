package App.Game.AI;

import App.Game.Ball.BallComponent;
import App.Game.Canvas.CanvasObject;
import App.Game.Fort.FortComponent;
import App.Game.Fort.Shield.ShieldComponent;
import App.Game.Fort.Warlord.WarlordComponent;
import App.Game.Loop.LooperChild;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Created by lichk on 9/04/2017.
 */
public class AIService implements LooperChild, CanvasObject {
    boolean disabled;
    Rectangle.Double bounds;
    BallComponent ball;
    FortComponent fort;

    Point.Double intersection;
    Line2D.Double ballTrajectory;
    private double distanceToIntersection;
    private boolean lastDirection;

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
        this.intersection = null;
        this.ballTrajectory = null;
        this.distanceToIntersection = Double.MAX_VALUE;
        this.lastDirection = true;
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
                    double dXToBounds = Math.abs(this.ball.getPosition().x - this.bounds.width * (this.ball
                            .getVelocity().x <
                            0 ? 0 : 1)) / this.ball.getVelocity().x;
                    double dYToBounds = Math.abs(this.ball.getPosition().y - this.bounds.height * (this.ball
                            .getVelocity().y <
                            0 ? 0 : 1)) / this.ball.getVelocity().y;

                    Line2D.Double ballTrajectory;
                    double absDXToBounds = Math.abs(dXToBounds);
                    double absDYToBounds = Math.abs(dYToBounds);
                    if (absDXToBounds < absDYToBounds) {
                        double posY2 = this.ball.getPosition().y + absDXToBounds * this.ball.getVelocity().y;
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
                        double posX2 = this.ball.getPosition().x + absDYToBounds * this.ball.getVelocity().x;
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

                    this.ballTrajectory = ballTrajectory;
                    boolean intersectsFort = fortHitbox.intersectsLine(ballTrajectory);

                    if (intersectsFort) {
                        Point.Double intersection;
                        Point.Double intersectionX = null;
                        Point.Double intersectionY = null;
                        if (this.fort.getMirrorX()) {
                            // Check left wall
                            Line2D.Double leftWall = new Line2D.Double(
                                    fortHitbox.x, fortHitbox.y,
                                    fortHitbox.x , fortHitbox.y + fortHitbox.height
                            );
                            if (leftWall.intersectsLine(ballTrajectory)) {
                                intersectionX = this.getIntersect(leftWall, ballTrajectory);
                            }
                        }
                        else {
                            // Check right wall
                            Line2D.Double rightWall = new Line2D.Double(
                                    fortHitbox.x + fortHitbox.width, fortHitbox.y,
                                    fortHitbox.x + fortHitbox.width, fortHitbox.y + fortHitbox.height
                            );
                            if (rightWall.intersectsLine(ballTrajectory)) {
                                intersectionX = this.getIntersect(rightWall, ballTrajectory);
                            }
                        }

                        if (this.fort.getMirrorY()) {
                            // Check top wall
                            Line2D.Double topWall = new Line2D.Double(
                                    fortHitbox.x, fortHitbox.y,
                                    fortHitbox.x + fortHitbox.width, fortHitbox.y
                            );
                            if (topWall.intersectsLine(ballTrajectory)) {
                                intersectionY = this.getIntersect(topWall, ballTrajectory);
                            }
                        }
                        else {
                            // Check bottom wall
                            Line2D.Double bottomWall = new Line2D.Double(
                                    fortHitbox.x, fortHitbox.y + fortHitbox.height,
                                    fortHitbox.y + fortHitbox.width, fortHitbox.y + fortHitbox.height
                            );
                            if (bottomWall.intersectsLine(ballTrajectory)) {
                                intersectionY = this.getIntersect(bottomWall, ballTrajectory);
                            }
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
                            this.intersection = intersection;
                            Point.Double shieldCenter = new Point.Double(
                                    shieldHitbox.getCenterX(),
                                    shieldHitbox.getCenterY()
                            );

                            double distToIntersect = shieldCenter.distance(intersection);
                            if (distToIntersect > shieldHitbox.width / 2 + 0.5) {
                                if (this.distanceToIntersection < distToIntersect) {
                                    this.lastDirection = !this.lastDirection;
                                }

                                if (this.lastDirection) {
                                    shield.setRailSpeed(-400);
                                }
                                else {
                                    shield.setRailSpeed(400);
                                }
                            }
                            this.distanceToIntersection = distToIntersect;
                        }
                    }

                }
            }
            else {
                this.disabled = true;
            }
        }
    }

    @Override
    public void renderOnContext(GraphicsContext context) {
        if (!this.disabled) {
            if (this.intersection != null) {
                context.setFill(Color.BLUE);
                context.fillOval(this.intersection.x - 2, this.intersection.y - 2, 2, 2);

            }

            if (this.ballTrajectory != null) {
                context.setFill(Color.BLUE);
                context.strokeLine(this.ballTrajectory.x1, this.ballTrajectory.y1,
                        this.ballTrajectory.x2, this.ballTrajectory.y2);
            }
        }
    }
}
