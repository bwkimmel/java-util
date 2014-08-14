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
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

/**
 * An <code>Archive</code> for reading from an <code>ObjectInput</code>.
 * @see java.io.ObjectInput
 * @author Brad Kimmel
 */
public final class InputArchive implements Archive {

  /** The <code>ObjectInput</code> to be read from. */
  private final ObjectInput input;

  /**
   * Creates a new <code>InputArchive</code>.
   * @param input The <code>ObjectInput</code> to be read from.
   */
  public InputArchive(ObjectInput input) {
    this.input = input;
  }

  /**
   * Creates an <code>InputArchive</code> that reads from the provided
   * <code>InputStream</code>.
   * @param stream The <code>InputStream</code> to be read from.
   * @return The new <code>InputArchive</code>.
   * @throws IOException If an <code>ObjectInputStream</code> could not be
   *     instantiated for the provided <code>InputStream</code>.
   */
  public static InputArchive fromInputStream(InputStream stream) throws IOException {
    if (stream instanceof ObjectInput) {
      return new InputArchive((ObjectInput) stream);
    } else {
      return new InputArchive(new ObjectInputStream(stream));
    }
  }

  /* (non-Javadoc)
   * @see ca.eandb.util.io.Archive#archiveBoolean(boolean)
   */
  public boolean archiveBoolean(boolean value) throws IOException {
    return input.readBoolean();
  }

  /* (non-Javadoc)
   * @see ca.eandb.util.io.Archive#archiveByte(byte)
   */
  public byte archiveByte(byte value) throws IOException {
    return input.readByte();
  }

  /* (non-Javadoc)
   * @see ca.eandb.util.io.Archive#archiveChar(char)
   */
  public char archiveChar(char value) throws IOException {
    return input.readChar();
  }

  /* (non-Javadoc)
   * @see ca.eandb.util.io.Archive#archiveDouble(double)
   */
  public double archiveDouble(double value) throws IOException {
    return input.readDouble();
  }

  /* (non-Javadoc)
   * @see ca.eandb.util.io.Archive#archiveFloat(float)
   */
  public float archiveFloat(float value) throws IOException {
    return input.readFloat();
  }

  /* (non-Javadoc)
   * @see ca.eandb.util.io.Archive#archiveInt(int)
   */
  public int archiveInt(int value) throws IOException {
    return input.readInt();
  }

  /* (non-Javadoc)
   * @see ca.eandb.util.io.Archive#archiveLong(long)
   */
  public long archiveLong(long value) throws IOException {
    return input.readLong();
  }

  /* (non-Javadoc)
   * @see ca.eandb.util.io.Archive#archiveObject(java.lang.Object)
   */
  public Object archiveObject(Object value) throws IOException, ClassNotFoundException {
    return input.readObject();
  }

  /* (non-Javadoc)
   * @see ca.eandb.util.io.Archive#archiveShort(short)
   */
  public short archiveShort(short value) throws IOException {
    return input.readShort();
  }

  /* (non-Javadoc)
   * @see ca.eandb.util.io.Archive#archiveUTF(java.lang.String)
   */
  public String archiveUTF(String value) throws IOException {
    return input.readUTF();
  }

}
