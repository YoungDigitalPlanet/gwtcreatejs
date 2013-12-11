package eu.ydp.gwtcreatejs.client.loader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.UnmodifiableIterator;

public class ScriptFileSortingJUtilTest extends ManifestFileJUnitTestBase {

	@Test
	public void sortScripts() {		
		LibraryInfo libraryInfo = libraryInfos.get(0);
		List<ScriptFile> libraryFiles = libraryInfo.getFiles();
		scripts.addAll(libraryFiles);
		ScriptFileSortingUtil sortingUtil = new ScriptFileSortingUtil();
		
		ImmutableCollection<ScriptFile> result = sortingUtil.sort(scripts);

		UnmodifiableIterator<ScriptFile> resultIterator = result.iterator();
		ScriptFile script = resultIterator.next();
		assertThat(script.getOrder(), equalTo(ManifestFileJUnitTestBase.SCRIPT_FILE_ORDER_1));
		assertThat(script.getPath().contains(ManifestFileJUnitTestBase.SCRIPT_FILE_NAME_1), equalTo(true));
		script = resultIterator.next();
		assertThat(script.getOrder(), equalTo(ManifestFileJUnitTestBase.SCRIPT_FILE_ORDER_3));		
		assertThat(script.getPath().contains(ManifestFileJUnitTestBase.SCRIPT_FILE_NAME_3), equalTo(true));		
		script = resultIterator.next();
		assertThat(script.getOrder(), equalTo(ManifestFileJUnitTestBase.LIBRARY_FILE_ORDER_2));
		assertThat(script.getPath().contains(ManifestFileJUnitTestBase.LIBRARY_FILE_2), equalTo(true));
		script = resultIterator.next();
		assertThat(script.getOrder(), equalTo(ManifestFileJUnitTestBase.SCRIPT_FILE_ORDER_4));
		assertThat(script.getPath().contains(ManifestFileJUnitTestBase.SCRIPT_FILE_NAME_4), equalTo(true));
	}
}
