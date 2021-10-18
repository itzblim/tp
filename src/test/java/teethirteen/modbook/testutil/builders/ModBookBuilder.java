package teethirteen.modbook.testutil.builders;

import teethirteen.modbook.model.ModBook;
import teethirteen.modbook.model.module.Module;

/**
 * A utility class to help with building ModBook objects.
 * Example usage: <br>
 *     {@code ModBook mb = new ModBookBuilder().withModule(CS2103T, CS2040S).build();}
 */
public class ModBookBuilder {

    private ModBook modBook;

    public ModBookBuilder() {
        modBook = new ModBook();
    }

    public ModBookBuilder(ModBook modBook) {
        this.modBook = modBook;
    }

    /**
     * Adds new {@code Module} objects to the {@code ModBook} that we are building.
     */
    public ModBookBuilder withModule(Module... modules) {
        for (Module module : modules) {
            modBook.addModule(module);
        }
        return this;
    }

    public ModBook build() {
        return modBook;
    }
}
