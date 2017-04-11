package App.Menu.GameSettings;

import App.Menu.MenuComponent;
import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.scene.input.KeyCode;
import App.Shared.JFX.EventReceiver;
import javafx.scene.input.KeyEvent;
import javafx.scene.effect.Glow;


public class GameSettingsComponent extends BorderPane implements EventReceiver{
	private SharedModule shared;
	private MenuComponent menu;
	private MediaPlayer buttonSound;
	private int currentButton;
	private Glow glow;

	@FXML
	private CheckBox MusicBox;

	@FXML
	private CheckBox EffectsBox;

	@FXML
	private Slider VolumeSlider;

	@FXML
	private Text back;

	@FXML
	private Text volumeText;

	@FXML
	private Text musicText;

	@FXML
	private Text soundText;

	/**
	 * load the menu, set listener for sliders and volume for button sounds
	 */
	private void construct() {
		this.shared.getJFX().loadFXML(this, GameSettingsComponent.class,
				"GameSettingsComponent.fxml");

		this.buttonSound = this.shared.getJFX().loadMedia(this.shared.getClass(), "assets/Button.mp3");
		this.buttonSound.setVolume(this.shared.getSettings().soundEffectsVolume);

		this.currentButton = 0;
		this.shared.getJFX().getEventReceivers().add(this);

		//adds a listener for slider value change and detect them, change volume proportional to slider
		this.VolumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			this.resetEffects();
			this.volumeText.setEffect(glow);
			this.currentButton = 0;
			this.shared.getSettings().musicVolume = (((double)newValue)/100);
			this.shared.getSettings().soundEffectsVolume = (((double)newValue)/100);
			menu.getMusic().setVolume(this.shared.getSettings().musicVolume);
			this.buttonSound.setVolume(this.shared.getSettings().soundEffectsVolume);
		});

		this.glow = new Glow(10.0);
		this.resetEffects();
		this.volumeText.setEffect(glow);
	}

	/**
	 * default constructor
	 * @param shared
	 * @param menu
	 */
	public GameSettingsComponent(SharedModule shared, MenuComponent menu) {
		this.shared = shared;
		this.menu = menu;
		this.construct();
	}

	/**
	 * plays the button sound effect
	 */
	public void playButtonSound() {
		this.buttonSound.stop();
		this.buttonSound.setVolume(this.shared.getSettings().soundEffectsVolume);
		this.buttonSound.play();
	}

	/**
	 * clears the effects on all components
	 */
	public void resetEffects() {
		this.soundText.setEffect(null);
		this.musicText.setEffect(null);
		this.volumeText.setEffect(null);
		this.back.setEffect(null);
	}

	/**
	 * transition to the main menu
	 */
	@FXML
	void onBackClicked() {
		this.resetEffects();
		this.volumeText.setEffect(glow);
		this.menu.transitionTitle();
		this.playButtonSound();
	}

	/**
	 *mutes the sound effects if selected
	 */
	@FXML
	void onEffectsClicked(){
		this.currentButton = 1;
		this.resetEffects();
		this.soundText.setEffect(glow);
		this.playButtonSound();
		if (this.EffectsBox.isSelected()) {
			this.shared.getSettings().soundEffectsVolume = 0.0;
		}
		else{
			this.shared.getSettings().soundEffectsVolume = this.shared.getSettings().musicVolume;
		}
	}

	/**
	 * mutes the music if selected
	 */
	@FXML
	void onMusicClicked(){
		this.currentButton = 2;
		this.resetEffects();
		this.musicText.setEffect(glow);
		this.playButtonSound();
		if (this.MusicBox.isSelected()) {
			this.menu.getMusic().setMute(true);
		}
		else {
			this.menu.getMusic().setMute(false);
		}
	}

	public void resetCurrentButton() {
		this.currentButton = 0;
	}

	@Override
	public void onKeyEvent(KeyEvent event){
		if ((event.getEventType() == KeyEvent.KEY_RELEASED) && (this.menu.getCurrentMenu() == 1)) {
			if (event.getCode() == KeyCode.TAB) {
				if (currentButton < 3) {
					currentButton++;
				}
				else if (currentButton == 3) {
					currentButton = 0;
				}

				switch(currentButton) {
				case 0:
					this.resetEffects();
					this.volumeText.setEffect(glow);
					break;
				case 1:
					this.resetEffects();
					this.soundText.setEffect(glow);
					break;
				case 2:
					this.resetEffects();
					this.musicText.setEffect(glow);
					break;
				case 3:
					this.resetEffects();
					this.back.setEffect(glow);
					break;
				}
			}

			else if (event.getCode() == KeyCode.ENTER) {
				switch (currentButton) {

				case 1: this.EffectsBox.fire();

				if (this.EffectsBox.isSelected()) {
					this.shared.getSettings().soundEffectsVolume = 0.0;
				}
				else{
					this.shared.getSettings().soundEffectsVolume = this.shared.getSettings().musicVolume;
					this.playButtonSound();
				}
				break;

				case 2: this.MusicBox.fire();
				if (this.MusicBox.isSelected()) {
					this.menu.getMusic().setMute(true);
				}
				else {
					this.menu.getMusic().setMute(false);
				}
				this.playButtonSound();
				break;

				case 3: this.menu.transitionTitle();
				this.resetEffects();
				this.volumeText.setEffect(glow);
				this.playButtonSound();
				break;
				}
			}
		}
	}
}
