package teethirteen.modbook.ui;

import java.util.stream.IntStream;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import teethirteen.modbook.model.module.Module;

/**
 * An UI component that displays information of a {@code Module}'s details.
 */
public class ModuleDetailCard extends UiPart<Region> {

    private static final String FXML = "ModuleDetailCard.fxml";

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
    private Label lessons;
    @FXML
    private Label exams;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code ModuleCode} with the given {@code Module} and index to display.
     */
    public ModuleDetailCard(Module module) {
        super(FXML);
        this.module = module;
        String moduleHeader = module.getCode().toString();
        if (module.getName().isPresent()) {
            moduleHeader += String.format(" %s", module.getName().get());
        }
        name.setText(moduleHeader);
        // set Lessons
        String lessonsString = (module.getLessons().isEmpty())
            ? "No Lessons available. :("
            : "Lessons:\n" + String.join("\n", IntStream.range(0, module.getLessons().size())
                    .mapToObj(i -> String.format("%d.%s", i + 1, module.getLessons().get(i)))
                    .toArray(String[]::new));
        lessons.setText(lessonsString);
        // set Exams
        String examsString = (module.getExams().isEmpty())
            ? "\nNo Exams available. :)"
            : "\nExams:\n" + String.join("\n", IntStream.range(0, module.getExams().size())
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
        if (!(other instanceof ModuleDetailCard)) {
            return false;
        }

        // state check
        ModuleDetailCard card = (ModuleDetailCard) other;
        return id.getText().equals(card.id.getText())
                && module.equals(card.module);
    }
}
