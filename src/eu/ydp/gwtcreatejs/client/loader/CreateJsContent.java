package eu.ydp.gwtcreatejs.client.loader;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Element;

import eu.ydp.gwtcreatejs.client.SoundJs;
import eu.ydp.gwtcreatejs.client.Stage;
import eu.ydp.gwtcreatejs.client.Ticker;

public class CreateJsContent {

	private static final String WEBKIT_TAP_HIGHLIGHT_COLOR = "webkitTapHighlightColor";

	private static final String OUTLINE = "outline";

	private static final String HEIGHT = "height";

	private static final String WIDTH = "width";

	private final Canvas canvas;

	private JavaScriptObject resource;

	private JavaScriptObject engine;

	private Stage stage;

	private final Manifest manifest;

	public CreateJsContent(Manifest mnf){
		canvas = Canvas.createIfSupported();
		manifest = mnf;

		if(canvas != null){
			Element canvasElement = canvas.getElement();
			canvasElement.setAttribute(WIDTH, String.valueOf(manifest.getWidth()));
			canvasElement.setAttribute(HEIGHT, String.valueOf(manifest.getHeight()));
			canvasElement.getStyle().setProperty(OUTLINE, "none");
			canvasElement.getStyle().setProperty(WEBKIT_TAP_HIGHLIGHT_COLOR, "rgba(255, 255, 255, 0)");

			initializeResource();
		}
	}

	public Canvas getCanvas(){
		return canvas;
	}

	public JavaScriptObject getResource(){
		return resource;
	}

	public void destroy(){
		Ticker.removeListener(getTickerListener());
		stopAllSounds();
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

	public void stopAllSounds(){
		for(AssetFileInfo info: manifest.getAssetInfos(AssetFileInfo.SOUND)){
			SoundJs.stop(info.getId());
		}
	}

	private final native JavaScriptObject initializeResourceEngine(JavaScriptObject resource, JavaScriptObject stage, String packageName, String className)/*-{
		return new $wnd[packageName][className](resource, stage);
	}-*/;

	private final native JavaScriptObject getResourceObject(String packageName, String className)/*-{
		return new $wnd[packageName][className]();
	}-*/;

}
