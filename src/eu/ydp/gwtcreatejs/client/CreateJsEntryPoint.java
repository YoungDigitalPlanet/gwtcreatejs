package eu.ydp.gwtcreatejs.client;

import com.google.gwt.core.client.EntryPoint;
import eu.ydp.gwtcreatejs.client.scripts.*;

import java.util.Collections;
import java.util.Stack;

public class CreateJsEntryPoint implements EntryPoint {

    @Override
    public void onModuleLoad() {
        Stack<ScriptUrl> scriptsUrl = new Stack<>();
        Collections.addAll(scriptsUrl, ScriptsList.values());
        new SynchronousScriptsLoader(scriptsUrl).injectAll();
    }
}
