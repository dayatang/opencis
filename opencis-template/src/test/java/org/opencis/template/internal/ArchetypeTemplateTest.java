/**
 * 
 */
package org.opencis.template.internal;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Test;
import org.opencis.core.Developer;
import org.opencis.core.Organization;
import org.opencis.core.Project;
import org.opencis.core.Role;
import org.opencis.template.ArchetypeTemplate;

/**
 * @author chencao
 * 
 *         2011-5-25
 */
public class ArchetypeTemplateTest {

	@Test
	public void empty() {

	}

	// @Test
	public void findByCatalog() {
		List<ArchetypeTemplate> templates = ArchetypeTemplate
				.findByCatalog(ArchetypeTemplate.CATALOG_LOCAL);
		for (ArchetypeTemplate template : templates) {
			System.out.println(template);
		}
	}

	// @Test
	public void findByCatalogs() {
		Map<String, List<ArchetypeTemplate>> templateMap = ArchetypeTemplate
				.findByCatalogs("http://cocoon.apache.org");
		Set<String> catalogs = templateMap.keySet();
		for (String catalog : catalogs) {
			System.out.println("============" + catalog + "===========");
			List<ArchetypeTemplate> archetypeList = templateMap.get(catalog);
			for (ArchetypeTemplate archetype : archetypeList) {
				System.out.println("    " + archetype);
			}
		}
	}

	// @Test
	public void createProject() {
		String dateStr = DateFormatUtils.format(new Date(),
				"yyyy-MM-dd-HH-mm-ss-SSS");
		System.out.println("dateStr:" + dateStr);

		Project project = new Project("com.company", "project" + dateStr, "1.0.0");

		ArchetypeTemplate template = new ArchetypeTemplate();
		template.setArchetypeCatalog("http://cocoon.apache.org/");
		template.setGroupId("org.apache.cocoon");
		template.setArtifactId("cocoon-22-archetype-block-plain");
		template.setTemplateVersion("1.0.0");
		// template.setArchetypeCatalog("local");
		// template.setGroupId("org.opencis");
		// template.setArtifactId("opencis-project-archetype");
		// template.setTemplateVersion("0.1-SNAPSHOT");

		template.setProject(project);

		project.setOrganization(new Organization("OPEN SOURCE", "http://www.dayatang.com"));

		Set<Developer> developers = new HashSet<Developer>();
		// developer1
		Developer developer1 = new Developer();
		Set<Role> roles4Developer1 = new HashSet<Role>();
		roles4Developer1.add(new Role("role1"));
		roles4Developer1.add(new Role("role2"));
		developer1.setDeveloperId("EMP01");
		developer1.setName("EMP-01");
		developer1.setEmail("emp01@cc.com");
		developer1.setRoles(roles4Developer1);
		developers.add(developer1);
		// developer2
		Developer developer2 = new Developer();
		Set<Role> roles4Developer2 = new HashSet<Role>();
		roles4Developer2.add(new Role("role3"));
		roles4Developer2.add(new Role("role4"));
		developer2.setDeveloperId("EMP02");
		developer2.setName("EMP-02");
		developer2.setEmail("emp02@cc.com");
		developer2.setRoles(roles4Developer2);
		developers.add(developer2);

		project.setDevelopers(developers);

		File file = template.createProject();
		System.out.println("project file path:" + file.getAbsolutePath());
	}

	// @Test
	public void updatePom() {

	}
}
