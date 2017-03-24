package App.Menu;

import App.Menu.Title.TitleComponent;
import App.Shared.SharedModule;
import App.Shared.SharedModule;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Created by lichk on 25/03/2017.
 */
public class MenuComponent extends Pane {
    private SharedModule shared;

    @FXML
    private Pane menuWrapper;

    private TitleComponent title;

    private void construct() {
        this.title = new TitleComponent(shared);

        this.shared.getJFX().loadFXML(this, MenuComponent.class,
                "MenuComponent.fxml");

        // Load the title by default
        this.transitionTitle();
    }

    public MenuComponent() {
        this.construct();
    }

    public MenuComponent(SharedModule shared) {
        this.shared = shared;
        this.construct();
    }

    public void transitionTitle() {
        this.menuWrapper.getChildren().removeAll();
        this.menuWrapper.getChildren().add(this.title);
    }
}
