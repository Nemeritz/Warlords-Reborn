package App.Game.Timer;

import javafx.animation.AnimationTimer;
import App.Shared.Observables.ObservableFrame;
/**
 * Created by lichk on 27/03/2017.
 */
public class TimerService extends AnimationTimer {
    private Long time;
    private ObservableFrame frame;

    public TimerService() {
        this.time = 18000L;
        this.frame = new ObservableFrame();
    }

    public void handle(long now) {
        this.time--;
        this.frame.increment();
    }

    //@Override
    //public void start(){

    //}

    public ObservableFrame getFrame() {
        return frame;
    }

    public Long currentTimeMs() {
        return time;
    }
}
