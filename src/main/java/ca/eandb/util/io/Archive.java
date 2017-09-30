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

/**
 * Represents an object that supports bidirectional object archiving.
 * @author Brad Kimmel
 */
public interface Archive {

  /**
   * Archives an integer value.
   * If this <code>Archive</code> is for input, the <code>value</code>
   * parameter will be ignored and the value read from the data source
   * will be returned.  If this <code>Archive</code> is for output, the
   * <code>value</code> parameter will be written to the archive and
   * returned.
   * @param value The value to be written, if this <code>Archive</code> is
   *     for output.
   * @return The value read from the data source if this
   *     <code>Archive</code> is for input, or <code>value</code> if this
   *     <code>Archive</code> is for output.
   * @throws IOException if {@code value} could not be read or written
   */
  int archiveInt(int value) throws IOException;

  /**
   * Archives a boolean value.
   * If this <code>Archive</code> is for input, the <code>value</code>
   * parameter will be ignored and the value read from the data source
   * will be returned.  If this <code>Archive</code> is for output, the
   * <code>value</code> parameter will be written to the archive and
   * returned.
   * @param value The value to be written, if this <code>Archive</code> is
   *     for output.
   * @return The value read from the data source if this
   *     <code>Archive</code> is for input, or <code>value</code> if this
   *     <code>Archive</code> is for output.
   * @throws IOException if {@code value} could not be read or written
   */
  boolean archiveBoolean(boolean value) throws IOException;

  /**
   * Archives an <code>Object</code>.
   * If this <code>Archive</code> is for input, the <code>value</code>
   * parameter will be ignored and the value read from the data source
   * will be returned.  If this <code>Archive</code> is for output, the
   * <code>value</code> parameter will be written to the archive and
   * returned.
   * @param value The value to be written, if this <code>Archive</code> is
   *     for output.
   * @return The value read from the data source if this
   *     <code>Archive</code> is for input, or <code>value</code> if this
   *     <code>Archive</code> is for output.
   * @throws IOException if {@code value} could not be read or written
   * @throws ClassNotFoundException if a class definition could not be found
   *     while deserializing an object from this {@link Archive} (only applies
   *     if this archive is in input mode).
   */
  Object archiveObject(Object value) throws IOException, ClassNotFoundException;

  /**
   * Archives a byte value.
   * If this <code>Archive</code> is for input, the <code>value</code>
   * parameter will be ignored and the value read from the data source
   * will be returned.  If this <code>Archive</code> is for output, the
   * <code>value</code> parameter will be written to the archive and
   * returned.
   * @param value The value to be written, if this <code>Archive</code> is
   *     for output.
   * @return The value read from the data source if this
   *     <code>Archive</code> is for input, or <code>value</code> if this
   *     <code>Archive</code> is for output.
   * @throws IOException if {@code value} could not be read or written
   */
  byte archiveByte(byte value) throws IOException;

  /**
   * Archives a character value.
   * If this <code>Archive</code> is for input, the <code>value</code>
   * parameter will be ignored and the value read from the data source
   * will be returned.  If this <code>Archive</code> is for output, the
   * <code>value</code> parameter will be written to the archive and
   * returned.
   * @param value The value to be written, if this <code>Archive</code> is
   *     for output.
   * @return The value read from the data source if this
   *     <code>Archive</code> is for input, or <code>value</code> if this
   *     <code>Archive</code> is for output.
   * @throws IOException if {@code value} could not be read or written
   */
  char archiveChar(char value) throws IOException;

  /**
   * Archives a double precision floating point value.
   * If this <code>Archive</code> is for input, the <code>value</code>
   * parameter will be ignored and the value read from the data source
   * will be returned.  If this <code>Archive</code> is for output, the
   * <code>value</code> parameter will be written to the archive and
   * returned.
   * @param value The value to be written, if this <code>Archive</code> is
   *     for output.
   * @return The value read from the data source if this
   *     <code>Archive</code> is for input, or <code>value</code> if this
   *     <code>Archive</code> is for output.
   * @throws IOException if {@code value} could not be read or written
   */
  double archiveDouble(double value) throws IOException;

  /**
   * Archives a single precision floating point value.
   * If this <code>Archive</code> is for input, the <code>value</code>
   * parameter will be ignored and the value read from the data source
   * will be returned.  If this <code>Archive</code> is for output, the
   * <code>value</code> parameter will be written to the archive and
   * returned.
   * @param value The value to be written, if this <code>Archive</code> is
   *     for output.
   * @return The value read from the data source if this
   *     <code>Archive</code> is for input, or <code>value</code> if this
   *     <code>Archive</code> is for output.
   * @throws IOException if {@code value} could not be read or written
   */
  float archiveFloat(float value) throws IOException;

  /**
   * Archives a long integer value.
   * If this <code>Archive</code> is for input, the <code>value</code>
   * parameter will be ignored and the value read from the data source
   * will be returned.  If this <code>Archive</code> is for output, the
   * <code>value</code> parameter will be written to the archive and
   * returned.
   * @param value The value to be written, if this <code>Archive</code> is
   *     for output.
   * @return The value read from the data source if this
   *     <code>Archive</code> is for input, or <code>value</code> if this
   *     <code>Archive</code> is for output.
   * @throws IOException if {@code value} could not be read or written
   */
  long archiveLong(long value) throws IOException;

  /**
   * Archives a short integer value.
   * If this <code>Archive</code> is for input, the <code>value</code>
   * parameter will be ignored and the value read from the data source
   * will be returned.  If this <code>Archive</code> is for output, the
   * <code>value</code> parameter will be written to the archive and
   * returned.
   * @param value The value to be written, if this <code>Archive</code> is
   *     for output.
   * @return The value read from the data source if this
   *     <code>Archive</code> is for input, or <code>value</code> if this
   *     <code>Archive</code> is for output.
   * @throws IOException if {@code value} could not be read or written
   */
  short archiveShort(short value) throws IOException;

  /**
   * Archives a <code>String</code> value.
   * If this <code>Archive</code> is for input, the <code>value</code>
   * parameter will be ignored and the value read from the data source
   * will be returned.  If this <code>Archive</code> is for output, the
   * <code>value</code> parameter will be written to the archive and
   * returned.
   * @param value The value to be written, if this <code>Archive</code> is
   *     for output.
   * @return The value read from the data source if this
   *     <code>Archive</code> is for input, or <code>value</code> if this
   *     <code>Archive</code> is for output.
   * @throws IOException if {@code value} could not be read or written
   */
  String archiveUTF(String value) throws IOException;

}
