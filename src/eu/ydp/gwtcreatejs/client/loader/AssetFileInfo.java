package eu.ydp.gwtcreatejs.client.loader;

import com.google.gwt.xml.client.Element;

public class AssetFileInfo {
	
	public static final String IMAGE = "image";
	
	public static final String SOUND = "sound";
	
	private final String source;
	
	private final String type;
	
	private final String id;
	
	public AssetFileInfo(Element element, String baseURL){
		source = baseURL.concat(element.getAttribute(Manifest.ATTR_SRC));
		type = element.getAttribute(Manifest.ATTR_TYPE);
		id = element.getAttribute(Manifest.ATTR_ID);
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
