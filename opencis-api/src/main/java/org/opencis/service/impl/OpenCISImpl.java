/**
 * 
 */
package org.opencis.service.impl;

import java.io.File;

import org.opencis.core.CIConfiguration;
import org.opencis.core.CiServer;
import org.opencis.core.Project;
import org.opencis.core.PmServer;
import org.opencis.core.PmConfiguration;
import org.opencis.core.ProjectTemplate;
import org.opencis.service.OpenCIS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chencao
 * 
 *         2011-5-14
 */
@SuppressWarnings({ "unused", "unchecked" })
public class OpenCISImpl implements OpenCIS {

	private static final Logger logger = LoggerFactory
			.getLogger(OpenCISImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencis.core.OpenCIS#createProject(org.opencis.core.ProjectInfo)
	 */
	@Override
	public void createProject(Project project, ProjectTemplate projectTemplate) {

		File generatedProjectFile = generateProject(project, projectTemplate);

		// 通过SCMServer
		submitToSCM(project, generatedProjectFile);

		// 通过CIServer
		createCInstanceAndBuild(project);

		// trac
		createProjectManagement(project);

	}

	/**
	 * @param project
	 */
	private void createProjectManagement(Project project) {
		PmServer<PmConfiguration> projectMgr = project
				.getPmServer();
		projectMgr.initProject(projectMgr.getConfiguration(project));

	}

	/**
	 * @param project
	 */
	private void createCInstanceAndBuild(Project project) {
		CiServer<CIConfiguration> ciMgr = project.getCiServer();
		CIConfiguration config = ciMgr.buildConfiguration(project);
		ciMgr.createJob(config);
		// build
		ciMgr.performBuild(config);
	}

	/**
	 * @param projectInfo
	 */
	private void submitToSCM(Project project, File localPath) {
		project.getScmServer().initProject(localPath,
				project.getScm().getDeveloperConnection(), "初始化项目",
				project.getScm().getUsername(), project.getScm().getPassword());
	}

	private File generateProject(Project project,
			ProjectTemplate projectTemplate) {
		project.save();
		projectTemplate.setProject(project);
		projectTemplate.save();

		return projectTemplate.createProject();
	}

}
