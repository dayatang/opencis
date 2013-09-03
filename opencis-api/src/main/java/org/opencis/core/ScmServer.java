/**
 * 
 */
package org.opencis.core;

import java.io.File;

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
 *         2011-5-12
 */
@Entity
@Table(name = "CIS_SCM_SERVERS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "SCM_CATEGORY", discriminatorType = DiscriminatorType.INTEGER)
public abstract class ScmServer extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2196960598719217843L;

	@Column(name = "NAME")
	private String name;

	/**
	 * 初始化项目：初始化本地项目到远程SCM地址
	 * 
	 * @param localPath
	 *            本地项目路径
	 * @param dstURL
	 *            远程SCM地址
	 * @param commitMessage
	 *            提交描述消息
	 * @param username
	 *            SCM用户名
	 * @param password
	 *            SCM密码
	 */
	public abstract void initProject(File localPath, String dstURL,
			String commitMessage, String username, String password);

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
	public boolean equals(Object obj) {
		if (obj instanceof ScmServer == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		ScmServer newObj = (ScmServer) obj;
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
		return "ScmServer [name=" + name + "]";
	}

}