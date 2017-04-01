package App.Shared.Observables;

import java.awt.*;
import java.util.Observable;

/**
 * Created by Jerry Fan on 28/03/2017.
 */
public class ObservablePoint extends Observable {
    private Point point;

    /**
     * Default constructor for observable point initializing the point variable to 0,0
     */
    public ObservablePoint() {
        this.point = new Point(0, 0);
    }

    /**
     * @param x initializes point variable's x value
     * @param y initializes point variable's y value
     */
    public ObservablePoint(int x, int y) {
        this.point = new Point(x, y);
    }

    /**
     * @return the current point
     */
    public Point current() {
        return this.point;
    }

    /**
     * @param x x coordinate of the new point
     * @param y y coordinate of the new point
     *          notify observers of a location change
     */
    public void setPoint(int x, int y) {
        this.point.setLocation(x, y);
        this.setChanged();
        this.notifyObservers();
    }
}
