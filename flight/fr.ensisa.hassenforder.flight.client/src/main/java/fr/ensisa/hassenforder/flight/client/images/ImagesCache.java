package fr.ensisa.hassenforder.flight.client.images;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import fr.ensisa.hassenforder.flight.client.model.SeatComfort;
import fr.ensisa.hassenforder.flight.client.network.ISession;
import fr.ensisa.hassenforder.io.FileHelper;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;

public class ImagesCache {

	static private String root = ".";
	static private String folder = "images";
	static private String extension = "png";
	
	private ISession session;

//	private ObservableMap<String, ObjectProperty<Image>> zz = FXCollections.observableMap(new TreeMap<String, ObjectProperty<Image>>());
	
	private TreeMap<String, ObjectProperty<Image>> cache = null;
	private Object cacheLock = new Object();
	private Lock dlLock = new ReentrantLock();
	
	public ImagesCache(ISession session) {
		this.session = session;
	}

	private String buildResourceName (String name) {
		StringBuilder tmp = new StringBuilder();
		tmp.append(name);
		tmp.append(".");
		tmp.append(extension);
		return getClass().getResource(tmp.toString()).toString();
	}

	private String buildFolderName () {
		StringBuilder tmp = new StringBuilder();
		tmp.append(root);
		tmp.append("/");
		tmp.append(folder);
		return tmp.toString();
	}

	private String buildFilename (String name) {
		StringBuilder tmp = new StringBuilder();
		tmp.append(root);
		tmp.append("/");
		tmp.append(folder);
		tmp.append("/");
		tmp.append(name);
		tmp.append(".");
		tmp.append(extension);
		return tmp.toString();
	}

	private Image createImageFromFile (String filename) {
		String uri = new File(filename).toURI().toString();
		Image image = new Image (uri);
		return image;
	}

	private Image createImageFromResource (String name) {
		Image image = new Image (name);
		return image;
	}

	private Map<String, ObjectProperty<Image>> getCache() {
		if (cache == null) cache = new TreeMap<String, ObjectProperty<Image>>();
		return cache;
	}

	private ObjectProperty<Image> getImageFromCache (String name) {
		synchronized(cacheLock) {
			return getCache().get(name);
		}
	}
	
	private void putImageInCache (String name, Image image) {
		synchronized(cacheLock) {
			ObjectProperty<Image> current = getCache().get(name);
			if (current == null) {
				getCache().put(name, new SimpleObjectProperty<Image>(image));
			} else {
				current.set(image);
			}
		}
	}
	
	private class Retriever implements Runnable {

		private String name;
		private String filename;
		
		public Retriever(String name, String filename) {
			super();
			this.name = name;
			this.filename = filename;
		}

		@Override
		public void run() {
			dlLock.lock();
			try {
				byte [] content = session.getImage(name);
				if (content != null) {
					FileHelper.writeContent(filename, content);
					putImageInCache(name, createImageFromFile(filename));
				} else {
					putImageInCache(name, createImageFromResource(buildResourceName("error")));
				}
			}
			catch (Exception e) {
			}
			finally {
				dlLock.unlock();
			}
		}
	}

	private ObjectProperty<Image> getImageByName (String name) {
    	if (getImageFromCache(name) == null) {
    		// need to find an image
			if (name.equals("null")) {
				// special case for null object so load error from the jar
				putImageInCache(name, createImageFromResource(buildResourceName("error")));
			} else {
				FileHelper.createFolder(buildFolderName());
				String filename = buildFilename(name);
				if (FileHelper.fileExists(filename)) {
					// image exists yet in the local file system
					putImageInCache(name, createImageFromFile(filename));
				} else {
					// image doesn't exist in the local file system
					// need to download it from server
					// real result is delayed but a place holder is put in place
					putImageInCache(name, createImageFromResource(buildResourceName("placeholder")));
					new Thread(new Retriever(name, filename)).start();
				}
			}
		}
        return getImageFromCache(name);
    }

    public ObjectProperty<Image> getImage (String name) {
    	if (name == null) return getImageByName("null");
    	return getImageByName(name.toLowerCase().replace(" ", "-"));
    }

    public ObjectProperty<Image> getImage (SeatComfort comfort) {
    	if (comfort == null) return getImageByName("null");
    	return getImageByName(comfort.name()+"-class");
    }

    public void setSession(ISession session) {
		this.session = session;
	}

}
