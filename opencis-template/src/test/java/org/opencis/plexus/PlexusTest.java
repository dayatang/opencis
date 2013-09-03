/**
 * 
 */
package org.opencis.plexus;

import static org.junit.Assert.assertEquals;

import org.codehaus.plexus.DefaultPlexusContainer;
import org.codehaus.plexus.PlexusContainer;
import org.junit.Test;

/**
 * @author chencao
 * 
 *         2011-5-24
 */
public class PlexusTest {

	@Test
	public void testPlexus() throws Exception {
		PlexusContainer container = new DefaultPlexusContainer();
		Cheese cheese = (Cheese) container.lookup(Cheese.ROLE, "parmesan");
		assertEquals("strong", cheese.getAroma());
		container.dispose();
	}
}
