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

package ca.eandb.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

/**
 * An <code>ObjectInputStream</code> that uses a provided
 * <code>ClassLoader</code> to load new classes.
 * @author Brad Kimmel
 */
public final class AlternateClassLoaderObjectInputStream extends
    ObjectInputStream {

  /** The <code>ClassLoader</code> to use. */
  private final ClassLoader loader;

  /**
   * Creates a new <code>AlternateClassLoaderObjectInputStream</code>.
   * @param in The <code>InputStream</code> to read from.
   * @param loader The <code>ClassLoader</code> to use.
   * @throws IOException if the stream cannot be read from
   */
  public AlternateClassLoaderObjectInputStream(InputStream in, ClassLoader loader)
      throws IOException {
    super(in);
    this.loader = loader;
  }

  /* (non-Javadoc)
   * @see java.io.ObjectInputStream#resolveClass(java.io.ObjectStreamClass)
   */
  @Override
  protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException,
      ClassNotFoundException {
    return Class.forName(desc.getName(), true, loader);
  }

}
