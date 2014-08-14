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

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ca.eandb.util.ui.renderer.ProgressBarRenderer;

/**
 * A <code>JPanel</code> that displays <code>ProgressMonitor</code>s as rows in
 * a <code>JTable</code>.
 * @author Brad Kimmel
 */
public final class ProgressPanel extends JPanel implements
    ProgressMonitorFactory {

  /**
   * Serialization version ID.
   */
  private static final long serialVersionUID = 3445300405526898766L;

  /** The <code>JScrollPane</code> to use to scroll around the table. */
  private JScrollPane scrollPane = null;

  /**
   * The <code>JTable</code> displaying the progress for the individual
   * <code>ProgressMonitor</code>s.
   */
  private JTable table = null;

  /**
   * The <code>ProgressTableModel</code> for this table.
   */
  private ProgressTableModel model = null;

  /**
   * This is the default constructor
   */
  public ProgressPanel() {
    super();
    initialize();
  }

  /**
   * This method initializes this
   *
   * @return void
   */
  private void initialize() {
    this.setSize(300, 200);
    this.setLayout(new BorderLayout());
    this.add(getScrollPane(), BorderLayout.CENTER);
  }

  /**
   * This method initializes scrollPane
   *
   * @return javax.swing.JScrollPane
   */
  private JScrollPane getScrollPane() {
    if (scrollPane == null) {
      scrollPane = new JScrollPane();
      scrollPane.setViewportView(getTable());
    }
    return scrollPane;
  }

  /**
   * This method initializes table
   *
   * @return javax.swing.JTable
   */
  private JTable getTable() {
    if (table == null) {
      table = new JTable();
      table.setModel(getModel());
      ProgressBarRenderer.applyTo(table);
    }
    return table;
  }

  /**
   * This method initializes model
   *
   * @return ca.eandb.util.progress.ProgressTableModel
   */
  private ProgressTableModel getModel() {
    if (model == null) {
      model = new ProgressTableModel();
    }
    return model;
  }

  /* (non-Javadoc)
   * @see ca.eandb.util.progress.ProgressMonitorFactory#createProgressMonitor(java.lang.String)
   */
  public ProgressMonitor createProgressMonitor(String title) {
    return getModel().createProgressMonitor(title);
  }

  /**
   * Removes all <code>ProgressMonitor</code>s from the table.
   */
  public void clear() {
    getModel().clear();
  }

}
