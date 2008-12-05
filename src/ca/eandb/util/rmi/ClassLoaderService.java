/**
 * 
 */
package ca.eandb.util.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author brad
 *
 */
public interface ClassLoaderService extends Remote {
	
	byte[] getClassDefinition(String name) throws RemoteException;

}
