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


import java.io.PrintStream;

/**
 * A progress monitor that displays the status on the console.
 * @author bkimmel
 *
 */
public final class ConsoleProgressMonitor implements ProgressMonitor {

	/**
	 * Initializes the progress monitor to use a progress bar of the
	 * default length.
	 */
	public ConsoleProgressMonitor() {
		this.length = DEFAULT_PROGRESS_BAR_LENGTH;
	}

	/**
	 * Initializes the progress monitor to use a progress bar of the
	 * specified length.
	 * @param length The length of the progress bar.
	 */
	public ConsoleProgressMonitor(int length) {
		this.length = length;
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#notifyCancelled()
	 */
	public void notifyCancelled() {
		this.notifyStatusChanged("CANCELLED");
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#notifyComplete()
	 */
	public void notifyComplete() {
		this.progress = 1.0;
		this.value = this.maximum;
		this.notifyStatusChanged("DONE");
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#notifyProgress(double)
	 */
	public boolean notifyProgress(double progress) {
		this.value = 0;
		this.maximum = 0;
		this.progress = progress;
		this.printProgressBar();
		return true;
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#notifyProgress(int, int)
	 */
	public boolean notifyProgress(int value, int maximum) {
		this.progress = (double) value / (double) maximum;
		this.value = value;
		this.maximum = maximum;
		this.printProgressBar();
		return true;
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#notifyIndeterminantProgress()
	 */
	public boolean notifyIndeterminantProgress() {
		this.progress = Double.NaN;
		this.value = 0;
		this.maximum = 0;
		this.printProgressBar();
		return true;
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#notifyStatusChanged(java.lang.String)
	 */
	public void notifyStatusChanged(String status) {

		/* If the new status message is shorter than the previous one, then
		 * first change the status message all spaces to clear out the previous
		 * message.
		 */
		if (status.length() < this.status.length()) {
			this.status = this.status.replaceAll(".", " ");
			this.printProgressBar();
		}

		this.status = status;
		this.printProgressBar();

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
		return DummyProgressMonitor.getInstance();
	}

	/**
	 * Writes the progress bar to the stream.
	 * @param progress
	 */
	private void printProgressBar() {

		double x;

		this.out.print("\r");
		this.out.print(PROGRESS_BAR_END_CHAR);

		for (int i = 1; i <= this.length; i++) {

			if (!Double.isNaN(this.progress)) {

				x = (double) i / this.length;
				this.out.print(this.progress >= x ? PROGRESS_BAR_CHAR : PROGRESS_BACKGROUND_CHAR);

			} else { // Double.isNaN(progress)

				this.out.print(PROGRESS_BAR_INDETERMINANT_CHAR);

			}

		}

		this.out.print(PROGRESS_BAR_END_CHAR);

		if (this.maximum > 0) {
			this.out.printf(" (%d/%d)", value, maximum);
		}

		this.out.print(" ");
		this.out.print(status);

	}

	/** The stream to write the progress output to. */
	private final PrintStream out = System.out;

	/** The progress as last updated. */
	private double progress = 0.0;

	/** The status as last updated. */
	private String status = "";

	/** The value as last updated. */
	private int value = 0;

	/** The maximum value of the progress bar as last updated. */
	private int maximum = 0;

	/** The length of the progress bar. */
	private final int length;

	/** The character to use for the progress bar. */
	private static final char PROGRESS_BAR_CHAR					= '=';

	/** The character to use for the background of the progress bar. */
	private static final char PROGRESS_BACKGROUND_CHAR			= ' ';

	/** The character to use when the progress is unknown. */
	private static final char PROGRESS_BAR_INDETERMINANT_CHAR	= '?';

	/** The character to use to delimit the progress bar. */
	private static final char PROGRESS_BAR_END_CHAR				= '|';

	/** The default length of the progress bar. */
	private static final char DEFAULT_PROGRESS_BAR_LENGTH		= 40;

}
