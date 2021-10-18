package t13.modbook.model.module;

import static java.util.Objects.requireNonNull;
import static t13.modbook.commons.util.AppUtil.checkArgument;

/**
 * Represents the code of a Module in the ModBook.
 * Guarantees: immutable; is valid as declared in {@link #isValidModuleCode(String)}
 */
public class ModuleCode {
    /*
     * "Each module of study has a unique module code consisting of a two‐ or three‐letter prefix
     * that denotes the discipline, and four digits, the first of which indicates the level of
     * the module (e.g., 1000 indicates a Level 1 module and 2000, a Level 2 module)."
     * There is also an optional last character representing the course of study.
     * Reference: https://www.nus.edu.sg/registrar/academic-information-policies/non-graduating/modular-system
     */
    public static final String VALIDATION_REGEX = "^([a-zA-Z]{2,3}[0-9]{4}[a-zA-Z]?)$";
    public static final String MESSAGE_CONSTRAINTS =
            "Module Code must have a two or three letter prefix that denotes disciple, four digits "
                    + "which represents the level of the module, and an optional last character that "
                    + "represents the course of study.";
    public final String moduleCode;

    /**
     * Constructs a {@code ModuleCode}.
     *
     * @param moduleCode A valid module code.
     */
    public ModuleCode(String moduleCode) {
        requireNonNull(moduleCode);
        checkArgument(isValidModuleCode(moduleCode), MESSAGE_CONSTRAINTS);
        this.moduleCode = moduleCode.toUpperCase();
    }

    public String getModuleCode() {
        return moduleCode;
    }

    /**
     * Returns true if a given string is a valid code.
     *
     * @param test A string to test for validity.
     */
    public static boolean isValidModuleCode(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return moduleCode;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ModuleCode)) {
            return false;
        }
        return this.moduleCode.equals(((ModuleCode) other).moduleCode);
    }

    @Override
    public int hashCode() {
        return moduleCode.hashCode();
    }
}
