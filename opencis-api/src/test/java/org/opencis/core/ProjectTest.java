/**
 * 
 */
package org.opencis.core;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.opencis.core.Developer;
import org.opencis.core.Project;
import org.opencis.core.SCM;

import com.dayatang.springtest.AbstractIntegratedTestCase;

/**
 * @author chencao
 * 
 *         2011-5-14
 */
public class ProjectTest extends AbstractIntegratedTestCase {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dayatang.springtest.AbstractIntegratedTestCase#rollback()
	 */
	@Override
	protected boolean rollback() {
		return false;
	}

	@Test
	public void empty() {

	}

	// @Test
	public void create() {
		Project project = new Project();
		project.setGroupdId("org.opencis");
		project.setArtifactId("opencis-project");
		project.setProjectVersion("0.1");
		project.save();

		Project savedObj = Project.get(Project.class, project.getId());
		assertEquals("org.opencis", savedObj.getGroupdId());
	}

	// @Test
	public void create2() {

		Project project = new Project();
		project.setGroupdId("org.opencis");
		project.setArtifactId("opencis-project");
		project.setProjectVersion("0.1");
		project.setCiUrl("");
		project.setName("adf");
		project.setScm(new SCM("", "", "", ""));

		// 设置developers
		Set<Developer> developers = new HashSet<Developer>();
		// developer1
		Developer developer1 = new Developer();
		developer1.setDeveloperId("EMP01");
		developer1.setName("EMP-01");
		developer1.setEmail("emp01@cc.com");
		developers.add(developer1);

		// developer2
		Developer developer2 = new Developer();
		developer2.setDeveloperId("EMP02");
		developer2.setName("EMP-02");
		developer2.setEmail("emp02@cc.com");
		developers.add(developer2);

		project.setDevelopers(developers);

		developer1.save();
		developer2.save();
		project.save();
	}

	// @Test
	public void delete() {
		Project.getRepository().remove(Project.get(Project.class, 5L));
	}

}
