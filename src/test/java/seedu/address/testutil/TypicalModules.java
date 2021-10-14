package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.ModBook;
import seedu.address.model.module.Module;
import seedu.address.testutil.builders.ModuleBuilder;

/**
 * A utility class containing a list of {@code Module} objects to be used in tests.
 */
public class TypicalModules {
    public static final Module DEFAULT_MODULE = new ModuleBuilder().build();
    public static final Module NAMED_MODULE = new ModuleBuilder().withDefaultName().build();
    public static final Module MODULE_WITH_LESSON = new ModuleBuilder().withDefaultName()
            .withLessons(TypicalLessons.DEFAULT_LESSON).build();
    public static final Module MODULE_WITH_EXAM = new ModuleBuilder().withDefaultName()
            .withExams(TypicalExams.DEFAULT_EXAM).build();

    // Computer Science modules for some real life modules
    public static final Module CS2103T_NO_NAME = new ModuleBuilder().withCode("CS2103T").build();
    public static final Module CS2103T_CODE_NAME = new ModuleBuilder().withCode("CS2103T")
            .withName("Software Engineering").build();
    public static final Module CS2103T = new ModuleBuilder().withCode("CS2103T").withName("Software Engineering")
            .withLessons(TypicalLessons.CS2103T_LECTURE, TypicalLessons.CS2103T_TUTORIAL)
            .withExams(TypicalExams.CS2103T_PRACTICAL, TypicalExams.CS2103T_FINALS).build();
    public static final Module CS2040S = new ModuleBuilder().withCode("CS2040S")
            .withName("Data Structures and Algorithms").withLessons(TypicalLessons.ONLINE_LECTURE)
            .withExams(TypicalExams.ONLINE_FINALS).build();
    public static final Module CS1231S = new ModuleBuilder().withCode("CS1231S")
            .withName("Discrete Structures").withLessons(TypicalLessons.ONLINE_TUTORIAL)
            .withExams(TypicalExams.ONLINE_MIDTERMS).build();
    public static final Module CS2030S = new ModuleBuilder().withCode("CS2030S")
            .withName("Programming Methodology II").build();
    public static final Module MA1521 = new ModuleBuilder().withCode("MA1521")
            .withName("Calculus for Computing").withLessons(TypicalLessons.DEFAULT_LESSON)
            .withExams(TypicalExams.DEFAULT_EXAM).build();

    private TypicalModules() {} // prevents instantiation

    /**
     * Returns an {@code ModBook} with all the typical modules.
     */
    public static ModBook getTypicalModBook() {
        ModBook mb = new ModBook();
        for (Module module : getTypicalModules()) {
            mb.addModule(module);
        }
        return mb;
    }

    public static List<Module> getTypicalModules() {
        return new ArrayList<>(Arrays.asList(CS2103T, CS2040S, CS1231S));
    }
}
