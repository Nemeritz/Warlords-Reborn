package App.Menu.Title;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * Created by lichk on 21/03/2017.
 */
public class TitleComponent extends BorderPane {
    @FXML
    Text play;

    @FXML
    Text settings;

    @FXML
    Text exit;

    public TitleComponent() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TitleComponent.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
