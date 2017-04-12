package App.Menu.ModeSelection;

import App.Menu.MenuComponent;
import App.Shared.JFX.EventReceiver;
import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;


public class ModeSelectionComponent extends BorderPane implements EventReceiver{
	private SharedModule shared;
	private MenuComponent menu;
	private MediaPlayer buttonSound;
	private int currentButton;
	private Glow glow;

	@FXML
	private Text back;

	@FXML
	private Text cont;

	@FXML
	private ComboBox<String> tlcombo;

	@FXML
	private ComboBox<String> trcombo;

	@FXML
	private ComboBox<String> blcombo;

	@FXML
	private ComboBox<String> brcombo;

	@FXML
	private TextField tltext;

	@FXML
	private TextField trtext;

	@FXML
	private TextField bltext;

	@FXML
	private TextField brtext;

	/**
	 * load the menu, set listener for sliders and volume for button sounds
	 */
	private void construct() {
		this.shared.getJFX().loadFXML(this, ModeSelectionComponent.class,
				"ModeSelectionComponent.fxml");

		this.buttonSound = this.shared.getJFX().loadMedia(this.shared.getClass(), "assets/Button.mp3");
		this.buttonSound.setVolume(this.shared.getSettings().soundEffectsVolume);

		this.currentButton = 0;
		this.shared.getJFX().getEventReceivers().add(this);

		this.glow = new Glow(10.0);
		this.resetEffects();
		this.back.setEffect(glow);

		this.tlcombo.getItems().addAll("Empty", "Bot (Normal)", "Bot (Unfair)", "Player");
		this.trcombo.getItems().addAll("Empty", "Bot (Normal)", "Bot (Unfair)", "Player");
		this.blcombo.getItems().addAll("Empty", "Bot (Normal)", "Bot (Unfair)", "Player");
		this.brcombo.getItems().addAll("Empty", "Bot (Normal)", "Bot (Unfair)", "Player");
		this.tlcombo.getSelectionModel().selectLast();
		this.trcombo.getSelectionModel().selectLast();
		this.blcombo.getSelectionModel().selectLast();
		this.brcombo.getSelectionModel().selectLast();

		this.tlcombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue,
				newValue) -> {
					if (this.tlcombo.getSelectionModel().getSelectedItem().equals("Player")) {
						this.tltext.setDisable(false);
					}
					else {
						this.tltext.setDisable(true);
					}
				});

		this.trcombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue,
				newValue) -> {
					if (this.trcombo.getSelectionModel().getSelectedItem().equals("Player")) {
						this.trtext.setDisable(false);
					}
					else {
						this.trtext.setDisable(true);
					}
				});

		this.blcombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue,
				newValue) -> {
					if (this.blcombo.getSelectionModel().getSelectedItem().equals("Player")) {
						this.bltext.setDisable(false);
					}
					else {
						this.bltext.setDisable(true);
					}
				});

		this.brcombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue,
				newValue) -> {
					if (this.brcombo.getSelectionModel().getSelectedItem().equals("Player")) {
						this.brtext.setDisable(false);
					}
					else {
						this.brtext.setDisable(true);
					}
				});
	}

	/**
	 * default constructor
	 * @param shared
	 * @param menu
	 */
	public ModeSelectionComponent(SharedModule shared, MenuComponent menu) {
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
		this.back.setEffect(null);
		this.cont.setEffect(null);
	}

	public void changeData() {
		this.shared.getSettings().topLeft = this.tlcombo.getSelectionModel().getSelectedIndex();
		this.shared.getSettings().topRight = this.trcombo.getSelectionModel().getSelectedIndex();
        this.shared.getSettings().botLeft = this.blcombo.getSelectionModel().getSelectedIndex();
        this.shared.getSettings().botRight = this.brcombo.getSelectionModel().getSelectedIndex();

		this.shared.getSettings().topLeftName = this.tltext.getText();
		this.shared.getSettings().topRightName = this.trtext.getText();
		this.shared.getSettings().botLeftName = this.bltext.getText();
		this.shared.getSettings().botRightName = this.brtext.getText();
	}

	/**
	 * transition to the main menu
	 */
	@FXML
	void onBackClicked() {
		this.resetEffects();
		this.back.setEffect(glow);
		this.menu.transitionOptions();
		this.playButtonSound();
	}

	@FXML
	void onContinueClicked() {
		this.resetEffects();
		this.back.setEffect(glow);
		changeData();

		this.menu.transitionTitle();

		this.shared.getJFX().setScene("game");

		this.playButtonSound();
		this.menu.getMusic().stop();
	}

	public void resetCurrentButton() {
		this.currentButton = 0;
	}

	@Override
	public void onKeyEvent(KeyEvent event){
		if ((event.getEventType() == KeyEvent.KEY_RELEASED) && (this.menu.getCurrentMenu() == 3)) {
			if (event.getCode() == KeyCode.TAB) {
				if (currentButton < 7) {
					currentButton++;
				}
				else if (currentButton == 7) {
					currentButton = 0;
				}

				switch(currentButton) {
				case 0:
					this.resetEffects();
					this.back.setEffect(glow);
					break;
				case 1:
					this.resetEffects();
					this.cont.setEffect(glow);
					break;
				}
			}

			else if (event.getCode() == KeyCode.ENTER) {
				switch (currentButton) {

				case 0:
					this.resetEffects();
					this.back.setEffect(glow);
					this.menu.transitionOptions();
					this.playButtonSound();
					break;

				case 1:
					this.resetEffects();
					this.back.setEffect(glow);
					this.shared.getJFX().setScene("game");

					this.playButtonSound();
					this.menu.getMusic().stop();
					break;
				}
			}
		}
	}
}
