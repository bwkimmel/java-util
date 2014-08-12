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

/**
 * A composite <code>CancelListener</code>.
 * @author Brad Kimmel
 */
public final class CompositeCancelListener implements CancelListener {
	
	/** The <code>List</code> of child <code>CancelListener</code>s. */
	private final List<CancelListener> children = new ArrayList<CancelListener>();
	
	/**
	 * Adds a child <code>CancelListener</code>.
	 * @param listener The <code>CancelListener</code> to add.
	 * @return A reference to this <code>CompositeCancelListener</code>.
	 */
	public CompositeCancelListener addCancelListener(CancelListener listener) {
		children.add(listener);
		return this;
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.CancelListener#cancelRequested()
	 */
	@Override
	public void cancelRequested() {
		for (CancelListener child : children) {
			child.cancelRequested();
		}
	}

}
