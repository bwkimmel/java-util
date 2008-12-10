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
 * Receives progress updates from a long running job, and potentially requests
 * that the job be cancelled.
 * @author Brad Kimmel
 */
public interface ProgressMonitor {

	/**
	 * Notify the progress monitor that <code>value</code> of
	 * <code>maximum</code> subtasks have been completed.
	 * @param value The number of subtasks that have been completed.
	 * @param maximum The total number of subtasks.
	 * @return A value indicating whether the job should continue.
	 */
	boolean notifyProgress(int value, int maximum);

	/**
	 * Notify the progress monitor what fraction of the job has been completed.
	 * @param progress The fraction of the job that has been completed (should
	 * 		be in <code>[0, 1]</code>).
	 * @return A value indicating whether the job should continue.
	 */
	boolean notifyProgress(double progress);

	/**
	 * Notify the progress monitor that the progress is unknown or that the
	 * job runs indefinitely.
	 * @return A value indicating whether the job should continue.
	 */
	boolean notifyIndeterminantProgress();

	/**
	 * Notify the progress monitor that the job has been completed.
	 */
	void notifyComplete();

	/**
	 * Notify the progress monitor that the job has been cancelled.
	 */
	void notifyCancelled();

	/**
	 * Notify the progress monitor that the status of the job has changed.
	 * @param status The <code>String</code> describing the new status of the
	 * 		job.
	 */
	void notifyStatusChanged(String status);

	/**
	 * Indicates if this progress monitor is requesting that the operation be
	 * cancelled.
	 * @return A value indicating if cancellation of the operation is pending.
	 */
	boolean isCancelPending();

	/**
	 * Creates a <code>ProgressMonitor</code> to monitor the progress of a
	 * subtask.
	 * @param title The title of the new progress monitor.
	 * @return A new child <code>ProgressMonitor</code>.
	 */
	ProgressMonitor createChildProgressMonitor(String title);

}
