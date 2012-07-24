package eu.ydp.gwtcreatejs.client.loader;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.gwtcreatejs.client.Stage;
import eu.ydp.gwtcreatejs.client.Ticker;

public class CreateJsContent {
	
	private Canvas canvas;
	
	public CreateJsContent(Manifest manifest){
		create(manifest);
	}
	
	public void create(Manifest manifest){
		canvas = Canvas.createIfSupported();
		
		if(canvas != null){
			canvas.getElement().setAttribute("width", String.valueOf(manifest.getWidth()));
			canvas.getElement().setAttribute("height", String.valueOf(manifest.getHeight()));
			
			initializeResource(manifest);
		}
	}
	
	public Canvas getCanvas(){
		return canvas;
	}
	
	private void initializeResource(Manifest manifest){
		JavaScriptObject engine = null;
		Stage stage = Stage.create(canvas.getCanvasElement());
		JavaScriptObject resource = getResourceObject(manifest.getPackageName(), manifest.getClassName()); 
		
		stage.addChild(resource);
		stage.update();
		
		if(manifest.hasEngineClass()){
			engine = initializeResourceEngine(resource, stage, manifest.getPackageName(), manifest.getEngineClassName());
		}
		setTicker((engine == null)? stage: engine);
	}
	
	private void setTicker(JavaScriptObject jso){
		Ticker.setFPS(25);
		Ticker.addListener(jso);
	}
	
	private final native JavaScriptObject initializeResourceEngine(JavaScriptObject resource, JavaScriptObject stage, String packageName, String className)/*-{
		return new $wnd[packageName][className](resource, stage);
	}-*/;
	
	private final native JavaScriptObject getResourceObject(String packageName, String className)/*-{
		return new $wnd[packageName][className]();
	}-*/;
	
}
