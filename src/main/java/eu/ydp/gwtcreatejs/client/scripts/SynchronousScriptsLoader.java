package eu.ydp.gwtcreatejs.client.scripts;

import com.google.gwt.core.client.Callback;

import java.util.Stack;

public class SynchronousScriptsLoader {

    private final ScriptInjectorWrapper scriptInjectorWrapper;
    private final UrlConverter urlConverter;

    private final Stack<ScriptUrl> scripts;

    private final Callback<Void, Exception> callback = new Callback<Void, Exception>() {

        @Override
        public void onFailure(Exception arg0) {
        }

        @Override
        public void onSuccess(Void arg0) {
            injectNext();
        }
    };

    public SynchronousScriptsLoader(Stack<ScriptUrl> scripts) {
        this.scripts = scripts;
        scriptInjectorWrapper = new ScriptInjectorWrapper();
        urlConverter = new UrlConverter();
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
