package t13.modbook.testutil;

import static t13.modbook.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static t13.modbook.logic.parser.CliSyntax.PREFIX_CODE;
import static t13.modbook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static t13.modbook.logic.parser.CliSyntax.PREFIX_NAME;
import static t13.modbook.logic.parser.CliSyntax.PREFIX_PHONE;
import static t13.modbook.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.modbook.logic.commands.EditCommand.EditPersonDescriptor;
import t13.modbook.logic.commands.add.AddCommand;
import t13.modbook.model.module.Module;
import seedu.modbook.model.person.Person;
import seedu.modbook.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns an add command string for adding the {@code module}.
     */
    public static String getAddCommand(Module module) {
        return AddCommand.COMMAND_WORD + " mod " + getModuleDetails(module);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");
        person.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code module}'s details.
     */
    public static String getModuleDetails(Module module) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_CODE + module.getCode().toString() + " ");
        if (module.getName().isPresent()) {
            sb.append(PREFIX_NAME + module.getName().toString() + " ");
        }
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
