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

package ca.eandb.util.classloader;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * A <code>ClassLoaderStrategy</code> that loads class definitions from a
 * <code>Map</code>.
 * @author brad
 */
public final class MapClassLoaderStrategy implements ClassLoaderStrategy {

	/** The <code>Map</code> containing the class definitions. */
	private final Map<String, ByteBuffer> classDefs;

	/**
	 * Creates a new <code>MapClassLoaderStrategy</code>.
	 */
	public MapClassLoaderStrategy() {
		this.classDefs = new HashMap<String, ByteBuffer>();
	}

	/**
	 * Creates a new <code>MapClassLoaderStrategy</code>.
	 * @param classDefs The <code>Map</code> from which to get class
	 * 		definitions.
	 */
	public MapClassLoaderStrategy(Map<String, ByteBuffer> classDefs) {
		this.classDefs = classDefs;
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.classloader.ClassLoaderStrategy#getClassDefinition(java.lang.String)
	 */
	public ByteBuffer getClassDefinition(String name) {
		return classDefs.get(name);
	}

	/**
	 * Sets the definition of a class.
	 * @param name The name of the class to define.
	 * @param def The definition of the class.
	 */
	public void setClassDefinition(String name, byte[] def) {
		classDefs.put(name, ByteBuffer.wrap(def));
	}

	/**
	 * Sets the definition of a class.
	 * @param name The name of the class to define.
	 * @param def The definition of the class.
	 */
	public void setClassDefinition(String name, ByteBuffer def) {
		classDefs.put(name, def);
	}

}