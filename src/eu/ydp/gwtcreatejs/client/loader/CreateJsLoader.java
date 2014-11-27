package eu.ydp.gwtcreatejs.client.loader;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
import eu.ydp.gwtcreatejs.client.handler.ManifestLoadHandler;
import eu.ydp.gwtcreatejs.client.handler.ManifestLoadHandlers;
import eu.ydp.jsfilerequest.client.FileRequest;
import eu.ydp.jsfilerequest.client.FileRequestCallback;
import eu.ydp.jsfilerequest.client.FileRequestException;
import eu.ydp.jsfilerequest.client.FileResponse;

public class CreateJsLoader {

	private Manifest manifest;

	private final ManifestLoadHandlers manifestLoadHandlers = new ManifestLoadHandlers();

	private int injectCounter;

	private CompleteHandler completeHandler;

	private CreateJsContent content;

	private String libraryURL;

	private int scriptsNumber;

	public CreateJsLoader(){
		initializeSound();
	}

	public void setLibraryURL(String libraryURL) {
		this.libraryURL = libraryURL;
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

	public void addManifestLoadHandler(ManifestLoadHandler handler){
		manifestLoadHandlers.addManifestLoadHandler(handler);
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
		manifest = new Manifest(document, baseURL, libraryURL);
		manifestLoadHandlers.callAllHandlers(manifest);
		List<ScriptFile> scripts = getScriptsSorted(getAllScriptList());
		injectScripts(scripts);
	}

	private List<ScriptFile> getAllScriptList(){
		List<ScriptFile> scriptList = new ArrayList<ScriptFile>();

		appendScripts(scriptList);
		appendLibraries(scriptList);

		return scriptList;
	}

	private void appendScripts(List<ScriptFile> list){
		for (ScriptFile script : manifest.getScripts()) {
			list.add(script);
		}
	}

	private void appendLibraries(List<ScriptFile> list){
		for (LibraryInfo libraryInfo : manifest.getLibraryInfos()) {

			if(!libraryInfo.getFiles().isEmpty()){
				initalizeLibrary(manifest.getPackageName(),
									libraryInfo.getPackageName(),
									libraryInfo.getNamespace());
			}

			for (ScriptFile librarySrc : libraryInfo.getFiles()) {
				list.add(librarySrc);
			}
		}
	}

	private void injectScripts(List<ScriptFile> scripts) {
		scriptsNumber = scripts.size();
		injectCounter = 0;

		for (ScriptFile scriptFile : scripts) {
			injectScriptFile(scriptFile.getPath());
		}
	}

	/**
	 * @param scripts
	 * @return
	 */
	private List<ScriptFile> getScriptsSorted(List<ScriptFile> scripts) {
		ScriptFileSortingUtil sortingUtil = new ScriptFileSortingUtil();
		return sortingUtil.sort(scripts);
	}

	private void injectScriptFile(final String path) {
		if (ScriptRegistry.isRegistered(path)) {
			onScriptInjectionSuccess();
		} else {
			ScriptInjector.fromUrl(path).setWindow(ScriptInjector.TOP_WINDOW).setCallback(
				new Callback<Void, Exception>() {
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

		if(injectCounter == scriptsNumber){
			initializeResource();
		}
	}

	private void initializeResource(){
		PreloadJs preload = PreloadJs.create();
		preload.addCompleteHandler(new PreloadCompleteHandler());
		preload.addFileLoadHandler(new PreloadFileLoadHandler());
		try {
			preload.loadManifest(manifest.getAssetInfos());
		} catch (NullPointerException ex) {
			Logger.getLogger(getClass().getName()).info(ex.getMessage());
			// - required only in production compilation (aggressive compile)
			// - this exception should never occured
			// - without try and catch block manifest is null and nobody knows
			// why.
			// task YPUB-6703
		}
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

	private final native void initalizeLibrary(String packageName, String libraryName, String libraryNamespace)/*-{
		$wnd[packageName] = $wnd[packageName] || {};
		$wnd[libraryNamespace] = $wnd[libraryNamespace] || {};
		$wnd[packageName][libraryName] = $wnd[packageName][libraryName] || {};
		$wnd[packageName][libraryName] = $wnd[libraryNamespace];

	}-*/;

	private final native void initializeSound()/*-{
		if ($wnd.playSound == undefined) {
			$wnd.playSound = function(name, loop) {
				$wnd.SoundJS.play(name, $wnd.SoundJS.INTERRUPT_EARLY, 0, 0,
						loop);
			}
		}
	}-*/;

	private final native void addImage(JavaScriptObject image, String id, String packageName)/*-{
		$wnd.images = $wnd.images || {};
		$wnd.images[packageName] = $wnd.images[packageName] || {};
		$wnd.images[packageName][id] = image;
	}-*/;

}