package terriaria.blocks;

import java.beans.ConstructorProperties;

import terriaria.Game;
import terriaria.World;

public class AirBlock extends Block {
	public AirBlock(int x, int y, World world) {
		super(x, y, world);
		loadTexture();
	}
	@ConstructorProperties({"x", "y"})
	public AirBlock(int x, int y) {
		super(x, y);
		loadTexture();
	}
	@Override
	public int getHardness() {
		return (int) Double.POSITIVE_INFINITY;
	}
	@Override
	public boolean hasHitbox() {
		return false;
	}
	@Override
	public void damage(int amount) {
	}
	
	@Override
	public boolean canBeReplaced() {
		return true;
	}
	@Override
	public void loadTexture() {
		image = Game.images.blocks.get(2);
	}
}
