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
 *         2011-5-14
 */
@Entity
@Table(name = "CIS_TENANT")
public class Tenant extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3494404832656103516L;

	@Column(name = "NAME")
	private String name;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dayatang.domain.BaseEntity#hashCode()
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Tenant == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Tenant newObj = (Tenant) obj;
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
