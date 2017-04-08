package App.Menu.GameSettings;

import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;


public class GameSettingsComponent extends BorderPane {
    private SharedModule shared;

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
        this.VolumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
        	this.shared.getJFX().getSharedSettings().musicVolume = (((double)newValue)/100);
        	this.shared.getJFX().getSharedSettings().soundEffectsVolume = (((double)newValue)/100);
        	this.shared.getJFX().getMusic().setVolume(this.shared.getJFX().getSharedSettings().musicVolume);
        	this.shared.getJFX().getSound().setVolume(this.shared.getJFX().getSharedSettings().soundEffectsVolume);
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
        this.shared.getJFX().setScene("menu");
        this.shared.getJFX().loadSound(this.shared.getClass(), "Button.mp3");
    }

    @FXML
    	void onEffectsClicked(){
    	if (this.EffectsBox.isSelected()) {
        	this.shared.getJFX().getSound().stop();
    	}
    	else{
        	this.shared.getJFX().getSound().play();
    	}
    }

    @FXML
		void onMusicClicked(){

    	if (this.EffectsBox.isSelected()) {
    		this.shared.getJFX().getMusic().pause();
    	}
    	else {
    		this.shared.getJFX().getMusic().play();
    	}
    }
}
