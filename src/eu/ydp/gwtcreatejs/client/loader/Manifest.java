package eu.ydp.gwtcreatejs.client.loader;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

public class Manifest {
	
	public static final String TAG_PROPERTIES = "properties";
	
	public static final String TAG_WIDTH = "width";
	
	public static final String TAG_CLASS = "class";
	
	public static final String TAG_SCRIPTS = "scripts";
	
	public static final String TAG_ASSETS = "assets";
	
	public static final String TAG_HEIGHT = "height";
	
	public static final String TAG_SCRIPTFILE = "scriptfile";
	
	public static final String TAG_ASSETFILE = "assetfile";
	
	public static final String ATTR_VALUE = "value";
	
	public static final String ATTR_SRC = "src";
	
	public static final String ATTR_TYPE = "type";
	
	public static final String ATTR_ID = "id";
	
	public static final String ATTR_NAME = "name";
	
	public static final String ATTR_PACKAGE = "package";
	
	private static final String ENGINE_SUFFIX = "Engine";
	
	private double width;
	
	private double height;
	
	private String className;
	
	private String packageName;
	
	private List<String> scripts;
	
	private List<AssetFileInfo> assetInfos;
	
	private String engineClassName;
	
	public Manifest(Document document, String baseURL){
		parseDocument(document, baseURL);
	}
	
	public double getWidth(){
		return width;
	}
	
	public double getHeight(){
		return height;
	}
	
	public String getClassName(){
		return className;
	}
	
	public String getEngineClassName(){
		if(engineClassName == null){
			String searchedName = className.concat(ENGINE_SUFFIX);
			
			for(String script: scripts){
				if(script.indexOf(searchedName.concat(".js")) >= 0){
					engineClassName = searchedName;
					break;
				}
			}
		}
		
		return engineClassName; 
	}
	
	public String getPackageName(){
		return packageName;
	}
	
	public List<String> getScripts(){
		return scripts;
	}
	
	public List<AssetFileInfo> getAssetInfos(){
		return assetInfos;
	}
	
	public boolean hasEngineClass(){
		return getEngineClassName() != null;
	}
	
	private void parseDocument(Document document, String baseURL){
		Element element = document.getDocumentElement();
		
		parseProperties(getElementByTagName(element, TAG_PROPERTIES));
		parseClass(getElementByTagName(element, TAG_CLASS));
		parseAssetFiles(getElementByTagName(element, TAG_ASSETS), baseURL);
		parseScriptFiles(getElementByTagName(element, TAG_SCRIPTS), baseURL);
	}
	
	private void parseClass(Element element){
		className = element.getAttribute(ATTR_NAME);
		packageName = element.getAttribute(ATTR_PACKAGE);
	}
	
	private void parseProperties(Element element){
		Element widthNode = getElementByTagName(element, TAG_WIDTH);
		Element heightNode = getElementByTagName(element, TAG_HEIGHT);
		
		width = Double.valueOf(widthNode.getAttribute(ATTR_VALUE));
		height = Double.valueOf(heightNode.getAttribute(ATTR_VALUE));
	}
	
	private void parseScriptFiles(Element element, String baseURL){
		scripts = new ArrayList<String>();
		NodeList scriptNodes = element.getElementsByTagName(TAG_SCRIPTFILE);
		
		for(int i = 0; i < scriptNodes.getLength(); i++){
			String scriptSrc = ((Element)scriptNodes.item(i)).getAttribute(ATTR_SRC);
			scripts.add(baseURL.concat(scriptSrc));
		}
	}
	
	private void parseAssetFiles(Element element, String baseURL){
		assetInfos = new ArrayList<AssetFileInfo>();
		NodeList assetNodes = element.getElementsByTagName(TAG_ASSETFILE);
		
		for(int i = 0; i < assetNodes.getLength(); i++){
			AssetFileInfo info = new AssetFileInfo((Element)assetNodes.item(i), baseURL);
			assetInfos.add(info);
		}
	}
	
	private Element getElementByTagName(Element parent, String childTagName){
		Element searchedElement = null;
		NodeList nodeList = parent.getElementsByTagName(childTagName);
		
		if(nodeList.getLength() > 0)
			searchedElement = (Element)nodeList.item(0);
		
		return searchedElement;
	}
	
}
