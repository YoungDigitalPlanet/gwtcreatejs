package eu.ydp.gwtcreatejs.client.handler;

import eu.ydp.gwtcreatejs.client.loader.Manifest;

import java.util.ArrayList;
import java.util.List;

public class ManifestLoadHandlers {
    private final List<ManifestLoadHandler> manifestLoadHandlers = new ArrayList<ManifestLoadHandler>();

    public void addManifestLoadHandler(ManifestLoadHandler handler) {
        manifestLoadHandlers.add(handler);
    }

    public void callAllHandlers(Manifest manifest) {
        for (ManifestLoadHandler handler : manifestLoadHandlers) {
            handler.onManifestLoad(manifest);
        }
    }
}
