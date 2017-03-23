package App.Shared.JFX;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by lichk on 23/03/2017.
 */
public class JFXService {
    private Stage stage;
    private Scene scene;
    private FXMLLoader loader;

    public JFXService() {
        this.loader = new FXMLLoader();
    }

    public Stage getStage() {
        return this.stage;
    }

    public void setStage(Stage value) {
        if (!value.equals(this.stage)) {
            this.stage = value;
        }
    }

    public Scene getScene() {
        return this.scene;
    }

    public void setScene(Scene value) {
        if (!value.equals(this.scene)) {
            this.scene = value;
            this.stage.setScene(value);
        }
    }

    public FXMLLoader getLoader() {
        return this.loader;
    }

    public void setLoader(FXMLLoader value) {
        if (!value.equals(this.loader)) {
            this.loader = value;
        }
    }
}
