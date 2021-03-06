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
 * A <code>ProgressMonitor</code> that synchronizes all operations on itself
 * and its descendant <code>ProgressMonitor</code>s.  This class is a
 * decorator.
 * @author Brad Kimmel
 */
public final class SynchronizedProgressMonitor implements ProgressMonitor {

  /** The <code>ProgressMonitor</code> to decorate. */
  private final ProgressMonitor monitor;

  /**
   * The <code>Object</code> to synchronize against.  This will be the root
   * <code>ProgressMonitor</code>.
   */
  private final Object syncObject;

  /**
   * Creates a new <code>SynchronizedProgressMonitor</code>.
   * @param monitor The <code>ProgressMonitor</code> whose operations are to
   *     be synchronized.
   */
  public SynchronizedProgressMonitor(ProgressMonitor monitor) {
    this(monitor, monitor);
  }

  /**
   * Creates a new <code>SynchronizedProgressMonitor</code>.
   * @param monitor The <code>ProgressMonitor</code> to decorate.
   * @param syncObject The <code>Object</code> on which to synchronize all
   *     operations.
   */
  private SynchronizedProgressMonitor(ProgressMonitor monitor, Object syncObject) {
    this.monitor = monitor;
    this.syncObject = syncObject;
  }

  /* (non-Javadoc)
   * @see ca.eandb.util.progress.ProgressMonitor#isCancelPending()
   */
  public boolean isCancelPending() {
    synchronized (syncObject) {
      return monitor.isCancelPending();
    }
  }

  /* (non-Javadoc)
   * @see ca.eandb.util.progress.ProgressMonitor#addCancelListener(ca.eandb.util.progress.CancelListener)
   */
  public void addCancelListener(CancelListener listener) {
    synchronized (syncObject) {
      monitor.addCancelListener(listener);
    }
  }

  /* (non-Javadoc)
   * @see ca.eandb.util.progress.ProgressMonitor#notifyCancelled()
   */
  public void notifyCancelled() {
    synchronized (syncObject) {
      monitor.notifyCancelled();
    }
  }

  /* (non-Javadoc)
   * @see ca.eandb.util.progress.ProgressMonitor#notifyComplete()
   */
  public void notifyComplete() {
    synchronized (syncObject) {
      monitor.notifyComplete();
    }
  }

  /* (non-Javadoc)
   * @see ca.eandb.util.progress.ProgressMonitor#notifyIndeterminantProgress()
   */
  public boolean notifyIndeterminantProgress() {
    synchronized (syncObject) {
      return monitor.notifyIndeterminantProgress();
    }
  }

  /* (non-Javadoc)
   * @see ca.eandb.util.progress.ProgressMonitor#notifyProgress(int, int)
   */
  public boolean notifyProgress(int value, int maximum) {
    synchronized (syncObject) {
      return monitor.notifyProgress(value, maximum);
    }
  }

  /* (non-Javadoc)
   * @see ca.eandb.util.progress.ProgressMonitor#notifyProgress(double)
   */
  public boolean notifyProgress(double progress) {
    synchronized (syncObject) {
      return monitor.notifyProgress(progress);
    }
  }

  /* (non-Javadoc)
   * @see ca.eandb.util.progress.ProgressMonitor#notifyStatusChanged(java.lang.String)
   */
  public void notifyStatusChanged(String status) {
    synchronized (syncObject) {
      monitor.notifyStatusChanged(status);
    }
  }

}
