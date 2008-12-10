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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;

import ca.eandb.util.StringUtil;

/**
 * A <code>LoginModule</code> that reads user names, password hashes, and roles
 * from a colon delimited file.
 *
 * The file must be formatted as follows.  There should be one line for each
 * user.  The line should have the form:
 *
 * name:password[:role1...:rolen]
 *
 * @author Brad Kimmel
 */
public final class FileLoginModule extends AbstractLoginModule {

	/** The default digest algorithm for storing hashed passwords. */
	private static final String DEFAULT_DIGEST_ALGORITHM = "MD5";

	/** The <code>File</code> in which passwords are stored. */
	private File passwordFile = null;

	/** The digest algorithm to use for storing hashed passwords. */
	private String digestAlgorithm = DEFAULT_DIGEST_ALGORITHM;

	/* (non-Javadoc)
	 * @see ca.eandb.util.auth.AbstractLoginModule#initialize(java.util.Map, java.util.Map)
	 */
	@Override
	protected void initialize(Map<String, ?> sharedState, Map<String, ?> options) {
		String fileName = (String) options.get("file");
		passwordFile = new File(fileName);

		if (options.containsKey("digestAlgorithm")) {
			digestAlgorithm = (String) options.get("digestAlgorithm");
		} else {
			digestAlgorithm = DEFAULT_DIGEST_ALGORITHM;
		}
	}

	/* (non-Javadoc)
	 * @see javax.security.auth.spi.LoginModule#login()
	 */
	@Override
	public boolean login() throws LoginException {
		NameCallback user = new NameCallback("Login:");
		PasswordCallback pass = new PasswordCallback("Password:", false);

		try {
			callback(user, pass);

			BufferedReader reader = new BufferedReader(new FileReader(passwordFile));

			while (reader.ready()) {
				String[] fields = reader.readLine().split(":");
				if (fields[0].equals(user.getName())) {
					MessageDigest alg = MessageDigest.getInstance(digestAlgorithm);
					String loginPassword = new String(pass.getPassword());
					byte[] loginDigest = alg.digest(loginPassword.getBytes());
					byte[] realDigest = StringUtil.hexToByteArray(fields[1]);
					if (MessageDigest.isEqual(loginDigest, realDigest)) {
						addPrincipal(new UserPrincipal(fields[0]));
						for (int i = 2; i < fields.length; i++) {
							addPrincipal(new RolePrincipal(fields[i]));
						}
						return true;
					}
					break;
				}
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			/* nothing to do. */
		} catch (UnsupportedCallbackException e) {
			/* nothing to do. */
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

		throw new FailedLoginException();
	}

}
