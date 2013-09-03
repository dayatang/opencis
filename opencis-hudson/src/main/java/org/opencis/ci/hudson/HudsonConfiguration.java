/**
 * 
 */
package org.opencis.ci.hudson;

import java.io.IOException;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Lob;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.opencis.core.CIConfiguration;
import org.opencis.core.Project;
import org.opencis.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chencao
 * 
 *         2011-5-14
 */
@Entity
@DiscriminatorValue("1")
public class HudsonConfiguration extends CIConfiguration {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1724622908566248176L;

	private static final Logger logger = LoggerFactory
			.getLogger(HudsonConfiguration.class);

	private final static String CONFIG_FILE = "/ci/hudson/config.xml";
	private final static String CREATE_JOB_URL = "createItem";
	private final static String BUILD_JOB_URL = "job";

	// Hudson URL地址
	@Column(name = "URL")
	private String url;
	
	// Hudson Job名字 不能有空格
	@Column(name = "PROJECT_NAME")
	private String projectName;
	
	@Lob
	@Column(name = "CONFIG_XML")
	private String configXML;

	/**
	 * @param url
	 * @param projectName
	 * @param configXML
	 */
	public HudsonConfiguration(String projectName, String url) {
		this.projectName = StringUtil.convertProjectName(projectName);
		this.url = url;
		String content = "";
		try {
			content = FileUtils.readFileToString(
					FileUtils.toFile(HudsonConfiguration.class
							.getResource(CONFIG_FILE)), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.configXML = content;
	}

	/**
	 * @param projectName
	 * @param url
	 * @param configXML
	 */
	public HudsonConfiguration(String projectName, String url, String configXML) {
		this.projectName = StringUtil.convertProjectName(projectName);
		this.url = url;
		this.configXML = configXML;
	}

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName
	 *            the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = StringUtil.convertProjectName(projectName);
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the configXML
	 */
	public String getConfigXML() {
		return configXML;
	}

	/**
	 * @param configXML
	 *            the configXML to set
	 */
	public void setConfigXML(String configXML) {
		this.configXML = configXML;
	}

	public String getCreateJobUrl() {
		// http://localhost:8080/createItem?name=XXXX
		String createJobUrl = "";
		String endStr = CREATE_JOB_URL + "?name="
				+ StringUtil.convertProjectName(projectName);
		if (url.endsWith("/")) {
			createJobUrl = url + endStr;
		} else {
			createJobUrl = url + "/" + endStr;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("create job url:" + createJobUrl);
		}
		return createJobUrl;
	}

	public String getBuildUrl() {
		// http://localhost:8080/job/xxx/build
		String buildJobUrl = "";
		String endStr = BUILD_JOB_URL + "/"
				+ StringUtil.convertProjectName(projectName) + "/build";
		if (url.endsWith("/")) {
			buildJobUrl = url + endStr;
		} else {
			buildJobUrl = url + "/" + endStr;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("build job url:" + buildJobUrl);
		}
		return buildJobUrl;
	}

	public static HudsonConfiguration getByProject(Project project) {
		return getRepository().getSingleResult(
				"from HudsonConfiguration o where o.project.id = ?",
				new Object[] { project.getId() }, HudsonConfiguration.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dayatang.domain.BaseEntity#hashCode()
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof HudsonConfiguration == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		HudsonConfiguration newObj = (HudsonConfiguration) obj;
		return new EqualsBuilder().append(url, newObj.url)
				.append(projectName, newObj.projectName)
				.append(configXML, newObj.configXML).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dayatang.domain.AbstractEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(projectName)
				.append(projectName).append(configXML).toHashCode();
	}

	@Override
	public String toString() {
		return projectName;
	}
}
