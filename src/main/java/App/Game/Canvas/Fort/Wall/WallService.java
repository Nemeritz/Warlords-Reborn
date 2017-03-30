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
        this.position = new Point.Double(500,500);
        this.Destroy = false;
        this.size = new Point.Double(30,30);
    }

    public double getSize() {
        return size.getX();
    }

    public void setDestroy (Boolean sDes) {
        this.Destroy = sDes;
    }

    public void setXPos(int x) {
        this.position.setLocation(x,this.position.getY());
    }

    public void setYPos(int y) {
        this.position.setLocation(this.position.getX(),y);
    }

    public double getXPos(){
        return this.position.getX();
    }

    public double getYPos() {
        return this.position.getY();
    }

    public boolean isDestroyed(){
        return Destroy;
    }
}
