package terriaria.blocks;

import java.beans.ConstructorProperties;

import terriaria.Game;
import terriaria.World;
import terriaria.entities.Item;

public class IronOreBlock extends Block {
	public IronOreBlock(int x, int y, World world) {
		super(x, y, world);
		type = 9;
		loadTexture(9);
	}
	@ConstructorProperties({"x", "y"})
	public IronOreBlock(int x, int y) {
		super(x, y);
		type = 9;
		loadTexture(9);
	}
	@Override
	public int getHardness() {
		return 700;
	}
	@Override
	public void breakBlock() {
		world.entities.add(new Item((x*Game.blockSize)+Game.blockSize/4,(y*Game.blockSize)+Game.blockSize/2, "iron_ore", world));
	}
}
