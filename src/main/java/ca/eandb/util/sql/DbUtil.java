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

package ca.eandb.util.sql;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

/**
 * Database utility methods.
 * @author Brad Kimmel
 */
public final class DbUtil {

  /**
   * Runs a SQL query that returns a single integer value.
   * @param ds The <code>DataSource</code> against which to run the query.
   * @param def The default value to return if the query returns no results.
   * @param query The SQL query to run.
   * @param param The parameters to the SQL query.
   * @return The value returned by the query, or <code>def</code> if the
   *     query returns no results.  It is assumed that the query
   *     returns a result set consisting of a single row and column, and
   *    that this value is an integer.  Any additional rows or columns
   *    returned will be ignored.
   * @throws SQLException If an error occurs while attempting to communicate
   *     with the database.
   */
  public static int queryInt(DataSource ds, int def, String query, Object... param) throws SQLException {
    Connection con = null;
    try {
      con = ds.getConnection();
      return queryInt(con, def, query, param);
    } finally {
      close(con);
    }
  }

  /**
   * Runs a SQL query that returns a single integer value.
   * @param con The <code>Connection</code> against which to run the query.
   * @param def The default value to return if the query returns no results.
   * @param query The SQL query to run.
   * @param param The parameters to the SQL query.
   * @return The value returned by the query, or <code>def</code> if the
   *     query returns no results.  It is assumed that the query
   *     returns a result set consisting of a single row and column, and
   *    that this value is an integer.  Any additional rows or columns
   *    returned will be ignored.
   * @throws SQLException If an error occurs while attempting to communicate
   *     with the database.
   */
  public static int queryInt(Connection con, int def, String query, Object... param) throws SQLException {
    PreparedStatement stmt = null;
    try {
      stmt = con.prepareStatement(query);
      stmt.setMaxRows(1);
      for (int i = 0; i < param.length; i++) {
        stmt.setObject(i + 1, param[i]);
      }
      return queryInt(stmt, def);
    } finally {
      close(stmt);
    }
  }

  /**
   * Runs a SQL query that returns a single integer value.
   * @param stmt The <code>PreparedStatement</code> to run.
   * @param def The default value to return if the query returns no results.
   * @return The value returned by the query, or <code>def</code> if the
   *     query returns no results.  It is assumed that the query
   *     returns a result set consisting of a single row and column, and
   *    that this value is an integer.  Any additional rows or columns
   *    returned will be ignored.
   * @throws SQLException If an error occurs while attempting to communicate
   *     with the database.
   */
  public static int queryInt(PreparedStatement stmt, int def) throws SQLException {
    ResultSet rs = null;
    try {
      rs = stmt.executeQuery();
      if (rs.next()) {
        int value = rs.getInt(1);
        if (!rs.wasNull()) {
          return value;
        }
      }
      return def;
    } finally {
      close(rs);
    }
  }

  /**
   * Runs a SQL query that returns a single <code>String</code> value.
   * @param ds The <code>DataSource</code> against which to run the query.
   * @param def The default value to return if the query returns no results.
   * @param query The SQL query to run.
   * @param param The parameters to the SQL query.
   * @return The value returned by the query, or <code>def</code> if the
   *     query returns no results.  It is assumed that the query
   *     returns a result set consisting of a single row and column, and
   *    that this value is a <code>String</code>.  Any additional rows or
   *    columns returned will be ignored.
   * @throws SQLException If an error occurs while attempting to communicate
   *     with the database.
   */
  public static String queryString(DataSource ds, String def, String query, Object... param) throws SQLException {
    Connection con = null;
    try {
      con = ds.getConnection();
      return queryString(con, def, query, param);
    } finally {
      close(con);
    }
  }

  /**
   * Runs a SQL query that returns a single <code>String</code> value.
   * @param con The <code>Connection</code> against which to run the query.
   * @param def The default value to return if the query returns no results.
   * @param query The SQL query to run.
   * @param param The parameters to the SQL query.
   * @return The value returned by the query, or <code>def</code> if the
   *     query returns no results.  It is assumed that the query
   *     returns a result set consisting of a single row and column, and
   *    that this value is a <code>String</code>.  Any additional rows or
   *    columns returned will be ignored.
   * @throws SQLException If an error occurs while attempting to communicate
   *     with the database.
   */
  public static String queryString(Connection con, String def, String query, Object... param) throws SQLException {
    PreparedStatement stmt = null;
    try {
      stmt = con.prepareStatement(query);
      stmt.setMaxRows(1);
      for (int i = 0; i < param.length; i++) {
        stmt.setObject(i + 1, param[i]);
      }
      return queryString(stmt, def);
    } finally {
      close(stmt);
    }
  }

  /**
   * Runs a SQL query that returns a single <code>String</code> value.
   * @param stmt The <code>PreparedStatement</code> to run.
   * @param def The default value to return if the query returns no results.
   * @return The value returned by the query, or <code>def</code> if the
   *     query returns no results.  It is assumed that the query
   *     returns a result set consisting of a single row and column, and
   *    that this value is a <code>String</code>.  Any additional rows or
   *    columns returned will be ignored.
   * @throws SQLException If an error occurs while attempting to communicate
   *     with the database.
   */
  public static String queryString(PreparedStatement stmt, String def) throws SQLException {
    ResultSet rs = null;
    try {
      rs = stmt.executeQuery();
      return rs.next() ? rs.getString(1) : def;
    } finally {
      close(rs);
    }
  }

  /**
   * Runs a SQL query that returns a single byte array value.
   * @param ds The <code>DataSource</code> against which to run the query.
   * @param def The default value to return if the query returns no results.
   * @param query The SQL query to run.
   * @param param The parameters to the SQL query.
   * @return The value returned by the query, or <code>def</code> if the
   *     query returns no results.  It is assumed that the query
   *     returns a result set consisting of a single row and column, and
   *    that this value is a byte array.  Any additional rows or columns
   *    returned will be ignored.
   * @throws SQLException If an error occurs while attempting to communicate
   *     with the database.
   */
  public static byte[] queryBinary(DataSource ds, byte[] def, String query, Object... param) throws SQLException {
    Connection con = null;
    try {
      con = ds.getConnection();
      return queryBinary(con, def, query, param);
    } finally {
      close(con);
    }
  }

  /**
   * Runs a SQL query that returns a single byte array value.
   * @param con The <code>Connection</code> against which to run the query.
   * @param def The default value to return if the query returns no results.
   * @param query The SQL query to run.
   * @param param The parameters to the SQL query.
   * @return The value returned by the query, or <code>def</code> if the
   *     query returns no results.  It is assumed that the query
   *     returns a result set consisting of a single row and column, and
   *    that this value is a byte array.  Any additional rows or columns
   *    returned will be ignored.
   * @throws SQLException If an error occurs while attempting to communicate
   *     with the database.
   */
  public static byte[] queryBinary(Connection con, byte[] def, String query, Object... param) throws SQLException {
    PreparedStatement stmt = null;
    try {
      stmt = con.prepareStatement(query);
      stmt.setMaxRows(1);
      for (int i = 0; i < param.length; i++) {
        stmt.setObject(i + 1, param[i]);
      }
      return queryBinary(stmt, def);
    } finally {
      close(stmt);
    }
  }

  /**
   * Runs a SQL query that returns a single byte array value.
   * @param stmt The <code>PreparedStatement</code> to run.
   * @param def The default value to return if the query returns no results.
   * @return The value returned by the query, or <code>def</code> if the
   *     query returns no results.  It is assumed that the query
   *     returns a result set consisting of a single row and column, and
   *    that this value is a byte array.  Any additional rows or columns
   *    returned will be ignored.
   * @throws SQLException If an error occurs while attempting to communicate
   *     with the database.
   */
  public static byte[] queryBinary(PreparedStatement stmt, byte[] def) throws SQLException {
    ResultSet rs = null;
    try {
      rs = stmt.executeQuery();
      return rs.next() ? rs.getBytes(1) : def;
    } finally {
      close(rs);
    }
  }

  /**
   * Runs a SQL query that inserts, updates, or deletes rows.
   * @param ds The <code>DataSource</code> against which to run the query.
   * @param def The default value to return if the query returns no results.
   * @param query The SQL query to run.
   * @param param The parameters to the SQL query.
   * @return The number of rows affected by the query.
   * @throws SQLException If an error occurs while attempting to communicate
   *     with the database.
   */
  public static int update(DataSource ds, String query, Object... param) throws SQLException {
    Connection con = null;
    try {
      con = ds.getConnection();
      return update(con, query, param);
    } finally {
      close(con);
    }
  }

  /**
   * Runs a SQL query that inserts, updates, or deletes rows.
   * @param con The <code>Connection</code> against which to run the query.
   * @param def The default value to return if the query returns no results.
   * @param query The SQL query to run.
   * @param param The parameters to the SQL query.
   * @return The number of rows affected by the query.
   * @throws SQLException If an error occurs while attempting to communicate
   *     with the database.
   */
  public static int update(Connection con, String query, Object... param) throws SQLException {
    PreparedStatement stmt = null;
    try {
      stmt = con.prepareStatement(query);
      for (int i = 0; i < param.length; i++) {
        stmt.setObject(i + 1, param[i]);
      }
      return stmt.executeUpdate();
    } finally {
      close(stmt);
    }
  }

  /**
   * Rolls back the current transaction for a <code>Connection</code>.  Any
   * <code>SQLException</code>s thrown are ignored.
   * @param con The <code>Connection</code> to roll back.  If this value is
   *     <code>null</code>, no action is performed.
   */
  public static void rollback(Connection con) {
    if (con != null) {
      try {
        con.rollback();
      } catch (SQLException e) {
        /* nothing to do. */
      }
    }
  }

  /**
   * Closes a connection.  Any <code>SQLException</code>s thrown are ignored.
   * @param con The <code>Connection</code> to close.  If this value is
   *     <code>null</code>, no action is performed.
   */
  public static void close(Connection con) {
    if (con != null) {
      try {
        con.close();
      } catch (SQLException e) {
        /* nothing to do. */
      }
    }
  }

  /**
   * Closes a result set.  Any <code>SQLException</code>s thrown are ignored.
   * @param con The <code>ResultSet</code> to close.  If this value is
   *     <code>null</code>, no action is performed.
   */
  public static void close(ResultSet rs) {
    if (rs != null) {
      try {
        rs.close();
      } catch (SQLException e) {
        /* nothing to do. */
      }
    }
  }

  /**
   * Closes a statement.  Any <code>SQLException</code>s thrown are ignored.
   * @param con The <code>Statement</code> to close.  If this value is
   *     <code>null</code>, no action is performed.
   */
  public static void close(Statement stmt) {
    if (stmt != null) {
      try {
        stmt.close();
      } catch (SQLException e) {
        /* nothing to do. */
      }
    }
  }

  /**
   * Closes a result set, statement, and connection.  Any
   * <code>SQLException</code>s thrown are ignored.
   * @param rs The <code>ResultSet</code> to close.  If this value is
   *     <code>null</code>, no <code>ResultSet</code> is closed.
   * @param stmt The <code>Statement</code> to close.  If this value is
   *     <code>null</code>, no <code>Statement</code> is closed.
   * @param con The <code>Connection</code> to close.  If this value is
   *     <code>null</code>, no <code>Connection</code> is closed.
   */
  public static void close(ResultSet rs, Statement stmt, Connection con) {
    close(rs);
    close(stmt);
    close(con);
  }

  /**
   * Gets the <code>String</code> denoting the specified SQL data type.
   * @param type The data type to get the name of.  Valid type values
   *     consist of the static fields of {@link java.sql.Types}.
   * @param ds The <code>DataSource</code> for which to get the type name.
   * @return The name of the type, or <code>null</code> if no such type
   *     exists.
   * @throws SQLException If an error occurs while communicating with the
   *     database.
   * @see java.sql.Types
   */
  public static String getTypeName(int type, DataSource ds) throws SQLException {
    return getTypeName(type, 0, ds);
  }

  /**
   * Gets the <code>String</code> denoting the specified SQL data type.
   * @param type The data type to get the name of.  Valid type values
   *     consist of the static fields of {@link java.sql.Types}.
   * @param length The length to assign to data types for those types
   *     that require a length (e.g., <code>VARCHAR(n)</code>), or zero
   *     to indicate that no length is required.
   * @param ds The <code>DataSource</code> for which to get the type name.
   * @return The name of the type, or <code>null</code> if no such type
   *     exists.
   * @throws SQLException If an error occurs while communicating with the
   *     database.
   * @see java.sql.Types
   */
  public static String getTypeName(int type, int length, DataSource ds) throws SQLException {
    Connection con = null;
    try {
      con = ds.getConnection();
      return getTypeName(type, length, con);
    } finally {
      close(con);
    }
  }

  /**
   * Gets the <code>String</code> denoting the specified SQL data type.
   * @param type The data type to get the name of.  Valid type values
   *     consist of the static fields of {@link java.sql.Types}.
   * @param con The <code>Connection</code> for which to get the type name.
   * @return The name of the type, or <code>null</code> if no such type
   *     exists.
   * @throws SQLException If an error occurs while communicating with the
   *     database.
   * @see java.sql.Types
   */
  public static String getTypeName(int type, Connection con) throws SQLException {
    return getTypeName(type, 0, con);
  }

  /**
   * Gets the <code>String</code> denoting the specified SQL data type.
   * @param type The data type to get the name of.  Valid type values
   *     consist of the static fields of {@link java.sql.Types}.
   * @param length The length to assign to data types for those types
   *     that require a length (e.g., <code>VARCHAR(n)</code>), or zero
   *     to indicate that no length is required.
   * @param ds The <code>DataSource</code> for which to get the type name.
   * @return The name of the type, or <code>null</code> if no such type
   *     exists.
   * @throws SQLException If an error occurs while communicating with the
   *     database.
   * @see java.sql.Types
   */
  public static String getTypeName(int type, int length, Connection con) throws SQLException {
    return getTypeName(type, length, con.getMetaData());
  }

  /**
   * Gets the <code>String</code> denoting the specified SQL data type.
   * @param type The data type to get the name of.  Valid type values
   *     consist of the static fields of {@link java.sql.Types}.
   * @param meta The <code>DatabaseMetaData</code> for which to get the type
   *     name.
   * @return The name of the type, or <code>null</code> if no such type
   *     exists.
   * @throws SQLException If an error occurs while communicating with the
   *     database.
   * @see java.sql.Types
   */
  public static String getTypeName(int type, DatabaseMetaData meta) throws SQLException {
    return getTypeName(type, 0, meta);
  }

  /**
   * Gets the <code>String</code> denoting the specified SQL data type.
   * @param type The data type to get the name of.  Valid type values
   *     consist of the static fields of {@link java.sql.Types}.
   * @param length The length to assign to data types for those types
   *     that require a length (e.g., <code>VARCHAR(n)</code>), or zero
   *     to indicate that no length is required.
   * @param meta The <code>DatabaseMetaData</code> for which to get the type
   *     name.
   * @return The name of the type, or <code>null</code> if no such type
   *     exists.
   * @throws SQLException If an error occurs while communicating with the
   *     database.
   * @see java.sql.Types
   */
  public static String getTypeName(int type, int length, DatabaseMetaData meta) throws SQLException {
    ResultSet typeInfo = null;
    try {
      typeInfo = meta.getTypeInfo();
      return getTypeName(type, length, typeInfo);
    } finally {
      close(typeInfo);
    }
  }

  /**
   * Gets the <code>String</code> denoting the specified SQL data type.
   * @param type The data type to get the name of.  Valid type values
   *     consist of the static fields of {@link java.sql.Types}.
   * @param length The length to assign to data types for those types
   *     that require a length (e.g., <code>VARCHAR(n)</code>), or zero
   *     to indicate that no length is required.
   * @param rs The <code>ResultSet</code> containing the type information for
   *     the database.  This must be obtained using
   *     {@link java.sql.DatabaseMetaData#getTypeInfo()}.
   * @return The name of the type, or <code>null</code> if no such type
   *     exists.
   * @throws SQLException If an error occurs while communicating with the
   *     database.
   * @see java.sql.Types
   * @see java.sql.DatabaseMetaData#getTypeInfo()
   */
  private static String getTypeName(int type, int length, ResultSet typeInfo) throws SQLException {
    int dataTypeColumn = typeInfo.findColumn("DATA_TYPE");
    while (typeInfo.next()) {
      if (typeInfo.getInt(dataTypeColumn) == type) {
        String typeName = typeInfo.getString("TYPE_NAME");
        if (length > 0) {
          if (typeName.contains("()")) {
            return typeName.replaceAll("\\(\\)", "(" + length + ")");
          } else {
            return typeName + "(" + length + ")";
          }
        } else {
          return typeName;
        }
      }
    }
    return null;
  }

  /**
   * Prints a <code>ResultSet</code>.
   * @param rs The <code>ResultSet</code> to print.
   * @param out The <code>PrintStream</code> to write to.
   * @throws SQLException If an error occurs while reading the
   *     <code>ResultSet</code>.
   */
  public static void print(ResultSet rs, PrintStream out) throws SQLException {
    ResultSetMetaData meta = rs.getMetaData();
    int columns = meta.getColumnCount();
    for (int i = 0; i < columns; i++) {
      if (i > 0) {
        out.print(",");
      }
      out.print("\"");
      out.print(meta.getColumnName(i + 1));
      out.print("\"");
    }
    out.println();

    while (rs.next()) {
      for (int i = 0; i < columns; i++) {
        if (i > 0) {
          out.print(",");
        }
        String value = rs.getString(i + 1);
        if (value != null) {
          out.print("\"");
          out.print(value.replaceAll("\"", "\"\""));
          out.print("\"");
        } else {
          out.print("NULL");
        }
      }
      out.println();
    }
  }

  /** Declared private to prevent this class from being instantiated. */
  private DbUtil() {}

}
