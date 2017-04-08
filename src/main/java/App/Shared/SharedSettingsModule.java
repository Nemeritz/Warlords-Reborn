package App.Shared;

/**
 * Created by Hanliang Ding on 09/04/2017.
 */
public class SharedSettingsModule {
	private double musicVolume;
	private double soundEffectsVolume;
	private boolean ghosting;
	private double maxGameTime;
	private boolean scoreWin;
	private double ballSpeed;
	private boolean powerups;

	public SharedSettingsModule() {
		this.musicVolume = 1.0;
		this.soundEffectsVolume = 1.0;
		this.ghosting = false;
		this.maxGameTime = 180;
		this.scoreWin = true;
		this.ballSpeed = 20;
		this.powerups = false;
	}

	public void setMusicVolume(double volume) {
		if (volume > 1.0) {
			return;
		}
		else {
			this.musicVolume = volume;
		}
	}

	public double getMusicVolume() {
		return this.musicVolume;
	}

	public void setSoundEffectsVolume(double volume) {
		if (volume > 1.0) {
			return;
		}
		else {
			this.soundEffectsVolume = volume;
		}
	}

	public double getSoundEffectsVolume() {
		return this.soundEffectsVolume;
	}

	public void setGhosting(boolean value) {
		if (value != true || value != false) {
			return;
		}
		else {
			this.ghosting = value;
		}
	}

	public boolean getGhosting() {
		return this.ghosting;
	}

	public void setMaxGameTime(double time) {
			this.maxGameTime = time;
	}

	public double getMaxGameTime() {
		return this.maxGameTime;
	}

	public void setBallSpeed(double speed) {
		this.ballSpeed = speed;
	}

	public double getBallSpeed() {
		return this.ballSpeed;
	}

	public void setPowerups(boolean value) {
		if (value != true || value != false) {
			return;
		}
		else {
			this.powerups = value;
		}
	}

	public boolean getPowerups() {
		return this.powerups;
	}

	public void setScoreWin(boolean value) {
		if (value != true || value != false) {
			return;
		}
		else {
			this.scoreWin = value;
		}
	}

	public boolean getScoreWin() {
		return this.scoreWin;
	}

}
