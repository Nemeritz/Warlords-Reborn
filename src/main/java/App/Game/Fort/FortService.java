package App.Game.Fort;

/**
 * Abstract model for use in representing player owned forts. All fort models should be contained in one of these fort
 * models to associate with player based data.
 * Created by Jerry Fan on 31/03/2017.
 */
public class FortService {
    int player;
    boolean won;

    public FortService(int player) {
        this.player = player;
        this.won = false;
    }


}
