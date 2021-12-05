package terriaria.entities;


import java.beans.ConstructorProperties;
import java.util.ArrayList;

import terriaria.Game;
import terriaria.ItemStack;
import terriaria.Recipe;
import terriaria.Toast;
import terriaria.World;

public class Item extends Entity{
	public String type;
	public int ticks = 0;
	public Item(int x, int y, String type, World world) {
		super(x, y, world);
		this.x = x;
		this.y = y;
		this.type = type;
		this.image = Game.images.items.get(type);
	}
	@ConstructorProperties({"x", "y", "type"})
	public Item(int x, int y, String type) {
		super(x, y);
		this.x = x;
		this.y = y;
		this.type = type;
		this.image = Game.images.items.get(type);
	}
	@Override
	public String toString() {
		return String.format("itemEn:%s:%s:%s%s", x, y, Game.er.getName(this.getClass()), type);
	}
//	public void update(Graphics g, ImageObserver ob) {
//		int size = Game.blockSize;
//		ticks += 1;
//		if (x-Game.scrollX>-Game.blockSize && x-Game.scrollX<Start.screenSize.getWidth()) {
//			if (y-Game.scrollY>-Game.blockSize && y-Game.scrollY<Start.screenSize.getHeight()) {
//				g.drawImage(image, x-Game.scrollX, y-Game.scrollY,size/2, size/2, ob);
//			}
//		}
//		if (inWater(getBlocks())) {
//			Sy = Sy*0.5f;
//			Sy += 0.5f;
//		} else {
//			Sy += 1;
//		}
//		move((int) Sx, (int)Sy);
//		int px = Game.p.x-Game.scrollX;
//		int py = Game.p.y-Game.scrollY;
//		int distance = (int) Math.sqrt(Math.pow((px-(x-Game.scrollX))+(py-(y-Game.scrollY)), 2));
//		if (distance < 2*Game.blockSize) {
//			if (ticks > 30) {
//				if (Game.inv.add(new ItemStack(type, 1))) {
//					
//				 world.deleteItem(this);
//				 itemPickedUp(type);
//				}
//			}
//		}
//	}
	@Override
	public void onTick() {
		ticks += 1;
		int px = Game.p.x-Game.scrollX;
		int py = Game.p.y-Game.scrollY;
		int distance = (int) Math.sqrt(Math.pow((px-(x-Game.scrollX))+(py-(y-Game.scrollY)), 2));
		if (distance < 2*Game.blockSize) {
			if (ticks > 30) {
				if (Game.p.inv.add(new ItemStack(type, 1))) {
				 this.world.deleteEntity(this);
				 itemPickedUp(type);
				}
			}
		}
	}
	public void itemPickedUp(String type) {
		if (type == "wood") {
			ArrayList<ItemStack> input = new ArrayList<>();
			input.add(new ItemStack("wood", 1));
			boolean hasRecipe = Recipe.hasRecipe(new Recipe(input, new ItemStack("crafting_table", 1), false));
			if (!hasRecipe) {
				Game.p.recipies.add(new Recipe(input, new ItemStack("crafting_table", 1), false));
				Game.toasts.add(new Toast(150, Game.images.items.get("crafting_table").getScaledInstance(Game.images.items.get("crafting_table").getWidth(null)*3, Game.images.items.get("crafting_table").getHeight(null)*3, 0), "new recipe"));
				Game.p.recipies.add(new Recipe(input, new ItemStack("planks", 4), false));
				Game.toasts.add(new Toast(150, Game.images.items.get("planks").getScaledInstance(Game.images.items.get("planks").getWidth(null)*3, Game.images.items.get("planks").getHeight(null)*3, 0), "new recipe"));
			}
		}
		if (type == "iron_ore") {
			ArrayList<ItemStack> input = new ArrayList<>();
			input.add(new ItemStack("iron_ore", 2));
			boolean hasRecipe = Recipe.hasRecipe(new Recipe(input, new ItemStack("iron_bar", 1), false));
			if (!hasRecipe) {
				Game.p.recipies.add(new Recipe(input, new ItemStack("iron_bar", 1), false));
				Game.toasts.add(new Toast(150, Game.images.items.get("iron_bar").getScaledInstance(Game.images.items.get("iron_bar").getWidth(null)*3, Game.images.items.get("iron_bar").getHeight(null)*3, 0), "new recipe"));
			}
		}
		if (type == "cobble_stone") {
			ArrayList<ItemStack> input = new ArrayList<>();
			input.add(new ItemStack("planks", 1));
			input.add(new ItemStack("cobble_stone", 3));
			boolean hasRecipe = Recipe.hasRecipe(new Recipe(input, new ItemStack("stone_pickaxe", 1), false));
			if (!hasRecipe) {
				Game.p.recipies.add(new Recipe(input, new ItemStack("stone_pickaxe", 1), false));
				Game.toasts.add(new Toast(150, Game.images.items.get("stone_pickaxe").getScaledInstance(Game.images.items.get("stone_pickaxe").getWidth(null)*3, Game.images.items.get("stone_pickaxe").getHeight(null)*3, 0), "new recipe"));
			}
		}
	}
}
