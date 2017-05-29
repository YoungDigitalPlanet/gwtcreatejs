package eu.ydp.gwtcreatejs.client.scripts;

import com.google.gwt.core.client.GWT;

public class UrlConverter {

    public String getModuleRelativeUrl(String url) {
        return GWT.getModuleBaseURL() + url;
    }
}
