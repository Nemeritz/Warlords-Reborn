package App.Game.Fort.Warlord;

import java.awt.*;

/**
 * Created by Hanliang Ding(Chris) on 30/03/17.
 */
public class WarlordService {
    private Dimension size;
    private Point.Double position;
    public int hitScore;
    public int destroyScore;

    public WarlordService() {
        this.position = new Point.Double(0, 0);
        this.size = new Dimension(33,47);
        this.hitScore = 20;
        this.destroyScore = 100;
    }

    public Point.Double getPosition() {
        return this.position;
    }

    public Dimension getSize(){
        return this.size;
    }
}

