package App.Shared.JFX;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by lichk on 23/03/2017.
 */
public class JFXService {
    private Stage stage;
    private Scene scene;

    public JFXService() {
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
        }
    }
}
