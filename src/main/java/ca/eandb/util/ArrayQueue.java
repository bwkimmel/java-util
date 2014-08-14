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

package ca.eandb.util;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A fixed <code>Queue</code> backed by an array.
 * @author Brad Kimmel
 */
public final class ArrayQueue<T> extends AbstractQueue<T> {

  /** An array containing the items in the queue. */
  private final T[] items;

  /** The index of the next item in the queue. */
  private int next = 0;

  /**
   * Creates an new <code>ArrayQueue</code> backed by the provided array.
   * @param items The array of items with which to initialize the queue.
   */
  public ArrayQueue(T[] items) {
    this.items = items;
  }

  /* (non-Javadoc)
   * @see java.util.AbstractCollection#iterator()
   */
  @Override
  public Iterator<T> iterator() {
    return new Iterator<T>() {

      /** The index of the next item. */
      private int next = ArrayQueue.this.next;

      /* (non-Javadoc)
       * @see java.util.Iterator#hasNext()
       */
      public boolean hasNext() {
        return next < items.length;
      }

      /* (non-Javadoc)
       * @see java.util.Iterator#next()
       */
      public T next() {
        if (next >= items.length) {
          throw new NoSuchElementException();
        }
        return items[next++];
      }

      /* (non-Javadoc)
       * @see java.util.Iterator#remove()
       */
      public void remove() {
        throw new UnsupportedOperationException();
      }

    };
  }

  /* (non-Javadoc)
   * @see java.util.AbstractCollection#size()
   */
  @Override
  public int size() {
    return items.length - next;
  }

  /* (non-Javadoc)
   * @see java.util.Queue#offer(java.lang.Object)
   */
  public boolean offer(T e) {
    return false;
  }

  /* (non-Javadoc)
   * @see java.util.Queue#peek()
   */
  public T peek() {
    return next < items.length ? items[next] : null;
  }

  /* (non-Javadoc)
   * @see java.util.Queue#poll()
   */
  public T poll() {
    return next < items.length ? items[next++] : null;
  }

}
