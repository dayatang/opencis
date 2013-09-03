/**
 * 
 */
package org.opencis.ci.hudson;

import org.junit.Test;
import org.opencis.core.Project;
import org.opencis.core.SCM;

/**
 * @author chencao
 * 
 *         2011-5-14
 */
public class HudsonManagerTest {

	private String configUrl = "http://www.dayatang.com:8080/hudson";

	@Test
	public void empty() {

	}

	@Test
	public void createJob() {

		HudsonCiServer mgr = new HudsonCiServer();

		HudsonConfiguration config = new HudsonConfiguration("新 项目3", configUrl);

		mgr.createJob(config);

		mgr.performBuild(config);
	}

	// @Test
	public void updateConfigXML() {

		HudsonCiServer mgr = new HudsonCiServer();
		HudsonConfiguration config = new HudsonConfiguration("新 项目3", configUrl);
		Project project = new Project("group", "artifact", "1.2");
		SCM scm = new SCM();
		scm.setDeveloperConnection("http://www.dayatang.com/svn/opencis/trunk/");
		project.setScm(scm);

		mgr.updateConfigXML(config.getConfigXML(), project);

	}
}
