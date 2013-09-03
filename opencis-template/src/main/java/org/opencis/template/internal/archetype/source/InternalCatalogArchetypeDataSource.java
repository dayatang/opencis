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

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

import org.apache.maven.archetype.catalog.ArchetypeCatalog;
import org.apache.maven.archetype.source.ArchetypeDataSourceException;
import org.codehaus.plexus.util.ReaderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jason van Zyl
 * @plexus.component role-hint="internal-catalog"
 */
@SuppressWarnings("unused")
public class InternalCatalogArchetypeDataSource extends
		CatalogArchetypeDataSource {

	private static final Logger logger = LoggerFactory
			.getLogger(InternalCatalogArchetypeDataSource.class);

	public ArchetypeCatalog getArchetypeCatalog(Properties properties)
			throws ArchetypeDataSourceException {
		try {
			InputStream in = getClass().getClassLoader().getResourceAsStream(
					ARCHETYPE_CATALOG_FILENAME);
			Reader reader = ReaderFactory.newXmlReader(in);

			return readCatalog(reader);
		} catch (IOException e) {
			throw new ArchetypeDataSourceException(
					"Error reading archetype catalog.", e);
		}
	}
}