package eu.ydp.gwtcreatejs.client.loader;

import java.util.List;

import org.junit.Before;

import com.google.gwt.xml.client.Document;

import eu.ydp.gwtutil.xml.XMLParser;

public class ManifestFileJUnitTestBase {
	
	protected static final String LIBRARY_FILE_1 = "cc__ButtonDecorator.js";
	protected static final String LIBRARY_FILE_2 = "Delegate.js";
	protected static final String LIBRARY_FILE_3 = "Logger.js";
	protected static final String LIBRARY_FILE_4 = "aa__Slider.js";
	protected static final int LIBRARY_FILE_ORDER_2 = 3;
	protected static final String LIBRARY_PACKAGE = "ydpjs";
	protected static final String MANIFEST_CLASS_NAME = "mcc_ma_up_07_03_06s";
	protected static final String PACKAGE = "mccmaup070306s";
	protected static final String SCRIPT_FILE_NAME_1 = "bb__mcc_ma_up_07_03_06aaa.js";
	protected static final String SCRIPT_FILE_NAME_2 = "bb__mcc_ma_up_07_03_06bbb.js";
	protected static final String SCRIPT_FILE_NAME_3 = "aa__mcc_ma_up_07_03_06bbb.js";
	protected static final String SCRIPT_FILE_NAME_4 = "aa__mcc_ma_up_07_03_06aaa.js";
	protected static final int SCRIPT_FILE_ORDER_1 = 1;
	protected static final int SCRIPT_FILE_ORDER_2 = 3;
	protected static final int SCRIPT_FILE_ORDER_3 = 2;
	protected static final int SCRIPT_FILE_ORDER_4 = 3;
	
	protected List<ScriptFile> scripts;
	protected List<LibraryInfo> libraryInfos;
	protected Manifest manifest;
	
	private String getManifestFileContent() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<manifest><class name=\""+MANIFEST_CLASS_NAME+"\" package=\""+PACKAGE+"\" /><properties><width value=\"460\" /><height value=\"370\" /></properties>" +
					"<scripts>" +
					"<scriptfile src=\""+SCRIPT_FILE_NAME_1+"\" order=\""+SCRIPT_FILE_ORDER_1+"\" /><scriptfile src=\""+SCRIPT_FILE_NAME_2+"\" order=\""+SCRIPT_FILE_ORDER_2+"\" /><scriptfile src=\""+SCRIPT_FILE_NAME_3+"\" order=\""+SCRIPT_FILE_ORDER_3+"\" /><scriptfile src=\""+SCRIPT_FILE_NAME_4+"\" order=\""+SCRIPT_FILE_ORDER_4+"\" />" +
					"</scripts>" +
					"<assets><assetfile src=\"aa__sounds/pm4_12_04.mp3\" type=\"sound\" id=\"pm4_12_04\"/></assets>" +
					"<libraries>" +
					"<library version=\"1.0.0.0\" package=\""+LIBRARY_PACKAGE+"\"><libraryfile src=\""+LIBRARY_FILE_1+"\"/><libraryfile src=\""+LIBRARY_FILE_2+"\" order=\""+LIBRARY_FILE_ORDER_2+"\" /><libraryfile src=\""+LIBRARY_FILE_3+"\"/><libraryfile src=\""+LIBRARY_FILE_4+"\"/></library></libraries>" +
				"</manifest>";
	}
	
	@Before
	public void prepare() {
		Document manifestDoc = XMLParser.parse(getManifestFileContent());
		manifest = new Manifest(manifestDoc, "/mu", "/lu");		
		libraryInfos = manifest.getLibraryInfos();
		scripts = manifest.getScripts();
	}
}
