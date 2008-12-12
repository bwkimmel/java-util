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

package ca.eandb.util.ui;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;

/**
 * Provides static utility methods for working with trees.
 * @author Brad Kimmel
 */
public final class UserInterfaceUtil {

	/**
	 * Default constructor.  This constructor is private because this is a
	 * utility class.
	 */
	private UserInterfaceUtil() {}

	/**
	 * Adds a <code>TreeModelListener</code> that will automatically expand
	 * the tree as new nodes are inserted.
	 * @param tree The <code>JTree</code>.
	 */
	public static void enableAutoExpansion(final JTree tree) {

		tree.getModel().addTreeModelListener(new TreeModelListener() {

			public void treeNodesChanged(TreeModelEvent e) {
				// nothing to do
			}

			public void treeNodesInserted(TreeModelEvent e) {
				tree.expandPath(e.getTreePath());
			}

			public void treeNodesRemoved(TreeModelEvent e) {
				// nothing to do
			}

			public void treeStructureChanged(TreeModelEvent e) {
				// nothing to do
			}

		});

	}

	public static boolean containsComponent(Container container, Component component) {
		while (component != null) {
			if (component == container) {
				return true;
			}
			component = component.getParent();
		}
		return false;
	}

}
