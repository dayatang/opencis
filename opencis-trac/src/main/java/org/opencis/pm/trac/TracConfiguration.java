/**
 * 
 */
package org.opencis.pm.trac;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.opencis.core.PmConfiguration;
import org.opencis.core.Project;
import org.opencis.utils.StringUtil;

/**
 * @author chencao
 * 
 *         2011-5-19
 */
@Entity
@DiscriminatorValue("1")
public class TracConfiguration extends PmConfiguration {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5011274590541722233L;

	// Trac服务器地址
	@Column(name = "HOST_NAME")
	private String hostname;
	// 登录Trac服务器的用户名（用于执行ssh命令）
	@Column(name = "USERNAME")
	private String username;
	// 登录Trac服务器的密码
	@Column(name = "PASSWORD")
	private String password;
	// 名字 不能有空格
	@Column(name = "PROJECT_NAME")
	private String projectName;
	// 完整Trac路径（含子目录）
	@Column(name = "TRAC_PATH")
	private String tracPath;
	// 执行的命令
	@Column(name = "COMMAND")
	private String command;

	/**
	 * @return the hostname
	 */
	public String getHostname() {
		return hostname;
	}

	/**
	 * @param hostname
	 *            the hostname to set
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * @return the tracPath
	 */
	public String getTracPath() {
		return tracPath;
	}

	/**
	 * @param tracPath
	 *            the tracPath to set
	 */
	public void setTracPath(String tracPath) {
		this.tracPath = tracPath;
	}

	/**
	 * @return the command
	 */
	public String getCommand() {
		return "trac-admin " + getTracPath() + " initenv "
				+ StringUtil.convertProjectName(projectName)
				+ " sqlite:db/trac.db";
	}

	/**
	 * @param command
	 *            the command to set
	 */
	@Deprecated
	public void setCommand(String command) {
		this.command = command;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dayatang.domain.BaseEntity#hashCode()
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TracConfiguration == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		TracConfiguration newObj = (TracConfiguration) obj;
		return new EqualsBuilder().append(hostname, newObj.hostname)
				.append(username, newObj.username)
				.append(password, newObj.password)
				.append(tracPath, newObj.tracPath)
				.append(command, newObj.command).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dayatang.domain.AbstractEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(hostname).append(username)
				.append(password).append(tracPath).append(command).toHashCode();
	}

	@Override
	public String toString() {
		return "TracConfiguration [hostname=" + hostname + ", username=" + username + ", password=" + password + ", projectName=" + projectName + ", tracPath="
				+ tracPath + ", command=" + command + "]";
	}

	public static TracConfiguration getByProject(Project project) {
		return getRepository().getSingleResult(
				"from TracConfiguration o where o.project.id = ?",
				new Object[] { project.getId() }, TracConfiguration.class);
	}

}
