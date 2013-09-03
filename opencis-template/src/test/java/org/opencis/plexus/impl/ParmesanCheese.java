/**
 * 
 */
package org.opencis.plexus.impl;

import org.opencis.plexus.Cheese;

/**
 * @author chencao
 * 
 *         2011-5-24
 */
public class ParmesanCheese implements Cheese {

	public void slice(int slices) {
		throw new UnsupportedOperationException("No can do");
	}

	public String getAroma() {
		return "strong";
	}
}
