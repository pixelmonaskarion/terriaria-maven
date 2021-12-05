package terriaria.items;

import terriaria.blocks.Block;
import terriaria.blocks.TorchBlock;

public class TorchItem extends Item{
	@Override
	public boolean rightClickBlock(Block b) {
		if (!(b instanceof TorchBlock)) {
			b.world.setBlock(b.x, b.y, new TorchBlock(b.x, b.y, b.world));
			return true;
		}
		return false;
	}
}
