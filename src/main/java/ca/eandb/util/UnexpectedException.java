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
 * An <code>UnexpectedException</code> is thrown to indicate that a checked
 * exception was caught that was not expected to be thrown.  For example, if a
 * method declares that it throws <code>MyException</code>, but steps were
 * taken in the calling code to ensure that <code>MyException</code> would not
 * be thrown and <code>MyException</code> was nonetheless thrown, the calling
 * code may throw an <code>UnexpectedException</code>.  In other words, a
 * statement throwing an <code>UnexpectedException</code> is essentially an
 * assertion declaring that that line of code will not be reached.
 *
 * @author Brad Kimmel
 */
public class UnexpectedException extends RuntimeException {

  /**
   * Serialization version ID.
   */
  private static final long serialVersionUID = 5836524376615051737L;

  /**
   * Creates a new <code>UnexpectedException</code>.
   */
  public UnexpectedException() {
    super();
  }

  /**
   * Creates a new <code>UnexpectedException</code>.
   * @param message The detailed message describing the condition.
   * @param cause The cause of the <code>UnexpectedException</code>.
   */
  public UnexpectedException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Creates a new <code>UnexpectedException</code>.
   * @param message The detailed message describing the condition.
   */
  public UnexpectedException(String message) {
    super(message);
  }

  /**
   * Creates a new <code>UnexpectedException</code>.
   * @param cause The cause of the <code>UnexpectedException</code>.
   */
  public UnexpectedException(Throwable cause) {
    super(cause);
  }


}
