package App.Shared.Observables;

import java.awt.*;
import java.util.Observable;

/**
 * Created by lichk on 28/03/2017.
 */
public class ObservablePoint extends Observable {
    private Point point;

    public ObservablePoint() {
        this.point = new Point(0, 0);
    }

    public ObservablePoint(int x, int y) {
        this.point = new Point(x, y);
    }

    public Point current() {
        return this.point;
    }

    public void setPoint(int x, int y) {
        this.point.setLocation(x, y);
        this.setChanged();
        this.notifyObservers();
    }
}
