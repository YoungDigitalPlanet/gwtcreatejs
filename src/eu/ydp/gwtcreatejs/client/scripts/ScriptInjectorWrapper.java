package eu.ydp.gwtcreatejs.client.scripts;

import com.google.gwt.core.client.*;

public class ScriptInjectorWrapper {

	public void fromUrl(final String url) {
		ScriptInjector.fromUrl(url).setWindow(ScriptInjector.TOP_WINDOW).inject();
	}

	public void fromUrl(String url, Callback<Void, Exception> callback) {
		ScriptInjector.fromUrl(url).setWindow(ScriptInjector.TOP_WINDOW).setCallback(callback).inject();
	}
}
