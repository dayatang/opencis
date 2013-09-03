/**
 * 
 */
package org.opencis.template.internal.archetype;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.maven.archetype.ArchetypeCreationRequest;
import org.apache.maven.archetype.ArchetypeCreationResult;
import org.apache.maven.archetype.ArchetypeGenerationRequest;
import org.apache.maven.archetype.ArchetypeGenerationResult;
import org.apache.maven.archetype.ArchetypeManager;
import org.apache.maven.archetype.catalog.Archetype;
import org.apache.maven.archetype.catalog.ArchetypeCatalog;
import org.apache.maven.archetype.source.ArchetypeDataSource;
import org.apache.maven.archetype.source.ArchetypeDataSourceException;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.codehaus.plexus.util.IOUtil;

/**
 * @author chencao
 * 
 *         2011-5-24
 */
public class OpenCISArchetypeManager implements ArchetypeManager {

	private Map<String, ArchetypeDataSource> archetypeSources;

	/**
	 * @param archetypeSources
	 */
	public OpenCISArchetypeManager(
			Map<String, ArchetypeDataSource> archetypeSources) {
		this.archetypeSources = archetypeSources;
	}

	public ArchetypeCreationResult createArchetypeFromProject(
			ArchetypeCreationRequest request) {
		throw new IllegalArgumentException("not supported.");
	}

	public ArchetypeGenerationResult generateProjectFromArchetype(
			ArchetypeGenerationRequest request) {
		throw new IllegalArgumentException("not supported.");
	}

	public File archiveArchetype(File archetypeDirectory, File outputDirectory,
			String finalName) throws DependencyResolutionRequiredException,
			IOException {
		File jarFile = new File(outputDirectory, finalName + ".jar");

		zip(archetypeDirectory, jarFile);

		return jarFile;
	}

	public void zip(File sourceDirectory, File archive) throws IOException {
		if (!archive.getParentFile().exists()) {
			archive.getParentFile().mkdirs();
		}

		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(archive));

		zos.setLevel(9);

		zipper(zos, sourceDirectory.getAbsolutePath().length(), sourceDirectory);

		zos.close();
	}

	private void zipper(ZipOutputStream zos, int offset,
			File currentSourceDirectory) throws IOException {
		File[] files = currentSourceDirectory.listFiles();

		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				zipper(zos, offset, files[i]);
			} else {
				String fileName = files[i].getAbsolutePath().substring(
						offset + 1);

				if (File.separatorChar != '/') {
					fileName = fileName.replace('\\', '/');
				}

				ZipEntry e = new ZipEntry(fileName);

				zos.putNextEntry(e);

				FileInputStream is = new FileInputStream(files[i]);

				IOUtil.copy(is, zos);

				is.close();

				zos.flush();

				zos.closeEntry();
			}
		}
	}

	public ArchetypeCatalog getInternalCatalog() {
		try {
			ArchetypeDataSource source = archetypeSources
					.get("internal-catalog");

			return source.getArchetypeCatalog(new Properties());
		} catch (ArchetypeDataSourceException e) {
			return new ArchetypeCatalog();
		}
	}

	public ArchetypeCatalog getDefaultLocalCatalog() {
		return getLocalCatalog("${user.home}/.m2/archetype-catalog.xml");
	}

	public ArchetypeCatalog getLocalCatalog(String path) {
		try {
			Properties properties = new Properties();
			properties.setProperty("file", path);
			ArchetypeDataSource source = archetypeSources.get("catalog");

			return source.getArchetypeCatalog(properties);
		} catch (ArchetypeDataSourceException e) {
			return new ArchetypeCatalog();
		}
	}

	public ArchetypeCatalog getRemoteCatalog() {
		return getRemoteCatalog("http://repo1.maven.org/maven2");
	}

	public ArchetypeCatalog getRemoteCatalog(String url) {
		try {
			Properties properties = new Properties();
			properties.setProperty("repository", url);
			ArchetypeDataSource source = archetypeSources.get("remote-catalog");

			return source.getArchetypeCatalog(properties);
		} catch (ArchetypeDataSourceException e) {
			return new ArchetypeCatalog();
		}
	}

	public void updateLocalCatalog(Archetype archetype) {
		updateLocalCatalog(archetype, "${user.home}/.m2/archetype-catalog.xml");
	}

	public void updateLocalCatalog(Archetype archetype, String path) {
		try {
			Properties properties = new Properties();
			properties.setProperty("file", path);
			ArchetypeDataSource source = archetypeSources.get("catalog");

			source.updateCatalog(properties, archetype);
		} catch (ArchetypeDataSourceException e) {
		}
	}

}
