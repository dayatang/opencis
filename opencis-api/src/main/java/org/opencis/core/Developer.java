package org.opencis.core;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "CIS_DEVELOPER")
public class Developer extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2773901274148476616L;
	
	@Column(name = "DEVELOPER_ID")
	private String developerId;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "EMAIL")
	private String email;

	@ManyToMany(mappedBy = "developers")
	private Set<Project> projects = new HashSet<Project>();

	@ManyToMany
	@JoinTable(name = "CIS_DEVELOPER_R_ROLE", joinColumns = { @JoinColumn(name = "DEVELOPER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	private Set<Role> roles = new HashSet<Role>();

	/**
	 * @return the developerId
	 */
	public String getDeveloperId() {
		return developerId;
	}

	/**
	 * @param developerId
	 *            the developerId to set
	 */
	public void setDeveloperId(String developerId) {
		this.developerId = developerId;
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the projects
	 */
	public Set<Project> getProjects() {
		return projects;
	}

	/**
	 * @param projects
	 *            the projects to set
	 */
	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	/**
	 * @return the roles
	 */
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles
	 *            the roles to set
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dayatang.domain.BaseEntity#hashCode()
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Developer == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Developer newObj = (Developer) obj;
		return new EqualsBuilder().append(developerId, newObj.developerId)
				.append(name, newObj.name).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dayatang.domain.AbstractEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(developerId).append(name)
				.toHashCode();
	}

	@Override
	public String toString() {
		return name;
	}

}
