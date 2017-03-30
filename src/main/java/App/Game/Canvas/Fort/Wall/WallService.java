package App.Game.Canvas.Fort.Wall;

import java.awt.*;

/**
 * Created by pie on 28/03/17.
 */
public class WallService {
    private Point.Double position;
    private Point.Double size;
    private Boolean Destroy;

    public WallService() {
        this.position = new Point.Double(0,0);
        this.Destroy = false;
        this.size = new Point.Double(0,0);
    }

    public double getSize() {
        return size.getX();
    }

    public void setDestroy (Boolean sDes) {
        this.Destroy = sDes;
    }

    public void setXpos(int x) {
        this.position.setLocation(x,this.position.getY());
    }

    public void setYpos(int y) {
        this.position.setLocation(this.position.getX(),y);
    }

    public double getXpos(){
        return this.position.getX();
    }

    public double getYpos() {
        return this.position.getY();
    }

    public boolean isDestroyed(){
        return Destroy;
    }
}
