package terriaria.items;

import terriaria.Game;
import terriaria.blocks.Block;
import terriaria.blocks.CobbleStoneBlock;

public class CobblestoneItem extends Item{
	@Override
	public boolean rightClickBlock(Block b) {
		b.world.setBlock(b.x, b.y, new CobbleStoneBlock(b.x, b.y, b.world));
		if (Game.p.isSuficating(Game.p.getBlocks())) {
			b.world.setBlock(b.x, b.y, b);
			return false;
		}
		return true;
	}
}
