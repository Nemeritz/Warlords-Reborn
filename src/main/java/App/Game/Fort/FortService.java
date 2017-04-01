package App.Game.Fort;

/**
 * Abstract model for use in representing player owned forts. All fort models should be contained in one of these fort
 * models to associate with player based data.
 * Created by Jerry Fan on 31/03/2017.
 */
public class FortService {
    public int player;
    public boolean destroyed;
    public boolean winner;

    /**
     * constructor for fortServices, initialize won to false
     */
    public FortService(int player) {
        this.player = player;
        this.winner = false;
        this.destroyed = false;
    }


}
