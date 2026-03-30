package fr.ensisa.hassenforder.brutes.client.model;

import java.util.Map;
import java.util.TreeMap;

import javafx.scene.image.Image;

public class ImagePool {

	static private ImagePool INSTANCE = null;
	private Map<Long, Image> pool;

	public ImagePool() {
		super();
	}

	public static ImagePool getINSTANCE() {
		if (INSTANCE == null) INSTANCE = new ImagePool();
		return INSTANCE;
	}

	private Map<Long, Image> getPool() {
		if (pool == null) pool = new TreeMap<>();
		return pool;
	}

	private Image getImage (long id, double w, double h) {
		if (getPool().containsKey(id)) return getPool().get(id);
		Image image = new Image ("file:"+pictureName(id), w, h, true, true, true);
		getPool().put (id, image);
		return image;
	}

	public Image getCharacterImage (long id) {
		return getImage (id, 96.0, 128.0);
	}

	public Image getBonusImage (long id) {
		return getImage (id, 96.0, 128.0);
	}

	public static String pictureName(long picture) {
		StringBuilder name = new StringBuilder();
		name.append("images");
		name.append("/");
		name.append(picture);
		name.append(".");
		name.append("png");
		return name.toString();
	}

}
