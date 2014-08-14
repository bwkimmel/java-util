/*
 * Copyright (c) 2008 Bradley W. Kimmel
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package ca.eandb.util.args;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * An <code>Annotation</code> that indicates to an
 * <code>ArgumentProcessor</code> that an option should be created.  If applied
 * to a field, an option will be added that sets the value of the field.  If
 * applied to a method parameter, an option will be created that sets the value
 * of the parameter before invoking the method.
 * @see ArgumentProcessor#addOption(String, char, Command)
 * @see ArgumentProcessor#processAnnotations(Class)
 * @author Brad Kimmel
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface OptionArgument {

	/**
	 * Gets the key that triggers the option.  That is, a command line argument
	 * consisting of this value preceded by "--" will trigger the option.  If
	 * this value is empty, the name of the field will be used.  This value
	 * must be specified if this <code>OptionArgument</code> is applied to a
	 * method parameter.
	 * @return The key that triggers the option.
	 */
	String value() default "";

	/**
	 * The alternative short key that triggers the option.  A command line
	 * argument consisting of this value preceded by "-" will trigger the
	 * option.  If this value is zero, the first character of
	 * <code>this.value()</code> will be used.
	 * @return The alternative short key that triggers the option.
	 * @see #value()
	 */
	char shortKey() default '\0';

}
