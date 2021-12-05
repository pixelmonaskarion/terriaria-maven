package terriaria.blocks;

import java.beans.ConstructorProperties;

import terriaria.Game;
import terriaria.World;
import terriaria.entities.Item;

public class StoneBlock extends Block {
	public StoneBlock(int x, int y, World world) {
		super(x, y, world);
		type = 3;
		loadTexture(3);
	}
	@ConstructorProperties({"x", "y"})
	public StoneBlock(int x, int y) {
		super(x, y);
		type = 3;
		loadTexture(3);
	}
	@Override
	public void breakBlock() {
		world.entities.add(new Item((x*Game.blockSize)+Game.blockSize/4,(y*Game.blockSize)+Game.blockSize/2, "cobble_stone", world));
	}
	@Override
	public int getHardness() {
		return 600;
	}
}
