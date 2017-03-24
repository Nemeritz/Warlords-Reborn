package App.Menu.Title;

import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * Created by lichk on 21/03/2017.
 */
public class TitleComponent extends BorderPane {
    private SharedModule shared;

    @FXML
    private Text play;

    @FXML
    private Text settings;

    @FXML
    private Text exit;

    private void construct() {
        this.shared.getJFX().loadFXML(this, TitleComponent.class,
                "TitleComponent.fxml");
    }

    public TitleComponent() {
        this.construct();
    }

    public TitleComponent(SharedModule shared) {
        this.shared = shared;
        this.construct();
    }
}
