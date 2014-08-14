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

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Collection;
import java.util.RandomAccess;

/**
 * A resizable array of chars.
 *
 * @author brad
 */
public final class CharacterArray extends AbstractList<Character> implements
    RandomAccess, Serializable {

  /**
   * Serialization version ID.
   */
  private static final long serialVersionUID = -1317237921863402485L;

  /** The elements of this array. */
  private char[] elements;

  /** The number of elements in this array. */
  private int size;

  /**
   * Creates an empty <code>CharacterArray</code>.
   */
  public CharacterArray() {
    this(0);
  }

  /**
   * Creates an empty <code>CharacterArray</code>.
   *
   * @param capacity
   *            The initial capacity of the array.
   */
  public CharacterArray(int capacity) {
    elements = new char[capacity];
    size = 0;
  }

  /**
   * Creates an <code>CharacterArray</code> containing the specified
   * elements.
   *
   * @param elements
   *            An array of elements to initialize the new array with.
   */
  public CharacterArray(char[] elements) {
    this.elements = elements.clone();
    this.size = elements.length;
  }

  /**
   * Creates an <code>CharacterArray</code> containing the specified
   * elements.
   *
   * @param c
   *            A collection of elements to initialize the new array with.
   */
  public CharacterArray(Collection<Character> c) {
    this.elements = new char[c.size()];
    this.size = 0;
    for (char e : c) {
      elements[size++] = e;
    }
  }

  /**
   * Creates a copy of an <code>CharacterArray</code>.
   *
   * @param other
   *            The array to copy.
   */
  public CharacterArray(CharacterArray other) {
    this.elements = other.elements.clone();
    this.size = other.size;
  }

  /**
   * Converts this <code>CharacterArray</code> to an array of chars.
   *
   * @return An array of chars containing the same elements as this array.
   */
  public char[] toCharacterArray() {
    char[] copy = new char[size];
    for (int i = 0; i < size; i++) {
      copy[i] = elements[i];
    }
    return copy;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#clone()
   */
  @Override
  public CharacterArray clone() {
    return new CharacterArray(this);
  }

  /*
   * (non-Javadoc)
   *
   * @see java.util.AbstractList#clear()
   */
  @Override
  public void clear() {
    size = 0;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.util.AbstractCollection#size()
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * Ensures that the specified index is valid for this array.
   *
   * @param index
   *            The index to check.
   * @throws IndexOutOfBoundsException
   *             if <code>index &lt; 0 || index &gt;= size</code>.
   */
  private void rangeCheck(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }
  }

  /**
   * Ensures that the specified range of indices is valid for this array.
   *
   * @param fromIndex
   *            The (inclusive) start of the range of indices to check.
   * @param toIndex
   *            The (exclusive) end of the range of indices to check.
   * @throws IndexOutOfBoundsException
   *             if <code>fromIndex &lt; 0 || toIndex &gt; size</code>.
   */
  private void rangeCheck(int fromIndex, int toIndex) {
    if (fromIndex < 0 || toIndex > size) {
      throw new IndexOutOfBoundsException();
    }
  }

  /**
   * Gets an element of this array.
   *
   * @param index
   *            The index of the element to get.
   * @return The indexed element.
   * @throws IndexOutOfBoundsException
   *             if <code>index &lt; 0 || index &gt;= size</code>.
   */
  public Character get(int index) {
    rangeCheck(index);
    return elements[index];
  }

  /**
   * Sets an element of this array.
   *
   * @param index
   *            The index of the element to set.
   * @param e
   *            The value of the element.
   * @return The value previously stored at the specified index.
   * @throws IndexOutOfBoundsException
   *             if <code>index &lt; 0 || index &gt;= size()</code>.
   */
  public char set(int index, char e) {
    rangeCheck(index);
    char value = elements[index];
    elements[index] = e;
    return value;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.util.AbstractList#set(int, java.lang.Object)
   */
  @Override
  public Character set(int index, Character element) {
    return set(index, element.charValue());
  }

  /**
   * Sets a range of elements of this array.
   *
   * @param index
   *            The index of the first element to set.
   * @param items
   *            The values to set.
   * @throws IndexOutOfBoundsException
   *             if
   *             <code>index &lt; 0 || index + items.length &gt; size()</code>.
   */
  public void setAll(int index, char[] items) {
    rangeCheck(index, index + items.length);
    for (int i = index, j = 0; j < items.length; i++, j++) {
      elements[i] = items[j];
    }
  }

  /**
   * Sets a range of elements of this array.
   *
   * @param index
   *            The index of the first element to set.
   * @param items
   *            The array containing the values to set.
   * @param start
   *         The index into <code>items</code> at which the values to
   *         insert start.
   * @param length
   *            The number of items to insert.
   * @throws IndexOutOfBoundsException
   *             if
   *             <code>index &lt; 0 || index + items.length &gt; size()</code>.
   */
  public void set(int index, char[] items, int start, int length) {
    rangeCheck(index, index + length);
    for (int i = 0; i < length; i++) {
      elements[index + i] = items[start + i];
    }
  }

  /**
   * Sets a range of elements of this array.
   *
   * @param index
   *            The index of the first element to set.
   * @param items
   *            The values to set.
   * @throws IndexOutOfBoundsException
   *             if
   *             <code>index &lt; 0 || index + items.size() &gt; size()</code>.
   */
  public void setAll(int index, CharacterArray items) {
    rangeCheck(index, index + items.size);
    for (int i = index, j = 0; j < items.size; i++, j++) {
      elements[i] = items.elements[j];
    }
  }

  /**
   * Sets a range of elements of this array.
   *
   * @param index
   *            The index of the first element to set.
   * @param items
   *            The values to set.
   * @throws IndexOutOfBoundsException
   *             if
   *             <code>index &lt; 0 || index + items.size() &gt; size()</code>.
   */
  public void setAll(int index, Collection<? extends Character> items) {
    rangeCheck(index, index + items.size());
    for (char e : items) {
      elements[index++] = e;
    }
  }

  /**
   * Appends a value to the end of this array.
   *
   * @param e
   *            The value to append.
   * @return Always returns <code>true</code>.
   */
  public boolean add(char e) {
    ensureCapacity(size + 1);
    elements[size++] = e;
    return true;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.util.AbstractList#add(java.lang.Object)
   */
  @Override
  public boolean add(Character e) {
    return add(e.charValue());
  }

  /**
   * Appends a range of values to the end of this array.
   *
   * @param items
   *            The values to append.
   * @return A value indicating if the array has changed.
   */
  public boolean addAll(char[] items) {
    ensureCapacity(size + items.length);
    for (int i = 0; i < items.length; i++) {
      elements[size++] = items[i];
    }
    return items.length > 0;
  }

  /**
   * Appends a range of values to the end of this array.
   *
   * @param items
   *            The array containing the values to append.
   * @param start
   *            The index into <code>items</code> of the first item to add.
   * @param length
   *         The number of items to add.
   * @return A value indicating if the array has changed.
   */
  public boolean add(char[] items, int start, int length) {
    ensureCapacity(size + length);
    for (int i = start, n = start + length; i < n; i++) {
      elements[size++] = items[i];
    }
    return length > 0;
  }

  /**
   * Appends a range of values to the end of this array.
   *
   * @param items
   *            The values to append.
   * @return A value indicating if the array has changed.
   */
  public boolean addAll(CharacterArray items) {
    ensureCapacity(size + items.size);
    for (int i = 0; i < items.size; i++) {
      elements[size++] = items.elements[i];
    }
    return items.size > 0;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.util.AbstractCollection#addAll(java.util.Collection)
   */
  @Override
  public boolean addAll(Collection<? extends Character> c) {
    ensureCapacity(size + c.size());
    for (char e : c) {
      elements[size++] = e;
    }
    return c.size() > 0;
  }

  /**
   * Inserts a value into the array at the specified index.
   *
   * @param index
   *            The index at which to insert the new value.
   * @param e
   *            The new value to insert.
   * @throws IndexOutOfBoundsException
   *             if <code>index &lt; 0 || index &gt; size()</code>.
   */
  public void add(int index, char e) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException();
    }
    ensureCapacity(size + 1);
    if (index < size) {
      for (int i = size; i > index; i--) {
        elements[i] = elements[i - 1];
      }
    }
    elements[index] = e;
    size++;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.util.AbstractList#add(int, java.lang.Object)
   */
  @Override
  public void add(int index, Character element) {
    add(index, element.charValue());
  }

  /**
   * Inserts values into the array at the specified index.
   *
   * @param index
   *            The index at which to insert the new values.
   * @param items
   *            An array of values to insert.
   * @return A value indicating if the array has changed.
   * @throws IndexOutOfBoundsException
   *             if <code>index &lt; 0 || index &gt; size()</code>.
   */
  public boolean addAll(int index, char[] items) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException();
    }
    ensureCapacity(size + items.length);
    if (index < size) {
      for (int i = size - 1; i >= index; i--) {
        elements[i + items.length] = elements[i];
      }
    }
    for (char e : items) {
      elements[index++] = e;
    }
    size += items.length;
    return items.length > 0;
  }

  /**
   * Inserts values into the array at the specified index.
   *
   * @param index
   *            The index at which to insert the new values.
   * @param items
   *            A <code>CharacterArray</code> containing the values to
   *            insert.
   * @return A value indicating if the array has changed.
   * @throws IndexOutOfBoundsException
   *             if <code>index &lt; 0 || index &gt; size()</code>.
   */
  public boolean addAll(int index, CharacterArray items) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException();
    }
    ensureCapacity(size + items.size);
    if (index < size) {
      for (int i = size - 1; i >= index; i--) {
        elements[i + items.size] = elements[i];
      }
    }
    for (int i = 0; i < items.size; i++) {
      elements[index++] = items.elements[i];
    }
    size += items.size;
    return items.size > 0;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.util.AbstractList#addAll(int, java.util.Collection)
   */
  @Override
  public boolean addAll(int index, Collection<? extends Character> c) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException();
    }
    ensureCapacity(size + c.size());
    size += c.size();
    for (int i = size + c.size() - 1, j = size - 1; j >= index; i--, j--) {
      elements[i] = elements[j];
    }
    for (char e : c) {
      elements[index++] = e;
    }
    return c.size() > 0;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.util.AbstractList#remove(int)
   */
  @Override
  public Character remove(int index) {
    rangeCheck(index);
    char value = elements[index];
    for (int i = index + 1; i < size; i++) {
      elements[i - 1] = elements[i];
    }
    size -= 1;
    return value;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.util.AbstractList#removeRange(int, int)
   */
  @Override
  protected void removeRange(int fromIndex, int toIndex) {
    rangeCheck(fromIndex, toIndex);
    for (int i = fromIndex, j = toIndex; j < size; j++) {
      elements[i] = elements[j];
    }
    size -= (toIndex - fromIndex);
  }

  /**
   * Resizes the array to the specified length, truncating or zero-padding the
   * array as necessary.
   *
   * @param newSize
   *            The new size of the array.
   */
  public void resize(int newSize) {
    if (newSize > elements.length) {
      reallocate(Math.max(newSize, 2 * elements.length));
    } else {
      for (int i = size; i < newSize; i++) {
        elements[i] = 0;
      }
    }
    size = newSize;
  }

  /**
   * Reallocates storage for this array so that there is only enough capacity
   * to hold all elements currently in this array.
   */
  public void trimToSize() {
    reallocate(size);
  }

  /**
   * Ensures that there is enough room in the array to hold the specified
   * number of elements.
   *
   * @param size
   *            The required capacity.
   */
  public void ensureCapacity(int size) {
    if (size > elements.length) {
      reallocate(Math.max(size, 2 * elements.length));
    }
  }

  /**
   * Resizes the underlying array.
   *
   * @param capacity
   *            The new size for the underlying array.
   */
  private void reallocate(int capacity) {
    if (capacity != elements.length) {
      assert (size <= capacity);
      char[] newArray = new char[capacity];
      for (int i = 0; i < size; i++) {
        newArray[i] = elements[i];
      }
      elements = newArray;
    }
  }

}
