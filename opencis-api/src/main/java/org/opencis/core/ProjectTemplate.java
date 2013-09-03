package org.opencis.core;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.dayatang.domain.AbstractEntity;

/**
 * @author chencao
 * 
 *         2011-5-12
 */
@Entity
@Table(name = "CIS_PROJECT_TEMPLATE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TEMPLATE_CATEGORY", discriminatorType = DiscriminatorType.INTEGER)
public abstract class ProjectTemplate extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 618977828359675918L;
	
	@Column(name = "NAME", nullable = false)
	protected String name;
	
	@Column(name = "TEMPLATE_VERSION", nullable = false)
	protected String templateVersion;
	
	@Column(name = "DESCRIPTION")
	protected String description;
	
	@ManyToOne
	@JoinColumn(name = "PROJECT_ID")
	protected Project project;

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
	 * @return the templateVersion
	 */
	public String getTemplateVersion() {
		return templateVersion;
	}

	/**
	 * @param templateVersion
	 *            the templateVersion to set
	 */
	public void setTemplateVersion(String templateVersion) {
		this.templateVersion = templateVersion;
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
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * @param project
	 *            the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dayatang.domain.BaseEntity#hashCode()
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ProjectTemplate == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		ProjectTemplate newObj = (ProjectTemplate) obj;
		return new EqualsBuilder().append(name, newObj.name)
				.append(templateVersion, newObj.templateVersion).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dayatang.domain.AbstractEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(name).append(templateVersion)
				.toHashCode();
	}

	@Override
	public String toString() {
		return "ProjectTemplate [name=" + name + "]";
	}

	public abstract String getDisplayName();

	/**
	 * 创建并生成项目
	 * 
	 * @return 生成项目的File
	 */
	public abstract File createProject();

}
