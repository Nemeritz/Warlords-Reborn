package App.Shared.Settings;

/**
 * Created by Hanliang Ding on 09/04/2017.
 */
public class SettingsService {
	public double musicVolume;
	public double soundEffectsVolume;
	public boolean ghosting;
	public double maxGameTime;
	public boolean scoreWin;
	public int ballSpeed;
	public boolean powerups;
	public int topLeft;
	public int topRight;
	public int botLeft;
	public int botRight;

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
	}
}
