/**
 *
 */
package ca.eandb.util.rmi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;

import ca.eandb.util.ClassUtil;

/**
 * @author brad
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
