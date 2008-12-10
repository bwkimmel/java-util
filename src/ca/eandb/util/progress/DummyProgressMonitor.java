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
 * A dummy progress monitor that does not report the progress to
 * anything.  This class is a singleton.
 * @author Brad Kimmel
 */
public final class DummyProgressMonitor implements ProgressMonitor {

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#notifyCancelled()
	 */
	public void notifyCancelled() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#notifyComplete()
	 */
	public void notifyComplete() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#notifyProgress(int, int)
	 */
	public boolean notifyProgress(int value, int maximum) {
		return true;
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#notifyProgress(double)
	 */
	public boolean notifyProgress(double progress) {
		return true;
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#notifyIndeterminantProgress()
	 */
	public boolean notifyIndeterminantProgress() {
		return true;
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#notifyStatusChanged(java.lang.String)
	 */
	public void notifyStatusChanged(String status) {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#isCancelPending()
	 */
	public boolean isCancelPending() {
		return false;
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#createChildProgressMonitor()
	 */
	public ProgressMonitor createChildProgressMonitor(String title) {

		/* Report progress to subtasks to the same monitor. */
		return this;

	}

	/**
	 * Creates a new <code>DummyProgressMonitor</code>.  This constructor is
	 * private because this class is a singleton.
	 */
	private DummyProgressMonitor() {
		// nothing to do.
	}

	/**
	 * Gets the single instance of <code>DummyProgressMonitor</code>.
	 * @return The single instance of <code>DummyProgressMonitor</code>.
	 */
	public static DummyProgressMonitor getInstance() {

		if (instance == null) {
			instance = new DummyProgressMonitor();
		}

		return instance;

	}

	/** The single instance of <code>DummyProgressMonitor</code>. */
	private static DummyProgressMonitor instance = null;

}
