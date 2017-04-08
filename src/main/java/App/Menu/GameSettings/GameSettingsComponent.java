package App.Menu.GameSettings;

import App.Game.GameComponent;
import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.Slider;
import javafx.scene.control.CheckBox;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.AbstractMap.SimpleImmutableEntry;


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
        	this.shared.getJFX().getMusic().setVolume(((double)newValue)/100);
        	this.shared.getJFX().getSound().setVolume(((double)newValue)/100);
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
        	this.shared.getJFX().getSound().setVolume(0);
    	}
    	else{
        	this.shared.getJFX().getSound().setVolume(1);
    	}
    }

    @FXML
		void onMusicClicked(){
    	if (this.EffectsBox.isSelected()) {
    		this.shared.getJFX().getMusic().setVolume(0);
    	}
    	else {
    		this.shared.getJFX().getSound().setVolume(1);
    	}
    }
}
