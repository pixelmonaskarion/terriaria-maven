package terriaria.items;

import terriaria.Game;
import terriaria.blocks.Block;

public class Item {
	public int damage = 1;
	public boolean damageBlock(Block b) {
		b.damage(1);
		return false;
	}
	public boolean rightClickBlock(Block b) {
		return false;
	}
	@Override
	public String toString() {
		return String.format("item:%s", Game.ir.getName(this.getClass()));
	}
}