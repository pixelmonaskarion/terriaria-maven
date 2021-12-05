package terriaria.blocks;

import java.beans.ConstructorProperties;
import java.util.Random;

import terriaria.Game;
import terriaria.World;
import terriaria.entities.Item;
import terriaria.particles.FlameParticle;

public class TorchBlock extends Block {
	public TorchBlock(int x, int y, World world) {
		super(x, y, world);
		type = 11;
		loadTexture(11);
	}
	@ConstructorProperties({"x", "y"})
	public TorchBlock(int x, int y) {
		super(x, y);
		type = 11;
		loadTexture(11);
	}
	@Override
	public void breakBlock() {
		world.entities.add(new Item((x*Game.blockSize)+Game.blockSize/4,(y*Game.blockSize)+Game.blockSize/2, "torch", world));
	}
	@Override
	public int getHardness() {
		return 6;
	}
	@Override
	public boolean hasHitbox() {
		return false;
	}
	@Override
	public float light() {
		return 10f;
	}
	@Override
	public void onTick() {
		if (new Random().nextInt(1000) == 1) {
			world.addParticle(new FlameParticle((x*Game.blockSize)+Game.blockSize/4+Game.blockSize/4,(y*Game.blockSize)+Game.blockSize/2));
		}
	}
//	@Override
//	public void onTick() {
//		if (!world.getBlock(x, y+1).hasHitbox()) {
//			world.items.add(new Item((x*Game.blockSize)+Game.blockSize/4,(y*Game.blockSize)+Game.blockSize/2, 9, world));
//			world.setBlock(x, y, new AirBlock(x, y, world));
//		}
//	}
}
