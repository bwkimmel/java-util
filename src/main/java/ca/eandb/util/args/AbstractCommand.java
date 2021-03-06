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

package ca.eandb.util.args;

import java.util.Queue;

/**
 * An abstract <code>Command</code> that provides a default implementation of
 * {@link Command#process(Queue, Object)} which extracts the arguments to an
 * array and passes them to an abstract {@link #run(String[], Object)} method.
 * @author Brad Kimmel
 */
public abstract class AbstractCommand<T> implements Command<T> {

  /* (non-Javadoc)
   * @see ca.eandb.util.args.Command#process(java.util.Queue, java.lang.Object)
   */
  public final void process(Queue<String> argq, T state) {
    String[] args = argq.toArray(new String[argq.size()]);
    argq.clear();
    run(args, state);
  }

  /**
   * Executes the command.
   * @param args The command line arguments for this command.
   * @param state The application options.
   */
  protected abstract void run(String[] args, T state);

}
