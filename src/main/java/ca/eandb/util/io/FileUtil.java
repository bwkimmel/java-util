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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import ca.eandb.util.UnexpectedException;

/**
 * File I/O utility methods.
 * @author Brad Kimmel
 */
public final class FileUtil {

  /**
   * Recursively removes all entries from a directory.
   * @param directory The directory to clear.
   * @return A value indicating whether the directory was successfully
   *     cleared.  If unsuccessful, the directory may have been partially
   *     cleared.
   */
  public static boolean clearDirectory(File directory) {
    if (directory.isDirectory()) {
      for (File file : directory.listFiles()) {
        if (!deleteRecursive(file)) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Recursively removes a file or a directory and all its subdirectories.
   * @param file The file or directory to delete.
   * @return A value indicating whether the file directory was successfully
   *     removed.  If unsuccessful, the directory may have been partially
   *     cleared.
   */
  public static boolean deleteRecursive(File file) {
    return clearDirectory(file) && file.delete();
  }

  /**
   * Reads the contents of a file into a byte array.
   * @param file The <code>File</code> to read.
   * @return A byte array containing the file's contents.
   * @throws IOException If an error occurred while reading the file.
   */
  public static byte[] getFileContents(File file) throws IOException {
    FileInputStream stream = new FileInputStream(file);
    byte[] contents = new byte[(int) file.length()];
    stream.read(contents);
    stream.close();
    return contents;
  }

  /**
   * Writes the specified byte array to a file.
   * @param file The <code>File</code> to write.
   * @param contents The byte array to write to the file.
   * @throws IOException If the file could not be written.
   */
  public static void setFileContents(File file, byte[] contents) throws IOException {
    setFileContents(file, contents, false);
  }

  /**
   * Writes the specified byte array to a file.
   * @param file The <code>File</code> to write.
   * @param contents The byte array to write to the file.
   * @param createDirectory A value indicating whether the directory
   *     containing the file to be written should be created if it does
   *     not exist.
   * @throws IOException If the file could not be written.
   */
  public static void setFileContents(File file, byte[] contents, boolean createDirectory) throws IOException {
    if (createDirectory) {
      File directory = file.getParentFile();
      if (!directory.exists()) {
        directory.mkdirs();
      }
    }

    FileOutputStream stream = new FileOutputStream(file);
    stream.write(contents);
    stream.close();
  }

  /**
   * Writes the specified byte array to a file.
   * @param file The <code>File</code> to write.
   * @param contents The byte array to write to the file.
   * @throws IOException If the file could not be written.
   */
  public static void setFileContents(File file, ByteBuffer contents) throws IOException {
    setFileContents(file, contents, false);
  }

  /**
   * Writes the specified byte array to a file.
   * @param file The <code>File</code> to write.
   * @param contents The byte array to write to the file.
   * @param createDirectory A value indicating whether the directory
   *     containing the file to be written should be created if it does
   *     not exist.
   * @throws IOException If the file could not be written.
   */
  public static void setFileContents(File file, ByteBuffer contents, boolean createDirectory) throws IOException {
    if (createDirectory) {
      File directory = file.getParentFile();
      if (!directory.exists()) {
        directory.mkdirs();
      }
    }

    FileOutputStream stream = new FileOutputStream(file);
    StreamUtil.writeBytes(contents, stream);
    stream.close();
  }

  /**
   * Writes a single object to a file.
   * @param file The <code>File</code> to write to.
   * @param object The <code>Object</code> to write.
   * @throws IOException If the file could not be written.
   */
  public static void writeObjectToFile(File file, Object object) throws IOException {
    FileOutputStream fs = new FileOutputStream(file);
    ObjectOutputStream os = new ObjectOutputStream(fs);
    os.writeObject(object);
    os.close();
  }

  /**
   * Reads a single object from a file.
   * @param file The <code>File</code> to read from.
   * @return The <code>Object</code> read from the file.
   * @throws IOException If the file could not be read.
   * @throws ClassNotFoundException If the object's class could not be found.
   */
  public static Object readObjectFromFile(File file) throws IOException, ClassNotFoundException {
    FileInputStream fs = new FileInputStream(file);
    ObjectInputStream os = new ObjectInputStream(fs);
    Object object = os.readObject();
    os.close();
    return object;
  }

  /**
   * Determines if the specified directory is an ancestor of the specified
   * file or directory.
   * @param file The file or directory to test.
   * @param ancestor The directory for which to determine whether
   *     <code>file</code> is an ancestor.
   * @return <code>true</code> if <code>ancestor</code> is equal to or an
   *     ancestor of <code>file</code>, <code>false</code> otherwise.
   * @throws IOException
   */
  public static boolean isAncestor(File file, File ancestor) throws IOException {
    file = file.getCanonicalFile();
    ancestor = ancestor.getCanonicalFile();
    do {
      if (file.equals(ancestor)) {
        return true;
      }
      file = file.getParentFile();
    } while (file != null);
    return false;
  }

  /**
   * Removes a file or directory and its ancestors up to, but not including
   * the specified directory, until a non-empty directory is reached.
   * @param file The file or directory at which to start pruning.
   * @param root The directory at which to stop pruning.
   */
  public static void prune(File file, File root) {
    while (!file.equals(root) && file.delete()) {
      file = file.getParentFile();
    }
  }

  /**
   * Walks a directory tree using post-order traversal.  The contents of a
   * directory are visited before the directory itself is visited.
   * @param root The <code>File</code> indicating the file or directory to
   *     walk.
   * @param visitor The <code>FileVisitor</code> to use to visit files and
   *     directories while walking the tree.
   * @return A value indicating whether the tree walk was completed without
   *     {@link FileVisitor#visit(File)} ever returning false.
   * @throws Exception If {@link FileVisitor#visit(File)} threw an exception.
   * @see FileVisitor#visit(File)
   */
  public static boolean postOrderTraversal(File root, FileVisitor visitor) throws Exception {
    if (root.isDirectory()) {
      for (File child : root.listFiles()) {
        if (!postOrderTraversal(child, visitor)) {
          return false;
        }
      }
    }
    return visitor.visit(root);
  }

  /**
   * Walks a directory tree using pre-order traversal.  The contents of a
   * directory are visited after the directory itself is visited.
   * @param root The <code>File</code> indicating the file or directory to
   *     walk.
   * @param visitor The <code>FileVisitor</code> to use to visit files and
   *     directories while walking the tree.
   * @return A value indicating whether the tree walk was completed without
   *     {@link FileVisitor#visit(File)} ever returning false.
   * @throws Exception If {@link FileVisitor#visit(File)} threw an exception.
   * @see FileVisitor#visit(File)
   */
  public static boolean preOrderTraversal(File root, FileVisitor visitor) throws Exception {
    if (!visitor.visit(root)) {
      return false;
    }
    if (root.isDirectory()) {
      for (File child : root.listFiles()) {
        if (!preOrderTraversal(child, visitor)) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Zips the contents of a directory.
   * @param zipFile The <code>File</code> indicating the zip file to create.
   * @param contents The <code>File</code> indicating the directory to zip.
   * @throws IOException If the contents could not be read or if the zip file
   *     could not be written.
   */
  public static void zip(File zipFile, final File contents) throws IOException {

    OutputStream os = new FileOutputStream(zipFile);
    final ZipOutputStream zs = new ZipOutputStream(os);

    try {
      preOrderTraversal(contents, new FileVisitor() {

        public boolean visit(File file) throws IOException {
          if (file.isFile()) {
            String name = getRelativePath(file, contents);
            zs.putNextEntry(new ZipEntry(name));

            FileInputStream fs = new FileInputStream(file);
            StreamUtil.writeStream(fs, zs);
            fs.close();

            zs.closeEntry();
          }
          return true;
        }

      });
    } catch (IOException e) {
      throw e;
    } catch (Exception e) {
      throw new UnexpectedException(e);
    }

    zs.close();

  }

  /**
   * Gets the <code>String</code> representing the path to a file relative to
   * a given directory.
   * @param file The <code>File</code> for which to obtain the relative path.
   * @param base The <code>File</code> representing the directory that the
   *     resulting path should be relative to.
   * @return The <code>String</code> representing the relative path.
   * @throws IOException If a directory along the walk from <code>base</code>
   *     to <code>file</code> could not be read.
   */
  public static String getRelativePath(File file, File base) throws IOException {
    StringWriter path = new StringWriter();

    while (!isAncestor(file, base)) {
      path.append("../");
    }

    String fileName = file.getAbsolutePath();
    String baseName = base.getAbsolutePath();
    int prefixLength = baseName.length();

    if (!baseName.endsWith("/")) {
      prefixLength++;
    }

    path.append(fileName.substring(prefixLength));

    return path.toString();
  }

  /**
     * Returns the appropriate working directory for storing application data. The result of this method is platform
     * dependant: On linux, it will return ~/applicationName, on windows, the working directory will be located in the
     * user's application data folder. For Mac OS systems, the working directory will be placed in the proper location
     * in "Library/Application Support".
     * <p/>
     * This method will also make sure that the working directory exists. When invoked, the directory and all required
     * subfolders will be created.
     *
     * @param applicationName Name of the application, used to determine the working directory.
     * @return the appropriate working directory for storing application data.
     */
  public static File getApplicationDataDirectory(final String applicationName) {
        final String userHome = System.getProperty("user.home", ".");
        final File workingDirectory;
        final String osName = System.getProperty("os.name", "").toLowerCase();
        if (osName.contains("windows")) {
          final String applicationData = System.getenv("APPDATA");
          if (applicationData != null)
              workingDirectory = new File(applicationData, applicationName + '/');
            else
              workingDirectory = new File(userHome, '.' + applicationName + '/');
        } else if (osName.contains("mac")) {
          workingDirectory = new File(userHome, "Library/Application Support/" + applicationName);
        } else {
          workingDirectory = new File(userHome, '.' + applicationName + '/');
        }
        if (!workingDirectory.exists())
            if (!workingDirectory.mkdirs())
                throw new RuntimeException("The working directory could not be created: " + workingDirectory);
        return workingDirectory;
    }

  /**
   * Gets the extension part of the specified file name.
   * @param fileName The name of the file to get the extension of.
   * @return The extension of the file name.
   */
  public static String getExtension(String fileName) {
    int pos = fileName.lastIndexOf('.');
    if (pos < 0) {
      return "";
    }
    return fileName.substring(pos + 1);
  }

  /** Declared private to prevent this class from being instantiated. */
  private FileUtil() {}

}
