package App.Shared.JFX;

import App.Shared.Observables.ObservableScene;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * The JFX Service provides functionality from the JFX library that does not belong to any individual component.
 * Created by Jerry Fan on 23/03/2017.
 */
public class JFXService {
    private Stage stage; // stage for the game
    private ObservableScene sceneChange; // notices all observers that scene has changed
    private Map<String,SimpleImmutableEntry<Parent, Scene>> scenes;
    private Set<EventReceiver> eventReceivers;
    private EventHandler<KeyEvent> keyEventHandler;
    public boolean active;

    private void setupEventHooks() {

        EventHandler<KeyEvent> keyAnyHandler = (key) -> {
            for (EventReceiver receiver : this.eventReceivers) {
                receiver.onKeyEvent(key);
            }
        };
        this.stage.addEventHandler(KeyEvent.ANY, keyAnyHandler);
        this.keyEventHandler = keyAnyHandler;
    }

    /**
     * Default constructor for the JFX Service.
     */
    public JFXService() {
        this.active = false;
        this.scenes = new ConcurrentHashMap<>();
        this.sceneChange = new ObservableScene();
        this.eventReceivers = new CopyOnWriteArraySet<>();
    }


    public void addEventReceiver(EventReceiver receiver) {
        this.eventReceivers.add(receiver);
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
            if (this.stage != null) {
                this.stage.removeEventHandler(KeyEvent.ANY,  this.keyEventHandler);
            }
            this.stage = value;
            this.setupEventHooks();
        }
    }

    public ObservableScene observeSceneChange() {
        return this.sceneChange;
    }

    public SimpleImmutableEntry<Parent, Scene> getScene(String sceneName) {
        return this.scenes.get(sceneName);
    }

    /**
     * @param sceneName the new scene to put on the stage
     * @param value
     */
    public void putScene(String sceneName, SimpleImmutableEntry<Parent, Scene> value) {
        if (!value.equals(this.scenes.get(sceneName))) {
            this.scenes.put(sceneName, value);
        }
    }

    /**
     * @param sceneName the key of the scene
     *                  sets the current scene to the value of the scene according to the key
     */
    public void setScene(String sceneName) {
        Scene scene = this.scenes.get(sceneName).getValue();
        if (scene != null) {
            this.stage.setScene(scene);
            this.sceneChange.setScene(scene);
        }
    }

    /**
     * Closes and exits the game
     */
    public void closeStage(){
    	this.stage.close();
    }
}
