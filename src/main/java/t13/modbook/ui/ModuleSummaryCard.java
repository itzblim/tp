package t13.modbook.ui;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import t13.modbook.model.module.Module;
import t13.modbook.model.module.exam.exceptions.ExamNotFoundException;

/**
 * An UI component that displays information of a {@code Module}.
 */
public class ModuleSummaryCard extends UiPart<Region> {

    private static final String FXML = "ModuleSummaryCard.fxml";

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
    private Label nextLesson;
    @FXML
    private Label nextExam;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code ModuleCode} with the given {@code Module} and index to display.
     */
    public ModuleSummaryCard(Module module, int displayedIndex) {
        super(FXML);
        this.module = module;
        id.setText(displayedIndex + ". ");
        String moduleHeader = module.getCode().toString();
        if (module.getName().isPresent()) {
            moduleHeader += String.format(" %s", module.getName().get());
        }
        name.setText(moduleHeader);
        if (module.getLessons().isEmpty()) {
            nextLesson.setText("No lessons added");
        } else {
            nextLesson.setText(String.format("Next Lesson: %s", module.getNextLesson()));
        }
        if (module.getExams().isEmpty()) {
            nextExam.setText("No exams added");
        } else {
            try {
                nextExam.setText(String.format("Next Exam: %s", module.getNextExam()));
            } catch (ExamNotFoundException e) {
                nextExam.setText("No upcoming exams");
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModuleSummaryCard)) {
            return false;
        }

        // state check
        ModuleSummaryCard card = (ModuleSummaryCard) other;
        return id.getText().equals(card.id.getText())
                && module.equals(card.module);
    }
}
