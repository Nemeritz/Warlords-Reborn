package App.Shared.Interfaces;

/**
 * Interface for classes that need to deregister as listeners. Prepare object to be garbage collected to prevent
 * memory leaks occurring due to leftover references.
 * Created by Jerry Fan on 8/04/2017.
 */
public interface Disposable {

    /**
     * Unload the object, remove from listener lists etc.
     */
    public void dispose();
}
