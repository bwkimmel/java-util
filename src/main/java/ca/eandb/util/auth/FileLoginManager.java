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

package ca.eandb.util.auth;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ca.eandb.util.StringUtil;
import ca.eandb.util.args.CommandArgument;
import ca.eandb.util.args.OptionArgument;

/**
 * Command line plugin for managing a users file used by FileLoginModule.
 * @author Brad Kimmel
 */
public final class FileLoginManager {
	
	/** Encrypts passwords using a one-way hash. */
	private final PasswordEncryptionService passwd = new PasswordEncryptionService();
	
	/** User account record used internally. */
	private static final class User {
		
		/** Username */
		final String username;
		
		/** One-way hash of the password. */
		byte[] hash;
		
		/** The salt used for the hashing algorithm. */
		byte[] salt;
		
		/** The roles that this user has. */
		Set<String> roles = new HashSet<String>();

		/**
		 * Creates a new <code>User</code>.
		 * @param username The username of the user to create.
		 */
		public User(String username) {
			this.username = username;
		}
		
		/**
		 * Creates a new <code>User</code> from the line in the passwd file.
		 * @param spec The line in the passwd file.
		 * @return The corresponding <code>User</code>.
		 */
		public static User fromString(String spec) {
			String[] fields = spec.split(":");
			User user = new User(fields[0]);
			user.hash = StringUtil.hexToByteArray(fields[1]);
			user.salt = StringUtil.hexToByteArray(fields[2]);
			for (int i = 3; i < fields.length; i++) {
				user.roles.add(fields[i]);
			}
			return user;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(username);
			sb.append(":");
			sb.append(StringUtil.toHex(hash));
			sb.append(":");
			sb.append(StringUtil.toHex(salt));
			for (String role : roles) {
				sb.append(":");
				sb.append(role);
			}
			return sb.toString();
		}
	}
	
	/** The currently loaded set of users. */
	private final Map<String, User> users = new HashMap<String, User>();
	
	/**
	 * Loads a users file.
	 * @param filename The filename of the users file to load.
	 */
	@CommandArgument
	public void load(
			@OptionArgument("filename") String filename) {
		
		users.clear();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));

			while (reader.ready()) {
				User user = User.fromString(reader.readLine());
				users.put(user.username, user);
			}
		} catch (FileNotFoundException e) {
			System.err.println(String.format("File not found: '%s'", filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Saves a users file.
	 * @param filename The filename of the file to save.
	 */
	@CommandArgument
	public void save(
			@OptionArgument("filename") String filename) {
		
		try {
			PrintStream out = new PrintStream(new FileOutputStream(filename));
			
			for (User user : users.values()) {
				out.println(user);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Adds a new user.
	 * @param username The username.
	 * @param password The password.
	 * @param roles A comma-separated list of roles that the new user will have.
	 */
	@CommandArgument
	public void adduser(
			@OptionArgument("username") String username,
			@OptionArgument("password") String password,
			@OptionArgument("roles") String roles) {
		
		if (users.containsKey(username)) {
			System.err.println(String.format("User '%s' already exists", username));
		} else {
			
			User user = new User(username);
			user.salt = passwd.generateSalt();
			user.hash = passwd.getEncryptedPassword(password, user.salt);
			user.roles.addAll(Arrays.asList(roles.split(",")));
			users.put(username, user);
			
		}
	}

	/**
	 * Deletes a user.
	 * @param username The username of the user to remove.
	 */
	@CommandArgument
	public void rmuser(
			@OptionArgument("username") String username) {
		
		if (users.containsKey(username)) {
			users.remove(username);
		} else {
			System.err.println(String.format("User '%s' does not exist", username));
		}
	}
	
	/**
	 * Resets the password of a user.
	 * @param username The username of the user whose password to reset.
	 * @param password The new password.
	 */
	@CommandArgument
	public void passwd(
			@OptionArgument("username") String username,
			@OptionArgument("password") String password) {

		User user = users.get(username);

		if (user != null) {
			user.salt = passwd.generateSalt();
			user.hash = passwd.getEncryptedPassword(password, user.salt);
		} else {
			System.err.println(String.format("User '%s' does not exist", username));
		}
		
	}

	/**
	 * Add roles for a user.
	 * @param username The username of the user for whom to add roles.
	 * @param roles A comma-separated list of the roles to add.
	 */
	@CommandArgument
	public void addroles(
			@OptionArgument("username") String username,
			@OptionArgument("roles") String roles) {
		
		User user = users.get(username);

		if (user != null) {
			user.roles.addAll(Arrays.asList(roles.split(",")));
		} else {
			System.err.println(String.format("User '%s' does not exist", username));
		}
		
	}

	/**
	 * Sets the roles for a user.
	 * @param username The username of the user for whom to set roles.
	 * @param roles A comma-separated list of the roles the user should have.
	 */
	@CommandArgument
	public void setroles(
			@OptionArgument("username") String username,
			@OptionArgument("roles") String roles) {
		
		User user = users.get(username);

		if (user != null) {
			user.roles.clear();
			user.roles.addAll(Arrays.asList(roles.split(",")));
		} else {
			System.err.println(String.format("User '%s' does not exist", username));
		}
		
	}
	
	/**
	 * Removes roles from a user.
	 * @param username The username of the user for whom to remove roles.
	 * @param roles A comma-separated list of the roles to remove from the user.
	 */
	@CommandArgument
	public void rmroles(
			@OptionArgument("username") String username,
			@OptionArgument("roles") String roles) {
		
		User user = users.get(username);

		if (user != null) {
			user.roles.removeAll(Arrays.asList(roles.split(",")));
		} else {
			System.err.println(String.format("User '%s' does not exist", username));
		}
		
	}
	
	/**
	 * Tests authentication for a user.  Prints a message indicating if the
	 * provided password is correct for the specified user.
	 * @param username The username of the user for whom to test authentication.
	 * @param password The password to test.
	 */
	@CommandArgument
	public void testpasswd(
			@OptionArgument("username") String username,
			@OptionArgument("password") String password) {
		
		User user = users.get(username);

		if (user != null) {
			if (passwd.authenticate(password, user.hash, user.salt)) {
				System.out.println(String.format("Password for user '%s' is correct", username));
			} else {
				System.out.println(String.format("Password for user '%s' is not correct", username));
			}
		} else {
			System.err.println(String.format("User '%s' does not exist", username));
		}

	}
	
}
