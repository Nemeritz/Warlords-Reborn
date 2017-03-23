package App.Menu;

import App.Menu.Title.TitleComponent;
import App.Shared.SharedComponent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by lichk on 21/03/2017.
 */
public class MenuModule {
    private SharedComponent shared;

    private TitleComponent titleComponent;

    private void construct() {
        this.titleComponent = new TitleComponent(shared);
    }

    public MenuModule() {
        this.construct();
    }

    public MenuModule(SharedComponent shared) {
        this.shared = shared;
        this.construct();
    }

    public void transitionTitle() {
        this.shared.getJFX().setScene(this.titleComponent.getScene());
        this.titleComponent.loadView();
    }
}
