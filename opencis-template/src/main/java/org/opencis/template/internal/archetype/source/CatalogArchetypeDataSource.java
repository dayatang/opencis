package org.opencis.template.internal.archetype.source;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Iterator;
import java.util.Properties;

import org.apache.maven.archetype.catalog.Archetype;
import org.apache.maven.archetype.catalog.ArchetypeCatalog;
import org.apache.maven.archetype.catalog.io.xpp3.ArchetypeCatalogXpp3Reader;
import org.apache.maven.archetype.catalog.io.xpp3.ArchetypeCatalogXpp3Writer;
import org.apache.maven.archetype.source.ArchetypeDataSource;
import org.apache.maven.archetype.source.ArchetypeDataSourceException;
import org.codehaus.plexus.util.IOUtil;
import org.codehaus.plexus.util.ReaderFactory;
import org.codehaus.plexus.util.StringUtils;
import org.codehaus.plexus.util.WriterFactory;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jason van Zyl
 * @plexus.component role-hint="catalog"
 */
public class CatalogArchetypeDataSource implements ArchetypeDataSource {

	private static final Logger logger = LoggerFactory
			.getLogger(CatalogArchetypeDataSource.class);

	public static final String ARCHETYPE_CATALOG_PROPERTY = "file";

	public static final String ARCHETYPE_CATALOG_FILENAME = "archetype-catalog.xml";

	public static final File USER_HOME = new File(
			System.getProperty("user.home"));

	public static final File MAVEN_CONFIGURATION = new File(USER_HOME, ".m2");

	public static final File DEFAULT_ARCHETYPE_CATALOG = new File(
			MAVEN_CONFIGURATION, ARCHETYPE_CATALOG_FILENAME);

	public void updateCatalog(Properties properties, Archetype archetype)
			throws ArchetypeDataSourceException {
		String s = properties.getProperty(ARCHETYPE_CATALOG_PROPERTY);

		s = StringUtils.replace(s, "${user.home}",
				System.getProperty("user.home"));

		logger.debug("Using catalog " + s);

		File catalogFile = new File(s);

		ArchetypeCatalog catalog;
		if (catalogFile.exists()) {
			try {
				logger.debug("Reading the catalog " + catalogFile);
				catalog = readCatalog(ReaderFactory.newXmlReader(catalogFile));
			} catch (FileNotFoundException ex) {
				logger.debug("Catalog file don't exist");
				catalog = new ArchetypeCatalog();
			} catch (IOException e) {
				throw new ArchetypeDataSourceException(
						"Error reading archetype catalog.", e);
			}
		} else {
			logger.debug("Catalog file don't exist");
			catalog = new ArchetypeCatalog();
		}

		Iterator<Archetype> archetypes = catalog.getArchetypes().iterator();
		boolean found = false;
		Archetype newArchetype = archetype;
		while (!found && archetypes.hasNext()) {
			Archetype a = (Archetype) archetypes.next();
			if (a.getGroupId().equals(archetype.getGroupId())
					&& a.getArtifactId().equals(archetype.getArtifactId())) {
				newArchetype = a;
				found = true;
			}
		}
		if (!found) {
			catalog.addArchetype(newArchetype);
		}

		newArchetype.setVersion(archetype.getVersion());
		newArchetype.setRepository(archetype.getRepository());
		newArchetype.setDescription(archetype.getDescription());
		newArchetype.setProperties(archetype.getProperties());
		newArchetype.setGoals(archetype.getGoals());

		writeLocalCatalog(catalog, catalogFile);
	}

	public ArchetypeCatalog getArchetypeCatalog(Properties properties)
			throws ArchetypeDataSourceException {
		String s = properties.getProperty(ARCHETYPE_CATALOG_PROPERTY);

		s = StringUtils.replace(s, "${user.home}",
				System.getProperty("user.home"));

		File catalogFile = new File(s);
		if (catalogFile.exists() && catalogFile.isDirectory()) {
			catalogFile = new File(catalogFile, ARCHETYPE_CATALOG_FILENAME);
		}
		logger.debug("Using catalog " + catalogFile);

		if (catalogFile.exists()) {
			try {
				return readCatalog(ReaderFactory.newXmlReader(catalogFile));
			} catch (FileNotFoundException e) {
				throw new ArchetypeDataSourceException(
						"The specific archetype catalog does not exist.", e);
			} catch (IOException e) {
				throw new ArchetypeDataSourceException(
						"Error reading archetype catalog.", e);
			}
		} else {
			return new ArchetypeCatalog();
		}
	}

	protected void writeLocalCatalog(ArchetypeCatalog catalog, File catalogFile)
			throws ArchetypeDataSourceException {
		Writer writer = null;
		try {
			writer = WriterFactory.newXmlWriter(catalogFile);

			ArchetypeCatalogXpp3Writer catalogWriter = new ArchetypeCatalogXpp3Writer();

			catalogWriter.write(writer, catalog);
		} catch (IOException e) {
			throw new ArchetypeDataSourceException(
					"Error writing archetype catalog.", e);
		} finally {
			IOUtil.close(writer);
		}
	}

	protected ArchetypeCatalog readCatalog(Reader reader)
			throws ArchetypeDataSourceException {
		try {
			ArchetypeCatalogXpp3Reader catalogReader = new ArchetypeCatalogXpp3Reader();

			return catalogReader.read(reader);
		} catch (IOException e) {
			throw new ArchetypeDataSourceException(
					"Error reading archetype catalog.", e);
		} catch (XmlPullParserException e) {
			throw new ArchetypeDataSourceException(
					"Error parsing archetype catalog.", e);
		} finally {
			IOUtil.close(reader);
		}
	}
}