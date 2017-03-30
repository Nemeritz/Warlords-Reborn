package App.Game.Fort.Warlord;

import warlordstest.IWarlord;

import java.awt.*;

/**
 * Created by pie on 30/03/17.
 */
public class WarlordService {
    private Point size;
    private Point.Double position;
    boolean dead;
    boolean won;
    int player;

    public WarlordService() {
        this.position = new Point.Double(0, 0);
        this.dead = false;
        this.won = false;
        this.size = new Point(50,50);
    }

    public Point.Double getPosition() {
        return this.position;
    }

    public Point getSize(){
        return this.size;
    }
}

