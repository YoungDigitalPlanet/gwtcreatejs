package eu.ydp.gwtcreatejs.client.loader;

import java.util.List;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;

import eu.ydp.gwtcreatejs.client.PreloadJs;
import eu.ydp.gwtcreatejs.client.event.FileLoadEvent;
import eu.ydp.gwtcreatejs.client.handler.CompleteHandler;
import eu.ydp.gwtcreatejs.client.handler.FileLoadHandler;
import eu.ydp.jsfilerequest.client.FileRequest;
import eu.ydp.jsfilerequest.client.FileRequestCallback;
import eu.ydp.jsfilerequest.client.FileRequestException;
import eu.ydp.jsfilerequest.client.FileResponse;

public class CreateJsLoader {
	
	private Manifest manifest;
	
	private int injectCounter;
	
	private CompleteHandler completeHandler;
	
	private CreateJsContent content;
	
	public CreateJsLoader(){
		initializeSound();
	}
	
	public void load(String path){
		FileRequest request = GWT.create(FileRequest.class);
		request.setUrl(path);
		
		try{
			request.send("", new FileRequestCallback() {
				
				@Override
				public void onResponseReceived(FileRequest request, FileResponse response) {
					Document manifestDoc = XMLParser.parse(response.getText());	
					String baseURL = request.getUrl();
					
					baseURL = baseURL.substring(0, baseURL.lastIndexOf('/') + 1);
					onMainifestLoad(manifestDoc, baseURL);
				}
				
				@Override
				public void onError(FileRequest request, Throwable exception) {
									
				}
			});
		}catch(FileRequestException exception){
			
		}
	}
	
	public void addCompleteHandler(CompleteHandler handler){
		completeHandler = handler;
	}
	
	public CreateJsContent getContent(){
		return content;
	}
	
	public void unload(){
		if(content != null){
			content.destroy();
		}
	}
	
	public void stopSounds(){
		if(content != null){
			content.stopAllSounds();
		}
	}
	
	private void onMainifestLoad(Document document, String baseURL){
		manifest = new Manifest(document, baseURL);
		injectScripts(manifest.getScripts());
	}
	
	private void injectScripts(List<String> scripts){
		injectCounter = 0;
		
		for(int i = 0; i < scripts.size(); i++){
			injectScriptFile(scripts.get(i));
		}
	}
	
	private void injectScriptFile(final String path){
		if(ScriptRegistry.isRegistered(path)){
			onScriptInjectionSuccess();
		}else{
			ScriptInjector.fromUrl(path).setWindow(ScriptInjector.TOP_WINDOW).
			setCallback(new Callback<Void, Exception>() {
			
				@Override
				public void onSuccess(Void result) {
					ScriptRegistry.register(path);
					onScriptInjectionSuccess();					
				}
				
				@Override
				public void onFailure(Exception reason) {
									
				}
			}).inject();
		}
	}
	
	private void onScriptInjectionSuccess(){
		injectCounter++;
		
		if(injectCounter == manifest.getScripts().size()){
			initializeResource();
		}
	}
	
	private void initializeResource(){
		PreloadJs preload = PreloadJs.create();
		preload.addCompleteHandler(new PreloadCompleteHandler());
		preload.addFileLoadHandler(new PreloadFileLoadHandler());
		preload.loadManifest(manifest.getAssetInfos());
	}
	
	private class PreloadCompleteHandler implements CompleteHandler{

		@Override
		public void onComplete() {
			content = new CreateJsContent(manifest);
			completeHandler.onComplete();
		}
	}
	
	private class PreloadFileLoadHandler implements FileLoadHandler{

		@Override
		public void onFileLoad(FileLoadEvent event) {
			addImage(event.getResult(), event.getId(), manifest.getPackageName());
		}
	}
	
	private final native void initializeSound()/*-{
		if($wnd.playSound == undefined){
			$wnd.playSound = function (name, loop) {
				$wnd.SoundJS.play(name, $wnd.SoundJS.INTERRUPT_EARLY, 0, 0, loop);
			}
		}
	}-*/;
	
	private final native void addImage(JavaScriptObject image, String id, String packageName)/*-{
		if($wnd.images == undefined)
			$wnd.images = {};	
		if($wnd.images[packageName] == undefined)
			$wnd.images[packageName] = {};
		$wnd.images[packageName][id] = image;
	}-*/;
	
}