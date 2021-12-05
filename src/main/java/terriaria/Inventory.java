package terriaria;

import java.util.ArrayList;

public class Inventory {
	public ItemStack[] items = new ItemStack[36];
	public Inventory() {
		for (int i = 0; i<36; i++) {
			items[i] = null;
		}
	}
	public boolean add(ItemStack item) {
		boolean done = false;
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null) {
				if (items[i].type.equals(item.type)) {
					items[i].amount += item.amount;
					done = true;
					break;
				}
			}
		}
		if (done == false) {
			int firstOpen = firstOpen();
			if (firstOpen != -1) { 
				items[firstOpen] = item;
				done = true;
			}
		}
		if (item.type.equals("crafting_table") && done) {
			giveCraftingTableRecipies();
		}
		return done;
	}
	public boolean remove(ItemStack item) {
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null) {
				if (items[i].type.equals(item.type)) {
					if (items[i].amount-item.amount > 0) {
						items[i].amount -= item.amount;
					} else {
						items[i] = null;
					}
					return true;
				}
			}
		}
		return false;
	}
	public void giveCraftingTableRecipies() {
		ArrayList<ItemStack> input = new ArrayList<>();
		input.add(new ItemStack("wood", 1));
		input.add(new ItemStack("leaves", 1));
		if (!Recipe.hasRecipe(new Recipe(input, new ItemStack("torch", 4), true))) {
			Game.p.recipies.add(new Recipe(input, new ItemStack("torch", 4), true));
			Game.toasts.add(new Toast(150, Game.images.items.get("torch").getScaledInstance(Game.images.items.get("torch").getWidth(null)*3, Game.images.items.get("torch").getHeight(null)*3, 0), "new recipe"));
			ArrayList<ItemStack> pickin = new ArrayList<>();
			pickin.add(new ItemStack("planks", 4));
			Game.p.recipies.add(new Recipe(pickin, new ItemStack("wood_pickaxe", 1), true));
			Game.toasts.add(new Toast(150, Game.images.items.get("wood_pickaxe").getScaledInstance(Game.images.items.get("wood_pickaxe").getWidth(null)*3, Game.images.items.get("wood_pickaxe").getHeight(null)*3, 0), "new recipe"));
		
		}
	}
	
	public int firstOpen() {
		for (int i = 0; i < items.length; i++) {
			if (items[i] == null) {
				return i;
			}
		}
		return -1;
	}
}
