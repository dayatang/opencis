/**
 * 
 */
package org.opencis.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chencao
 * 
 *         2011-5-25
 */
public class SystemCommandUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 296533805192165772L;

	private static final Logger logger = LoggerFactory
			.getLogger(SystemCommandUtil.class);

	public static void exec(String command, String[] envp, File dir) {
		String s = null;

		try {

			// using the Runtime exec method:
			Process p = Runtime.getRuntime()
					.exec(command, envp, dir);

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(
					p.getInputStream()));

			BufferedReader stdError = new BufferedReader(new InputStreamReader(
					p.getErrorStream()));

			if (logger.isDebugEnabled()) {
				// read the output from the command
				logger.debug("Here is the standard output/error of the command:");
				while ((s = stdInput.readLine()) != null) {
					logger.debug(s);
				}

				while ((s = stdError.readLine()) != null) {
					logger.debug(s);
				}
			}

			// System.exit(0);
		} catch (IOException e) {
			if (logger.isErrorEnabled()) {
				logger.error("exception happened - here's what I know: ");
			}
			e.printStackTrace();
			// System.exit(-1);
		}
	}

	public static void exec(String command, File dir) {
		exec(command, new String[] {}, dir);
		String s = null;

		try {

			// using the Runtime exec method:
			Process p = Runtime.getRuntime()
					.exec(command, new String[] {}, dir);

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(
					p.getInputStream()));

			BufferedReader stdError = new BufferedReader(new InputStreamReader(
					p.getErrorStream()));

			if (logger.isDebugEnabled()) {
				// read the output from the command
				logger.debug("Here is the standard output/error of the command:");
				while ((s = stdInput.readLine()) != null) {
					logger.debug(s);
				}

				while ((s = stdError.readLine()) != null) {
					logger.debug(s);
				}
			}

			// System.exit(0);
		} catch (IOException e) {
			if (logger.isErrorEnabled()) {
				logger.error("exception happened - here's what I know: ");
			}
			e.printStackTrace();
			// System.exit(-1);
		}
	}

	public static void main(String[] args) {
		String tempDirPath = FileUtils.getTempDirectoryPath();
		System.out.println(tempDirPath);
		SystemCommandUtil.exec("pwd", FileUtils.getTempDirectory());
	}
}
