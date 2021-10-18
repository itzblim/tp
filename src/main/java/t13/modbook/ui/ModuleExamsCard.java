package t13.modbook.ui;

import java.util.stream.IntStream;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import t13.modbook.model.module.Module;

/**
 * A UI component that displays exams of a {@code Module}.
 */
public class ModuleExamsCard extends UiPart<Region> {

    private static final String FXML = "ModuleExamsCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Module module;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label exams;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code ModuleCode} with the given {@code Module} and index to display.
     */
    public ModuleExamsCard(Module module) {
        super(FXML);
        this.module = module;
        String moduleHeader = module.getCode().toString();
        if (module.getName().isPresent()) {
            moduleHeader += String.format(" %s", module.getName().get());
        }
        name.setText(moduleHeader);
        // set Exams
        String examsString = (module.getExams().isEmpty())
            ? "No Exams available. :)"
            : "Exams:\n" + String.join("\n", IntStream.range(0, module.getExams().size())
                    .mapToObj(i -> String.format("%d.%s", i + 1, module.getExams().get(i)))
                    .toArray(String[]::new));
        exams.setText(examsString);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModuleExamsCard)) {
            return false;
        }

        // state check
        ModuleExamsCard card = (ModuleExamsCard) other;
        return id.getText().equals(card.id.getText())
                && module.equals(card.module);
    }
}
