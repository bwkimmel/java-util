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

package ca.eandb.util.io;

import java.io.OutputStream;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import ca.eandb.util.UnexpectedException;

/**
 * An <code>OutputStream</code> for writing to a <code>Document</code>.
 * @see javax.swing.text.Document
 * @author Brad Kimmel
 */
public class DocumentOutputStream extends OutputStream {

	/** The <code>Document</code> to write to. */
	private final Document document;

	/**
	 * The <code>AttributeSet</code> to apply to the text written to the
	 * <code>Document</code>.
	 */
	private AttributeSet attributes = null;

	/**
	 * Creates a new <code>DocumentOutputStream</code>.
	 * @param document The <code>Document</code> to write to.
	 */
	public DocumentOutputStream(Document document) {
		this(document, null);
	}

	/**
	 * Creates a new <code>DocumentOutputStream</code>.
	 * @param document The <code>Document</code> to write to.
	 * @param attributes The <code>AttributeSet</code> to apply to the text
	 * 		written to the <code>Document</code>.
	 */
	public DocumentOutputStream(Document document, AttributeSet attributes) {
		this.document = document;
		this.attributes = attributes;
	}

	/**
	 * Sets the <code>AttributeSet</code> to apply to the text written to the
	 * <code>Document</code>.
	 * @param attributes The <code>AttributeSet</code> to apply to the text
	 * 		written to the <code>Document</code>.
	 */
	public void setAttributeSet(AttributeSet attributes) {
		this.attributes = attributes;
	}

	/**
	 * Gets the <code>AttributeSet</code> to apply to the text written to the
	 * <code>Document</code>.
	 * @return The <code>AttributeSet</code> to apply to the text written to
	 * 		the <code>Document</code>.
	 */
	public AttributeSet getAttributeSet() {
		return attributes;
	}

	/* (non-Javadoc)
	 * @see java.io.OutputStream#write(byte[], int, int)
	 */
	@Override
	public void write(byte[] b, int off, int len) {
		write(new String(b, off, len));
	}

	/* (non-Javadoc)
	 * @see java.io.OutputStream#write(byte[])
	 */
	@Override
	public void write(byte[] b) {
		write(b, 0, b.length);
	}

	/* (non-Javadoc)
	 * @see java.io.OutputStream#write(int)
	 */
	@Override
	public void write(int b) {
		write(new byte[] { (byte) b });
	}

	/**
	 * Writes a <code>String</code> to the <code>Document</code> using the
	 * current attribute set.
	 * @param s The <code>String</code> to write to the document.
	 */
	private void write(String s) {
		synchronized (document) {
			int offset = document.getLength();
			try {
				document.insertString(offset, s, attributes);
			} catch (BadLocationException e) {
				throw new UnexpectedException(e);
			}
		}
	}

}
