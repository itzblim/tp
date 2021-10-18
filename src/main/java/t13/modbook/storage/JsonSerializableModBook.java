package t13.modbook.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import t13.modbook.commons.exceptions.IllegalValueException;
import t13.modbook.model.ModBook;
import t13.modbook.model.ReadOnlyModBook;
import t13.modbook.model.module.Module;

/**
 * An Immutable ModBook that is serializable to JSON format.
 */
@JsonRootName(value = "modbook")
class JsonSerializableModBook {

    public static final String MESSAGE_DUPLICATE_MODULE = "Modules list contains duplicate module(s).";

    private final List<JsonAdaptedModule> modules = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableModBook} with the given modules.
     */
    @JsonCreator
    public JsonSerializableModBook(@JsonProperty("modules") List<JsonAdaptedModule> modules) {
        this.modules.addAll(modules);
    }

    /**
     * Converts a given {@code ReadOnlyModBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableModBook}.
     */
    public JsonSerializableModBook(ReadOnlyModBook source) {
        modules.addAll(source.getModuleList().stream().map(JsonAdaptedModule::new).collect(Collectors.toList()));
    }

    /**
     * Converts this mod book into the model's {@code ModBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ModBook toModelType() throws IllegalValueException {
        ModBook modBook = new ModBook();
        for (JsonAdaptedModule jsonAdaptedModule : modules) {
            Module module = jsonAdaptedModule.toModelType();
            if (modBook.hasModule(module)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MODULE);
            }
            modBook.addModule(module);
        }
        return modBook;
    }

}
