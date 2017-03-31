package App.Game.Timer;

import javafx.animation.AnimationTimer;
import App.Shared.Observables.ObservableFrame;
/**
 * Created by lichk on 27/03/2017.
 */
public class TimerService extends AnimationTimer {
    private Long startTime;
    private long time;
    private ObservableFrame frame;

    public TimerService() {
        this.time = 0L;
        this.frame = new ObservableFrame();
    }

    public void handle(long now) {
        if (startTime == null) {
            this.startTime = now;
        }
        this.time = now - this.startTime;
        this.frame.increment();
    }

    public void setTimeS(Long seconds) {
        this.time = seconds * 1000000000;
    }

    public ObservableFrame getFrame() {
        return frame;
    }

    public long currentTimeMs() {
        return time / 1000000;
    }
}
