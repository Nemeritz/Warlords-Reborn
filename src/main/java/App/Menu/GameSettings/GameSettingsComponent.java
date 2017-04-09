package App.Menu.GameSettings;

import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.media.MediaPlayer;


public class GameSettingsComponent extends BorderPane {
    private SharedModule shared;
    private MediaPlayer buttonSound;

    @FXML
    private CheckBox MusicBox;

    @FXML
    private CheckBox EffectsBox;

    @FXML
    private Slider VolumeSlider;

    @FXML
    private Text back;

    private void construct() {
        this.shared.getJFX().loadFXML(this, GameSettingsComponent.class,
                "GameSettingsComponent.fxml");

        this.buttonSound = this.shared.getJFX().loadMedia(this.shared.getClass(), "Button.mp3");
        this.buttonSound.setVolume(this.shared.getSettings().soundEffectsVolume);

        this.VolumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
        	this.shared.getSettings().musicVolume = (((double)newValue)/100);
        	this.shared.getSettings().soundEffectsVolume = (((double)newValue)/100);
        	this.shared.getJFX().getMenu().menuMusic.setVolume(this.shared.getSettings().musicVolume);
        	this.buttonSound.setVolume(this.shared.getSettings().soundEffectsVolume);
        });
    }

    public GameSettingsComponent() {
        this.construct();
    }

    public GameSettingsComponent(SharedModule shared) {
        this.shared = shared;
        this.construct();
    }

    @FXML
    	void onBackClicked() {
    	this.shared.getJFX().getMenu().transitionTitle();
        this.buttonSound.setVolume(this.shared.getSettings().soundEffectsVolume);
        this.buttonSound.stop();
        this.buttonSound.play();
    }

    @FXML
        void onEffectsClicked(){
    	if (this.EffectsBox.isSelected()) {
    		this.shared.getSettings().soundEffectsVolume = 0.0;
    	}
    	else{
    		this.shared.getSettings().soundEffectsVolume = this.shared.getSettings().musicVolume;
    	}
    }

    @FXML
		void onMusicClicked(){
    	if (this.MusicBox.isSelected()) {
    		this.shared.getJFX().getMenu().menuMusic.setMute(true);
    	}
    	else {
    		this.shared.getJFX().getMenu().menuMusic.setMute(false);
    	}
    }
}
