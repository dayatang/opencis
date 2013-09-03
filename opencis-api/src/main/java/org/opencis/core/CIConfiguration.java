/**
 * 
 */
package org.opencis.core;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.dayatang.domain.AbstractEntity;

/**
 * @author chencao
 * 
 *         2011-5-14
 */
@Entity
@Table(name = "CIS_CI_CONFIGURATION")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "CONFIGURATION_CATEGORY", discriminatorType = DiscriminatorType.INTEGER)
public abstract class CIConfiguration extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8175279426954847686L;

	@OneToOne
	@JoinColumn(name = "PROJECT_ID")
	protected Project project;

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

}
