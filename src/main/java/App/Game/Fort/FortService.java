package App.Game.Fort;

import java.awt.*;

/**
 * Abstract model for use in representing player owned forts. All fort models should be contained in one of these fort
 * models to associate with player based data.
 * Created by Jerry Fan on 31/03/2017.
 */
public class FortService {
    public int player;
    public boolean destroyed;
    public boolean winner;
    public boolean mirrorX;
    public boolean mirrorY;
    private Point.Double position;
    private Dimension size;

    /**
     * Constructor for FortService
     */
    public FortService(int player) {
        this.position = new Point.Double(0, 0);
        this.size = new Dimension(0, 0);
        this.player = player;
        this.winner = false;
        this.mirrorX = false;
        this.mirrorY = false;
        this.destroyed = false;
    }

    /**
     * @return Double position of the wall created, containing x and y coordinates 0,0 being top left
     */
    public Point.Double getPosition() {
        return this.position;
    }

    public Dimension getSize() {
        return this.size;
    }
}
