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

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

/**
 * A <code>CallbackHandler</code> that uses a predefined user name and
 * password.
 * @author Brad Kimmel
 */
public final class FixedCallbackHandler implements CallbackHandler {

  /** The user name. */
  private final String name;

  /** The password. */
  private final char[] password;

  /**
   * Creates a new <code>FixedCallbackHandler</code>.
   * @param name The user name.
   * @param password The password.
   */
  private FixedCallbackHandler(String name, char[] password) {
    this.name = name;
    this.password = password;
  }

  /* (non-Javadoc)
   * @see javax.security.auth.callback.CallbackHandler#handle(javax.security.auth.callback.Callback[])
   */
  public void handle(Callback[] callbacks) throws UnsupportedCallbackException {
    for (Callback callback : callbacks) {
      if (callback instanceof NameCallback) {
        ((NameCallback) callback).setName(name);
      } else if (callback instanceof PasswordCallback) {
        ((PasswordCallback) callback).setPassword(password);
      } else {
        throw new UnsupportedCallbackException(callback);
      }
    }
  }

  /**
   * Creates a <code>CallbackHandler</code> that provides the specified user
   * name and password.
   * @param name The user name.
   * @param password The password.
   * @return The new <code>CallbackHandler</code>.
   */
  public static CallbackHandler forNameAndPassword(String name, char[] password) {
    return new FixedCallbackHandler(name, password);
  }

  /**
   * Creates a <code>CallbackHandler</code> that provides the specified user
   * name and password.
   * @param name The user name.
   * @param password The password.
   * @return The new <code>CallbackHandler</code>.
   */
  public static CallbackHandler forNameAndPassword(String name, String password) {
    return FixedCallbackHandler.forNameAndPassword(name, password.toCharArray());
  }

}
