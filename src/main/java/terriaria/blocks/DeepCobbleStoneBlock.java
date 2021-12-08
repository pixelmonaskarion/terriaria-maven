package terriaria.blocks;

import terriaria.Game;
import terriaria.World;
import terriaria.entities.Item;

public class DeepCobbleStoneBlock extends Block {
	public DeepCobbleStoneBlock(int x, int y, World world) {
		super(x, y, world);
		loadTexture();
	}
	public DeepCobbleStoneBlock(int x, int y) {
		super(x, y);
		loadTexture();
	}
	@Override
	public void breakBlock() {
		world.entities.add(new Item((x*Game.blockSize)+Game.blockSize/4,(y*Game.blockSize)+Game.blockSize/2, "deep_cobble_stone", world));
	}
	@Override
	public int getHardness() {
		return 800;
	}
	
	@Override
	public void loadTexture() {
		image = Game.images.blocks.get(15);
	}
}
