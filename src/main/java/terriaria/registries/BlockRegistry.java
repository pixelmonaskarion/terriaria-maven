package terriaria.registries;

import java.util.HashMap;

import terriaria.blocks.Block;

public class BlockRegistry {
	private HashMap<String, Class<? extends Block>> registy = new HashMap<String, Class<? extends Block>>();
	public void registerBlock(Class<? extends Block> block, String name) {
		if (!registy.containsKey(name)) {
			registy.put(name, block);
		}
	}
	public Class<? extends Block> getBlock(String name) {
		return registy.get(name);
	}
	public String getName(Class<? extends Block> block) {
		for (String key : registy.keySet()) {
			if (registy.get(key) == block) {
				return key;
			}
		}
		return null;
	}
}
