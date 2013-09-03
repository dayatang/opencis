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
 *         2011-5-13
 */
@Embeddable
public class Organization implements ValueObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7876630130080687431L;
	
	@Column(name = "ORGANIZATION_NAME")
	private String name;
	
	@Column(name = "ORGANIZATION_URL")
	private String url;

	Organization() {
	}

	public Organization(String name) {
		super();
		this.name = name;
	}

	public Organization(String name, String url) {
		super();
		this.name = name;
		this.url = url;
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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}
