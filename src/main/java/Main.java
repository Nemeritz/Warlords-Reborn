import App.AppModule;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class for the application, launches the JavaFX render process.
 * Created by Jerry Fan on 21/03/2017.
 */
public class Main extends Application {
    private AppModule app;

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.app = new AppModule();
        this.app.bootstrap(stage);
    }


    /**
     * Entry point for the application.
     * @param args Program arguments.
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}