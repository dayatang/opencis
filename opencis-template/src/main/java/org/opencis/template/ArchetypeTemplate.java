/**
 * 
 */
package org.opencis.template;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.maven.archetype.ArchetypeManager;
import org.apache.maven.archetype.catalog.Archetype;
import org.apache.maven.archetype.source.ArchetypeDataSource;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.opencis.core.Developer;
import org.opencis.core.Organization;
import org.opencis.core.ProjectTemplate;
import org.opencis.core.Role;
import org.opencis.template.internal.archetype.OpenCISArchetypeManager;
import org.opencis.template.internal.archetype.source.CatalogArchetypeDataSource;
import org.opencis.template.internal.archetype.source.InternalCatalogArchetypeDataSource;
import org.opencis.template.internal.archetype.source.RemoteCatalogArchetypeDataSource;
import org.opencis.template.internal.artifact.manager.DefaultWagonManager;
import org.opencis.utils.SystemCommandUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dayatang.utils.Assert;

/**
 * @author chencao
 * 
 *         2011-5-25
 */
@Entity
@DiscriminatorValue("1")
public class ArchetypeTemplate extends ProjectTemplate {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1963745031531160303L;

	private static final Logger logger = LoggerFactory
			.getLogger(ArchetypeTemplate.class);

	public static final String CATALOG_INTERNAL = "internal";
	public static final String CATALOG_REMOTE = "remote";
	public static final String CATALOG_LOCAL = "local";
	public static final String CATALOG_PROTOCOL_FILE = "file://";
	public static final String CATALOG_PROTOCOL_HTTP = "http://";

	/**
	 * The groupId of the archetype.
	 */
	@Column(name = "GROUP_ID", nullable = false)
	private String groupId;

	/**
	 * The artifactId of the archetype.
	 */
	@Column(name = "ARTIFACT_ID", nullable = false)
	private String artifactId;

	@Column(name = "ARCHETYPE_CATALOG", nullable = false)
	private String archetypeCatalog;

	/**
	 * The repository where to find the archetype. When omitted, the archetype
	 * is searched for in the repository where the catalog comes from.
	 */
	@Column(name = "ARTIFACT_REPOSITORY")
	private String archetypeRepository;

	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
		updateName();
	}

	/**
	 * @return the artifactId
	 */
	public String getArtifactId() {
		return artifactId;
	}

	/**
	 * @param artifactId
	 *            the artifactId to set
	 */
	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
		updateName();
	}

	/**
	 * @return the archetypeCatalog
	 */
	public String getArchetypeCatalog() {
		return archetypeCatalog;
	}

	/**
	 * @param archetypeCatalog
	 *            the archetypeCatalog to set
	 */
	public void setArchetypeCatalog(String archetypeCatalog) {
		this.archetypeCatalog = archetypeCatalog;
	}

	/**
	 * @return the archetypeRepository
	 */
	public String getArchetypeRepository() {
		return archetypeRepository;
	}

	/**
	 * @param archetypeRepository
	 *            the archetypeRepository to set
	 */
	public void setArchetypeRepository(String archetypeRepository) {
		this.archetypeRepository = archetypeRepository;
	}

	/**
	 * 
	 */
	private void updateName() {
		setName(groupId + ":" + artifactId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencis.core.domain.ProjectTemplate#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return name + ":" + getVersion();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencis.core.domain.ProjectTemplate#createProject()
	 */
	@Override
	public File createProject() {

		Assert.notNull(project);

		// mvn archetype:generate -B -DarchetypeCatalog=http://cocoon.apache.org
		// -DarchetypeGroupId=org.apache.cocoon
		// -DarchetypeArtifactId=cocoon-22-archetype-block-plain
		// -DarchetypeVersion=1.0.0 -DgroupId=com.company -DartifactId=project
		// -Dversion=1.0.0 -Dpackage=1.9

		String createProjectCommand = "mvn archetype:generate -B -DarchetypeCatalog="
				+ archetypeCatalog
				+ " -DarchetypeGroupId="
				+ groupId
				+ " -DarchetypeArtifactId="
				+ artifactId
				+ " -DarchetypeVersion="
				+ templateVersion
				+ " -DgroupId="
				+ project.getGroupdId()
				+ " -DartifactId="
				+ project.getArtifactId()
				+ " -Dversion="
				+ project.getProjectVersion() + " -X";

		if (logger.isDebugEnabled()) {
			logger.debug("create project command is:{}", createProjectCommand);
		}
		SystemCommandUtil.exec(createProjectCommand,
				FileUtils.getTempDirectory());

		File createdProject = new File(FileUtils.getTempDirectoryPath(),
				project.getArtifactId());

		if (createdProject.exists()) {
			updatePom(createdProject);
		} else {
			throw new RuntimeException("依据模板创建项目失败。");
		}

		if (logger.isInfoEnabled()) {
			logger.info("=============依据模板创建项目成功。路径是：{}=============",
					createdProject.getAbsolutePath());
		}

		return createdProject;

	}

	/**
	 * 
	 */
	public void updatePom(File createdProject) {

		try {
			// 构建SAXBuilder对象
			SAXBuilder builder = new SAXBuilder();
			// 构建Document对象
			Document document = builder.build(new File(createdProject,
					"pom.xml"));
			Namespace ns = Namespace
					.getNamespace("http://maven.apache.org/POM/4.0.0");
			// 获取根元素
			Element root = document.getRootElement();

			int indexOfName = root.indexOf(root.getChild("name", ns));
			int indexOfPackaging = root.indexOf(root.getChild("packaging", ns));
			int indexOfGroupId = root.indexOf(root.getChild("groupId", ns));
			int indexOfArtifactId = root.indexOf(root
					.getChild("artifactId", ns));
			int indexOfVersion = root.indexOf(root.getChild("version", ns));

			if (logger.isDebugEnabled()) {
				logger.debug("name索引为：{}", indexOfName);
				logger.debug("packaging索引为：{}", indexOfPackaging);
				logger.debug("groupId索引为：{}", indexOfGroupId);
				logger.debug("artifactId索引为：{}", indexOfArtifactId);
				logger.debug("version索引为：{}", indexOfVersion);
			}

			int indexOfDescription = NumberUtils.max(new int[] { indexOfName,
					indexOfPackaging, indexOfGroupId, indexOfArtifactId,
					indexOfVersion }) + 1;

			// 修改description元素
			root.removeChild("description", ns);
			root.addContent(indexOfDescription, new Element("description", ns)
					.setText(project.getDescription()));

			// 修改organization元素
			root.removeChild("organization", ns);
			Organization org = project.getOrganization();
			if (org != null) {
				Element orgElement = new Element("organization", ns);
				orgElement.addContent(new Element("name", ns).setText(org
						.getName()));
				orgElement.addContent(new Element("url", ns).setText(org
						.getUrl()));
				root.addContent(indexOfDescription + 1, orgElement);
			}

			// 修改developers元素
			root.removeChild("developers", ns);
			Set<Developer> developers = project.getDevelopers();
			if (!developers.isEmpty()) {
				Element developersElement = new Element("developers", ns);
				for (Developer developer : developers) {

					if (logger.isDebugEnabled()) {
						logger.debug("构建开发者：{}", developer);
					}

					Element developerElement = new Element("developer", ns);
					developerElement.addContent(new Element("id", ns)
							.setText(developer.getDeveloperId()));
					developerElement.addContent(new Element("name", ns)
							.setText(developer.getName()));
					developerElement.addContent(new Element("email", ns)
							.setText(developer.getEmail()));

					Set<Role> roles = developer.getRoles();
					if (!roles.isEmpty()) {
						Element rolesElement = new Element("roles", ns);
						for (Role role : roles) {
							rolesElement.addContent(new Element("role", ns)
									.setText(role.getName()));
						}
						developerElement.addContent(rolesElement);
					}

					developersElement.addContent(developerElement);
				}
				root.addContent(indexOfDescription + 2, developersElement);
			}

			String xmlFileData = new XMLOutputter().outputString(document);
			if (logger.isDebugEnabled()) {
				logger.debug("修改后的pom.xml为：{}", xmlFileData);
			}
			// System.out.println("Modified XML file is : " + xmlFileData);

			FileUtils.write(new File(createdProject, "pom.xml"), xmlFileData,
					"UTF-8");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static Map<String, List<ArchetypeTemplate>> findByCatalogs(
			String... catalogs) {
		ArchetypeManager archetypeManager = getArchetypeManager();

		if (catalogs == null) {
			throw new NullPointerException("catalogs cannot be null");
		}

		Map<String, List<ArchetypeTemplate>> archetypes = new LinkedHashMap<String, List<ArchetypeTemplate>>();

		for (String catalog : catalogs) {
			if (CATALOG_INTERNAL.equalsIgnoreCase(catalog)) {
				archetypes.put(CATALOG_INTERNAL,
						convertArchetype(archetypeManager.getInternalCatalog()
								.getArchetypes()));
			} else if (CATALOG_LOCAL.equalsIgnoreCase(catalog)) {
				archetypes.put(CATALOG_LOCAL, convertArchetype(archetypeManager
						.getDefaultLocalCatalog().getArchetypes()));
			} else if (CATALOG_REMOTE.equalsIgnoreCase(catalog)) {
				List<Archetype> archetypesFromRemote = archetypeManager
						.getRemoteCatalog().getArchetypes();
				if (archetypesFromRemote.size() > 0) {
					archetypes.put(CATALOG_REMOTE,
							convertArchetype(archetypesFromRemote));
				} else {
					logger.warn("No archetype found in remote catalog. Defaulting to internal catalog");
					archetypes.put(CATALOG_INTERNAL,
							convertArchetype(archetypeManager
									.getInternalCatalog().getArchetypes()));
				}
			} else if (catalog.startsWith(CATALOG_PROTOCOL_FILE)) {
				String path = catalog.substring(7);
				archetypes.put(catalog, convertArchetype(archetypeManager
						.getLocalCatalog(path).getArchetypes()));
			} else if (catalog.startsWith(CATALOG_PROTOCOL_HTTP)) {
				archetypes.put(catalog, convertArchetype(archetypeManager
						.getRemoteCatalog(catalog).getArchetypes()));
			}
		}

		if (archetypes.size() == 0) {
			logger.info("No catalog defined. Using internal catalog");

			archetypes.put(CATALOG_INTERNAL, convertArchetype(archetypeManager
					.getInternalCatalog().getArchetypes()));
		}
		return archetypes;
	}

	public static List<ArchetypeTemplate> findByCatalog(String catalog) {
		Map<String, List<ArchetypeTemplate>> archetypes = findByCatalogs(catalog);
		return archetypes.get(catalog);
	}

	private static List<ArchetypeTemplate> convertArchetype(
			List<Archetype> archetypes) {
		List<ArchetypeTemplate> archetypeTemplates = new ArrayList<ArchetypeTemplate>();
		for (Archetype archetype : archetypes) {
			ArchetypeTemplate archetypeTemplate = new ArchetypeTemplate();
			archetypeTemplate.setGroupId(archetype.getGroupId());
			archetypeTemplate.setArtifactId(archetype.getArtifactId());
			archetypeTemplate.setArchetypeRepository(archetype.getRepository());
			archetypeTemplate.setDescription(archetype.getDescription());
			archetypeTemplate.setTemplateVersion(archetype.getVersion());
			archetypeTemplates.add(archetypeTemplate);
		}
		return archetypeTemplates;
	}

	/**
	 * @return
	 */
	private static ArchetypeManager getArchetypeManager() {
		CatalogArchetypeDataSource catalogDS = new CatalogArchetypeDataSource();
		InternalCatalogArchetypeDataSource internalDS = new InternalCatalogArchetypeDataSource();
		RemoteCatalogArchetypeDataSource remoteDS = new RemoteCatalogArchetypeDataSource(
				new DefaultWagonManager());

		Map<String, ArchetypeDataSource> archetypeSources = new HashMap<String, ArchetypeDataSource>();
		archetypeSources.put("catalog", catalogDS);
		archetypeSources.put("internal-catalog", internalDS);
		archetypeSources.put("remote-catalog", remoteDS);

		ArchetypeManager archetypeManager = new OpenCISArchetypeManager(
				archetypeSources);

		// ArchetypeManager archetypeManager = new DefaultArchetypeManager();

		return archetypeManager;
	}

}
