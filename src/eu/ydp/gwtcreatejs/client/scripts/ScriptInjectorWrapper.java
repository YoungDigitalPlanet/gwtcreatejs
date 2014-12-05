package eu.ydp.gwtcreatejs.client.scripts;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.user.client.Window;

public class ScriptInjectorWrapper {

	public void fromUrl(final String url) {
		ScriptInjector.fromUrl(url).setWindow(ScriptInjector.TOP_WINDOW).setCallback(createCallback(url)).inject();
	}

	public void fromUrl(String url, Callback<Void, Exception> callback) {
		ScriptInjector.fromUrl(url).setWindow(ScriptInjector.TOP_WINDOW).setCallback(callback).inject();
	}

	private Callback<Void, Exception> createCallback(final String url) {
		return new Callback<Void, Exception>() {

			@Override
			public void onSuccess(Void arg0) {
				Window.alert(url);
			}

			@Override
			public void onFailure(Exception arg0) {

			}
		};
	}
}
