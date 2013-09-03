/**
 * 
 */
package org.opencis.core;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.dayatang.domain.ValueObject;

/**
 * @author chencao
 * 
 *         2011-5-18
 */
@Embeddable
public class SCM implements ValueObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -900900703136245565L;

	@Column(name = "CONNECTION", nullable = false)
	private String connection;
	
	@Column(name = "DEV_CONNECTION", nullable = false)
	private String developerConnection;
	
	@Column(name = "USERNAME", nullable = false)
	private String username;
	
	@Column(name = "PASSWORD", nullable = false)
	private String password;

	/**
	 * 
	 */
	public SCM() {
	}

	/**
	 * @param connection
	 * @param developerConnection
	 * @param username
	 * @param password
	 */
	public SCM(String connection, String developerConnection, String username,
			String password) {
		this.connection = connection;
		this.developerConnection = developerConnection;
		this.username = username;
		this.password = password;
	}

	/**
	 * @return the connection
	 */
	public String getConnection() {
		return connection;
	}

	/**
	 * @param connection
	 *            the connection to set
	 */
	public void setConnection(String connection) {
		this.connection = connection;
	}

	/**
	 * @return the developerConnection
	 */
	public String getDeveloperConnection() {
		return developerConnection;
	}

	/**
	 * @param developerConnection
	 *            the developerConnection to set
	 */
	public void setDeveloperConnection(String developerConnection) {
		this.developerConnection = developerConnection;
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

}
