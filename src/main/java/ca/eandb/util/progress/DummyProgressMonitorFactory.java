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

package ca.eandb.util.progress;

/**
 * A <code>ProgressMonitorFactory</code> that creates
 * <code>DummyProgressMonitor</code>s.  This class is a singleton.
 * @see DummyProgressMonitor
 * @author Brad Kimmel
 */
public final class DummyProgressMonitorFactory implements
    ProgressMonitorFactory {

  /** The single instance of <code>DummyProgressMonitorFactory</code> */
  private static DummyProgressMonitorFactory instance = null;

  /* (non-Javadoc)
   * @see ca.eandb.util.progress.ProgressMonitorFactory#createProgressMonitor(java.lang.String)
   */
  public ProgressMonitor createProgressMonitor(String title) {
    return DummyProgressMonitor.getInstance();
  }

  /**
   * Creates a new <code>DummyProgressMonitorFactory</code>.  This
   * constructor is private because this class is a singleton.
   */
  private DummyProgressMonitorFactory() {
    // nothing to do.
  }

  /**
   * Gets the single instance of <code>DummyProgressMonitorFactory</code>.
   * @return The single instance of <code>DummyProgressMonitorFactory</code>.
   */
  public static DummyProgressMonitorFactory getInstance() {

    if (instance == null) {
      instance = new DummyProgressMonitorFactory();
    }

    return instance;

  }

}
