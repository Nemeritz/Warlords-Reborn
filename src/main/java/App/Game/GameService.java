package App.Game;

import App.Game.Timer.TimerService;

/**
 * Created by lichk on 27/03/2017.
 */
public class GameService {
    TimerService timer;

    public GameService() {
        this.timer = new TimerService();
    }

    public TimerService getTimer() {
        return timer;
    }
}
