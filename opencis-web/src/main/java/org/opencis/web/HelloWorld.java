/**
 * 
 */
package org.opencis.web;

import java.util.ArrayList;

import org.opencis.core.ProjectTemplate;

/**
 * @author chencao
 * 
 *         2011-5-19
 */
public class HelloWorld {
	private String message;
	private String userName;
	private ArrayList<ProjectTemplate> templates = new ArrayList<ProjectTemplate>();
	private String country;

	public HelloWorld() {
	}

	public String execute() {
		setMessage("Hello " + getUserName());
		return "SUCCESS";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the templates
	 */
	public ArrayList<ProjectTemplate> getTemplates() {
		if (templates.isEmpty()) {
			// templates.add(new ProjectTemplate("1", "opencis-jar"));
			// templates.add(new ProjectTemplate("2", "opencis-web"));
			// templates.add(new ProjectTemplate("3", "opencis-ejb3"));
		}
		return templates;
	}

	/**
	 * @param templates
	 *            the templates to set
	 */
	public void setTemplates(ArrayList<ProjectTemplate> templates) {
		this.templates = templates;
	}

}
