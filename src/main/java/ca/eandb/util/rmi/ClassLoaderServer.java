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

package ca.eandb.util.rmi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;

import ca.eandb.util.ClassUtil;

/**
 * @author Brad Kimmel
 *
 */
public final class ClassLoaderServer implements ClassLoaderService {

  /* (non-Javadoc)
   * @see ca.eandb.util.rmi.ClassLoaderService#getClassDefinition(java.lang.String)
   */
  public byte[] getClassDefinition(String name) throws RemoteException {

    try {

      ByteArrayOutputStream out = new ByteArrayOutputStream();
      Class<?> cl = Class.forName(name);

      ClassUtil.writeClassToStream(cl, out);

      out.flush();
      return out.toByteArray();

    } catch (ClassNotFoundException e) {

      return null;

    } catch (IOException e) {

      e.printStackTrace();
      return null;

    }

  }

}
