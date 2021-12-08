package terriaria.blocks;

import java.beans.ConstructorProperties;
import java.util.Random;

import terriaria.Game;
import terriaria.World;
import terriaria.entities.Item;

public class DirtBlock extends Block {
	public DirtBlock(int x, int y, World world) {
		super(x, y, world);
		loadTexture();
	}
	@ConstructorProperties({"x", "y"})
	public DirtBlock(int x, int y) {
		super(x, y);
		loadTexture();
	}
	@Override
	public void breakBlock() {
		world.entities.add(new Item((x*Game.blockSize)+Game.blockSize/4,(y*Game.blockSize)+Game.blockSize/2, "dirt", world));
	}
	@Override
	public int getHardness() {
		return 60;
	}
	@Override
	public void onTick() {
		if (world.getBlock(x, y-1) instanceof AirBlock) {
			if (new Random().nextInt(100) == 1) {
			for (Block b : Block.getBlocksAroundBlock(this)) {
				if (b instanceof GrassBlock) {
					world.setBlock(x, y, new GrassBlock(x, y, world));
				}
			}
		}
		}
	}
	
	@Override
	public void loadTexture() {
		image = Game.images.blocks.get(1);
	}
}
