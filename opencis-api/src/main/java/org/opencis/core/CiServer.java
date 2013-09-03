/**
 * 
 */
package org.opencis.core;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.dayatang.domain.AbstractEntity;

/**
 * @author chencao
 * 
 *         2011-5-26
 */
@Entity
@Table(name = "CIS_CI_SERVERS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "CI_CATEGORY", discriminatorType = DiscriminatorType.INTEGER)
public abstract class CiServer<T extends CIConfiguration> extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8607382275414902462L;

	@Column(name = "NAME")
	private String name;

	/**
	 * 创建构建任务Job
	 * 
	 * @param configuration
	 *            CI配置信息
	 */
	public abstract void createJob(T configuration);

	/**
	 * 执行一次构建任务
	 * 
	 * @param configuration
	 *            CI配置信息
	 */
	public abstract void performBuild(T configuration);

	/**
	 * 根据Project构建CI配置信息
	 * 
	 * @param project
	 *            Project信息
	 * @return CI配置信息
	 */
	public abstract T buildConfiguration(Project project);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dayatang.domain.BaseEntity#hashCode()
	 */
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (obj instanceof CiServer == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		CiServer<CIConfiguration> newObj = (CiServer<CIConfiguration>) obj;
		return new EqualsBuilder().append(name, newObj.name).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dayatang.domain.AbstractEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(name).toHashCode();
	}

	@Override
	public String toString() {
		return getName();
	}

}
