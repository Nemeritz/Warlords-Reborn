package App.Menu.Title;

import App.Game.GameComponent;
import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.AbstractMap.SimpleImmutableEntry;


public class GameSettingsComponent extends BorderPane {
    private SharedModule shared;

    @FXML
    private Text back;

    private void construct() {
        this.shared.getJFX().loadFXML(this, TitleComponent.class,
                "GameSettingsComponent.fxml");
    }

    public GameSettingsComponent() {
        this.construct();
    }

  /*  public GameSettingsComponent(SharedModule shared) {
        this.shared = shared;
        this.construct();
    }*/

    @FXML
    	void onBackClicked() {
        this.shared.getJFX().setScene("title");
    }
}
