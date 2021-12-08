package terriaria.blocks;

import java.beans.ConstructorProperties;

import terriaria.Game;
import terriaria.World;
import terriaria.entities.Item;

public class WoodBlock extends Block {
	public WoodBlock(int x, int y, World world) {
		super(x, y, world);
		loadTexture();
	}
	@ConstructorProperties({"x", "y"})
	public WoodBlock(int x, int y) {
		super(x, y);
		loadTexture();
	}
	@Override
	public int getHardness() {
		return 300;
	}
	@Override
	public void breakBlock() {
		world.entities.add(new Item((x*Game.blockSize)+Game.blockSize/4,(y*Game.blockSize)+Game.blockSize/2, "wood", world));
	}
	
	@Override
	public void loadTexture() {
		image = Game.images.blocks.get(7);
	}
}
