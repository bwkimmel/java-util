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
 * A <code>Command</code> handler that is used to process unrecognized
 * commands.
 * @author Brad Kimmel
 */
public final class UnrecognizedCommand implements Command<Object> {

  private static UnrecognizedCommand instance;

  /**
   *
   */
  private UnrecognizedCommand() {
    /* nothing to do */
  }

  public static Command<Object> getInstance() {
    if (instance == null) {
      instance = new UnrecognizedCommand();
    }
    return instance;
  }

  /* (non-Javadoc)
   * @see ca.eandb.util.args.Command#process(java.util.Queue, java.lang.Object)
   */
  public void process(Queue<String> argq, Object state) {
    String command = argq.peek();
    if (command != null) {
      System.err.print("Unrecognzied command: ");
      System.err.println(command);
    }
  }

}
