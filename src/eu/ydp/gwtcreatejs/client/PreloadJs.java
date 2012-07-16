package eu.ydp.gwtcreatejs.client;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.gwtcreatejs.client.handler.CompleteHandler;
import eu.ydp.gwtcreatejs.client.handler.FileLoadHandler;
import eu.ydp.gwtcreatejs.client.loader.AssetFileInfo;

public class PreloadJs extends JavaScriptObject {
	
	protected PreloadJs(){}
	
	public static native PreloadJs create(boolean useXHR2)/*-{
		return new $wnd.PreloadJS(useXHR2);
	}-*/;
	
	public static PreloadJs create(){
		return create(false);
	}
	
	public final void loadFile(String path){
		loadFile(path, false);
	}
	
	public final void loadManifest(List<AssetFileInfo> manifest){
		for(int i = 0; i < manifest.size(); i++){
			AssetFileInfo info = manifest.get(i);
			loadAsset(info.getSource(), info.getType(), info.getId(), false);
		}
		
		load();
	}
	
	public final native void close()/*-{
		this.close();
	}-*/;
	
	public final native void loadAsset(String src, String type, String id, boolean loadNow)/*-{
		this.loadFile({"src":src, "type":type, "id":id}, loadNow);
	}-*/;
	
	public final native void loadFile(String path, boolean loadNow)/*-{
		this.loadFile(path, loadNow);
	}-*/;
	
	public final native void load()/*-{
		this.load();
	}-*/;
	
	public final native void addCompleteHandler(CompleteHandler handler)/*-{
		this.onComplete = function(event){
			handler.@eu.ydp.gwtcreatejs.client.handler.CompleteHandler::onComplete()();
		}
	}-*/;
	
	public final native void addFileLoadHandler(FileLoadHandler handler)/*-{
		this.onFileLoad = function(event){
			handler.@eu.ydp.gwtcreatejs.client.handler.FileLoadHandler::onFileLoad(Leu/ydp/gwtcreatejs/client/event/FileLoadEvent;)(event);
		}
	}-*/;
}
