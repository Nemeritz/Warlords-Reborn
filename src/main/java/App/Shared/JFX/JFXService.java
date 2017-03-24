package App.Shared.JFX;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lichk on 23/03/2017.
 */
public class JFXService {
    private Stage stage;
    private Map<String,Scene> scenes;

    public JFXService() {
        this.scenes = new ConcurrentHashMap<>();
    }

    public FXMLLoader loadFXML(Object controller, Class<?> classType,
                                   String resourceName) {
        FXMLLoader fxmlLoader = new FXMLLoader(
                classType.getResource(resourceName)
        );
        fxmlLoader.setRoot(controller);
        fxmlLoader.setController(controller);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        return fxmlLoader;
    }

    public Stage getStage() {
        return this.stage;
    }

    public void setStage(Stage value) {
        if (!value.equals(this.stage)) {
            this.stage = value;
        }
    }

    public Scene getScene(String sceneName) {
        return this.scenes.get(sceneName);
    }

    public void putScene(String sceneName, Scene value) {
        if (!value.equals(this.scenes.get(sceneName))) {
            this.scenes.put(sceneName, value);
        }
    }
}
