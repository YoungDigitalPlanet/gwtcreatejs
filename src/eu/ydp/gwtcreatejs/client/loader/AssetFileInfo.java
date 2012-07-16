package eu.ydp.gwtcreatejs.client.loader;

import com.google.gwt.xml.client.Element;

public class AssetFileInfo {
	
	public static final String IMAGE = "image";
	
	public static final String SOUND = "sound";
	
	private String source;
	
	private String type;
	
	private String id;
	
	public AssetFileInfo(Element element, String baseURL){
		source = element.getAttribute(Manifest.ATTR_SRC);
		type = element.getAttribute(Manifest.ATTR_TYPE);
		id = element.getAttribute(Manifest.ATTR_ID);
		
		source = baseURL.concat(source);
	}
	
	public String getSource(){
		return source;
	}
	
	public String getId(){
		return id;
	}
	
	public String getType(){
		return type;
	}
}
