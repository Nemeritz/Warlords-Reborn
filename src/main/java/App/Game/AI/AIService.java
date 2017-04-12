package App.Game.AI;

import App.Game.Ball.BallComponent;
import App.Game.Canvas.CanvasObject;
import App.Game.Fort.FortComponent;
import App.Game.Fort.Shield.ShieldComponent;
import App.Game.Fort.Warlord.WarlordComponent;
import App.Game.Loop.LooperChild;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * AI intelligence service to inherit player forts.
 * Created by Jerry Fan on 9/04/2017.
 */
public class AIService implements LooperChild, CanvasObject {
    public static boolean AI_DEBUG = false; // Enable for debug trace.

    boolean disabled;
    Rectangle.Double bounds;
    BallComponent ball;
    FortComponent fort;
    boolean cheese;

    private double distanceToTarget;
    private boolean lastDirection;
    private Point.Double intersection;
    private Line2D.Double ballTrajectory;

    /**
     * Returns the intersection point between two lines. Does not check if they really intersect.
     * @param line1 First line to check.
     * @param line2 Second line to check.
     * @return
     */
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

    /**
     * See if the AI service has all the needed objects to start working.
     */
    private void checkReady() {
        if (this.ball != null && this.fort != null && this.bounds != null) {
            this.disabled = false;
        }
    }

    public AIService() {
        this.disabled = true;
        this.distanceToTarget = Double.MAX_VALUE;
        this.lastDirection = true;
        this.cheese = false;
    }

    /**
     * @param ball The ball to be tracked by the AI.
     */
    public void trackBall(BallComponent ball) {
        this.ball = ball;
        this.checkReady();
    }

    /**
     * @param fort The fort to be inherited by the AI.
     */
    public void giveFort(FortComponent fort) {
        this.fort = fort;
        this.fort.setAIControl(true);
        this.checkReady();
    }

    /**
     * @param bounds AI needs to know where the edges of the game are.
     */
    public void setBounds(Rectangle.Double bounds) {
        this.bounds = bounds;
        this.checkReady();
    }

    /**
     * @param value Enable at your own risk.
     */
    public void setCheese(Boolean value) {
        this.cheese = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onGameLoop(Double intervalS) {
        if (!this.disabled && this.fort.isAIControlled()) {
            WarlordComponent warlord = this.fort.getWarlord();

            if (!this.fort.isDestroyed() || warlord.isGhost() || !this.ball.isInvisible()) {
                // AI is not cheating if this condition is true, so proceed with the processing.
                ShieldComponent shield = this.fort.getShield();

                // Start off with rail speed 0
                shield.setRailSpeed(0);

                Point.Double ballCenter = new Point.Double(
                        ball.getPosition().x + (double) ball.getSize().height / 2,
                        ball.getPosition().y + (double) ball.getSize().width / 2
                );

                // Check if the ball is getting closer
                double currentDistance = warlord.getPosition().distance(this.ball.getPosition());
                double nextDistance = warlord.getPosition().distance(
                        ballCenter.x + this.ball.getVelocity().x,
                        ballCenter.y + this.ball.getVelocity().y
                );

                Rectangle.Double shieldHitbox = new Rectangle.Double(
                        shield.getPosition().x, shield.getPosition().y,
                        shield.getSize().height, shield.getSize().width
                );

                Point.Double shieldCenter = new Point.Double(
                        shieldHitbox.getCenterX(),
                        shieldHitbox.getCenterY()
                );

                Rectangle.Double fortHitbox = new Rectangle.Double(
                        this.fort.getPosition().x, this.fort.getPosition().y,
                        this.fort.getSize().width, this.fort.getSize().height
                );

                if (currentDistance > nextDistance) {
                    // Ball is getting closer.
                    double dXToBounds = Math.abs(ballCenter.x - this.bounds.width * (this.ball
                            .getVelocity().x <
                            0 ? 0 : 1)) / this.ball.getVelocity().x;
                    double dYToBounds = Math.abs(ballCenter.y - this.bounds.height * (this.ball
                            .getVelocity().y <
                            0 ? 0 : 1)) / this.ball.getVelocity().y;


                    // Check the amount of steps until the ball collides with a bound.
                    Line2D.Double ballTrajectory;
                    double absDXToBounds = Math.abs(dXToBounds);
                    double absDYToBounds = Math.abs(dYToBounds);
                    if (absDXToBounds < absDYToBounds) {
                        double posY2 = ballCenter.y + absDXToBounds * this.ball.getVelocity().y;
                        // Hits X bounds first
                        if (dXToBounds < 0) {
                            // Hits left bound
                            ballTrajectory = new Line2D.Double(
                                        ballCenter.x, ballCenter.y,
                                        this.bounds.x, posY2
                                    );
                        }
                        else {
                            // Hits right bound
                            ballTrajectory = new Line2D.Double(
                                    ballCenter.x, ballCenter.y,
                                    this.bounds.width, posY2
                            );
                        }
                    }
                    else {
                        // Hits Y bounds first
                        double posX2 = ballCenter.x + absDYToBounds * this.ball.getVelocity().x;
                        if (dYToBounds < 0) {
                            // Hits top bound
                            ballTrajectory = new Line2D.Double(
                                    ballCenter.x, ballCenter.y,
                                    posX2, this.bounds.y
                            );
                        }
                        else {
                            // Hits bottom bound
                            ballTrajectory = new Line2D.Double(
                                    ballCenter.x, ballCenter.y,
                                    posX2, this.bounds.height
                            );
                        }
                    }

                    this.ballTrajectory = ballTrajectory;

                    // Check if fort is in the way
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
                                    fortHitbox.x + fortHitbox.width, fortHitbox.y + fortHitbox.height
                            );
                            if (bottomWall.intersectsLine(ballTrajectory)) {
                                intersectionY = this.getIntersect(bottomWall, ballTrajectory);
                            }
                        }

                        // If one intersection doesn't exist, take the other one.
                        if (intersectionX == null) {
                            intersection = intersectionY;
                        }
                        else if (intersectionY == null) {
                            intersection = intersectionX;
                        }
                        else {
                            // If both exist, check which one is closer to the ball.
                            if (intersectionX.distance(this.ball.getPosition()) <
                                    intersectionY.distance(this.ball.getPosition())) {
                                intersection = intersectionX;
                            }
                            else {
                                intersection = intersectionY;
                            }
                        }

                        if (intersection != null) {
                            // Intersection exists, use this slightly stupid way to move the shield so that it blocks
                            // the path.
                            this.intersection = intersection;

                            double distToIntersect = shieldCenter.distance(intersection);
                            if (distToIntersect > shieldHitbox.width / 2 + 2) {
                                // Tolerance of 2 units.
                                if (this.distanceToTarget < distToIntersect) {
                                    this.lastDirection = !this.lastDirection;
                                }

                                if (this.lastDirection) {
                                    shield.setRailSpeed(-400);
                                }
                                else {
                                    shield.setRailSpeed(400);
                                }
                            }
                            this.distanceToTarget = distToIntersect;
                        }
                    }

                }
                else {
                    // This is a dumb(er) version of the above algorithm to get the AI to move the paddle closer to the
                    // ball if there is no collision path between the ball and the AI fort. Surprisingly cheesy /
                    // effective.
                    if (this.cheese) {
                        if (!fortHitbox.contains(ballCenter)) {
                            double distToBall = shieldCenter.distance(ballCenter);
                            if (distToBall > this.ball.getSize().width / 2 + 2) {
                                if (this.distanceToTarget < distToBall) {
                                    this.lastDirection = !this.lastDirection;
                                }

                                if (this.lastDirection) {
                                    shield.setRailSpeed(-400);
                                } else {
                                    shield.setRailSpeed(400);
                                }
                            }
                            this.distanceToTarget = distToBall;
                        }
                    }
                }
            }
            else {
                this.disabled = true;
            }
        }
    }

    /**
     * Used for debug only.
     * {@inheritDoc}
     */
    @Override
    public void renderOnContext(GraphicsContext context) {
        if (!this.disabled) {
            if (this.intersection != null) {
                context.setFill(javafx.scene.paint.Color.BLUE);
                context.fillOval(this.intersection.x - 2, this.intersection.y - 2, 2, 2);

            }

            if (this.ballTrajectory != null) {
                context.setStroke(javafx.scene.paint.Color.BLUE);
                context.strokeLine(this.ballTrajectory.x1, this.ballTrajectory.y1,
                        this.ballTrajectory.x2, this.ballTrajectory.y2);
            }
        }
    }
}
