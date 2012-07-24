package eu.ydp.gwtcreatejs.client;

import com.google.gwt.core.client.JavaScriptObject;

public class Ticker extends JavaScriptObject {
	
	protected Ticker(){};
	
	public static native void setFPS(int fps)/*-{
		$wnd.Ticker.setFPS(25);
	}-*/;
	
	public static native void addListener(JavaScriptObject listener)/*-{
		$wnd.Ticker.addListener(listener);
	}-*/;
	
}
