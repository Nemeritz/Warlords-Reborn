package App.Game.Fort.Warlord;

import warlordstest.IWarlord;

import java.awt.*;

/**
 * Created by pie on 30/03/17.
 */
public class WarlordService implements IWarlord {
    private Point size;
    private Point.Double position;
    private Boolean dead;
    private Boolean won;
    private Integer player;

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

    public void setDead (Boolean value) {
        if (value.equals(this.dead))
        this.dead = value;
    }

    public void setWon (Boolean value) {
        if (value.equals(this.won)) {
            this.won = value;
        }
    }

    public void setXPos(int x) {
        this.position.setLocation(x,this.position.getY());
    }

    public void setYPos(int y) {
        this.position.setLocation(this.position.getX(),y);
    }

<<<<<<< Updated upstream:src/main/java/App/Game/Canvas/Fort/Warlord/WarlordService.java
    public double getXPos(){
        return this.position.getX();
    }

    public double getYPos() {
        return this.position.getY();
=======
    public boolean hasWon(){
        return this.won;
    }

    public boolean isDead(){
        return this.dead;
>>>>>>> Stashed changes:src/main/java/App/Game/Fort/Warlord/WarlordService.java
    }

    public Integer getPlayer() {
        return this.player;
    }

    public void setPlayer(Integer value) {
        if (!value.equals(this.player)) {
            this.player = player;
        }
    }
}

