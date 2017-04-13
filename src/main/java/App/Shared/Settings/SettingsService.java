package App.Shared.Settings;

/**
 * Created by Hanliang Ding on 09/04/2017.
 */
public class SettingsService {
	public double musicVolume;
	public double soundEffectsVolume;
	public boolean ghosting;
	public int maxGameTime;
	public boolean scoreWin;
	public int ballSpeed;
	public boolean powerups;
	//0 is empty, 1 is bot, 2 is bot (hard), 3 is player
	public int topLeft;
	public String topLeftName;
	public int topRight;
	public String topRightName;
	public int botLeft;
	public String botLeftName;
	public int botRight;
	public String botRightName;

	public SettingsService() {
		this.musicVolume = 1.0;
		this.soundEffectsVolume = 1.0;
		this.ghosting = false;
		this.maxGameTime = 120;
		this.scoreWin = true;
		this.ballSpeed = 200;
		this.powerups = false;
		this.topLeft = 0;
		this.topRight = 0;
		this.botLeft = 0;
		this.botRight = 0;
		this.topLeftName = "";
		this.topRightName = "";
		this.botLeftName = "";
		this.botRightName = "";
	}
}
