package terriaria.blocks;

import java.beans.ConstructorProperties;

import terriaria.Game;
import terriaria.World;
import terriaria.entities.Item;

public class PlankBlock extends Block{
	public PlankBlock(int x, int y, World world) {
		super(x, y, world);
		type = 12;
		loadTexture(12);
	}
	@ConstructorProperties({"x", "y"})
	public PlankBlock(int x, int y) {
		super(x, y);
		type = 12;
		loadTexture(12);
	}
	@Override
	public void breakBlock() {
		world.entities.add(new Item((x*Game.blockSize)+Game.blockSize/4,(y*Game.blockSize)+Game.blockSize/2, "planks", world));
	}
	@Override
	public int getHardness() {
		return 300;
	}
}
