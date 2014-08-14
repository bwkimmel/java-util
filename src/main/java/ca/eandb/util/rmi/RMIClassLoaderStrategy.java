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

import java.net.URL;
import java.nio.ByteBuffer;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.jnlp.BasicService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;

import ca.eandb.util.classloader.ClassLoaderStrategy;

/**
 * @author Brad Kimmel
 *
 */
public final class RMIClassLoaderStrategy implements ClassLoaderStrategy {

  private final ClassLoaderService service;

  public RMIClassLoaderStrategy() throws RemoteException, NotBoundException {
    this(getClassLoaderService());
  }

  public RMIClassLoaderStrategy(URL baseUrl) throws RemoteException, NotBoundException {
    this(getClassLoaderService(baseUrl));
  }

  public RMIClassLoaderStrategy(ClassLoaderService service) {
    this.service = service;
  }

  public static ClassLoaderService getClassLoaderService() throws RemoteException, NotBoundException {
    try {
      BasicService basic = (BasicService) ServiceManager.lookup("javax.jnlp.BasicService");
      return getClassLoaderService(basic.getCodeBase());
    } catch (UnavailableServiceException e) {
      throw new IllegalStateException("javax.jnlp.BasicService is required.", e);
    }
  }

  public static ClassLoaderService getClassLoaderService(URL baseUrl) throws RemoteException, NotBoundException {
    Registry registry = LocateRegistry.getRegistry(baseUrl.getHost());
    return (ClassLoaderService) registry.lookup("ClassLoaderService");
  }

  /* (non-Javadoc)
   * @see ca.eandb.util.classloader.ClassLoaderStrategy#getClassDefinition(java.lang.String)
   */
  public ByteBuffer getClassDefinition(String name) {

    try {

      byte[] def = service.getClassDefinition(name);

      if (def != null) {
        return ByteBuffer.wrap(def);
      }

    } catch (RemoteException e) {
      e.printStackTrace();
    }

    return null;

  }

}
