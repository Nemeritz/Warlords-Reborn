import App.AppModule;
import App.Menu.Title.TitleComponent;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Jerry Fan on 21/03/2017.
 */
public class Main extends Application {
    private AppModule app;//creates a new game app

    @Override
    public void start(Stage stage) throws Exception {
        this.app = new AppModule();
        this.app.bootstrap(stage);
    }

    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}