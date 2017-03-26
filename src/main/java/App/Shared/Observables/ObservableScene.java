package App.Shared.Observables;

import javafx.beans.NamedArg;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.util.Observable;

/**
 * Created by lichk on 27/03/2017.
 */
public class ObservableScene extends Observable {
    Scene scene;

    public ObservableScene() {
    }

    public Scene getScene() {
        return this.scene;
    }

    public void setScene(Scene value)  {
        if (!value.equals(scene)) {
            this.scene = value;
            this.setChanged();
            this.notifyObservers();
        }
    }
}
