package App; /**
 * Created by lichk on 21/03/2017.
 */

import App.Menu.Title.TitleComponent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppComponent{
    public AppComponent(Stage stage)  {
        TitleComponent titleComponent = new TitleComponent();
        stage.setTitle("Warlords Reborn");
        stage.setScene(new Scene(titleComponent));
        stage.show();
    }
}
