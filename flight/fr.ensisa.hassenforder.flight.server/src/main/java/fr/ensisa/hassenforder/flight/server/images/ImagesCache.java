package fr.ensisa.hassenforder.flight.server.images;

import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

public class ImagesCache {

	static private String extension = "png";
	
	private TreeMap<String, byte []> cache = null;
	private Object lock = new Object();
	
	private String buildResourceName (String name) {
		StringBuilder tmp = new StringBuilder();
		tmp.append(name);
		tmp.append(".");
		tmp.append(extension);
		return tmp.toString();
	}

	private byte [] loadFromResource (String name) {
		byte [] content = null;
		InputStream in = getClass().getResourceAsStream(name);
		try { content = in.readAllBytes(); } catch (Exception e) { }
		try { in.close(); } catch (Exception e) { }
		return content;
	}

	private Map<String, byte[]> getCache() {
		if (cache == null) cache = new TreeMap<String, byte[]>();
		return cache;
	}

	private byte[] getImageFromCache (String name) {
		synchronized(lock) {
			return getCache().get(name);
		}
	}
	
	private void putImageInCache (String name, byte[] image) {
		synchronized(lock) {
			getCache().put(name, image);
		}
	}
	
	private byte[] getImageByName (String name) {
		byte [] content = null;
		if (content == null) content = getImageFromCache(name);
		if (content == null) {
			if (content == null) content = loadFromResource(buildResourceName(name));
			if (content == null) content = loadFromResource(buildResourceName("null"));
			if (content != null) putImageInCache(name, content);
		}
		return content;
    }

    public byte[] getImage (String name) {
    	if (name == null) return getImageByName("null");
    	return getImageByName(name.toLowerCase().replace(" ", "-"));
    }

}
