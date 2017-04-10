package App.Menu.MatchOptions;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextInputControl;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.AbstractMap.SimpleImmutableEntry;

/**
 * Created by Hanliang Ding on 09/04/2017.
 */
public class MatchOptionsComponent extends BorderPane {
    private SharedModule shared;
    private MediaPlayer buttonSound;

    @FXML
    private CheckBox GhostingBox;

    @FXML
    private CheckBox PowerupsBox;

    @FXML
    private ComboBox timeCombo;

    @FXML
    private RadioButton timeRadio;

    @FXML
    private RadioButton deathRadio;

    @FXML
    private Text back;

    @FXML
    private Text cont;

    @FXML
    private CheckBox topLeftBox, topRightBox, botLeftBox, botRightBox;

    /**
     * Constructs the Match options component
     */
    private void construct() {
        this.shared.getJFX().loadFXML(this, MatchOptionsComponent.class,
                "MatchOptionsComponent.fxml");

        this.buttonSound = this.shared.getJFX().loadMedia(this.shared.getClass(), "Button.mp3");
        this.buttonSound.setVolume(this.shared.getSettings().soundEffectsVolume);

        this.timeCombo.getItems().addAll("3 minutes", "6 minutes", "9 minutes");

        this.timeCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        	this.playButtonSound();
        	if (this.timeCombo.getSelectionModel().getSelectedItem().toString().equals("3 minutes")) {
        		this.shared.getSettings().maxGameTime = 180;
        	}
        	else if (this.timeCombo.getSelectionModel().getSelectedItem().toString().equals("6 minutes")) {
        		this.shared.getSettings().maxGameTime = 360;
        	}
        	else if (this.timeCombo.getSelectionModel().getSelectedItem().toString().equals("9 minutes")) {
        		this.shared.getSettings().maxGameTime = 540;
        	}
        });
    }

    /**
     * Default Constructor for the title component
     */
    public MatchOptionsComponent() {
        this.construct();
    }

    /**
     * Constructor
     * @param shared access to the JFX scenes and stages
     */
    public MatchOptionsComponent(SharedModule shared) {
        this.shared = shared;
        this.construct();
    }

    public void playButtonSound() {
        this.buttonSound.stop();
        this.buttonSound.setVolume(this.shared.getSettings().soundEffectsVolume);
        this.buttonSound.play();
    }

    /**
     * transition scene back to main
     */
    @FXML
    void onBackClicked() {
    	this.shared.getJFX().getMenu().transitionTitle();
		this.playButtonSound();
    }

    /**
     * transition scene to game
     */
    @FXML
    void onContinueClicked() {
    	this.shared.getJFX().setScene("game");
        ((GameComponent) this.shared.getJFX().getScene("game").getKey())
        .startGameCountdown();

        ((GameComponent) this.shared.getJFX().getScene("game").getKey())
        .startGameMusic();

		this.playButtonSound();
        this.shared.getJFX().getMenu().menuMusic.stop();
    }

    @FXML
		void onDeathClicked(){
			this.playButtonSound();
    		this.timeCombo.setDisable(true);
    		this.shared.getSettings().scoreWin = false;
    }

    @FXML
		void onTimeClicked(){
			this.playButtonSound();
    		this.timeCombo.setDisable(false);
    		this.shared.getSettings().scoreWin = true;
    }

    @FXML
		void onGhostClicked(){
    	this.playButtonSound();
    	if (this.GhostingBox.isSelected()) {
    		this.shared.getSettings().ghosting = true;
    	}
    	else {
    		this.shared.getSettings().ghosting = false;
    	}
    }

    @FXML
		void onPowerClicked(){
    	this.playButtonSound();
    	if (this.PowerupsBox.isSelected()) {
    		this.shared.getSettings().powerups = true;
    	}
    	else {
    		this.shared.getSettings().powerups = false;
    	}
    }

    @FXML
		void onTopLeftClicked(){
    	this.playButtonSound();
    	if (this.topLeftBox.isSelected()) {
    		this.shared.getSettings().topLeftAI = true;
    	}
    	else {
    		this.shared.getSettings().topLeftAI = false;
    	}
    }

    @FXML
		void onTopRightClicked(){
    	this.playButtonSound();
    	if (this.topRightBox.isSelected()) {
    		this.shared.getSettings().topRightAI = true;
    	}
    	else {
    		this.shared.getSettings().topRightAI = false;
    	}
    }

    @FXML
		void onBotLeftClicked(){
    	this.playButtonSound();
    	if (this.botLeftBox.isSelected()) {
    		this.shared.getSettings().botLeftAI = true;
    	}
    	else {
    		this.shared.getSettings().botLeftAI = false;
    	}
    }

    @FXML
		void onBotRightClicked(){
    	this.playButtonSound();
    	if (this.botRightBox.isSelected()) {
    		this.shared.getSettings().botRightAI = true;
    	}
    	else {
    		this.shared.getSettings().botRightAI = false;
    	}
    }

    @FXML
		void onSlowClicked(){
			this.playButtonSound();
    		this.shared.getSettings().ballSpeed = 10;
    }

    @FXML
		void onMediumClicked(){
			this.playButtonSound();
			this.shared.getSettings().ballSpeed = 20;
    }

    @FXML
		void onFastClicked(){
    		this.playButtonSound();
			this.shared.getSettings().ballSpeed = 30;
    }

}
