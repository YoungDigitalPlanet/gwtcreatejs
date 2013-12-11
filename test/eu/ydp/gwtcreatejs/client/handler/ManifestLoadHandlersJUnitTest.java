package eu.ydp.gwtcreatejs.client.handler;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import eu.ydp.gwtcreatejs.client.loader.Manifest;

public class ManifestLoadHandlersJUnitTest {

	ManifestLoadHandlers instance = new ManifestLoadHandlers();
	ManifestLoadHandler handler = mock(ManifestLoadHandler.class);

	@Before
	public void before() {
		instance.addManifestLoadHandler(handler);
	}

	@Test
	public void testCallAllHandlers() {
		Manifest manifest = mock(Manifest.class);
		instance.callAllHandlers(manifest);

		verify(handler).onManifestLoad(Mockito.eq(manifest));

	}

}
