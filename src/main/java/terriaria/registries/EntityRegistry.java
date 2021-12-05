package terriaria.registries;

import java.util.HashMap;

import terriaria.entities.Entity;

public class EntityRegistry {
	private HashMap<String, Class<? extends Entity>> registy = new HashMap<String, Class<? extends Entity>>();
	public void registerEntity(Class<? extends Entity> entity, String name) {
		if (!registy.containsKey(name)) {
			registy.put(name, entity);
		}
	}
	public Class<? extends Entity> getEntity(String name) {
		return registy.get(name);
	}
	public String getName(Class<? extends Entity> entity) {
		for (String key : registy.keySet()) {
			if (registy.get(key) == entity) {
				return key;
			}
		}
		return null;
	}
}
