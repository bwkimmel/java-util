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

package ca.eandb.util;

/**
 * An <code>UnexpetedException</code> that is thrown to indicate that a method
 * has not yet been implemented.
 * @author brad
 */
public final class UnimplementedException extends UnexpectedException {

	/**
	 * Serialization version ID.
	 */
	private static final long serialVersionUID = -9112158490754682865L;

	/**
	 * Creates a new <code>UnimplementedException</code>.
	 */
	public UnimplementedException() {
		super("Not implemented");
	}

	/**
	 * Creates a new <code>UnimplementedException</code>.
	 * @param message A <code>String</code> describing the exceptional
	 * 		condition.
	 * @param cause The <code>Throwable</code> that caused this exception.
	 */
	public UnimplementedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a new <code>UnimplementedException</code>.
	 * @param message A <code>String</code> describing the exceptional
	 * 		condition.
	 */
	public UnimplementedException(String message) {
		super(message);
	}

	/**
	 * Creates a new <code>UnimplementedException</code>.
	 * @param cause The <code>Throwable</code> that caused this exception.
	 */
	public UnimplementedException(Throwable cause) {
		super("Not implemented", cause);
	}

}
