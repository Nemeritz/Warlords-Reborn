package App.Game.AI;

import App.Game.Ball.BallComponent;
import App.Game.Fort.FortComponent;
import App.Game.Fort.Warlord.WarlordComponent;
import App.Game.Loop.Looper;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Created by lichk on 9/04/2017.
 */
public class AIService implements Looper {
    boolean disabled;
    Dimension bounds;
    BallComponent ball;
    FortComponent fort;

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
        this.checkReady();
    }

    public void setBounds(Dimension bounds) {
        this.bounds = bounds;
        this.checkReady();
    }

    @Override
    public void onGameLoop(Double intervalS) {
        if (!this.disabled) {
            if (!this.fort.isDestroyed()) {
                WarlordComponent warlord = this.fort.getWarlord();

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

                    Line2D.Double ballTrajectory = new Line2D.Double();
                    if (Math.abs(dXToBounds) < Math.abs(dYToBounds)) {
                        // Hits X bounds first
                        if (dXToBounds < 0) {
                            // Hits left bound
                        }
                        else {
                            // Hits right bound
                        }
                    }
                    else {
                        // Hits Y bounds first
                        if (dYToBounds < 0) {
                            // Hits top bound
                        }
                        else {
                            // Hits bottom bound
                        }
                    }

                    // Move shield to block

                }


            }
            else {
                this.disabled = true;
            }
        }
    }
}
