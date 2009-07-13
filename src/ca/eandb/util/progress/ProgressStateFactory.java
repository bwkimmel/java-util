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
 * A <code>ProgressMonitorFactory</code> that creates
 * <code>ProgressState</code>s and adds them to a list.
 * @see ProgressState
 * @author Brad Kimmel
 */
public class ProgressStateFactory implements ProgressMonitorFactory {

	/**
	 * The <code>List</code> of <code>ProgressState</code>s created by this
	 * factory.
	 */
	private final List<ProgressState> states;

	/**
	 * Creates a <code>ProgressStateFactory</code>.
	 * @param states The <code>List</code> to add created
	 * 		<code>ProgressState</code>s to.
	 */
	public ProgressStateFactory(List<ProgressState> states) {
		this.states = states;
	}

	/**
	 * Creates a <code>ProgressStateFactory</code>.
	 */
	public ProgressStateFactory() {
		this(new ArrayList<ProgressState>());
	}

	/**
	 * Gets the list of <code>ProgressState</code>s created by this factory.
	 * @return The list of <code>ProgressState</code>s created by this factory.
	 */
	public final List<ProgressState> getProgressStates() {
		return states;
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.progress.ProgressMonitorFactory#createProgressMonitor(java.lang.String)
	 */
	@Override
	public ProgressMonitor createProgressMonitor(String title) {
		ProgressState state = new ProgressState(title);
		states.add(state);
		return state;
	}

}
