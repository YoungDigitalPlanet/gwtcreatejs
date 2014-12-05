package eu.ydp.gwtcreatejs.client.scripts;

import java.util.Collections;
import java.util.Stack;

import com.google.gwt.core.client.Callback;

public class SynchronousScriptsLoader {

	private final Stack<ScriptsList> scripts;

	private final ScriptInjectorWrapper scriptInjectorWrapper;
	private final UrlConverter urlConverter;

	private final Callback<Void, Exception> callback = new Callback<Void, Exception>() {

		@Override
		public void onFailure(Exception arg0) {
		}

		@Override
		public void onSuccess(Void arg0) {
			injectNext();
		}
	};

	public SynchronousScriptsLoader() {
		scriptInjectorWrapper = new ScriptInjectorWrapper();
		urlConverter = new UrlConverter();
		scripts = new Stack<>();
		Collections.addAll(scripts, ScriptsList.values());
	}

	public void injectAll() {
		injectNext();
	}

	private void injectNext() {
		if (!scripts.isEmpty()) {
			String nextScript = scripts.pop().getUrl();
			injectScript(nextScript);
		}
	}

	private void injectScript(String script) {
		String correctUrl = urlConverter.getModuleRelativeUrl(script);
		scriptInjectorWrapper.fromUrl(correctUrl, callback);
	}
}
