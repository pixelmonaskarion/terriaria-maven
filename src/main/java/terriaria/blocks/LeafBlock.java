package terriaria.blocks;

import java.beans.ConstructorProperties;
import java.util.Random;

import terriaria.Game;
import terriaria.World;
import terriaria.entities.Item;

public class LeafBlock extends Block {
	public LeafBlock(int x, int y, World world) {
		super(x, y, world);
		type = 8;
		loadTexture(8);
	}
	@ConstructorProperties({"x", "y"})
	public LeafBlock(int x, int y) {
		super(x, y);
		type = 8;
		loadTexture(8);
	}
	@Override
	public int getHardness() {
		return 6;
	}
	@Override
	public void breakBlock() {
		if (new Random().nextInt(3) == 1) {
			world.entities.add(new Item((x*Game.blockSize)+Game.blockSize/4,(y*Game.blockSize)+Game.blockSize/2, "leaves", world));
		}
	}
	@Override
	public boolean letsLightThrough() {
		return true;
	}
}
