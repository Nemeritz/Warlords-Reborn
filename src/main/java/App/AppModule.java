package App;

/**
 * Created by Jerry Fan on 21/03/2017.
 */

import App.Game.GameComponent;
import App.Menu.MenuComponent;
import App.Shared.SharedModule;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.AbstractMap.SimpleImmutableEntry;

public class AppModule {
    private SharedModule shared;

    /**
     * AppModule default constructor. Provides the shared module for all
     * child components and creates the scenes.
     */
    public AppModule()  {
        // Provides SharedModule
        this.shared = new SharedModule();
        this.shared.getJFX().active = true;

        // Create the menu and game scenes
        MenuComponent menu = new MenuComponent(this.shared);
        Scene menuScene = new Scene(menu);
        this.shared.getJFX().putScene("menu",
                new SimpleImmutableEntry<>(menu, menuScene));

        GameComponent game = new GameComponent(this.shared);
         Scene gameScene = new Scene(game);
         this.shared.getJFX().putScene("game",
                 new SimpleImmutableEntry<>(game, gameScene));
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
        this.shared.getJFX().setScene("menu");

        stage.show();
    }
}
