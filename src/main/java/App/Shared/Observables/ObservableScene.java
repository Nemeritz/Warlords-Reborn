package App.Shared.Observables;

import javafx.scene.Scene;

import java.util.AbstractMap;
import java.util.Observable;

/**
 * Created by Jerry Fan on 27/03/2017.
 */
public class ObservableScene extends Observable {
    AbstractMap.SimpleImmutableEntry<String, Scene> sceneTuple;

    /**
     * Constructor for the observable scene
     */

    /**
     * @return the scene
     */
    public AbstractMap.SimpleImmutableEntry<String, Scene> current() {
        return this.sceneTuple;
    }

    /**
     * @param sceneName
     * @param value if not equal to current scene then notify observers that the scene has changed
     */
    public void setScene(String sceneName, Scene value)  {
        if (this.sceneTuple == null || !sceneName.equals(this.sceneTuple.getKey()) || !value.equals(this.sceneTuple
                .getValue())) {
            this.sceneTuple = new AbstractMap.SimpleImmutableEntry<>(sceneName, value);
            this.setChanged();
            this.notifyObservers();
        }
    }
}
