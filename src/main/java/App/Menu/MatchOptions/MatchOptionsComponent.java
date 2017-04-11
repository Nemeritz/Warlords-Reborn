package App.Menu.MatchOptions;

import App.Game.GameComponent;
import App.Menu.MenuComponent;
import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;

/**
 * Created by Hanliang Ding on 09/04/2017.
 */
public class MatchOptionsComponent extends BorderPane {
    private MenuComponent menu;
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

        this.timeCombo.getItems().addAll("2 minutes", "4 minutes", "6 minutes");

        //adds a listener for combo box selection change and detect them
        this.timeCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        	this.playButtonSound();
        	if (this.timeCombo.getSelectionModel().getSelectedItem().toString().equals("3 minutes")) {
        		this.shared.getSettings().maxGameTime = 120;
        	}
        	else if (this.timeCombo.getSelectionModel().getSelectedItem().toString().equals("6 minutes")) {
        		this.shared.getSettings().maxGameTime = 240;
        	}
        	else if (this.timeCombo.getSelectionModel().getSelectedItem().toString().equals("9 minutes")) {
        		this.shared.getSettings().maxGameTime = 360;
        	}
        });
    }

    /**
     * Default Constructor for the title component
     * @param shared
     * @param menu
     */
    public MatchOptionsComponent(SharedModule shared, MenuComponent menu) {
		this.shared = shared;
		this.menu = menu;
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

	/**
	 * plays the button press sound effect
	 */
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
    	this.menu.transitionTitle();
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

		this.playButtonSound();
        this.menu.getMusic().stop();
    }

	/**
	 * sets game mode to death match
	 */
	@FXML
		void onDeathClicked(){
			this.playButtonSound();
    		this.timeCombo.setDisable(true);
    		this.shared.getSettings().scoreWin = false;
    }

	/**
	 * sets game mode to score based, win condition is highest score when game limit ends
	 */
	@FXML
		void onTimeClicked(){
			this.playButtonSound();
    		this.timeCombo.setDisable(false);
    		this.shared.getSettings().scoreWin = true;
    }

	/**
	 * Allows ghosting after a player dies
	 */
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

	/**
	 * Adds powerups
	 */
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

	/**
	 * top left is AI or human
	 */
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

	/**
	 * top right is AI or human
	 */
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

	/**
	 * bottom left AI or human player
	 */
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

	/**
	 * bottom right AI or human player
	 */
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

	/**
	 * sets ball speed to slow
	 */
	@FXML
		void onSlowClicked(){
			this.playButtonSound();
    		this.shared.getSettings().ballSpeed = 100;
    }

	/**
	 * sets ball speed to medium
	 */
	@FXML
		void onMediumClicked(){
			this.playButtonSound();
			this.shared.getSettings().ballSpeed = 200;
    }

	/**
	 * sets ball speed to fast
	 */
	@FXML
		void onFastClicked(){
    		this.playButtonSound();
			this.shared.getSettings().ballSpeed = 300;
    }

}
