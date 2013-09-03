/**
 * 
 */
package org.opencis.pm.trac;

import org.junit.Test;
import org.opencis.pm.trac.TracPmServer;
import org.opencis.pm.trac.TracConfiguration;

/**
 * @author chencao
 * 
 *         2011-5-19
 */
public class TracManagerTest {

	@Test
	public void empty() {

	}

	// @Test
	public void initProject() {

		TracPmServer mgr = new TracPmServer();

		TracConfiguration config = new TracConfiguration();

		config.setHostname("www.dayatang.com");
		config.setUsername("www-data");
		config.setPassword("opencis");
		config.setProjectName("asdfsdf");
		// config.setTracPath("/tmp/hello");
		config.setTracPath("/home/www-data/trac/hello7");

		mgr.initProject(config);
	}
}
