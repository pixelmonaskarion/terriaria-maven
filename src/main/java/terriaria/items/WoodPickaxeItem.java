package terriaria.items;

import terriaria.blocks.Block;
import terriaria.blocks.CobbleStoneBlock;
import terriaria.blocks.StoneBlock;

public class WoodPickaxeItem extends Item{
	@Override
	public boolean damageBlock(Block b) {
		if (b instanceof StoneBlock || b instanceof CobbleStoneBlock) {
			b.damage(5);
		} else {
			b.damage(1);
		}
		return false;
	}
}
