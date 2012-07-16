package eu.ydp.gwtcreatejs.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.CanvasElement;

public class Stage extends JavaScriptObject {
	
	protected Stage(){}
	
	public static native Stage create(CanvasElement canvas)/*-{
		return new $wnd.Stage(canvas);
	}-*/;
	
	public final native void addChild(JavaScriptObject displayObject)/*-{
		this.addChild(displayObject);
	}-*/;
	
	public final native void update()/*-{
		this.update();
	}-*/;
	
}
