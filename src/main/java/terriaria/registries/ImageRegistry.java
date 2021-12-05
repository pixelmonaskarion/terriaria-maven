package terriaria.registries;

import java.awt.Image;
import java.util.HashMap;
import java.util.Set;


public class ImageRegistry {
	private HashMap<String, Image> registy = new HashMap<String, Image>();
	public void registerImage(Image image, String name) {
		if (!registy.containsKey(name)) {
			registy.put(name, image);
		}
	}
	public Image getImage(String name) {
		return registy.get(name);
	}
	public Set<String> getAll() {
		return registy.keySet();
	}
}
