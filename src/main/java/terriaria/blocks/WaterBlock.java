package terriaria.blocks;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.beans.ConstructorProperties;

import terriaria.Game;
import terriaria.World;

public class WaterBlock extends Block {
	public WaterBlock(int x, int y, World world) {
		super(x, y, world);
	}
	@ConstructorProperties({"x", "y"})
	public WaterBlock(int x, int y) {
		super(x, y);
	}
	@Override
	public void update(Graphics g, ImageObserver ob) {
		try {
			if (this.world.getBlock(x, y-1) instanceof WaterBlock) {
				image = Game.images.water.get(1);
			} else {
				image = Game.images.water.get(0);
			}
		} catch (IndexOutOfBoundsException e) {
			image = Game.images.water.get(0);
		}
		super.update(g, ob);
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
		image = Game.images.blocks.get(0);
	}
}
