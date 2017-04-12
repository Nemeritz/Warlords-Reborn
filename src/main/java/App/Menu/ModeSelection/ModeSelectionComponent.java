package App.Menu.ModeSelection;

import App.Menu.MenuComponent;
import App.Game.GameComponent;
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
import javafx.scene.paint.Color;
import javafx.scene.Node;


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

	@FXML
	private Text blsel;

	@FXML
	private Text brsel;

	@FXML
	private Text tlsel;

	@FXML
	private Text trsel;

	@FXML
	private Text tlname;

	@FXML
	private Text trname;

	@FXML
	private Text brname;

	@FXML
	private Text blname;


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

		this.tlcombo.focusedProperty().addListener((observable, oldValue,
				newValue) -> {
					if (this.shared.getJFX().getSceneValue("menu").getFocusOwner()==this.tlcombo) {
						this.resetEffects();
						this.tlsel.setEffect(glow);
						this.tlsel.setFill(Color.RED);
					}
				});
		this.trcombo.focusedProperty().addListener((observable, oldValue,
				newValue) -> {
					if (this.shared.getJFX().getSceneValue("menu").getFocusOwner()==this.trcombo) {
						this.resetEffects();
						this.trsel.setEffect(glow);
						this.trsel.setFill(Color.RED);
					}
				});
		this.blcombo.focusedProperty().addListener((observable, oldValue,
				newValue) -> {
					if (this.shared.getJFX().getSceneValue("menu").getFocusOwner()==this.blcombo) {
						this.resetEffects();
						this.blsel.setEffect(glow);
						this.blsel.setFill(Color.RED);
					}
				});
		this.brcombo.focusedProperty().addListener((observable, oldValue,
				newValue) -> {
					if (this.shared.getJFX().getSceneValue("menu").getFocusOwner()==this.brcombo) {
						this.resetEffects();
						this.brsel.setEffect(glow);
						this.brsel.setFill(Color.RED);
					}
				});
		this.tlcombo.focusedProperty().addListener((observable, oldValue,
				newValue) -> {
					if (this.shared.getJFX().getSceneValue("menu").getFocusOwner()==this.tlcombo) {
						this.resetEffects();
						this.tlsel.setEffect(glow);
						this.tlsel.setFill(Color.RED);
					}
				});

		this.tltext.focusedProperty().addListener((observable, oldValue,
				newValue) -> {
					if (this.shared.getJFX().getSceneValue("menu").getFocusOwner()==this.tltext) {
						this.resetEffects();
						this.tlname.setEffect(glow);
						this.tlname.setFill(Color.RED);
					}
				});
		this.trtext.focusedProperty().addListener((observable, oldValue,
				newValue) -> {
					if (this.shared.getJFX().getSceneValue("menu").getFocusOwner()==this.trtext) {
						this.resetEffects();
						this.trname.setEffect(glow);
						this.trname.setFill(Color.RED);
					}
				});
		this.bltext.focusedProperty().addListener((observable, oldValue,
				newValue) -> {
					if (this.shared.getJFX().getSceneValue("menu").getFocusOwner()==this.bltext) {
						this.resetEffects();
						this.blname.setEffect(glow);
						this.blname.setFill(Color.RED);
					}
				});
		this.brtext.focusedProperty().addListener((observable, oldValue,
				newValue) -> {
					if (this.shared.getJFX().getSceneValue("menu").getFocusOwner()==this.brtext) {
						this.resetEffects();
						this.brname.setEffect(glow);
						this.brname.setFill(Color.RED);
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
		this.tlsel.setEffect(null);
		this.trsel.setEffect(null);
		this.blsel.setEffect(null);
		this.brsel.setEffect(null);
		this.tlname.setEffect(null);
		this.trname.setEffect(null);
		this.blname.setEffect(null);
		this.brname.setEffect(null);
		this.tlsel.setFill(Color.WHITE);
		this.trsel.setFill(Color.WHITE);
		this.blsel.setFill(Color.WHITE);
		this.brsel.setFill(Color.WHITE);
		this.tlname.setFill(Color.WHITE);
		this.trname.setFill(Color.WHITE);
		this.blname.setFill(Color.WHITE);
		this.brname.setFill(Color.WHITE);
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
		if (event.getEventType() == KeyEvent.KEY_PRESSED && (this.menu.getCurrentMenu() == 3)) {
			this.playButtonSound();
			if (event.getCode() == KeyCode.B) {
				this.menu.transitionOptions();
			}

			else if ((event.getCode() == KeyCode.N) && (this.menu.getCurrentMenu() == 3)) {
				changeData();
				this.menu.transitionTitle();
				this.shared.getJFX().setScene("game");
				this.playButtonSound();
				this.menu.getMusic().stop();
			}
		}
	}
}


