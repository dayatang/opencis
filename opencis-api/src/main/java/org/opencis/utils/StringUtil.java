/**
 * 
 */
package org.opencis.utils;

import org.apache.commons.lang.StringUtils;

/**
 * @author chencao
 * 
 *         2011-5-28
 */
public class StringUtil {
	/**
	 * @param projectName
	 * @return
	 */
	public static String convertProjectName(String projectName) {
		String[] tmpStrArr = StringUtils.splitByWholeSeparator(projectName, null);
		return StringUtils.join(tmpStrArr, "_");
	}
}
