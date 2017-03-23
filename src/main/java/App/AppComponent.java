package App; /**
 * Created by lichk on 21/03/2017.
 */

import App.Menu.MenuComponent;
import App.Menu.Title.TitleComponent;
import App.Shared.SharedComponent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppComponent{

    private SharedComponent shared;
    private MenuComponent menu;

    public AppComponent(Stage stage)  {
        // Provides SharedComponent
        this.shared = new SharedComponent();
        this.shared.getJFX().setStage(stage);

        this.menu = new MenuComponent(this.shared);
    }

    public void bootstrap() {
        // Set the stage and scene in the JFX service for shared use
        Stage stage = this.shared.getJFX().getStage();

        stage.setResizable(false);
        stage.setTitle("Warlords Reborn");

        this.menu.transitionTitle();
        stage.show();
    }
}
