/**
 * 
 */
package org.opencis.core.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.time.DateFormatUtils;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;
import org.opencis.ci.hudson.HudsonCiServer;
import org.opencis.core.Developer;
import org.opencis.core.Organization;
import org.opencis.core.Project;
import org.opencis.core.Role;
import org.opencis.core.SCM;
import org.opencis.pm.trac.TracPmServer;
import org.opencis.pm.trac.TracConfiguration;
import org.opencis.scm.svn.SvnScmServer;
import org.opencis.service.impl.OpenCISImpl;
import org.opencis.template.ArchetypeTemplate;

import com.dayatang.dbunit.DataSetStrategy;
import com.dayatang.springtest.AbstractIntegratedTestCase;

/**
 * @author chencao
 * 
 *         2011-5-24
 */
public class OpenCISImplTest extends AbstractIntegratedTestCase {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dayatang.springtest.AbstractIntegratedTestCase#rollback()
	 */
	@Override
	protected boolean rollback() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dayatang.dbunit.Dbunit#getDataSetFilePaths()
	 */
	@Override
	protected String[] getDataSetFilePaths() {
		return new String[] { "dataset/product-data.xml" };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dayatang.springtest.AbstractIntegratedTestCase#getDataSetStrategy()
	 */
	@Override
	protected DataSetStrategy getDataSetStrategy() {
		return DataSetStrategy.FlatXml;
	}

	@Override
	protected DatabaseOperation setUpOp() {
		return DatabaseOperation.CLEAN_INSERT;
	}

	@Override
	protected DatabaseOperation tearDownOp() {
		return DatabaseOperation.NONE;
	}

	@Test
	public void empty() {

	}

	@Test
	public void createProject() {
		OpenCISImpl opencis = new OpenCISImpl();

		String dateStr = DateFormatUtils.format(new Date(),
				"yyyy-MM-dd-HH-mm-ss-SSS");
		System.out.println("dateStr:" + dateStr);

		Project project = initProject(dateStr);

		ArchetypeTemplate template = initProjectTemplate();

		template.setProject(project);
		// template.save();

		opencis.createProject(project, template);
	}

	/**
	 * @param dateStr
	 * @return
	 */
	private Project initProject(String dateStr) {
		Project project = new Project("com.company", "project" + dateStr, "1.0.0");
		project.setName("myproject" + dateStr);
		project.setOrganization(new Organization("OPEN SOURCE", "http://www.dayatang.com"));
	
		// 设置developers
		Set<Developer> developers = new HashSet<Developer>();
		// developer1
		Developer developer1 = new Developer();
		Set<Role> roles4Developer1 = new HashSet<Role>();
		Role role1 = new Role("role1");
		Role role2 = new Role("role2");
		roles4Developer1.add(role1);
		roles4Developer1.add(role2);
		developer1.setDeveloperId("EMP01");
		developer1.setName("EMP-01");
		developer1.setEmail("emp01@cc.com");
		developer1.setRoles(roles4Developer1);
	
		role1.save();
		role2.save();
		developer1.save();
	
		developers.add(developer1);
	
		// developer2
		Developer developer2 = new Developer();
		Set<Role> roles4Developer2 = new HashSet<Role>();
		Role role3 = new Role("role3");
		Role role4 = new Role("role4");
		roles4Developer2.add(role3);
		roles4Developer2.add(role4);
		developer2.setDeveloperId("EMP02");
		developer2.setName("EMP-02");
		developer2.setEmail("emp02@cc.com");
		developer2.setRoles(roles4Developer2);
	
		role3.save();
		role4.save();
		developer2.save();
	
		developers.add(developer2);
	
		project.setDevelopers(developers);
	
		// 设置SCM
		String svn = "http://www.dayatang.com/svn/opencis/svntest" + dateStr;
		SCM scm = new SCM(svn, svn, "cchen", "1234");
		project.setScm(scm);
	
		// 设置projectMgr
		project.setPmServer(TracPmServer.getInstance());
		// 设置scmMgr
		project.setScmServer(SvnScmServer.getInstance());
		// 设置ciMgr
		project.setCiServer(HudsonCiServer.getInstance());
		// 设置ciUrl
		//project.setCiUrl("http://localhost:8180");
		project.setCiUrl("http://www.dayatang.com:8080/hudson");
	
		project.save();
	
		// 设置并保存TracMgrConfiguration
		TracConfiguration traConfig = new TracConfiguration();
		traConfig.setHostname("www.dayatang.com");
		traConfig.setUsername("cchen");
		traConfig.setPassword("1234");
		traConfig.setProjectName(project.getName() + dateStr);
		traConfig.setTracPath("/home/apache/trac/hello" + dateStr);
		traConfig.setProject(project);
	
		traConfig.save();
	
		return project;
	}

	/**
	 * @param project
	 * @return
	 */
	private ArchetypeTemplate initProjectTemplate() {
		ArchetypeTemplate template = new ArchetypeTemplate();
		template.setArchetypeCatalog("http://cocoon.apache.org/");
		template.setGroupId("org.apache.cocoon");
		template.setArtifactId("cocoon-22-archetype-block-plain");
		template.setTemplateVersion("1.0.0");
		// template.setArchetypeCatalog("local");
		// template.setGroupId("org.opencis");
		// template.setArtifactId("opencis-project-archetype");
		// template.setTemplateVersion("0.1-SNAPSHOT");

		return template;
	}

}
