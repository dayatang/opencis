/**
 * 
 */
package org.opencis.pm.trac;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.opencis.core.Project;
import org.opencis.core.PmServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dayatang.utils.Assert;
import com.trilead.ssh2.Connection;
import com.trilead.ssh2.Session;
import com.trilead.ssh2.StreamGobbler;

/**
 * @author chencao
 * 
 *         2011-5-19
 */
@Entity
@DiscriminatorValue("1")
public class TracPmServer extends PmServer<TracConfiguration> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1922067074193684757L;

	private static final Logger logger = LoggerFactory
			.getLogger(TracPmServer.class);

	private static final Long ID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opencis.core.domain.ProjectManager#initProject(org.opencis.core.domain
	 * .Project)
	 */
	@Override
	public void initProject(TracConfiguration configuration) {

		Assert.notNull(configuration);

		String hostname = configuration.getHostname();
		String username = configuration.getUsername();
		String password = configuration.getPassword();

		try {
			/* Create a connection instance */

			Connection conn = new Connection(hostname);

			/* Now connect */

			conn.connect();

			/*
			 * Authenticate. If you get an IOException saying something like
			 * "Authentication method password not supported by the server at this stage."
			 * then please check the FAQ.
			 */

			boolean isAuthenticated = conn.authenticateWithPassword(username,
					password);

			if (isAuthenticated == false)
				throw new IOException("Authentication failed.");

			/* Create a session */

			Session sess = conn.openSession();

			sess.execCommand(configuration.getCommand());

			if (logger.isDebugEnabled()) {
				logger.debug("Here is some information about the remote host:");

				/*
				 * This basic example does not handle stderr, which is sometimes
				 * dangerous (please read the FAQ).
				 */
				// debug std out
				InputStream stdout = new StreamGobbler(sess.getStdout());
				BufferedReader br = new BufferedReader(new InputStreamReader(
						stdout));
				while (true) {
					String line = br.readLine();
					if (line == null)
						break;
					logger.debug(line);
				}

				// debug std err
				InputStream stderr = new StreamGobbler(sess.getStderr());
				BufferedReader ebr = new BufferedReader(new InputStreamReader(
						stderr));
				while (true) {
					String line = ebr.readLine();
					if (line == null)
						break;
					logger.debug(line);
				}
			}

			if (logger.isErrorEnabled()) {
				logger.error("ExitCode: {}", sess.getExitStatus());
			}

			sess.close();
			conn.close();

		} catch (IOException e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage());
			}
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		if (logger.isInfoEnabled()) {
			logger.info("=============创建Trac站点成功。=============");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opencis.core.domain.ProjectManager#getConfiguration(org.opencis.core
	 * .domain.Project)
	 */
	@Override
	public TracConfiguration getConfiguration(Project project) {

		Assert.notNull(project);

		return TracConfiguration.getByProject(project);
	}

	public static TracPmServer getInstance() {
		return get(TracPmServer.class, ID);
	}

}
