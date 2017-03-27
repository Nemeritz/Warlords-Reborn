package App.Game;

import App.Game.Canvas.CanvasComponent;
import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.text.DecimalFormat;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by lichk on 21/03/2017.
 */
public class GameComponent extends BorderPane implements Observer{
    private SharedModule shared;
    private GameService game;

    @FXML
    Text gameTime;

//    private TimerComponent timer;
    private CanvasComponent canvas;

    private boolean started;

    private void gameLoop() {
        Double timeS = new Double(this.game.getTimer().currentTimeMs());
        int min = timeS.intValue() / 6000;
        int sec = (timeS.intValue() / 100) % 60;
        int msec = timeS.intValue() % 100;
	if (msec<10){
	    this.gameTime.setText(
	        Integer.toString(min)+":"+Integer.toString(sec)+":"+"0"+Integer.toString(msec)
	    );
	}
	else {
            this.gameTime.setText(
                Integer.toString(min)+":"+Integer.toString(sec)+":"+Integer.toString(msec)
            );
	}
    }

    public GameComponent(SharedModule shared) {
        this.shared = shared;
        this.game = new GameService();
        this.canvas = new CanvasComponent(this.shared);
//        this.timer = new TimerComponent(this.shared, this.game);
        this.shared.getJFX().loadFXML(this, GameComponent.class,
                "GameComponent.fxml");
        this.setCenter(canvas);
    }

    public void update(Observable obs, Object obj) {
        if (obs == this.game.getTimer().getFrame()) {
            if (this.game.getTimer().currentTimeMs() > 3000) {
                if (!started) {
                    this.started = true;
                    this.canvas.renderGameObjects();
                }
                this.gameLoop();
            }
        }
    }

    public void startGameCountdown() {
        this.started = false;
        this.game.getTimer().getFrame().addObserver(this);
        this.game.getTimer().start();
    }
}
