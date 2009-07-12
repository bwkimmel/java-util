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

package ca.eandb.util.args;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import ca.eandb.util.ArrayQueue;

/**
 * An object that processes command line arguments.
 * @author Brad Kimmel
 */
public final class ArgumentProcessor<T> {

	/**
	 * A <code>Map</code> for looking up option <code>Command</code> options,
	 * keyed by the option name.  A command line argument of the form
	 * <code>--&lt;option_name&gt;</code> is used to trigger a given option
	 * handler.
	 *
	 * The difference between an option and a command (stored in
	 * {@link #commands} is that multiple options may be specified.  Only a
	 * single command may be specified.  When a command is encountered, control
	 * is tranferred to that command and no further options or commands are
	 * processed by this <code>ArgumentProcessor</code>.
	 */
	private final Map<String, Command<? super T>> options = new HashMap<String, Command<? super T>>();

	/**
	 * A <code>Map</code> for looking up the full option name associated with
	 * a given single character shortcut (e.g., <code>V</code> may be a
	 * shortcut for <code>verbose</code>).
	 */
	private final Map<Character, String> shortcuts = new HashMap<Character, String>();

	/**
	 * A <code>Map</code> for looking up <code>Command</code> options, keyed
	 * by the command name.  A command line option of the form
	 * <code>&lt;command_name&gt;</code> is used to trigger a given command
	 * handler.
	 *
	 * The difference between an option (stored in {@link #options} and a
	 * command (stored in {@link #commands} is that multiple options may be
	 * specified.  Only a single command may be specified.  When a command is
	 * encountered, control is tranferred to that command and no further
	 * options or commands are processed by this
	 * <code>ArgumentProcessor</code>.
	 */
	private final Map<String, Command<? super T>> commands = new HashMap<String, Command<? super T>>();

	/**
	 * The <code>Command</code> to execute if the end of the command line
	 * options is reached without a <code>Command</code> being executed, or if
	 * an unrecognized command is encountered.
	 */
	private Command<? super T> defaultCommand = null;

	/**
	 * The <code>Command</code> to execute to enter shell mode.
	 */
	private Command<? super T> shellCommand = null;

	/**
	 * Creates a new <code>ArgumentProcessor</code>.
	 * @param shell A value indicating whether a shell should be started if no
	 * 		commands are provided on the command line.
	 */
	public ArgumentProcessor(boolean shell) {
		addCommand("help", new HelpCommand());
		if (shell) {
			shellCommand = new ShellCommand();
		}
	}

	/**
	 * Creates a new <code>ArgumentProcessor</code>.
	 */
	public ArgumentProcessor() {
		this(false);
	}

	/**
	 * A <code>Command</code> that prints a list of options and commands
	 * registered with this <code>ArgumentProcessor</code> to the console.
	 * @author Brad Kimmel
	 */
	private class HelpCommand implements Command<Object> {

		/* (non-Javadoc)
		 * @see ca.eandb.util.args.Command#process(java.util.Queue, java.lang.Object)
		 */
		public void process(Queue<String> argq, Object state) {

			System.out.println("Usage:  <java_cmd> [ <options> ] <command> <args>");

			System.out.println();
			System.out.println("Options:");
			for (Map.Entry<Character, String> entry : shortcuts.entrySet()) {
				System.out.printf("-%c, --%s", entry.getKey(), entry.getValue());
				System.out.println();
			}

			System.out.println();
			System.out.println("Commands:");
			for (String cmd : commands.keySet()) {
				System.out.println(cmd);
			}

		}

	}

	/**
	 * A <code>Command</code> for starting a shell.
	 * @author Brad
	 */
	private class ShellCommand implements Command<T> {

		/** A flag indicating whether the shell should exit. */
		private boolean exit = false;

		/** A flag indicating whether the shell is currently running. */
		private boolean running = false;

		/* (non-Javadoc)
		 * @see ca.eandb.util.args.Command#process(java.util.Queue, java.lang.Object)
		 */
		public void process(Queue<String> argq, T state) {
			if (running) {
				return;
			}

			running = true;
			addCommand("exit", new Command<Object>() {
				public void process(Queue<String> argq, Object state) {
					exit = true;
				}
			});

			BufferedReader shell = new BufferedReader(new InputStreamReader(System.in));

			do {
				System.out.print(">> ");
				String cmd = null;
				try {
					cmd = shell.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (cmd == null) {
					break;
				}
				String[] args = cmd.trim().split("\\s+");
				try {
					ArgumentProcessor.this.process(args, state);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} while (!exit);

			running = false;
		}

	}

	/**
	 * Add a command line option handler.
	 * @param key The <code>String</code> that identifies this option.  The
	 * 		option may be triggered by specifying <code>--&lt;key&gt;</code>
	 * 		on the command line.
	 * @param shortKey The <code>char</code> that serves as a shortcut to the
	 * 		option.  The option may be triggered by specifying
	 * 		<code>-&lt;shortKey&gt;</code> on the command line.
	 * @param handler The <code>Command</code> that is used to process the
	 * 		option.
	 */
	public void addOption(String key, char shortKey,
			Command<? super T> handler) {
		options.put(key, handler);
		shortcuts.put(shortKey, key);
	}

	/**
	 * Adds a new command handler.
	 * @param key The <code>String</code> that identifies this command.  The
	 * 		command may be triggered by a command line argument with this
	 * 		value.
	 * @param handler The <code>Command</code> that is used to process the
	 * 		command.
	 */
	public void addCommand(String key, Command<? super T> handler) {
		commands.put(key, handler);
	}

	/**
	 * Sets the command handler that is used to process unrecognized commands
	 * or that is executed when the end of the command line arguments is
	 * reached without a command being executed.
	 * @param command The <code>Command</code> to use (may be null).
	 */
	public void setDefaultCommand(Command<? super T> command) {
		defaultCommand = command;
	}

	/**
	 * Processes command line arguments.
	 * @param args The command line arguments to process.
	 * @param state The application state object.
	 */
	public void process(String[] args, T state) {
		process(new ArrayQueue<String>(args), state);
	}

	/**
	 * Processes command line arguments.
	 * @param argq A <code>Queue</code> containing the command line arguments
	 * 		to be processed.
	 * @param state The application state object.
	 */
	public void process(Queue<String> argq, T state) {
		while (!argq.isEmpty()) {
			String nextArg = argq.peek();
			if (nextArg.startsWith("--")) {
				argq.remove();
				String key = nextArg.substring(2);
				Command<? super T> option = options.get(key);
				if (option != null) {
					option.process(argq, state);
				}
			} else if (nextArg.startsWith("-")) {
				argq.remove();
				for (int i = 1; i < nextArg.length(); i++) {
					char key = nextArg.charAt(i);
					Command<? super T> option = options.get(shortcuts.get(key));
					if (option != null) {
						option.process(argq, state);
					}
				}
			} else {
				Command<? super T> command = commands.get(nextArg);
				if (command != null) {
					argq.remove();
					command.process(argq, state);
					return;
				}
				break;
			}
		}
		if (defaultCommand != null) {
			defaultCommand.process(argq, state);
		}
		if (shellCommand != null) {
			shellCommand.process(argq, state);
		}
	}

}
