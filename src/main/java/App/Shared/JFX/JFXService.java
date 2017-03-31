package App.Shared.JFX;

import App.Shared.Observables.ObservableScene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lichk on 23/03/2017.
 */
public class JFXService {
    private Stage stage;
    private Map<String,SimpleImmutableEntry<Parent, Scene>> scenes;
    private ObservableScene sceneChange;
    private boolean active;

    public JFXService() {
        this.active = false;
        this.scenes = new ConcurrentHashMap<>();
        this.sceneChange = new ObservableScene();
    }

    public void loadFXML(Object controller, Class<?> classType, String fxmlName) {
        if (this.active) {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    classType.getResource(fxmlName)
            );
            fxmlLoader.setRoot(controller);
            fxmlLoader.setController(controller);

            try {
                fxmlLoader.load();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    public Image loadImage(Class<?> classType, String imageName) {
        return new Image(classType.getResourceAsStream(imageName));
    }

    public void setStage(Stage value) {
        if (!value.equals(this.stage)) {
            this.stage = value;
        }
    }

    public ObservableScene observeSceneChange() {
        return this.sceneChange;
    }

    public SimpleImmutableEntry<Parent, Scene> getScene(String sceneName) {
        return this.scenes.get(sceneName);
    }

    public void putScene(String sceneName, SimpleImmutableEntry<Parent, Scene> value) {
        if (!value.equals(this.scenes.get(sceneName))) {
            this.scenes.put(sceneName, value);
        }
    }

    public void setScene(String sceneName) {
        Scene scene = this.scenes.get(sceneName).getValue();
        if (scene != null) {
            this.stage.setScene(scene);
            this.sceneChange.setScene(scene);
        }
    }

    public void closeStage(){
    	this.stage.close();
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
