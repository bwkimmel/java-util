package ca.eandb.util.args;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An <code>Annotation</code> that indicates to an
 * <code>ArgumentProcessor</code> that a command should be added.  The declared
 * type of the field to which this annotation is applied will be examined for
 * <code>CommandArgument</code> and <code>OptionArgument</code> annotations.  A
 * child <code>ArgumentProcessor</code> will be created from its annotations.
 * A command will then be added to the first <code>ArgumentProcessor</code>
 * that will pass control to the field using child
 * <code>ArgumentProcessor</code>.
 *
 * If no command is provided to the child <code>ArgumentProcessor</code> when
 * the corresponding command is invoked, a shell will be started.
 * @see ArgumentProcessor#ArgumentProcessor(String)
 * @see ArgumentProcessor#addCommand(String, Command)
 * @see ArgumentProcessor#processAnnotations(Class)
 * @author Brad Kimmel
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ShellArgument {

	/**
	 * Gets the key that triggers the command.  If empty, the name of the
	 * method or field that this <code>CommandArgument</code> is applied to
	 * will be used.
	 * @return The key that triggers the command.
	 */
	String value() default "";

	/**
	 * Gets the string to use to prompt the user for input.  If empty, the
	 * value used by {{@link #value()} will be used to prompt the user.
	 * @return The string to use to prompt the user for input.
	 */
	String prompt() default "";

}
