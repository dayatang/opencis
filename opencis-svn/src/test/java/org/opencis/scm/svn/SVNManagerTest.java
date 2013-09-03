/**
 * 
 */
package org.opencis.scm.svn;

import java.io.File;

import org.junit.Test;
import org.opencis.core.ScmServer;

/**
 * @author chencao
 * 
 *         2011-5-18
 */
public class SVNManagerTest {

	@Test
	public void empty() {

	}

	// @Test
	public void initProject() {
		ScmServer scmgr = new SvnScmServer();
		scmgr.initProject(new File("/importDir"),
				"http://www.dayatang.com/svn/opencis/svntest2",
				"init project...", "cchen", "1234");
	}
}
