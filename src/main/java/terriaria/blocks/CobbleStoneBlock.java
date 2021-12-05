package terriaria.blocks;

import java.beans.ConstructorProperties;

import terriaria.Game;
import terriaria.World;
import terriaria.entities.Item;

public class CobbleStoneBlock extends Block {
	public CobbleStoneBlock(int x, int y, World world) {
		super(x, y, world);
		type = 13;
		loadTexture(13);
	}
	@ConstructorProperties({"x", "y"})
	public CobbleStoneBlock(int x, int y) {
		super(x, y);
		type = 13;
		loadTexture(13);
	}
	@Override
	public void breakBlock() {
		world.entities.add(new Item((x*Game.blockSize)+Game.blockSize/4,(y*Game.blockSize)+Game.blockSize/2, "cobble_stone", world));
	}
	@Override
	public int getHardness() {
		return 500;
	}
}
