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
	public boolean topLeftAI;
	public boolean topRightAI;
	public boolean botLeftAI;
	public boolean botRightAI;

	public SettingsService() {
		this.musicVolume = 1.0;
		this.soundEffectsVolume = 1.0;
		this.ghosting = false;
		this.maxGameTime = 120;
		this.scoreWin = true;
		this.ballSpeed = 200;
		this.powerups = false;
		this.topLeftAI = false;
		this.topRightAI = false;
		this.botLeftAI = false;
		this.botRightAI = false;
	}
}
