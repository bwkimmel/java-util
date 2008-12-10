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

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import ca.eandb.util.ArrayQueue;

/**
 * @author Brad Kimmel
 *
 */
public final class ArgumentProcessor<T> {

	private final Map<String, Command<? super T>> handlers = new HashMap<String, Command<? super T>>();
	private final Map<Character, String> shortcuts = new HashMap<Character, String>();
	private final Map<String, Command<? super T>> commands = new HashMap<String, Command<? super T>>();

	private Command<? super T> defaultCommand = null;

	public ArgumentProcessor() {
		addCommand("help", new HelpCommand());
	}

	private class HelpCommand implements Command<Object> {

		@Override
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

			System.exit(0);

		}

	}

	public void addOption(String key, char shortKey,
			Command<? super T> handler) {
		handlers.put(key, handler);
		shortcuts.put(shortKey, key);
	}

	public void addCommand(String key, Command<? super T> handler) {
		commands.put(key, handler);
	}

	public void setDefaultCommand(Command<? super T> command) {
		defaultCommand = command;
	}

	public void process(String[] args, T state) {
		process(new ArrayQueue<String>(args), state);
	}

	public void process(Queue<String> argq, T state) {
		while (!argq.isEmpty()) {
			String nextArg = argq.peek();
			if (nextArg.startsWith("--")) {
				argq.remove();
				String key = nextArg.substring(2);
				Command<? super T> command = handlers.get(key);
				if (command != null) {
					command.process(argq, state);
				}
			} else if (nextArg.startsWith("-")) {
				argq.remove();
				for (int i = 1; i < nextArg.length(); i++) {
					char key = nextArg.charAt(i);
					Command<? super T> command = handlers.get(shortcuts.get(key));
					if (command != null) {
						command.process(argq, state);
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
	}

}
