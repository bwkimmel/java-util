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

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * Utility methods for working with streams.
 * @author Brad Kimmel
 */
public final class StreamUtil {

  /**
   * Reads a null-terminated string of bytes from the specified input stream.
   * @param in The <code>InputStream</code> to read from.
   * @return The <code>String</string> read from the file.
   * @throws IOException If an I/O error occurs
   * @throws EOFException If the end of the file is reached before a null
   *     byte is read.
   */
  public static String readNullTerminatedString(InputStream in) throws IOException, EOFException {
    return readNullTerminatedString((DataInput) new DataInputStream(in));
  }

  /**
   * Reads a null-terminated string of bytes from the specified input.
   * @param in The <code>DataInput</code> to read from.
   * @return The <code>String</string> read from the file.
   * @throws IOException If an I/O error occurs
   * @throws EOFException If the end of the file is reached before a null
   *     byte is read.
   */
  public static String readNullTerminatedString(DataInput in) throws IOException, EOFException {
    StringBuilder s = new StringBuilder();
    while (true) {
      int b = in.readByte();
      if (b == 0) {
        break;
      }
      s.append((char) b);
    }
    return s.toString();
  }

  /**
   * Copies the contents of an <code>InputStream</code> to an
   * <code>OutputStream</code>.
   * @param in The <code>InputStream</code> to read from.
   * @param out The <code>OutputStream</code> to write to.
   * @throws IOException If unable to read from <code>in</code> or write to
   *     <code>out</code>.
   */
  public static void writeStream(InputStream in, OutputStream out) throws IOException {
    int c;
    while ((c = in.read()) >= 0) {
      out.write((byte) c);
    }
  }

  /**
   * Writes the contents of a <code>ByteBuffer</code> to the provided
   * <code>OutputStream</code>.
   * @param bytes The <code>ByteBuffer</code> containing the bytes to
   *     write.
   * @param out The <code>OutputStream</code> to write to.
   * @throws IOException If unable to write to the
   *     <code>OutputStream</code>.
   */
  public static void writeBytes(ByteBuffer bytes, OutputStream out) throws IOException {
    final int BUFFER_LENGTH = 1024;
    byte[] buffer;

    if (bytes.remaining() >= BUFFER_LENGTH) {
      buffer = new byte[BUFFER_LENGTH];
      do {
        bytes.get(buffer);
        out.write(buffer);
      } while (bytes.remaining() >= BUFFER_LENGTH);
    } else {
      buffer = new byte[bytes.remaining()];
    }

    int remaining = bytes.remaining();
    if (remaining > 0) {
      bytes.get(buffer, 0, remaining);
      out.write(buffer, 0, remaining);
    }
  }

  /** Declared private to prevent this class from being instantiated. */
  private StreamUtil() {}

}
