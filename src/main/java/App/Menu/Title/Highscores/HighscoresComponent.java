package App.Menu.Title.Highscores;

import App.Game.GameComponent;
import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.AbstractMap.SimpleImmutableEntry;

/**
 * Created by Hanliang Ding on 09/04/2017.
 */
public class HighscoresComponent extends BorderPane {
    private SharedModule shared;
    /**
     * Constructs the Match options component
     */
    private void construct() {
        this.shared.getJFX().loadFXML(this, HighscoresComponent.class,
                "HighscoresComponent.fxml");
    }

    /**
     * Default Constructor for the title component
     */
    public HighscoresComponent() {
        this.construct();
    }

    /**
     * Constructor
     * @param shared access to the JFX scenes and stages
     */
    public HighscoresComponent(SharedModule shared) {
        this.shared = shared;
        this.construct();
    }
}
