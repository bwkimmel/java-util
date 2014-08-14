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

import java.io.OutputStream;

/**
 * An <code>OutputStream</code> that ignores everything written to it.  This
 * class is a singleton.
 * @author Brad Kimmel
 */
public final class NullOutputStream extends OutputStream {

  /** The single instance of <code>NullOutputStream</code>. */
  private static NullOutputStream instance;

  /**
   * Creates a new <code>NullOutputStream</code>.  This constructor is
   * private because this class is a singleton.
   */
  private NullOutputStream() {
    /* nothing to do */
  }

  /**
   * Gets the single instance of <code>NullOutputStream</code>.
   * @return The single instance of <code>NullOutputStream</code>.
   */
  public static NullOutputStream getInstance() {
    if (instance == null) {
      instance = new NullOutputStream();
    }
    return instance;
  }

  /* (non-Javadoc)
   * @see java.io.OutputStream#write(int)
   */
  @Override
  public void write(int arg0) {
    /* do nothing */
  }

  /* (non-Javadoc)
   * @see java.io.OutputStream#write(byte[], int, int)
   */
  @Override
  public void write(byte[] b, int off, int len) {
    /* do nothing */
  }

  /* s(non-Javadoc)
   * @see java.io.OutputStream#write(byte[])
   */
  @Override
  public void write(byte[] b) {
    /* do nothing */
  }

}
