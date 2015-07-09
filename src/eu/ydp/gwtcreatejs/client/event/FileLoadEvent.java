package eu.ydp.gwtcreatejs.client.event;

import com.google.gwt.core.client.JavaScriptObject;

public class FileLoadEvent extends JavaScriptObject {

    protected FileLoadEvent() {
    }

    ; // NOPMD by MKaldonek on 25.07.12 14:17

    public final native String getId()/*-{
        return this.id;
    }-*/;

    public final native String getType()/*-{
        return this.type;
    }-*/;

    public final native JavaScriptObject getResult()/*-{
        return this.result;
    }-*/;

    public final native JavaScriptObject getData()/*-{
        return this.data;
    }-*/;

    public final native String getSource()/*-{
        return this.src;
    }-*/;

}
