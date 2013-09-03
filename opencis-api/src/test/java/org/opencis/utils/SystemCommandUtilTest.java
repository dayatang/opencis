package org.opencis.utils;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SystemCommandUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExecWithoutParams() {
		SystemCommandUtil.exec("ls", FileUtils.getTempDirectory());
	}

	@Test
	public void testExecWithParams() {
		SystemCommandUtil.exec("ls -al", new String[] {"-al"}, FileUtils.getTempDirectory());
	}

}
