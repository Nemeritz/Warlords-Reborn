package App.Menu.Title;

import App.Menu.MenuComponent;
import App.Shared.JFX.EventReceiver;
import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.scene.effect.Glow;


/**
 * Created by Jerry Fan on 21/03/2017.
 */
public class TitleComponent extends BorderPane implements EventReceiver {
    private SharedModule shared;
    private MenuComponent menu;
    private MediaPlayer buttonSound;
    private int currentButton;
    private Glow glow;

    @FXML
    private Text play;

    @FXML
    private Text settings;

    @FXML
    private Text exit;

    /**
     * Constructs the title component
     */
    private void construct() {
        this.shared.getJFX().loadFXML(this, TitleComponent.class,
                "TitleComponent.fxml");
        this.buttonSound = this.shared.getJFX().loadMedia(this.shared.getClass(), "assets/Button.mp3");
        this.buttonSound.setVolume(this.shared.getSettings().soundEffectsVolume);
        this.currentButton = 0;
		this.glow = new Glow(10.0);
		this.play.setEffect(glow);
        this.shared.getJFX().getEventReceivers().add(this);
    }

    /**
     * Default Constructor for the title component
     * @param shared
     * @param menu
     */
    public TitleComponent(SharedModule shared, MenuComponent menu) {
        this.shared = shared;
        this.menu = menu;
        this.construct();
    }

    public void playButtonSound() {
        this.buttonSound.stop();
        this.buttonSound.setVolume(this.shared.getSettings().soundEffectsVolume);
        this.buttonSound.play();
    }

	public void resetEffects() {
		this.play.setEffect(null);
		this.settings.setEffect(null);
		this.exit.setEffect(null);
	}

    /**
     * transitions scene to match options when clicked
     */
    @FXML
    void onPlayClicked() {
		this.resetEffects();
		this.play.setEffect(glow);
        this.menu.transitionOptions();
        this.playButtonSound();
    }

    /**
     * exits the game when clicked, closing scenes
     */
    @FXML
    void onExitClicked() {
    	this.playButtonSound();
    	this.shared.getJFX().closeStage();
    }

    /**
     * transitions scene to the Game Settings menu when clicked
     */
    @FXML
    void onSettingsClicked() {
		this.resetEffects();
		this.settings.setEffect(glow);
        this.menu.transitionSettings();
        this.playButtonSound();
    }

    public void resetCurrentButton() {
    	this.currentButton = 0;
    }

    @Override
    public void onKeyEvent(KeyEvent event){
    	if ((event.getEventType() == KeyEvent.KEY_RELEASED) && (this.menu.getCurrentMenu() == 0)) {
    		if (event.getCode() == KeyCode.TAB) {
    			if (currentButton < 2) {
    				currentButton++;
    			}
    			else if (currentButton == 2) {
    				currentButton = 0;
    			}

				switch(currentButton) {
				case 0:
					this.resetEffects();
					this.play.setEffect(glow);
					break;
				case 1:
					this.resetEffects();
					this.settings.setEffect(glow);
					break;
				case 2:
					this.resetEffects();
					this.exit.setEffect(glow);
					break;
				}
    		}

    		else if (event.getCode() == KeyCode.ENTER) {
    			switch (currentButton) {
    			case 0:	this.menu.transitionOptions();
						this.resetEffects();
						this.play.setEffect(glow);
    					this.playButtonSound();
    			break;

    			case 1: this.menu.transitionSettings();
    					this.resetEffects();
    					this.play.setEffect(glow);
    					this.playButtonSound();
    			break;

    			case 2: this.playButtonSound();
    					this.shared.getJFX().closeStage();
    			break;
    			}
    		}
    	}
    }
}
