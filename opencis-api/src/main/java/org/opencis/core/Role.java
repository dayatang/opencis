/**
 * 
 */
package org.opencis.core;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "CIS_ROLE")
public class Role extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -472671196863677095L;
	@Column(name = "NAME")
	private String name;

	/**
	 * 
	 */
	public Role() {
	}

	/**
	 * @param name
	 */
	public Role(String name) {
		this.name = name;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dayatang.domain.BaseEntity#hashCode()
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Role == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Role newObj = (Role) obj;
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
		return name;
	}

}
