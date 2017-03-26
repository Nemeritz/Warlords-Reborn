package App.Shared.Observables;

import java.util.Observable;

/**
 * Created by lichk on 27/03/2017.
 */
public class ObservableFrame extends Observable {
    private long frame;

    public ObservableFrame() {
        this.frame = 0;
    }

    public long current() {
        return frame;
    }

    public void increment() {
        this.frame++;
        this.setChanged();
        this.notifyObservers();
    }
}
