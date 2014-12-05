package eu.ydp.gwtcreatejs.client;

import com.google.gwt.core.client.EntryPoint;

import eu.ydp.gwtcreatejs.client.scripts.SynchronousScriptsLoader;

public class CreateJsEntryPoint implements EntryPoint {

	@Override
	public void onModuleLoad() {
		SynchronousScriptsLoader scripts = new SynchronousScriptsLoader();
		scripts.injectAll();
	}
}
