import App.AppComponent;

import static javafx.application.Application.launch;

/**
 * Created by lichk on 21/03/2017.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println(new AppComponent().getGreeting());
        launch(args);
    }
}