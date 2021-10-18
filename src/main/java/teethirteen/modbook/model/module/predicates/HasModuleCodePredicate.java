package teethirteen.modbook.model.module.predicates;

import java.util.function.Predicate;

import teethirteen.modbook.model.module.Module;
import teethirteen.modbook.model.module.ModuleCode;

/**
 * Tests that a {@code Module}'s {@code ModuleCode} is the same as the given {@code ModuleCode}
 */
public class HasModuleCodePredicate implements Predicate<Module> {

    private final ModuleCode code;

    public HasModuleCodePredicate(ModuleCode code) {
        this.code = code;
    }

    public ModuleCode getCode() {
        return code;
    }

    @Override
    public boolean test(Module mod) {
        return mod.getCode().equals(code);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof HasModuleCodePredicate // instanceof handles nulls
            && code.equals(((HasModuleCodePredicate) other).code)); // state check
    }
}
