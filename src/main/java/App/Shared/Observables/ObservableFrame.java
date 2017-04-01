package App.Shared.Observables;

import java.util.Observable;

/**
 * Created by lichk on 27/03/2017.
 */
public class ObservableFrame extends Observable {
    private long frame;

    /**
     * Default constructor for observable frame, initializes frame to 1
     */
    public ObservableFrame() {
        this.frame = 0;
    }

    /**
     * @return current frame number
     */
    public long current() {
        return frame;
    }

    /**
     * increments the frame count and notify all observers of this frame change
     */
    public void increment() {
        this.frame++;
        this.setChanged();
        this.notifyObservers();
    }
}
