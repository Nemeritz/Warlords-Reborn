package App.Menu.MatchOptions;

import App.Menu.MenuComponent;
import App.Shared.JFX.EventReceiver;
import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
/**
 * Created by Hanliang Ding on 09/04/2017.
 */
public class MatchOptionsComponent extends BorderPane implements EventReceiver {
    private MenuComponent menu;
    private SharedModule shared;
    private MediaPlayer buttonSound;
    private int currentButton;

    @FXML
    private CheckBox GhostingBox;

    @FXML
    private CheckBox PowerupsBox;

    @FXML
    private ComboBox<String> timeCombo;

    @FXML
    private RadioButton timeRadio;

    @FXML
    private RadioButton deathRadio;

    @FXML
    private RadioButton slowRadio;

    @FXML
    private RadioButton mediumRadio;

    @FXML
    private RadioButton fastRadio;

    @FXML
    private Text back;

    @FXML
    private Text cont;

    @FXML
    private BorderPane pane;

    @FXML
    private ToggleGroup timeoptions;
    /**
     * Constructs the Match options component
     */
    private void construct() {

        this.shared.getJFX().loadFXML(this, MatchOptionsComponent.class,
                "MatchOptionsComponent.fxml");

        this.buttonSound = this.shared.getJFX().loadMedia(this.shared.getClass(), "assets/Button.mp3");
        this.buttonSound.setVolume(this.shared.getSettings().soundEffectsVolume);

        this.currentButton = 0;
        this.shared.getJFX().getEventReceivers().add(this);

        this.timeCombo.getItems().addAll("2 minutes", "4 minutes", "6 minutes");
        this.timeCombo.getSelectionModel().selectFirst();

        //adds a listener for combo box selection change and detect them
        this.timeCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue,
																						 newValue) -> {
        	this.playButtonSound();
        	if (this.timeCombo.getSelectionModel().getSelectedItem().equals("2 minutes")) {
        		this.shared.getSettings().maxGameTime = 120;
        	}
        	else if (this.timeCombo.getSelectionModel().getSelectedItem().equals("4 minutes")) {
        		this.shared.getSettings().maxGameTime = 240;
        	}
        	else if (this.timeCombo.getSelectionModel().getSelectedItem().equals("6 minutes")) {
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

    public void changeData() {
    	if (this.GhostingBox.isSelected()) {
    		this.shared.getSettings().ghosting = true;
    	}
    	else {
    		this.shared.getSettings().ghosting = false;
    	}

    	if (this.PowerupsBox.isSelected()) {
    		this.shared.getSettings().powerups = true;
    	}
    	else {
    		this.shared.getSettings().powerups = false;
    	}

    	if (this.slowRadio.isSelected()) {
    		this.shared.getSettings().ballSpeed = 200;
    	}
    	else if (this.mediumRadio.isSelected()) {
    		this.shared.getSettings().ballSpeed = 300;
    	}
    	else if (this.fastRadio.isSelected()) {
    		this.shared.getSettings().ballSpeed = 400;
    	}

    	if (this.timeRadio.isSelected()) {
    		this.shared.getSettings().scoreWin = true;
    	}
    	else if (this.deathRadio.isSelected()) {
    		this.shared.getSettings().scoreWin = false;
    	}
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
    	this.menu.transitionMode();
		this.playButtonSound();
		changeData();
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

    public void resetCurrentButton() {
    	this.currentButton = 0;
    }

    @Override
    public void onKeyEvent(KeyEvent event){

    	if (event.getEventType() == KeyEvent.KEY_RELEASED) {
    		if ((event.getCode() == KeyCode.SPACE) && (this.menu.getCurrentMenu() == 2)) {
    				this.playButtonSound();
    		}
    		else if (((event.getCode() == KeyCode.LEFT)||(event.getCode() == KeyCode.RIGHT)) && (this.menu.getCurrentMenu() == 2)) {
    			if(this.deathRadio.isSelected()) {
    				this.timeCombo.setDisable(true);
    				this.playButtonSound();
    			}
    			else if(this.timeRadio.isSelected()) {
    				this.timeCombo.setDisable(false);
    				this.playButtonSound();
    			}
    		}
    	}
    }
}
