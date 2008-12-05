/**
 *
 */
package ca.eandb.util.io;

import java.io.File;

/**
 * @author brad
 *
 */
public interface FileVisitor {

	boolean visit(File file) throws Exception;

}
