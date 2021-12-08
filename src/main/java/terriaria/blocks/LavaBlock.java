package terriaria.blocks;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.beans.ConstructorProperties;

import terriaria.Game;
import terriaria.World;

public class LavaBlock extends Block {
	public LavaBlock(int x, int y, World world) {
		super(x, y, world);
	}
	@ConstructorProperties({"x", "y"})
	public LavaBlock(int x, int y) {
		super(x, y);
	}
	@Override
	public boolean hasHitbox() {
		return false;
	}
	@Override
	public void update(Graphics g, ImageObserver ob) {
		try {
			if (this.world.getBlock(x, y-1) instanceof LavaBlock) {
				image = Game.images.water.get(3);
			} else {
				image = Game.images.water.get(2);
			}
		} catch (IndexOutOfBoundsException e) {
			image = Game.images.water.get(3);
		}
		super.update(g, ob);
	}
	@Override
	public float light() {
		return 1f;
	}

	@Override
	public void damage(int amount) {
	}
	
	@Override
	public void loadTexture() {
		image = Game.images.blocks.get(0);
	}
}
