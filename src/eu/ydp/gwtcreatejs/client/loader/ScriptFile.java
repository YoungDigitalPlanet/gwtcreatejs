package eu.ydp.gwtcreatejs.client.loader;

import com.google.gwt.xml.client.Element;

public class ScriptFile {

	public final static Integer DEFAULT_ORDER = Integer.MAX_VALUE;
	
	private String path;
	private Integer order = DEFAULT_ORDER;

	public void initialize(Element libraryFileNode) {
		initialize(libraryFileNode, "");
	}
	
	public void initialize(Element libraryFileNode, String baseURL) {
		setPath(baseURL.concat(libraryFileNode.getAttribute(Manifest.ATTR_SRC)));
		String orderAttr = libraryFileNode.getAttribute(Manifest.ATTR_ORDER);		
		if (orderAttr != null && !orderAttr.isEmpty()) {
			setOrder(Integer.valueOf(orderAttr));
		}
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

}
