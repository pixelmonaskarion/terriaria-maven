package terriaria.blocks;

import java.beans.ConstructorProperties;

import terriaria.Game;
import terriaria.World;
import terriaria.entities.Item;

public class GrassBlock extends Block {
	public GrassBlock(int x, int y, World world) {
		super(x, y, world);
		type = 0;
		loadTexture(0);
	}
	@ConstructorProperties({"x", "y"})
	public GrassBlock(int x, int y) {
		super(x, y);
		type = 0;
		loadTexture(0);
	}
	@Override
	public void breakBlock() {
		world.entities.add(new Item((x*Game.blockSize)+Game.blockSize/4,(y*Game.blockSize)+Game.blockSize/2, "dirt", world));
	}
	@Override
	public int getHardness() {
		return 60;
	}
}
