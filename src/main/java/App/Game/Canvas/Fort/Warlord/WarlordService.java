package App.Game.Canvas.Fort.Warlord;

import java.awt.*;

/**
 * Created by pie on 30/03/17.
 */
public class WarlordService {
    private Point.Double size;
    private Point.Double position;
    private Boolean dead;
    private Boolean won;

    public WarlordService() {
        this.position = new Point.Double(0, 0);
        this.dead = false;
        this.won = false;
        this.size = new Point.Double(50,50);
    }

    public double getSize(){ return this.size.getX(); }

    public void setDead (Boolean sDead) {
        this.dead = sDead;
    }

    public void setWon (Boolean sWon) {
        this.won = sWon;
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

    public boolean hasWon(){
        return won;
    }

    public boolean isDead(){
        return dead;
    }
}

