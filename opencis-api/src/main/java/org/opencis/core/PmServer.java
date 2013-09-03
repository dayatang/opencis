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
 *         2011-5-15
 */
@Entity
@Table(name = "CIS_PM_SERVERS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "PM_CATEGORY", discriminatorType = DiscriminatorType.INTEGER)
public abstract class PmServer<T extends PmConfiguration> extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2924963270520523953L;

	@Column(name = "NAME")
	private String name;

	/**
	 * 初始化项目：根据配置信息初始化项目管理平台，如trac等
	 * 
	 * @param configuration
	 */
	public abstract void initProject(T configuration);

	/**
	 * 根据Project构建项目管理平台配置信息
	 * 
	 * @param project
	 *            Project信息
	 * @return 项目管理平台配置信息
	 */
	public abstract T getConfiguration(Project project);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dayatang.domain.BaseEntity#hashCode()
	 */
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (obj instanceof PmServer == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		PmServer<PmConfiguration> newObj = (PmServer<PmConfiguration>) obj;
		return new EqualsBuilder().append(name, newObj.name).isEquals();
	}

	@Override
	public String toString() {
		return "PmServer [name=" + name + "]";
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

}
