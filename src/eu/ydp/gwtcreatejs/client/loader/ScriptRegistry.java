package eu.ydp.gwtcreatejs.client.loader;

import java.util.HashMap;
import java.util.Map;

public class ScriptRegistry {
	
	private static Map<String, Integer> registry = new HashMap<String, Integer>(); 
	
	public static void register(String scriptPath){
		int counter = 1;
		
		if(registry.containsKey(scriptPath)){
			counter = registry.get(scriptPath).intValue() + 1;
		}
		
		registry.put(scriptPath, Integer.valueOf(counter));
	}
	
	public static void unregister(String scriptPath){
		if(registry.containsKey(scriptPath)){
			int counter = registry.get(scriptPath).intValue();
			registry.put(scriptPath, Integer.valueOf(Math.max(0, counter--)));
		}
	}
	
	public static boolean isRegistered(String scriptPath){
		return (registry.containsKey(scriptPath) && registry.get(scriptPath).intValue() > 0);
	}
	
}
