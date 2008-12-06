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
import java.util.Collection;

/**
 * @author brad
 *
 */
public final class CompositeClassLoaderStrategy implements ClassLoaderStrategy {
	
	private final ClassLoaderStrategy[] strategies;
	
	public CompositeClassLoaderStrategy(Collection<ClassLoaderStrategy> strategies) {
		this(strategies.toArray(new ClassLoaderStrategy[strategies.size()]));
	}
	
	private CompositeClassLoaderStrategy(ClassLoaderStrategy[] strategies) {
		this.strategies = strategies;
	}

	/* (non-Javadoc)
	 * @see ca.eandb.util.classloader.ClassLoaderStrategy#getClassDefinition(java.lang.String)
	 */
	public ByteBuffer getClassDefinition(String name) {

		ByteBuffer def;
		
		for (ClassLoaderStrategy strategy : strategies) {
			if ((def = strategy.getClassDefinition(name)) != null) {
				return def;
			}
		}
		
		return null;

	}

}
