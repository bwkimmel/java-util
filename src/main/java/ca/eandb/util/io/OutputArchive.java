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

import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * An <code>Archive</code> for writing to an <code>ObjectOutput</code>.
 * @see java.io.ObjectOutput
 * @author Brad Kimmel
 */
public final class OutputArchive implements Archive {

  /** The <code>ObjectOutput</code> to be written to. */
  private final ObjectOutput output;

  /**
   * Creates a new <code>OutputArchive</code>.
   * @param output The <code>ObjectOutput</code> to be written to.
   */
  public OutputArchive(ObjectOutput output) {
    this.output = output;
  }

  /**
   * Creates a new <code>OutputArchive</code> that writes to the specified
   * <code>OutputStream</code>.
   * @param stream The <code>OutputStream</code> to be written to.
   * @return The new <code>OutputArchive</code>.
   * @throws IOException If unable to instantiate an
   *     <code>ObjectOutputStream</code> for the provided
   *     <code>OutputStream</code>.
   */
  public static OutputArchive fromOutputStream(OutputStream stream) throws IOException {
    if (stream instanceof ObjectOutput) {
      return new OutputArchive((ObjectOutput) stream);
    } else {
      return new OutputArchive(new ObjectOutputStream(stream));
    }
  }

  /* (non-Javadoc)
   * @see ca.eandb.util.io.Archive#archiveBoolean(boolean)
   */
  public boolean archiveBoolean(boolean value) throws IOException {
    output.writeBoolean(value);
    return value;
  }

  /* (non-Javadoc)
   * @see ca.eandb.util.io.Archive#archiveByte(byte)
   */
  public byte archiveByte(byte value) throws IOException {
    output.writeByte(value);
    return value;
  }

  /* (non-Javadoc)
   * @see ca.eandb.util.io.Archive#archiveChar(char)
   */
  public char archiveChar(char value) throws IOException {
    output.writeChar(value);
    return value;
  }

  /* (non-Javadoc)
   * @see ca.eandb.util.io.Archive#archiveDouble(double)
   */
  public double archiveDouble(double value) throws IOException {
    output.writeDouble(value);
    return value;
  }

  /* (non-Javadoc)
   * @see ca.eandb.util.io.Archive#archiveFloat(float)
   */
  public float archiveFloat(float value) throws IOException {
    output.writeFloat(value);
    return value;
  }

  /* (non-Javadoc)
   * @see ca.eandb.util.io.Archive#archiveInt(int)
   */
  public int archiveInt(int value) throws IOException {
    output.writeInt(value);
    return value;
  }

  /* (non-Javadoc)
   * @see ca.eandb.util.io.Archive#archiveLong(long)
   */
  public long archiveLong(long value) throws IOException {
    output.writeLong(value);
    return value;
  }

  /* (non-Javadoc)
   * @see ca.eandb.util.io.Archive#archiveObject(java.lang.Object)
   */
  public Object archiveObject(Object value) throws IOException {
    output.writeObject(value);
    return value;
  }

  /* (non-Javadoc)
   * @see ca.eandb.util.io.Archive#archiveShort(short)
   */
  public short archiveShort(short value) throws IOException {
    output.writeShort(value);
    return value;
  }

  /* (non-Javadoc)
   * @see ca.eandb.util.io.Archive#archiveUTF(java.lang.String)
   */
  public String archiveUTF(String value) throws IOException {
    output.writeUTF(value);
    return value;
  }

}
