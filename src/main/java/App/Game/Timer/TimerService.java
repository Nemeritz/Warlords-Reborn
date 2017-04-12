package App.Game.Timer;

import javafx.animation.AnimationTimer;
import App.Shared.Observables.ObservableFrame;
/**
 * The main timer for the game. Tracks times based on a timestamp method from the system.
 * Created by Jerry Fan on 27/03/2017.
 */
public class TimerService extends AnimationTimer {
    private Long startTime; // starting value of now, the time used in the animation timer
    private ObservableFrame frame; // current frame count
    private long time; // current elapsed time

    /**
     * Default constructor for time service
     */
    public TimerService() {
        this.time = 0L;
        this.frame = new ObservableFrame();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(long now) {
        if (startTime == null) {
            this.startTime = now; //find the starting value of now
        }
        this.time = now - this.startTime; //current time is current now - starting value of now
        this.frame.increment(); //increments the frame count
    }

    /**
     * Sets the time to a specified second
     * @param seconds Seconds.
     */
    public void setTimeS(Long seconds) {
        this.time = seconds * 1000000000;
    }

    /**
     * @return the current frame count
     */
    public ObservableFrame getFrame() {
        return frame;
    }

    /**
     * @return current elapsed time in ms
     */
    public long currentTimeMs() {
        return time / 1000000;
    }
}
