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

import java.lang.reflect.Field;
import java.util.Queue;

import ca.eandb.util.UnexpectedException;

/**
 * @author brad
 *
 */
public abstract class AbstractFieldOption<T> implements Command<T> {

	private final String fieldName;

	public AbstractFieldOption(String fieldName) {
		this.fieldName = fieldName;
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.args.Command#process(java.util.Queue, java.lang.Object)
	 */
	public final void process(Queue<String> argq, T state) {
		try {
			Field field = state.getClass().getField(fieldName);
			Object value = getOptionValue(argq);
			field.set(state, value);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			throw new UnexpectedException(e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new UnexpectedException(e);
		}
	}

	protected abstract Object getOptionValue(Queue<String> argq);

}
