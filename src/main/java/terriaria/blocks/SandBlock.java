package terriaria.blocks;

import java.beans.ConstructorProperties;

import terriaria.Game;
import terriaria.World;
import terriaria.entities.Item;

public class SandBlock extends Block {
	public SandBlock(int x, int y, World world) {
		super(x, y, world);
		type = 14;
		loadTexture(14);
	}
	@ConstructorProperties({"x", "y"})
	public SandBlock(int x, int y) {
		super(x, y);
		type = 14;
		loadTexture(14);
	}
	@Override
	public void breakBlock() {
		world.entities.add(new Item((x*Game.blockSize)+Game.blockSize/4,(y*Game.blockSize)+Game.blockSize/2, "sand", world));
	}
	@Override
	public int getHardness() {
		return 60;
	}
}
