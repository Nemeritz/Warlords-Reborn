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
public class MenuComponent extends VBox {
    private SharedComponent shared;

    private TitleComponent titleComponent;

    public MenuComponent(SharedComponent shared) {
        this.shared = shared;
        this.titleComponent = new TitleComponent(shared);
    }

    public void transitionTitle() {
        this.titleComponent.setAsRoot();
    }
}
