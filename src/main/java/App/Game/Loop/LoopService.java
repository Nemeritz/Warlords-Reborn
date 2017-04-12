package App.Game.Loop;

import App.Game.Timer.TimerService;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Controls the game loop, watches the timer to loop on next tick.
 * Created by Jerry Fan on 9/04/2017.
 */
public class LoopService implements Observer {
    private TimerService timer;
    private Set<Looper> loopers;
    private long lastGameLoopTimeMs;
    private double schedulerInterval; // Target interval for each game loop.


    public LoopService() {
        this.lastGameLoopTimeMs = 0L;
        this.schedulerInterval = 1.0/60;
        this.loopers = new CopyOnWriteArraySet<>();
    }

    /**
     * Divide the intervals if they are larger than the scheduler intervals, and iteratively progress the game using
     * game loops of interval size + remainder. Dt must be small for collisions to process correctly.
     * @param intervalS Seconds to simulate the game loop for.
     */
    private void gameLoopScheduler(Double intervalS) {
        double remainder;

        if (intervalS > this.schedulerInterval) {
            int iterations = (int) Math.floor(intervalS / this.schedulerInterval);

            for (int i = 0; i < iterations; i++) {
                this.loopers.forEach((l) -> l.onGameLoop(this.schedulerInterval));
            }

            remainder = intervalS % this.schedulerInterval;
        }
        else {
            remainder = intervalS;
        }

        this.loopers.forEach((l) -> l.onGameLoop(remainder));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Observable obs, Object arg) {
        if (obs == this.timer.getFrame()) {
            long currentTimeMs = this.timer.currentTimeMs();

            long intervalMs = currentTimeMs - this.lastGameLoopTimeMs; // Calculate time since last game loop iteration.

            if (intervalMs > 0) {
                this.lastGameLoopTimeMs = currentTimeMs; // Immediately set current time as last iteration time
                this.gameLoopScheduler((double) intervalMs / 1000); // Runs every animation frame (optimally).
            }
        }
    }


    /**
     * @param timer Set the timer service to observe for ticks.
     */
    public void setMasterTimer(TimerService timer) {
        this.timer = timer;
        timer.getFrame().addObserver(this);
    }

    /**
     * @return A set of objects implementing the game loop.
     */
    public Set<Looper> getLoopers() {
        return this.loopers;
    }

    /**
     * Run game loop scheduler for an interval of 1 second. Used for testing only.
     */
    public void tick() {
        this.gameLoopScheduler(1.0);
    }
}
