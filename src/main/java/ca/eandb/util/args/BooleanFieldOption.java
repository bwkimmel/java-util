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

import java.util.Queue;

/**
 * Represents a command line option that represents a boolean value.  The
 * corresponding field in the application state object is assigned the value
 * <code>true</code> if this option is present.
 * @author Brad Kimmel
 */
public class BooleanFieldOption<T> extends AbstractFieldOption<T> {

	/**
	 * Creates a new <code>BooleanFieldOption</code>.
	 * @param fieldName The name of the field to assign to in the application
	 * 		state object.
	 */
	public BooleanFieldOption(String fieldName) {
		super(fieldName);
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.args.AbstractFieldOption#getOptionValue(java.util.Queue)
	 */
	@Override
	protected Object getOptionValue(Queue<String> argq) {
		return true;
	}

}
