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
	
	private static final String TAG_LIBRARIES = "libraries";
	
	private static final String TAG_LIBRARY = "library";
	
	private static final String TAG_LIBRARYFILE = "libraryfile";
	
	public static final String ATTR_VALUE = "value";
	
	public static final String ATTR_SRC = "src";
	
	public static final String ATTR_TYPE = "type";
	
	public static final String ATTR_ID = "id";
	
	public static final String ATTR_NAME = "name";
	
	public static final String ATTR_PACKAGE = "package";
	
	public static final String ATTR_VERSION = "version";
	
	private static final String ENGINE_SUFFIX = "Engine";
	
	private double width;
	
	private double height;
	
	private String className;
	
	private String packageName;
	
	private List<String> scripts;
	
	private List<AssetFileInfo> assetInfos;
	
	private String engineClassName;
	
	private List<LibraryInfo> libraryInfos;
	
	public Manifest(Document document, String baseURL, String libraryURL){
		parseDocument(document, baseURL, libraryURL);
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
	
	public List<LibraryInfo> getLibraryInfos(){
		return libraryInfos;
	}
	
	public List<AssetFileInfo> getAssetInfos(String type){
		List<AssetFileInfo> assets = new ArrayList<AssetFileInfo>();
		
		for(AssetFileInfo info: assetInfos){
			if(type.equals(info.getType())){
				assets.add(info);
			}
		}
		
		return assets;
	}
	
	public boolean hasEngineClass(){
		return getEngineClassName() != null;
	}
	
	private void parseDocument(Document document, String baseURL, String libraryURL){
		Element element = document.getDocumentElement();
		
		parseProperties(getElementByTagName(element, TAG_PROPERTIES));
		parseClass(getElementByTagName(element, TAG_CLASS));
		parseAssetFiles(getElementByTagName(element, TAG_ASSETS), baseURL);
		parseScriptFiles(getElementByTagName(element, TAG_SCRIPTS), baseURL);
		parseLibraries(getElementByTagName(element, TAG_LIBRARIES), libraryURL);
	}
	
	private void parseClass(Element element){
		className = element.getAttribute(ATTR_NAME);
		packageName = element.getAttribute(ATTR_PACKAGE);
	}
	
	private void parseLibraries(Element element, String libraryURL){
		libraryInfos = new ArrayList<LibraryInfo>();
		
		if(element != null){
			NodeList libraryNodes = element.getElementsByTagName(TAG_LIBRARY);
			
			for (int i = 0; i < libraryNodes.getLength(); i++) {
				Element libraryNode = (Element) libraryNodes.item(i);
				String libraryVersion = libraryNode.getAttribute(ATTR_VERSION);
				String libraryPackage = libraryNode.getAttribute(ATTR_PACKAGE);
				LibraryInfo libraryInfo = new LibraryInfo(libraryVersion, libraryPackage, libraryURL);
				
				collectLibraryFiles(libraryNode, libraryInfo);
				libraryInfos.add(libraryInfo);
			}
		}
	}
	
	private void collectLibraryFiles(Element element, LibraryInfo libraryInfo){
		NodeList libraryFileNodes = element.getElementsByTagName(TAG_LIBRARYFILE);
		
		for (int i = 0; i < libraryFileNodes.getLength(); i++) {
			Element libraryFileNode = (Element) libraryFileNodes.item(i);
			String src = libraryFileNode.getAttribute(ATTR_SRC);
			
			libraryInfo.addFilePath(src);
		}
	}
	
	private void parseProperties(Element element){
		Element widthNode = getElementByTagName(element, TAG_WIDTH);
		Element heightNode = getElementByTagName(element, TAG_HEIGHT);
		
		width = Double.valueOf(widthNode.getAttribute(ATTR_VALUE));
		height = Double.valueOf(heightNode.getAttribute(ATTR_VALUE));
	}
	
	private void parseScriptFiles(Element element, String baseURL){
		scripts = new ArrayList<String>();
		
		if(element != null){
			NodeList scriptNodes = element.getElementsByTagName(TAG_SCRIPTFILE);
			
			for(int i = 0; i < scriptNodes.getLength(); i++){
				String scriptSrc = ((Element)scriptNodes.item(i)).getAttribute(ATTR_SRC);
				scripts.add(baseURL.concat(scriptSrc));
			}
		}
	}
	
	private void parseAssetFiles(Element element, String baseURL){
		assetInfos = new ArrayList<AssetFileInfo>();
		
		if(element != null){
			NodeList assetNodes = element.getElementsByTagName(TAG_ASSETFILE);
			
			for(int i = 0; i < assetNodes.getLength(); i++){
				AssetFileInfo info = new AssetFileInfo((Element)assetNodes.item(i), baseURL); // NOPMD by MKaldonek on 25.07.12 14:21
				assetInfos.add(info);
			}
		}
	}
	
	private Element getElementByTagName(Element parent, String childTagName){
		Element searchedElement = null;
		NodeList nodeList = parent.getElementsByTagName(childTagName);
		
		if(nodeList.getLength() > 0){
			searchedElement = (Element)nodeList.item(0);
		}
		
		return searchedElement;
	}
	
}
