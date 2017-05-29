package eu.ydp.gwtcreatejs.client;

import com.google.gwt.core.client.JavaScriptObject;

public class Ticker extends JavaScriptObject { // NOPMD by MKaldonek on 25.07.12 14:28

    protected Ticker() {
    }

    ; // NOPMD by MKaldonek on 25.07.12 14:28

    public static native void setFPS(int fps)/*-{
        $wnd.Ticker.setFPS(fps);
    }-*/;

    public static native void addListener(JavaScriptObject listener)/*-{
        $wnd.Ticker.addListener(listener);
    }-*/;

    public static native void removeListener(JavaScriptObject listener)/*-{
        $wnd.Ticker.removeListener(listener);
    }-*/;

}
