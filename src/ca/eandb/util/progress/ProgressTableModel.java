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

import java.util.ArrayList;
import java.util.List;

import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

/**
 * A <code>ProgressMonitorFactory</code> that serves as a
 * <code>TableModel</code> to be displayed in a <code>JTable</code>.
 * @author Brad Kimmel
 */
public final class ProgressTableModel extends AbstractTableModel implements
		ProgressMonitorFactory {

	/**
	 * Serialization version ID.
	 */
	private static final long serialVersionUID = 8512331741100309356L;

	/** The classes for each column. */
	private static final Class<?>[] COLUMN_CLASS = { String.class, JProgressBar.class, String.class };

	/** The names of each column. */
	private static final String[] COLUMN_NAME = { "Title", "Progress", "Status" };

	/** The index of the title column. */
	private static final int TITLE_COLUMN = 0;

	/** The index of the progres bar column. */
	private static final int PROGRESS_COLUMN = 1;

	/** The index of the status column. */
	private static final int STATUS_COLUMN = 2;

	/** The collection of <code>ProgressMonitor</code>s in this model. */
	private List<TableRowProgressMonitor> monitors = new ArrayList<TableRowProgressMonitor>();

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitorFactory#createProgressMonitor(java.lang.String)
	 */
	public synchronized ProgressMonitor createProgressMonitor(final String title) {
		final TableRowProgressMonitor monitor = new TableRowProgressMonitor(title);
		final int row = monitors.size();
		monitors.add(monitor);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				monitor.setProgressBar(new JProgressBar());
				fireTableRowsInserted(row, row);
			}
		});
		return monitor;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	public Class<?> getColumnClass(int columnIndex) {
		return COLUMN_CLASS[columnIndex];
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	public String getColumnName(int column) {
		return COLUMN_NAME[column];
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return COLUMN_NAME.length;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public synchronized int getRowCount() {
		return monitors.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public synchronized Object getValueAt(int rowIndex, int columnIndex) {
		TableRowProgressMonitor monitor = monitors.get(rowIndex);
		switch (columnIndex) {
		case TITLE_COLUMN:
			return monitor.getTitle();
		case PROGRESS_COLUMN:
			return monitor.getProgressBar();
		case STATUS_COLUMN:
			return monitor.getStatus();
		default:
			throw new IndexOutOfBoundsException();
		}
	}

	/**
	 * Removes all <code>ProgressMonitor</code>s from the table.
	 */
	public void clear() {
		monitors.clear();
		fireTableDataChanged();
	}

	/**
	 * Indicate that the progress for a particular monitor has been updated.
	 * @param source The <code>TableRowProgressMonitor</code> whose progress
	 * 		has been updated.
	 */
	private synchronized void fireProgressUpdated(TableRowProgressMonitor source) {
		int row = monitors.indexOf(source);
		if (row >= 0) {
			fireTableCellUpdated(row, PROGRESS_COLUMN);
		}
	}

	/**
	 * Indicate that the status for a particular monitor has been updated.
	 * @param source The <code>TableRowProgressMonitor</code> whose status has
	 * 		been updated.
	 */
	private synchronized void fireStatusUpdated(TableRowProgressMonitor source) {
		int row = monitors.indexOf(source);
		if (row >= 0) {
			fireTableCellUpdated(row, STATUS_COLUMN);
		}
	}

	/**
	 * Remove a <code>ProgressMonitor</code> from this model.
	 * @param monitor The <code>TableRowProgressMonitor</code> to remove.
	 */
	private synchronized void remove(TableRowProgressMonitor monitor) {
		final int row = monitors.indexOf(monitor);
		if (row >= 0) {
			monitors.remove(row);
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					fireTableRowsDeleted(row, row); // row your boat...
				}
			});
		}
	}

	/**
	 * A <code>ProgressMonitor</code> representing a row in the table.
	 * @author Brad Kimmel
	 */
	private class TableRowProgressMonitor implements ProgressMonitor {

		/** The title for this <code>ProgressMonitor</code>. */
		private final String title;

		/** The <code>JProgressBar</code> used to indicate the progress. */
		private JProgressBar progressBar;

		/** The status of this <code>ProgressMonitor</code>. */
		private String status = "";

		/**
		 * Creates a new <code>TableRowProgressMonitor</code>.
		 * @param title The title.
		 * @param progressBar The <code>JProgressBar</code> to use to indicate
		 * 		the progress.
		 */
		public TableRowProgressMonitor(String title) {
			this.title = title;
		}

		/**
		 * Gets the title of this <code>ProgressMonitor</code>.
		 * @return The title of this <code>ProgressMonitor</code>.
		 */
		public String getTitle() {
			return title;
		}

		/**
		 * Gets the <code>JProgressBar</code> to use to indicate the progress.
		 * @return The <code>JProgressBar</code> to use to indicate the
		 * 		progress.
		 */
		public JProgressBar getProgressBar() {
			return progressBar;
		}

		/**
		 * Sets the <code>JProgressBar</code> to use to indicate the progress.
		 * @param progressBar The <code>JProgressBar</code> to use to indicate
		 * 		the progress.
		 */
		public void setProgressBar(JProgressBar progressBar) {
			this.progressBar = progressBar;
		}

		/**
		 * Gets the status of this <code>ProgressMonitor</code>.
		 * @return The status of this <code>ProgressMonitor</code>.
		 */
		public String getStatus() {
			return status;
		}

		/* (non-Javadoc)
		 * @see ca.eandb.util.progress.ProgressMonitor#isCancelPending()
		 */
		public boolean isCancelPending() {
			return false;
		}
		
		/* (non-Javadoc)
		 * @see ca.eandb.util.progress.ProgressMonitor#addCancelListener(ca.eandb.util.progress.CancelListener)
		 */
		public void addCancelListener(CancelListener listener) {
			/* nothing to do. */
		}

		/* (non-Javadoc)
		 * @see ca.eandb.util.progress.ProgressMonitor#notifyCancelled()
		 */
		public void notifyCancelled() {
			remove(this);
		}

		/* (non-Javadoc)
		 * @see ca.eandb.util.progress.ProgressMonitor#notifyComplete()
		 */
		public void notifyComplete() {
			remove(this);
		}

		/* (non-Javadoc)
		 * @see ca.eandb.util.progress.ProgressMonitor#notifyIndeterminantProgress()
		 */
		public boolean notifyIndeterminantProgress() {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					progressBar.setIndeterminate(true);
					fireProgressUpdated(TableRowProgressMonitor.this);
				}
			});
			return !isCancelPending();
		}

		/* (non-Javadoc)
		 * @see ca.eandb.util.progress.ProgressMonitor#notifyProgress(int, int)
		 */
		public boolean notifyProgress(final int value, final int maximum) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					progressBar.setIndeterminate(false);
					if (progressBar.getMaximum() != maximum) {
						progressBar.setMaximum(maximum);
					}
					progressBar.setValue(value);
					fireProgressUpdated(TableRowProgressMonitor.this);
				}
			});
			return !isCancelPending();
		}

		/* (non-Javadoc)
		 * @see ca.eandb.util.progress.ProgressMonitor#notifyProgress(double)
		 */
		public boolean notifyProgress(final double progress) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					progressBar.setIndeterminate(false);
					if (progressBar.getMaximum() != 100) {
						progressBar.setMaximum(100);
					}
					progressBar.setValue((int) Math.floor(progress * 100.0));
					fireProgressUpdated(TableRowProgressMonitor.this);
				}
			});
			return !isCancelPending();
		}

		/* (non-Javadoc)
		 * @see ca.eandb.util.progress.ProgressMonitor#notifyStatusChanged(java.lang.String)
		 */
		public void notifyStatusChanged(final String status) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					TableRowProgressMonitor.this.status = status;
					fireStatusUpdated(TableRowProgressMonitor.this);
				}
			});
		}

	}

}
