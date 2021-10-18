package t13.modbook.model.module;

import static java.util.Objects.requireNonNull;
import static t13.modbook.commons.util.AppUtil.checkArgument;

/**
 * Represents the name of a Module in the ModBook.
 * Guarantees: immutable; is valid as declared in {@link #isValidModuleName(String)}
 */
public class ModuleName {
    /*
     * The first character of the module must not be a whitespace.
     */
    public static final String VALIDATION_REGEX = "^\\S.*$";
    public static final String MESSAGE_CONSTRAINTS =
            "Module name should not be blank";
    public final String moduleName;

    /**
     * Constructs an {@code ModuleName}.
     *
     * @param moduleName A valid module name.
     */
    public ModuleName(String moduleName) {
        requireNonNull(moduleName);
        checkArgument(isValidModuleName(moduleName), MESSAGE_CONSTRAINTS);
        this.moduleName = moduleName;
    }

    /**
     * Returns true if a given string is a valid name.
     *
     * @param test A string to test for validity.
     */
    public static boolean isValidModuleName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getModuleName() {
        return moduleName;
    }

    @Override
    public String toString() {
        return moduleName;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ModuleName)) {
            return false;
        }
        return this.moduleName.equals(((ModuleName) other).moduleName);
    }

    @Override
    public int hashCode() {
        return moduleName.hashCode();
    }
}
