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
 * A <code>ProgressMonitor</code> that stores the progress in fields accessible
 * via getter methods.
 * @author Brad Kimmel
 */
public class ProgressState implements ProgressMonitor {

	/** Indicates if the task should be cancelled. */
	private boolean cancelPending = false;

	/**
	 * Indicates if the task being monitored has acknowledged that it is to be
	 * cancelled.
	 */
	private boolean cancelled = false;

	/** Indicates if the task being monitored is complete. */
	private boolean complete = false;

	/** Indicates if the progress is indeterminant. */
	private boolean indeterminant = true;

	/** The number of work units involved in the task being monitored. */
	private int maximum = 0;

	/** The number of completed work units. */
	private int value = 0;

	/** The fraction of the task being monitored that is completed. */
	private double progress = 0.0;

	/** The status of the task being monitored. */
	private String status = "";

	/** The title of this <code>ProgressMonitor</code>. */
	private final String title;
	
	/**
	 * The <code>CancelListener</code> to be notified if the operation is to be
	 * cancelled.
	 */
	private final CompositeCancelListener cancelListeners = new CompositeCancelListener();

	/**
	 * Creates a new <code>ProgressState</code>.
	 * @param title The title of this <code>ProgressMonitor</code>.
	 */
	public ProgressState(String title) {
		this.title = title;
	}

	/**
	 * Creates a new <code>ProgressState</code>.
	 */
	public ProgressState() {
		this("");
	}

	/**
	 * Gets the title of this <code>ProgressState</code>.
	 * @return The title of this <code>ProgressState</code>.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets a value indicating if the task has cancelled.
	 * @return A value indicating if the task has cancelled.
	 */
	public boolean isCancelled() {
		return cancelled;
	}

	/**
	 * Gets a value indicating if the task is complete.
	 * @return A value indicating if the task is complete.
	 */
	public boolean isComplete() {
		return complete;
	}

	/**
	 * Gets a value indicating if the progress is indeterminant.
	 * @return A value indicating if the progress is indeterminant.
	 */
	public boolean isIndeterminant() {
		return indeterminant;
	}

	/**
	 * Gets the total number of work units.
	 * @return The total number of work units.
	 */
	public int getMaximum() {
		return maximum;
	}

	/**
	 * Gets the number of work units completed.
	 * @return The number of work units completed.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Gets the fraction of the task completed.
	 * @return The fraction of the task completed.
	 */
	public double getProgress() {
		return progress;
	}

	/**
	 * Gets the status of the task.
	 * @return The status of the task.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Tells the task that this progress is monitoring that it should abort.
	 */
	public void setCancelPending() {
		cancelPending = true;
		cancelListeners.cancelRequested();
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#isCancelPending()
	 */
	public boolean isCancelPending() {
		return cancelPending;
	}
	
	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#addCancelListener(ca.eandb.util.progress.CancelListener)
	 */
	public void addCancelListener(CancelListener listener) {
		cancelListeners.addCancelListener(listener);
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#notifyCancelled()
	 */
	public void notifyCancelled() {
		cancelled = true;
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#notifyComplete()
	 */
	public void notifyComplete() {
		complete = true;
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#notifyIndeterminantProgress()
	 */
	public boolean notifyIndeterminantProgress() {
		indeterminant = true;
		return !cancelPending;
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#notifyProgress(int, int)
	 */
	public boolean notifyProgress(int value, int maximum) {
		this.indeterminant = false;
		this.value = value;
		this.maximum = maximum;
		this.progress = (double) value / (double) maximum;
		return !cancelPending;
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#notifyProgress(double)
	 */
	public boolean notifyProgress(double progress) {
		this.indeterminant = false;
		this.value = this.maximum = 0;
		this.progress = progress;
		return !cancelPending;
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#notifyStatusChanged(java.lang.String)
	 */
	public void notifyStatusChanged(String status) {
		this.status = status;
	}

}
