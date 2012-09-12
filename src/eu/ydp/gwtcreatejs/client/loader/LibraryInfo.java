package eu.ydp.gwtcreatejs.client.loader;

import java.util.ArrayList;
import java.util.List;

public class LibraryInfo {

	private final String version;

	private final String packageName;
	
	private final List<String> files;
	
	private final String baseURL;

	public LibraryInfo(String version, String packageName, String baseURL){
		this.version = version.replaceAll("\\.",	"_");
		this.packageName = packageName;
		this.baseURL = baseURL;
		this.files = new ArrayList<String>();
	}
	
	public String getVersion() {
		return version;
	}

	public String getPackageName() {
		return packageName;
	}
	
	public String getNamespace(){
		return packageName + "_" + version;
	}
	
	public void addFilePath(String path){
		files.add(baseURL + getNamespace() + "/" + path);
	}
	
	public List<String> getFiles() {
		return files;
	}
	
}
