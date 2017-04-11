package App.Shared.JFX;

import App.Shared.Observables.ObservableScene;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.AbstractMap.SimpleImmutableEntry;
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
    private MediaPlayer mediaPlayer;
    public boolean active; // Must currently be manually set to true.


    /**
     * Runs on the acquisition of a new stage, where the existing event handlers are attached to the new stage.
     */
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


    /**
     */
    public Set<EventReceiver> getEventReceivers() {
        return this.eventReceivers;
    }

    /**
     * Loads custom FXML components from the specified controller class' directory.
     * @param controller The controller for loaded FXML component.
     * @param classType The class type of the controller.
     * @param fxmlName The name of the FXML file.
     */
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

    /**
     * Loads images from the specified class' directory.
     * @param classType The class type to search from.
     * @param imageName Name of the image file.
     * @return Image object containing the requested image.
     */
    public Image loadImage(Class<?> classType, String imageName) {
        return new Image(classType.getResourceAsStream(imageName));
    }

    /**
     * Sets the current stage used by the JFX service. This must be done as soon as possible.
     * @param value The new stage.
     */
    public void setStage(Stage value) {
        if (!value.equals(this.stage)) {
            if (this.stage != null) {
                this.stage.removeEventHandler(KeyEvent.ANY,  this.keyEventHandler);
            }
            this.stage = value;
            this.setupEventHooks();
        }
    }

    /**
     * Gets a registered scene entry from the JFX service.
     * @param sceneName The name of the scene that was registered in the JFX service.
     * @return An entry set created from a JFX node (component) and a scene containing that node.
     */
    public SimpleImmutableEntry<Parent, Scene> getScene(String sceneName) {
        return this.scenes.get(sceneName);
    }

    /**
     * Registers a new scene with the JFX service. Currently the controller must also be included, although this
     * won't be needed once observable scene chances trigger the scene switch initialisation.
     * @param sceneName The name of the new scene to put on the stage
     * @param value An entry set created from a JFX node (component) and a scene containing that node.
     */
    public void putScene(String sceneName, SimpleImmutableEntry<Parent, Scene> value) {
        if (!value.equals(this.scenes.get(sceneName))) {
            this.scenes.put(sceneName, value);
        }
    }

    /**
     * Sets the current scene of the stage to the specified scene.
     * @param sceneName The name of the scene that was registered in the JFX service.
     */
    public void setScene(String sceneName) {
        Scene scene = this.scenes.get(sceneName).getValue();
        if (scene != null) {
            this.stage.setScene(scene);
            this.sceneChange.setScene(scene);
        }
    }

    /**
     * creates a media player using a mp3 file
     * @param classType class where the media files is located at
     * @param fileName name of the file
     * @return the new mediaplayer created
     */
    public MediaPlayer loadMedia( Class<?> classType, String fileName) {
        this.mediaPlayer = new MediaPlayer(
                new Media(classType.getResource(fileName).toString())
        );
        return this.mediaPlayer;
    }
    
    /**
     * Closes and exits the game
     */
    public void closeStage(){
    	this.stage.close();
    }
}
