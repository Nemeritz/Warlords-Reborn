package App;

/**
 * Created by lichk on 21/03/2017.
 */

import App.Game.GameComponent;
import App.Menu.MenuComponent;
import App.Shared.SharedModule;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppModule {
    private SharedModule shared;

    /**
     * AppModule default constructor. Provides the shared module for all
     * child components and creates the scenes.
     */
    public AppModule()  {
        // Provides SharedModule
        this.shared = new SharedModule();

        // Create the menu and game scenes
        Scene menuScene = new Scene(new MenuComponent(this.shared));
        this.shared.getJFX().putScene("menu", menuScene);

        // Scene gameScene = new Scene(new GameComponent(this.shared));
        // this.shared.getJFX().putScene("game", gameScene);
    }

    /**
     * @param stage The stage to bootstrap the application on. Scenes will be
     *              loaded on to here.
     */
    public void bootstrap(Stage stage) {
        // Set the stage and scene in the JFX service for shared use
        this.shared.getJFX().setStage(stage);

        stage.setResizable(false);
        stage.setTitle("Warlords Reborn");

        // Initially change scene to the menu
        this.shared.getJFX().getStage().setScene(
                this.shared.getJFX().getScene("menu")
        );

        stage.show();
    }
}
