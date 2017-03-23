package App.Menu.Title;

import App.Shared.SharedComponent;
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
    SharedComponent shared;
    Scene scene;

    @FXML
    Text play;

    @FXML
    Text settings;

    @FXML
    Text exit;

    private void construct() {
        this.scene = new Scene(this);
    }

    public TitleComponent() {
        this.construct();
    }

    public TitleComponent(SharedComponent shared) {
        this.shared = shared;
        this.construct();
    }

    public void loadView() {
        FXMLLoader loader = this.shared.getJFX().getLoader();
        loader.setLocation(this.getClass().getResource("TitleComponent.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
