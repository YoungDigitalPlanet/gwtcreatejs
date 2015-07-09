package eu.ydp.gwtcreatejs.client.loader;

import java.util.ArrayList;
import java.util.List;

public class LibraryInfo {

    private String version;

    private String packageName;

    private List<ScriptFile> files;

    private String baseURL;

    public void initialize(String version, String packageName, String baseURL) {
        this.version = version.replaceAll("\\.", "_");
        this.packageName = packageName;
        this.baseURL = baseURL;
        this.files = new ArrayList<ScriptFile>();
    }

    public String getVersion() {
        return version;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getNamespace() {
        return packageName + "_" + version;
    }

    public void addFile(ScriptFile file) {
        file.setPath(baseURL + getNamespace() + "/" + file.getPath());
        files.add(file);
    }

    public List<ScriptFile> getFiles() {
        return files;
    }

}
