package terriaria.blocks;

import terriaria.Game;
import terriaria.World;
import terriaria.entities.Item;

public class DeepCobbleStoneBlock extends Block {
	public DeepCobbleStoneBlock(int x, int y, World world) {
		super(x, y, world);
		type = 15;
		loadTexture(15);
	}
	public DeepCobbleStoneBlock(int x, int y) {
		super(x, y);
		type = 15;
		loadTexture(15);
	}
	@Override
	public void breakBlock() {
		world.entities.add(new Item((x*Game.blockSize)+Game.blockSize/4,(y*Game.blockSize)+Game.blockSize/2, "deep_cobble_stone", world));
	}
	@Override
	public int getHardness() {
		return 800;
	}
}
