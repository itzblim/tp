package t13.modbook.model;

import javafx.collections.ObservableList;
import t13.modbook.model.module.Module;

/**
 * Unmodifiable view of a module book
 */
public interface ReadOnlyModBook {

    /**
     * Returns an unmodifiable view of the modules list.
     * This list will not contain any duplicate modules.
     */
    ObservableList<Module> getModuleList();
}
