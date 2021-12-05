package terriaria.blocks;

import java.beans.ConstructorProperties;

import terriaria.World;

public class AirBlock extends Block {
	public AirBlock(int x, int y, World world) {
		super(x, y, world);
		type = 2;
		loadTexture(2);
	}
	@ConstructorProperties({"x", "y"})
	public AirBlock(int x, int y) {
		super(x, y);
		type = 2;
		loadTexture(2);
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
}
