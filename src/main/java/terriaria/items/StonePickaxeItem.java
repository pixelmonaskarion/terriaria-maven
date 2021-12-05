package terriaria.items;

import terriaria.blocks.Block;
import terriaria.blocks.CobbleStoneBlock;
import terriaria.blocks.DeepCobbleStoneBlock;
import terriaria.blocks.DeepStoneBlock;
import terriaria.blocks.StoneBlock;

public class StonePickaxeItem extends Item{
	@Override
	public boolean damageBlock(Block b) {
		if (b instanceof StoneBlock || b instanceof CobbleStoneBlock || b instanceof DeepStoneBlock || b instanceof DeepCobbleStoneBlock) {
			b.damage(7);
		} else {
			b.damage(1);
		}
		return false;
	}
}
