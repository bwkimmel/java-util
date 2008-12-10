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

import java.util.Collection;
import java.util.HashSet;

/**
 * A <code>ProgressMonitor</code> which aggregates individual progress
 * monitors.  Notifications are copied to each of the individual progress
 * monitors.  Cancellation is considered to be pending by this
 * <code>CompositeProgressMonitor</code> if cancellation is pending on any of
 * the individual <code>ProgressMonitor</code>s.  Child
 * <code>ProgressMonitor</code>s are created by aggregating child monitors
 * from each individual monitor.
 * @author Brad Kimmel
 */
public final class CompositeProgressMonitor implements ProgressMonitor {

	/**
	 * Adds a <code>ProgressMonitor</code> to the collection.
	 * @param monitor The <code>ProgressMonitor</code> to add to the
	 * 		collection.
	 * @return This <code>CompositeProgressMonitor</code> is returns so that
	 * 		calls to this method may be chained.
	 */
	public CompositeProgressMonitor addProgressMonitor(ProgressMonitor monitor) {
		this.monitors.add(monitor);
		return this;
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#createChildProgressMonitor(java.lang.String)
	 */
	public ProgressMonitor createChildProgressMonitor(String title) {

		CompositeProgressMonitor result = new CompositeProgressMonitor();

		/* Create a child progress monitor for each of this composite's
		 * monitors and add it to the resulting composite progress
		 * monitor.
		 */
		for (ProgressMonitor monitor : this.monitors) {

			ProgressMonitor child = monitor.createChildProgressMonitor(title);

			/* Don't bother adding dummy progress monitors. */
			if (!(child instanceof DummyProgressMonitor)) {
				result.addProgressMonitor(child);
			}

		}

		return result;

	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#isCancelPending()
	 */
	public boolean isCancelPending() {

		for (ProgressMonitor monitor : this.monitors) {
			if (monitor.isCancelPending()) {
				return true;
			}
		}

		return false;

	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#notifyCancelled()
	 */
	public void notifyCancelled() {
		for (ProgressMonitor monitor : this.monitors) {
			monitor.notifyCancelled();
		}
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#notifyComplete()
	 */
	public void notifyComplete() {
		for (ProgressMonitor monitor : this.monitors) {
			monitor.notifyComplete();
		}
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#notifyIndeterminantProgress()
	 */
	public boolean notifyIndeterminantProgress() {

		boolean result = true;

		for (ProgressMonitor monitor : this.monitors) {
			if (!monitor.notifyIndeterminantProgress()) {
				result = false;
			}
		}

		return result;

	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#notifyProgress(int, int)
	 */
	public boolean notifyProgress(int value, int maximum) {

		boolean result = true;

		for (ProgressMonitor monitor : this.monitors) {
			if (!monitor.notifyProgress(value, maximum)) {
				result = false;
			}
		}

		return result;

	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#notifyProgress(double)
	 */
	public boolean notifyProgress(double progress) {

		boolean result = true;

		for (ProgressMonitor monitor : this.monitors) {
			if (!monitor.notifyProgress(progress)) {
				result = false;
			}
		}

		return result;

	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#notifyStatusChanged(java.lang.String)
	 */
	public void notifyStatusChanged(String status) {
		for (ProgressMonitor monitor : this.monitors) {
			monitor.notifyStatusChanged(status);
		}
	}

	/**
	 * The <code>Collection</code> of individual <code>ProgressMonitor</code>s
	 * that make up this <code>CompositeProgressMonitor</code>.
	 */
	private final Collection<ProgressMonitor> monitors = new HashSet<ProgressMonitor>();

}
