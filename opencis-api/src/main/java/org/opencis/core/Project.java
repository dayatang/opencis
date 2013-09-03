/**
 * 
 */
package org.opencis.core;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.dayatang.domain.AbstractEntity;

/**
 * @author chencao
 * 
 *         2011-5-14
 */
@Entity
@Table(name = "CIS_PROJECT")
@SuppressWarnings("rawtypes")
public class Project extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3267537290338967509L;

	@Column(name = "GROUP_ID", nullable = false)
	private String groupdId;
	
	@Column(name = "ARTIFACT_ID", nullable = false)
	private String artifactId;
	
	@Column(name = "PROJECT_VERSION", nullable = false)
	private String projectVersion;
	
	@Column(name = "PACKAGING")
	private String packaging;
	
	@Column(name = "CI_URL", nullable = false)
	private String ciUrl;
	
	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Embedded
	private Organization organization;
	
	@Embedded
	private SCM scm;
	
	@ManyToOne
	@JoinColumn(name = "CI_SERVER_ID")
	private CiServer ciServer;
	
	@ManyToOne
	@JoinColumn(name = "SCM_SERVER_ID")
	private ScmServer scmServer;
	
	@ManyToOne
	@JoinColumn(name = "PROJECT_SERVER_ID")
	private PmServer pmServer;

	@ManyToMany
	@JoinTable(name = "CIS_PROJECT_R_DEVELOPER", joinColumns = { @JoinColumn(name = "PROJECT_ID") }, inverseJoinColumns = { @JoinColumn(name = "DEVELOPER_ID") })
	private Set<Developer> developers = new HashSet<Developer>();

	Project() {
	}

	public Project(String groupdId, String artifactId, String projectVersion) {
		super();
		this.groupdId = groupdId;
		this.artifactId = artifactId;
		this.projectVersion = projectVersion;
	}

	/**
	 * @return the groupdId
	 */
	public String getGroupdId() {
		return groupdId;
	}

	/**
	 * @param groupdId
	 *            the groupdId to set
	 */
	public void setGroupdId(String groupdId) {
		this.groupdId = groupdId;
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
	}

	/**
	 * @return the projectVersion
	 */
	public String getProjectVersion() {
		return projectVersion;
	}

	/**
	 * @param projectVersion
	 *            the projectVersion to set
	 */
	public void setProjectVersion(String projectVersion) {
		this.projectVersion = projectVersion;
	}

	/**
	 * @return the ciUrl
	 */
	public String getCiUrl() {
		return ciUrl;
	}

	/**
	 * @param ciUrl
	 *            the ciUrl to set
	 */
	public void setCiUrl(String ciUrl) {
		this.ciUrl = ciUrl;
	}

	/**
	 * @return the packaging
	 */
	public String getPackaging() {
		return packaging;
	}

	/**
	 * @param packaging
	 *            the packaging to set
	 */
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the organization
	 */
	public Organization getOrganization() {
		return organization;
	}

	/**
	 * @param organization
	 *            the organization to set
	 */
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	/**
	 * @return the scm
	 */
	public SCM getScm() {
		return scm;
	}

	/**
	 * @param scm
	 *            the scm to set
	 */
	public void setScm(SCM scm) {
		this.scm = scm;
	}

	/**
	 * @return the ciServer
	 */
	public CiServer getCiServer() {
		return ciServer;
	}

	/**
	 * @param ciServer
	 *            the ciServer to set
	 */
	public void setCiServer(CiServer ciManager) {
		this.ciServer = ciManager;
	}

	/**
	 * @return the scmServer
	 */
	public ScmServer getScmServer() {
		return scmServer;
	}

	/**
	 * @param scmServer
	 *            the scmServer to set
	 */
	public void setScmServer(ScmServer scmManager) {
		this.scmServer = scmManager;
	}

	/**
	 * @return the developers
	 */
	public Set<Developer> getDevelopers() {
		return developers;
	}

	/**
	 * @return the pmServer
	 */
	public PmServer getPmServer() {
		return pmServer;
	}

	/**
	 * @param pmServer
	 *            the pmServer to set
	 */
	public void setPmServer(PmServer pmServer) {
		this.pmServer = pmServer;
	}

	/**
	 * @param developers
	 *            the developers to set
	 */
	public void setDevelopers(Set<Developer> developers) {
		this.developers = developers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dayatang.domain.AbstractEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Project == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Project newObj = (Project) obj;
		return new EqualsBuilder().append(groupdId, newObj.groupdId)
				.append(artifactId, newObj.artifactId)
				.append(projectVersion, newObj.projectVersion).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dayatang.domain.AbstractEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(groupdId).append(artifactId)
				.append(projectVersion).toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dayatang.domain.AbstractEntity#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append(groupdId).append(artifactId).append(projectVersion)
				.toString();
	}

}
