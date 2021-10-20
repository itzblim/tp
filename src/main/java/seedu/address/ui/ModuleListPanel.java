package seedu.address.ui;

import static seedu.address.commons.core.Messages.MESSAGE_EMPTY_MODULE_LIST;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.module.Module;

/**
 * Panel containing the list of modules.
 */
public class ModuleListPanel extends UiPart<Region> {
    private static final String FXML = "ModuleListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ModuleListPanel.class);

    @FXML
    private ListView<Module> moduleListView;

    @FXML
    private Label placeholder;

    /**
     * Creates a {@code ModuleListPanel} with the given {@code ObservableList}.
     */
    public ModuleListPanel(ObservableList<Module> moduleList) {
        super(FXML);
        placeholder.setText(MESSAGE_EMPTY_MODULE_LIST);
        moduleListView.setItems(moduleList);
        moduleListView.setCellFactory(listView -> new ModuleSummaryViewCell());
    }

    public void showSummaryList() {
        moduleListView.setCellFactory(listView -> new ModuleSummaryViewCell());
    }

    public void showDetailList() {
        moduleListView.setCellFactory(listView -> new ModuleDetailViewCell());
    }

    public void showLessonsList() {
        moduleListView.setCellFactory(listView -> new ModuleLessonsViewCell());
    }

    public void showExamsList() {
        moduleListView.setCellFactory(listView -> new ModuleExamsViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Module} using a {@code ModuleSummaryCard}.
     */
    class ModuleSummaryViewCell extends ListCell<Module> {
        @Override
        protected void updateItem(Module module, boolean empty) {
            super.updateItem(module, empty);

            if (empty || module == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ModuleSummaryCard(module, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Module} using a {@code ModuleDetailCard}.
     */
    class ModuleDetailViewCell extends ListCell<Module> {
        @Override
        protected void updateItem(Module module, boolean empty) {
            super.updateItem(module, empty);

            if (empty || module == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ModuleDetailCard(module).getRoot());
            }
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Module} using a {@code ModuleLessonsCard}.
     */
    class ModuleLessonsViewCell extends ListCell<Module> {
        @Override
        protected void updateItem(Module module, boolean empty) {
            super.updateItem(module, empty);

            if (empty || module == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ModuleLessonsCard(module).getRoot());
            }
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Module} using a {@code ModuleExamsCard}.
     */
    class ModuleExamsViewCell extends ListCell<Module> {
        @Override
        protected void updateItem(Module module, boolean empty) {
            super.updateItem(module, empty);

            if (empty || module == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ModuleExamsCard(module).getRoot());
            }
        }
    }
}
