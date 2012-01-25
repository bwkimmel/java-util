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

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * An <code>OutputStream</code> that writes data in little-endian format.
 * @author Brad Kimmel
 */
public final class LittleEndianDataOutputStream extends OutputStream implements
		DataOutput {

	/** The <code>OutputStream</code> to write to. */
	private final DataOutputStream inner;
	
	/** A temporary working buffer. */
	private final byte[] work = new byte[8];
	
	/**
	 * Creates a new <code>LittleEndianDataOutputStream</code>.
	 * @param inner The <code>OutputStream</code> to write to.
	 */
	public LittleEndianDataOutputStream(OutputStream inner) {
		this.inner = inner instanceof DataOutputStream ? (DataOutputStream) inner : new DataOutputStream(inner);
	}

	/* (non-Javadoc)
	 * @see java.io.DataOutput#writeBoolean(boolean)
	 */
	@Override
	public void writeBoolean(boolean v) throws IOException {
		work[0] = (byte) (v ? 1 : 0);
		write(work, 0, 1);
	}

	/* (non-Javadoc)
	 * @see java.io.DataOutput#writeByte(int)
	 */
	@Override
	public void writeByte(int v) throws IOException {
		work[0] = (byte) (v & 0xff);
		write(work, 0, 1);
	}

	/* (non-Javadoc)
	 * @see java.io.DataOutput#writeBytes(java.lang.String)
	 */
	@Override
	public void writeBytes(String s) throws IOException {
		inner.writeBytes(s);
	}

	/* (non-Javadoc)
	 * @see java.io.DataOutput#writeChar(int)
	 */
	@Override
	public void writeChar(int v) throws IOException {
		work[0] = (byte) (0xff & v); 
		work[1] = (byte) (0xff & (v >> 8));
		write(work, 0, 2);
	}

	/* (non-Javadoc)
	 * @see java.io.DataOutput#writeChars(java.lang.String)
	 */
	@Override
	public void writeChars(String s) throws IOException {
		for (int i = 0, n = s.length(); i < n; i++) {
			writeChar(s.charAt(i));
		}
	}

	/* (non-Javadoc)
	 * @see java.io.DataOutput#writeDouble(double)
	 */
	@Override
	public void writeDouble(double v) throws IOException {
		writeLong(Double.doubleToLongBits(v));
	}

	/* (non-Javadoc)
	 * @see java.io.DataOutput#writeFloat(float)
	 */
	@Override
	public void writeFloat(float v) throws IOException {
		writeInt(Float.floatToIntBits(v));
	}

	/* (non-Javadoc)
	 * @see java.io.DataOutput#writeInt(int)
	 */
	@Override
	public void writeInt(int v) throws IOException {
		work[0] = (byte) (0xffL & v);
		work[1] = (byte) (0xffL & (v >> 8));
		work[2] = (byte) (0xffL & (v >> 16));
		work[3] = (byte) (0xffL & (v >> 24));
		write(work, 0, 8);
	}

	/* (non-Javadoc)
	 * @see java.io.DataOutput#writeLong(long)
	 */
	@Override
	public void writeLong(long v) throws IOException {
		work[0] = (byte) (0xffL & v);
		work[1] = (byte) (0xffL & (v >> 8));
		work[2] = (byte) (0xffL & (v >> 16));
		work[3] = (byte) (0xffL & (v >> 24));
		work[4] = (byte) (0xffL & (v >> 32));
		work[5] = (byte) (0xffL & (v >> 40));
		work[6] = (byte) (0xffL & (v >> 48));
		work[7] = (byte) (0xffL & (v >> 56));
		write(work, 0, 8);
	}

	/* (non-Javadoc)
	 * @see java.io.DataOutput#writeShort(int)
	 */
	@Override
	public void writeShort(int v) throws IOException {
		work[0] = (byte) (0xff & v);
		work[1] = (byte) (0xff & (v >> 8));
		write(work, 0, 2);
	}

	/* (non-Javadoc)
	 * @see java.io.DataOutput#writeUTF(java.lang.String)
	 */
	@Override
	public void writeUTF(String str) throws IOException {
		inner.writeUTF(str);
	}

	/* (non-Javadoc)
	 * @see java.io.OutputStream#write(int)
	 */
	@Override
	public void write(int b) throws IOException {
		inner.write(b);
	}

	/* (non-Javadoc)
	 * @see java.io.OutputStream#close()
	 */
	@Override
	public void close() throws IOException {
		inner.close();
	}

	/* (non-Javadoc)
	 * @see java.io.OutputStream#flush()
	 */
	@Override
	public void flush() throws IOException {
		inner.flush();
	}

	/* (non-Javadoc)
	 * @see java.io.OutputStream#write(byte[], int, int)
	 */
	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		inner.write(b, off, len);
	}

	/* (non-Javadoc)
	 * @see java.io.OutputStream#write(byte[])
	 */
	@Override
	public void write(byte[] b) throws IOException {
		inner.write(b);
	}

}
