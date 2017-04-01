package App.Game.Timer;

import javafx.animation.AnimationTimer;
import App.Shared.Observables.ObservableFrame;
/**
 * Created by lichk on 27/03/2017.
 */
public class TimerService extends AnimationTimer {
    private Long startTime;//starting value of now, the time used in the animation timer
    private Long time;//current elapsed time
    private ObservableFrame frame;//current frame count

    /**
     * default constructor for time service
     */
    public TimerService() {
        this.time = 0L;
        this.frame = new ObservableFrame();
    }

    /**
     * @param now refreshes and increments every nanosecond
     */
    public void handle(long now) {
        if (startTime == null) {
            this.startTime = now;//find the starting value of now
        }
        this.time = now - this.startTime;//current time is current now - starting value of now
        this.frame.increment();//increments the frame count
    }

    /**
     * @return the current frame count
     */
    public ObservableFrame getFrame() {
        return frame;
    }

    /**
     * @return current ellapsed time in ms
     */
    public Long currentTimeMs() {
        return time / 1000000;
    }
}
