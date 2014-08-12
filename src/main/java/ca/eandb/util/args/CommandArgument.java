package ca.eandb.util.args;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * An <code>Annotation</code> that indicates to an
 * <code>ArgumentProcessor</code> that a command should be added.  If this
 * annotation is applied to a method, a command will be added that calls the
 * method.  If applied to a field, the declared type of the field will be
 * examined for <code>CommandArgument</code> and <code>OptionArgument</code>
 * annotations.  A child <code>ArgumentProcessor</code> will be created from
 * its annotations.  A command will then be added to the first
 * <code>ArgumentProcessor</code> that will pass control to the field using
 * child <code>ArgumentProcessor</code>.
 * @see ArgumentProcessor#addCommand(String, Command)
 * @see ArgumentProcessor#processAnnotations(Class)
 * @author Brad Kimmel
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandArgument {

	/**
	 * Gets the key that triggers the command.  If empty, the name of the
	 * method or field that this <code>CommandArgument</code> is applied to
	 * will be used.
	 * @return The key that triggers the command.
	 */
	String value() default "";

}
