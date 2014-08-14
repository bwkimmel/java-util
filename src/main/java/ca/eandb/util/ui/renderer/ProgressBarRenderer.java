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

package ca.eandb.util.ui.renderer;

import java.awt.Component;

import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * A <code>TableCellRenderer</code> that renders a progress bar within the
 * table cell.  The value of the cell must be the <code>JProgressBar</code> to
 * be rendered.
 *
 * This class is a singleton.
 *
 * @author Brad Kimmel
 */
public final class ProgressBarRenderer implements TableCellRenderer {

  /** The single <code>ProgressBarRenderer</code> instance. */
  private static ProgressBarRenderer instance = null;

  /**
   * Default constructor.  This constructor is private because this class is
   * a singleton.
   */
  private ProgressBarRenderer() {
    // nothing to do.
  }

  /**
   * Gets the single <code>ProgressBarRenderer</code> instance.
   * @return The single <code>ProgressBarRenderer</code> instance.
   */
  public static ProgressBarRenderer getInstance() {
    if (instance == null) {
      instance = new ProgressBarRenderer();
    }
    return instance;
  }

  /**
   * Sets <code>ProgressBarRenderer</code> as the default table cell renderer
   * for <code>JProgressBar</code> columns.
   * @param table The <code>JTable</code> in which to use this renderer.
   */
  public static void applyTo(JTable table) {
    table.setDefaultRenderer(JProgressBar.class, getInstance());
  }

  /* (non-Javadoc)
   * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
   */
  public Component getTableCellRendererComponent(JTable table, Object value,
      boolean isSelected, boolean hasFocus, int row, int column) {

    return (JProgressBar) value;

  }

}
