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
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * @author brad
 *
 */
public class StrategyClassLoader extends ClassLoader {

	private final ClassLoaderStrategy strategy;

	/**
	 *
	 */
	public StrategyClassLoader(ClassLoaderStrategy strategy) {
		this(strategy, null);
	}

	/**
	 * @param parent
	 */
	public StrategyClassLoader(ClassLoaderStrategy strategy, ClassLoader parent) {
		super(parent);
		this.strategy = strategy;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.ClassLoader#findClass(java.lang.String)
	 */
	@Override
	protected Class<?> findClass(final String name) throws ClassNotFoundException {

		ByteBuffer def = AccessController.doPrivileged(new PrivilegedAction<ByteBuffer>() {
			public ByteBuffer run() {
				return strategy.getClassDefinition(name);
			}
		});

		if (def != null) {

			Class<?> result = super.defineClass(name, def, null);

			if (result != null) {
				super.resolveClass(result);
				return result;
			}

		}

		throw new ClassNotFoundException(name);

	}

}
