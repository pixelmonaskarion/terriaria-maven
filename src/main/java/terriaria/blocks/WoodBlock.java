package terriaria.blocks;

import java.beans.ConstructorProperties;

import terriaria.Game;
import terriaria.World;
import terriaria.entities.Item;

public class WoodBlock extends Block {
	public WoodBlock(int x, int y, World world) {
		super(x, y, world);
		type = 7;
		loadTexture(7);
	}
	@ConstructorProperties({"x", "y"})
	public WoodBlock(int x, int y) {
		super(x, y);
		type = 7;
		loadTexture(7);
	}
	@Override
	public int getHardness() {
		return 300;
	}
	@Override
	public void breakBlock() {
		world.entities.add(new Item((x*Game.blockSize)+Game.blockSize/4,(y*Game.blockSize)+Game.blockSize/2, "wood", world));
	}
}
