package terriaria.blocks;

import java.beans.ConstructorProperties;

import terriaria.Game;
import terriaria.World;
import terriaria.entities.Item;

public class CraftingTableBlock extends Block{
	public CraftingTableBlock(int x, int y, World world) {
		super(x, y, world);
		type = 10;
		loadTexture(10);
	}
	@ConstructorProperties({"x", "y"})
	public CraftingTableBlock(int x, int y) {
		super(x, y);
		type = 10;
		loadTexture(10);
	}
	@Override
	public void breakBlock() {
		world.entities.add(new Item((x*Game.blockSize)+Game.blockSize/4,(y*Game.blockSize)+Game.blockSize/2, "crafting_table", world));
	}
	@Override
	public int getHardness() {
		return 60;
	}
	@Override
	public boolean hasHitbox() {
		return false;
	}
}
