package terriaria.blocks;

import java.beans.ConstructorProperties;

import terriaria.Game;
import terriaria.World;
import terriaria.entities.Item;

public class BlackCrystalOreBlock extends Block {
	public BlackCrystalOreBlock(int x, int y, World world) {
		super(x, y, world);
		type = 17;
		loadTexture(17);
	}
	@ConstructorProperties({"x", "y"})
	public BlackCrystalOreBlock(int x, int y) {
		super(x, y);
		type = 17;
		loadTexture(17);
	}
	@Override
	public void breakBlock() {
		world.entities.add(new Item((x*Game.blockSize)+Game.blockSize/4,(y*Game.blockSize)+Game.blockSize/2, "black_crystal", world));
	}
	@Override
	public int getHardness() {
		return 900;
	}

}
