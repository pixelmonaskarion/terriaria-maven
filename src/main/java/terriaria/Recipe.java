package terriaria;

import java.beans.ConstructorProperties;
import java.util.ArrayList;

import terriaria.blocks.CraftingTableBlock;

public class Recipe {
	public final ArrayList<ItemStack> input;
	public final ItemStack output;
	public boolean needsTable;
	@ConstructorProperties({"input", "output", "needsTable"})
	public Recipe(ArrayList<ItemStack> input, ItemStack output, boolean needsTable) {
		this.input = input;
		this.output = output;
		this.needsTable = needsTable;
	}
	public boolean canMake(Inventory inv) {
		int sameAmount = 0;
		if (Game.p.touchingBlock(Game.p.getBlocks(), CraftingTableBlock.class) || !needsTable) {
			for (ItemStack initem : input) {
				for (ItemStack item : inv.items) {
					if (item != null) {
						if (item.equals(initem) && item.amount >= initem.amount) {
							sameAmount += 1;
							break;
						}
					}
				}
			}
			if (sameAmount >= input.size()) {
				return true;
			} else {
				return false;
			}
		}
		return false;
		
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Recipe) {
			Recipe r = (Recipe)obj;
			if (r.input.equals(r.input)) {
				if (r.output.equals(this.output)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	public static boolean hasRecipe(Recipe input) {
		for (Recipe r : Game.p.recipies) {
			if (r.equals(input)) {
				return true;
			}
		}
		return false;
	}
}
