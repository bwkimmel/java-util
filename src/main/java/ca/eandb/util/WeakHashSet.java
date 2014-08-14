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

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * A hash set that holds weak references to its contents.
 * @author Brad Kimmel
 */
public final class WeakHashSet<T> extends AbstractSet<T> implements Set<T> {

	/** Underlying storage for the hash set. */
	private final transient WeakHashMap<T, Boolean> map;

	/**
	 * Creates a new <code>WeakHashSet</code>.
	 */
	public WeakHashSet() {
		map = new WeakHashMap<T, Boolean>();
	}

	/**
	 * Creates a new <code>WeakHashSet</code>.
	 * @param c A <code>Collection</code> of objects to initialize the set
	 * 		with.
	 */
	public WeakHashSet(Collection<T> c) {
		map = new WeakHashMap<T, Boolean>(Math.max((int) (c.size()/.75f) + 1, 16));
		addAll(c);
	}

	/**
	 * Creates a new <code>WeakHashSet</code>.
	 * @param initialCapacity The initial capacity of the set.
	 * @param loadFactor A value indicating the fraction of the set that
	 * 		should be full when the capacity is to be expanded.
	 */
    public WeakHashSet(int initialCapacity, float loadFactor) {
		map = new WeakHashMap<T, Boolean>(initialCapacity, loadFactor);
	}

	/**
	 * Creates a new <code>WeakHashSet</code>.
	 * @param initialCapacity The initial capacity of the set.
	 */
	public WeakHashSet(int initialCapacity) {
		map = new WeakHashMap<T, Boolean>(initialCapacity);
	}

	/* (non-Javadoc)
	 * @see java.util.AbstractCollection#iterator()
	 */
	@Override
	public Iterator<T> iterator() {
		return map.keySet().iterator();
	}

	/* (non-Javadoc)
	 * @see java.util.AbstractCollection#size()
	 */
	@Override
	public int size() {
		return map.size();
	}

	/* (non-Javadoc)
	 * @see java.util.AbstractCollection#isEmpty()
	 */
    public boolean isEmpty() {
		return map.isEmpty();
	}

    /* (non-Javadoc)
     * @see java.util.AbstractCollection#contains(java.lang.Object)
     */
	public boolean contains(Object o) {
		return map.containsKey(o);
	}

	/* (non-Javadoc)
	 * @see java.util.AbstractCollection#add(java.lang.Object)
	 */
	public boolean add(T o) {
		return map.put(o, Boolean.TRUE) == null;
	}

	/* (non-Javadoc)
	 * @see java.util.AbstractCollection#remove(java.lang.Object)
	 */
	public boolean remove(Object o) {
		return map.remove(o) == Boolean.TRUE;
	}

	/* (non-Javadoc)
	 * @see java.util.AbstractCollection#clear()
	 */
	public void clear() {
		map.clear();
	}

}
