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

/*
 * ProgressDialog.java
 *
 * Created on October 21, 2007, 3:34 PM
 */

package ca.eandb.util.progress;

/**
 * A dialog for indicating the progress of an operation.
 * @author Brad Kimmel
 */
public class ProgressDialog extends javax.swing.JDialog implements ProgressMonitor {

	/** Creates a new <code>ProgressDialog</code>. */
	public ProgressDialog() {
		super((java.awt.Frame) null, false);
		initComponents();
	}

	/** Creates new form ProgressDialog */
	public ProgressDialog(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
	private void initComponents() {
		statusLabel = new javax.swing.JLabel();
		progressBar = new javax.swing.JProgressBar();
		cancelButton = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		statusLabel.setText("Progress...");

		progressBar.setIndeterminate(true);

		cancelButton.setMnemonic('C');
		cancelButton.setText("Cancel");
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelButtonActionPerformed(evt);
			}
		});

		/** TODO: Redo using Java 5 supported classes. */
		
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
					.addComponent(statusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
					.addComponent(cancelButton, javax.swing.GroupLayout.Alignment.TRAILING))
				.addContainerGap())
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addComponent(statusLabel)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(cancelButton)
				.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		pack();
	}// </editor-fold>//GEN-END:initComponents

	/**
	 * Fires when the user clicks on the cancel button.
	 * @param evt The event arguments.
	 */
	private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
		this.onCancel();
	}//GEN-LAST:event_cancelButtonActionPerformed

	/**
	 * Initiates the process of cancelling the operation.
	 */
	private void onCancel() {
		this.setCancelPending();
	}

	/**
	 * Causes a cancellation to be pending.
	 */
	private synchronized void setCancelPending() {
		this.cancelPending = true;
		cancelListeners.cancelRequested();
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#isCancelPending()
	 */
	public synchronized boolean isCancelPending() {
		return this.cancelPending;
	}
	
	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#addCancelListener(ca.eandb.util.progress.CancelListener)
	 */
	public synchronized void addCancelListener(CancelListener listener) {
		cancelListeners.addCancelListener(listener);
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#notifyProgress(double)
	 */
	public boolean notifyProgress(double progress) {
		this.ensureVisible();
		this.setProgressBarValue((int) Math.floor(100.0 * progress), 100);
		this.clearProgressText();
		return !this.isCancelPending();
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#notifyProgress(int, int)
	 */
	public boolean notifyProgress(int value, int maximum) {
		this.ensureVisible();
		this.setProgressBarValue(value, maximum);
		this.setProgressText(value, maximum);
		return !this.isCancelPending();
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#notifyIndeterminantProgress()
	 */
	public boolean notifyIndeterminantProgress() {
		this.ensureVisible();
		this.progressBar.setIndeterminate(true);
		this.clearProgressText();
		return !this.isCancelPending();
	}

	/**
	 * Updates the progress bar on this dialog.
	 * @param value The number of parts of the operation that have been
	 *	    completed.
	 * @param maximum The number of parts that compose the operation.
	 */
	private void setProgressBarValue(int value, int maximum) {

		this.progressBar.setIndeterminate(false);

		if (this.progressBar.getMaximum() != maximum) {
			this.progressBar.setMaximum(maximum);
		}

		this.progressBar.setValue(value);

	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#notifyStatusChanged(java.lang.String)
	 */
	public void notifyStatusChanged(String status) {
		this.ensureVisible();
		this.statusText = status;
		this.updateStatusLabel();
	}

	/**
	 * Updates the text on the status label on this dialog.
	 */
	private void updateStatusLabel() {
		this.statusLabel.setText(this.statusText + " " + this.progressText);
	}

	/**
	 * Displays the progress as part of the status label on this dialog.
	 * @param value The number of parts of the operation that have been
	 *	    completed.
	 * @param maximum The number of parts that compose this operation.
	 */
	private void setProgressText(int value, int maximum) {
		this.progressText = String.format("(%d/%d)", value, maximum);
		this.updateStatusLabel();
	}

	/**
	 * Removes the text showing the progress from the status label on this
	 * dialog.
	 */
	private void clearProgressText() {
		if (this.progressText != "") {
			this.progressText = "";
			this.updateStatusLabel();
		}
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#notifyCancelled()
	 */
	public void notifyCancelled() {
		this.setVisible(false);
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitor#notifyComplete()
	 */
	public void notifyComplete() {
		this.setVisible(false);
	}

	/**
	 * Ensures that this <code>ProgressDialog</code> is visible.
	 */
	private void ensureVisible() {
		if (!this.isVisible()) {
			this.setVisible(true);
		}
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton cancelButton;
	private javax.swing.JProgressBar progressBar;
	private javax.swing.JLabel statusLabel;
	// End of variables declaration//GEN-END:variables

	/** Indicates if the cancel button was clicked. */
	private boolean cancelPending = false;

	/** The text describing the status of the operation. */
	private String statusText = "Progress...";

	/** The text describing the progress of the operation. */
	private String progressText = "";
	
	/**
	 * The <code>CancelListener</code> to notify if operation is to be
	 * cancelled.
	 */
	private CompositeCancelListener cancelListeners = new CompositeCancelListener();

	/** Serialization version. */
	private static final long serialVersionUID = -8886817308462948089L;

}
