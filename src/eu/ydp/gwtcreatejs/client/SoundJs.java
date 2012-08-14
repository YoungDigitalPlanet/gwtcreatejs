package eu.ydp.gwtcreatejs.client;

import com.google.gwt.core.client.JavaScriptObject;

public class SoundJs extends JavaScriptObject {
	
	protected SoundJs(){}
	
	public static native void stop(String soundId)/*-{
		$wnd.SoundJS.stop(soundId);
	}-*/;
	
}
