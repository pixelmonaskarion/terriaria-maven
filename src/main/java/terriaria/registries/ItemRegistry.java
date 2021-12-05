package terriaria.registries;

import java.util.HashMap;

import terriaria.items.Item;

public class ItemRegistry {
	private HashMap<String, Class<? extends Item>> registy = new HashMap<String, Class<? extends Item>>();
	public void registerItem(Class<? extends Item> item, String name) {
		if (!registy.containsKey(name)) {
			registy.put(name, item);
		}
	}
	public Class<? extends Item> getItem(String name) {
		return registy.get(name);
	}
	public String getName(Class<? extends Item> item) {
		for (String key : registy.keySet()) {
			if (registy.get(key) == item) {
				return key;
			}
		}
		return null;
	}
}
