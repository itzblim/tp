package teethirteen.modbook.logic.commands.delete;

import static java.util.Objects.requireNonNull;

import java.util.List;

import teethirteen.modbook.commons.core.Messages;
import teethirteen.modbook.commons.core.index.Index;
import teethirteen.modbook.commons.util.CollectionUtil;
import teethirteen.modbook.logic.commands.CommandResult;
import teethirteen.modbook.logic.commands.exceptions.CommandException;
import teethirteen.modbook.logic.parser.CliSyntax;
import teethirteen.modbook.model.Model;
import teethirteen.modbook.model.module.Module;
import teethirteen.modbook.model.module.ModuleCode;
import teethirteen.modbook.model.module.exam.Exam;

public class DeleteExamCommand extends DeleteCommand {
    public static final String MESSAGE_DELETE_EXAM_SUCCESS = "Deleted Exam %s from Module %s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an Exam from a "
            + "specified Module from the Mod book."
            + "\nParameters: Index "
            + CliSyntax.PREFIX_CODE + "MOD_CODE"
            + "\nExample: " + COMMAND_WORD + " exam 1 "
            + CliSyntax.PREFIX_CODE + "CS2103T";
    private final Index targetIndex;
    private final ModuleCode targetModuleCode;

    /**
     * Creates an DeleteExamCommand to delete the Exam at specified {@code Index} of the specified {@code Module}.
     */
    public DeleteExamCommand(Index targetIndex, ModuleCode targetModuleCode) {
        CollectionUtil.requireAllNonNull(targetIndex, targetModuleCode);
        this.targetIndex = targetIndex;
        this.targetModuleCode = targetModuleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Module module = model.getModule(targetModuleCode);
        List<Exam> exams = module.getExams();
        if (targetIndex.getZeroBased() >= exams.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXAM_DISPLAYED_INDEX);
        }
        Exam examToDelete = exams.get(targetIndex.getZeroBased());
        model.deleteExam(module, examToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_EXAM_SUCCESS, examToDelete.getName(), targetModuleCode));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DeleteExamCommand)) {
            return false;
        }
        return targetIndex.equals(((DeleteExamCommand) other).targetIndex)
                && targetModuleCode.equals(((DeleteExamCommand) other).targetModuleCode);
    }
}
