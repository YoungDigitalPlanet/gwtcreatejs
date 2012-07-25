package eu.ydp.gwtcreatejs.client.loader;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.gwtcreatejs.client.Stage;
import eu.ydp.gwtcreatejs.client.Ticker;

public class CreateJsContent {
	
	private Canvas canvas;
	
	private JavaScriptObject resource;
	
	private JavaScriptObject engine;
	
	private Stage stage;
	
	private Manifest manifest;
	
	public CreateJsContent(Manifest manifest){
		create(manifest);
	}
	
	public void create(Manifest mf){
		canvas = Canvas.createIfSupported();
		manifest = mf;
		
		if(canvas != null){
			canvas.getElement().setAttribute("width", String.valueOf(manifest.getWidth()));
			canvas.getElement().setAttribute("height", String.valueOf(manifest.getHeight()));
			
			initializeResource();
		}
	}
	
	public Canvas getCanvas(){
		return canvas;
	}
	
	public void destroy(){
		Ticker.removeListener(getTickerListener());
	}
	
	private void initializeResource(){
		stage = Stage.create(canvas.getCanvasElement());
		resource = getResourceObject(manifest.getPackageName(), manifest.getClassName()); 
		
		stage.addChild(resource);
		stage.update();
		
		if(manifest.hasEngineClass()){
			engine = initializeResourceEngine(resource, stage, manifest.getPackageName(), manifest.getEngineClassName());
		}
		
		initializeTicker(getTickerListener());
	}
	
	private JavaScriptObject getTickerListener(){		
		return (engine == null)? stage: engine;
	}
	
	private void initializeTicker(JavaScriptObject listener){
		Ticker.setFPS(25);
		Ticker.addListener(listener);
	}
	
	private final native JavaScriptObject initializeResourceEngine(JavaScriptObject resource, JavaScriptObject stage, String packageName, String className)/*-{
		return new $wnd[packageName][className](resource, stage);
	}-*/;
	
	private final native JavaScriptObject getResourceObject(String packageName, String className)/*-{
		return new $wnd[packageName][className]();
	}-*/;
	
}
