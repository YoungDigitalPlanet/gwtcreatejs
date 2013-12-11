package eu.ydp.gwtcreatejs.client.loader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.junit.Test;

public class ManifestJUnitTest extends ManifestFileJUnitTestBase {
	
	@Test
	public void parseDocument() {		
		LibraryInfo libraryInfo = libraryInfos.get(0);
		List<ScriptFile> libraryFiles = libraryInfo.getFiles();
		
		assertThat(manifest.getPackageName(), equalTo(ManifestFileJUnitTestBase.PACKAGE));
		assertThat(manifest.getClassName(), equalTo(ManifestFileJUnitTestBase.MANIFEST_CLASS_NAME));
		assertThat(libraryInfo.getPackageName(), equalTo(ManifestFileJUnitTestBase.LIBRARY_PACKAGE));				
		assertThat(scripts.get(0).getPath(), equalTo(ManifestFileJUnitTestBase.SCRIPT_FILE_NAME_1));
		assertThat(scripts.get(0).getOrder(), equalTo(ManifestFileJUnitTestBase.SCRIPT_FILE_ORDER_1));
		assertThat(scripts.get(1).getOrder(), equalTo(ManifestFileJUnitTestBase.SCRIPT_FILE_ORDER_2));
		assertThat(libraryFiles.get(0).getOrder(), equalTo(ScriptFile.DEFAULT_ORDER));
		assertThat(libraryFiles.get(1).getOrder(), equalTo(ManifestFileJUnitTestBase.LIBRARY_FILE_ORDER_2));
	}
	
}
