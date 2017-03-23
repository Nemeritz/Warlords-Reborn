package App; /**
 * Created by lichk on 21/03/2017.
 */

import App.Menu.MenuModule;
import App.Menu.Title.TitleComponent;
import App.Shared.SharedComponent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppModule {

    private SharedComponent shared;
    private MenuModule menu;

    public AppModule()  {
        // Provides SharedComponent
        this.shared = new SharedComponent();

        this.menu = new MenuModule(this.shared);
    }

    public void bootstrap(Stage stage) {
        // Set the stage and scene in the JFX service for shared use
        this.shared.getJFX().setStage(stage);

        stage.setResizable(false);
        stage.setTitle("Warlords Reborn");

        this.menu.transitionTitle();
        stage.show();
    }
}
