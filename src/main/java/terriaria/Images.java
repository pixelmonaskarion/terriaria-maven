package terriaria;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Images {
	public ArrayList<BufferedImage> blocks = new ArrayList<BufferedImage>();
	public ArrayList<BufferedImage> water = new ArrayList<BufferedImage>();
	public ArrayList<BufferedImage> blockDamage = new ArrayList<BufferedImage>();
	public HashMap<String, Image> items = new HashMap<String, Image>();
	public HashMap<String, Image> moded = new HashMap<String, Image>();
	public ArrayList<BufferedImage> entities = new ArrayList<BufferedImage>();
	public ArrayList<BufferedImage> particles = new ArrayList<BufferedImage>();
	public BufferedImage hotbarSlot;
	public BufferedImage heart;
	public BufferedImage heart_half;
	public static BufferedImage player;
	public BufferedImage selectedSlot;
	public BufferedImage red_slot;
	public BufferedImage green_slot;
	public BufferedImage bubble;
	public BufferedImage bubble_half;
	public BufferedImage inventory;
	public BufferedImage mouse;
	public BufferedImage heart_alpha;
	public Images(boolean scratch) {
		if (!scratch) {
			try {
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/grass.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/dirt.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/air.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/stone.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/water.png")));//do not use this
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/gold_ore.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/diamond_ore.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/wood.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/leaves.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/iron_ore.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/crafting_table.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/torch.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/wood_planks.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/cobble_stone.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/sand.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/deep_stone.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/deep_cobble_stone.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/black_crystal_ore.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/white_crystal_ore.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/magma_ore.png")));
				items.put("dirt", ImageIO.read(getClass().getResourceAsStream("/images/dirt.png")));
				items.put("cobble_stone", ImageIO.read(getClass().getResourceAsStream("/images/cobble_stone.png")));
				items.put("gold_ore", ImageIO.read(getClass().getResourceAsStream("/images/gold_ore_item.png")));
				items.put("iron_ore", ImageIO.read(getClass().getResourceAsStream("/images/iron_ore_item.png")));
				items.put("diamond", ImageIO.read(getClass().getResourceAsStream("/images/diamond.png")));
				items.put("wood", ImageIO.read(getClass().getResourceAsStream("/images/wood.png")));
				items.put("leaves", ImageIO.read(getClass().getResourceAsStream("/images/leaves.png")));
				items.put("stone", ImageIO.read(getClass().getResourceAsStream("/images/stone.png")));
				items.put("crafting_table", ImageIO.read(getClass().getResourceAsStream("/images/crafting_table.png")));
				items.put("torch", ImageIO.read(getClass().getResourceAsStream("/images/torch.png")));
				items.put("wood_pickaxe", ImageIO.read(getClass().getResourceAsStream("/images/wood_pickaxe.png")));
				items.put("planks", ImageIO.read(getClass().getResourceAsStream("/images/wood_planks.png")));
				items.put("sand", ImageIO.read(getClass().getResourceAsStream("/images/sand.png")));
				items.put("deep_stone", ImageIO.read(getClass().getResourceAsStream("/images/deep_stone.png")));
				items.put("deep_cobble_stone", ImageIO.read(getClass().getResourceAsStream("/images/deep_cobble_stone.png")));
				items.put("magma", ImageIO.read(getClass().getResourceAsStream("/images/magma.png")));
				items.put("white_crystal", ImageIO.read(getClass().getResourceAsStream("/images/white_crystal.png")));
				items.put("black_crystal", ImageIO.read(getClass().getResourceAsStream("/images/black_crystal.png")));
				items.put("iron_bar", ImageIO.read(getClass().getResourceAsStream("/images/iron_bar.png")));
				items.put("stone_pickaxe", ImageIO.read(getClass().getResourceAsStream("/images/stone_pickaxe.png")));
				player = ImageIO.read(getClass().getResourceAsStream("/images/player_idle.png"));
				hotbarSlot = ImageIO.read(getClass().getResourceAsStream("/images/hotbar_slot.png"));
				heart = ImageIO.read(getClass().getResourceAsStream("/images/heart.png"));
				heart_half = ImageIO.read(getClass().getResourceAsStream("/images/heart_half.png"));
				heart_alpha = ImageIO.read(getClass().getResourceAsStream("/images/heart_alpha.png"));
				selectedSlot = ImageIO.read(getClass().getResourceAsStream("/images/selected_slot.png"));
				green_slot = ImageIO.read(getClass().getResourceAsStream("/images/green_slot.png"));
				red_slot = ImageIO.read(getClass().getResourceAsStream("/images/red_slot.png"));
				inventory = ImageIO.read(getClass().getResourceAsStream("/images/inventory.png"));
				bubble = ImageIO.read(getClass().getResourceAsStream("/images/bubble.png"));
				bubble_half = ImageIO.read(getClass().getResourceAsStream("/images/bubble_half.png"));
				mouse = ImageIO.read(getClass().getResourceAsStream("/images/mouse.png"));
				water.add(ImageIO.read(getClass().getResourceAsStream("/images/water_top.png")));
				water.add(ImageIO.read(getClass().getResourceAsStream("/images/water.png")));
				water.add(ImageIO.read(getClass().getResourceAsStream("/images/lava_top.png")));
				water.add(ImageIO.read(getClass().getResourceAsStream("/images/lava.png")));
				blockDamage.add(ImageIO.read(getClass().getResourceAsStream("/images/damage_0.png")));
				blockDamage.add(ImageIO.read(getClass().getResourceAsStream("/images/damage_1.png")));
				blockDamage.add(ImageIO.read(getClass().getResourceAsStream("/images/damage_2.png")));
				blockDamage.add(ImageIO.read(getClass().getResourceAsStream("/images/damage_3.png")));
				blockDamage.add(ImageIO.read(getClass().getResourceAsStream("/images/damage_4.png")));
				blockDamage.add(ImageIO.read(getClass().getResourceAsStream("/images/damage_5.png")));
				entities.add(ImageIO.read(getClass().getResourceAsStream("/images/pig.png")));
				particles.add(ImageIO.read(getClass().getResourceAsStream("/images/particles/flame.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/scratch/grass.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/scratch/dirt.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/scratch/air.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/scratch/stone.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/scratch/water/water.png")));//don't use this again
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/scratch/gold_ore_block.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/scratch/diamond_ore.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/scratch/wood.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/scratch/leaves.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/scratch/iron_ore_block.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/scratch/crafting_table.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/scratch/torch.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/scratch/wood_planks.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/cobble_stone.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/scratch/sand.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/deep_stone.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/deep_cobble_stone.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/black_crystal_ore.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/white_crystal_ore.png")));
				blocks.add(ImageIO.read(getClass().getResourceAsStream("/images/magma_ore.png")));
				items.put("dirt", ImageIO.read(getClass().getResourceAsStream("/images/scratch/dirt.png")));
				items.put("cobble_stone", ImageIO.read(getClass().getResourceAsStream("/images/cobble_stone.png")));
				items.put("gold_ore", ImageIO.read(getClass().getResourceAsStream("/images/gold_ore_item.png")));
				items.put("iron_ore", ImageIO.read(getClass().getResourceAsStream("/images/iron_ore_item.png")));
				items.put("diamond", ImageIO.read(getClass().getResourceAsStream("/images/diamond.png")));
				items.put("wood", ImageIO.read(getClass().getResourceAsStream("/images/scratch/wood.png")));
				items.put("leaves", ImageIO.read(getClass().getResourceAsStream("/images/scratch/leaves.png")));
				items.put("stone", ImageIO.read(getClass().getResourceAsStream("/images/scratch/stone.png")));
				items.put("crafting_table", ImageIO.read(getClass().getResourceAsStream("/images/crafting_table.png")));
				items.put("torch", ImageIO.read(getClass().getResourceAsStream("/images/torch.png")));
				items.put("wood_pickaxe", ImageIO.read(getClass().getResourceAsStream("/images/wood_pickaxe.png")));
				items.put("planks", ImageIO.read(getClass().getResourceAsStream("/images/wood_planks.png")));
				items.put("sand", ImageIO.read(getClass().getResourceAsStream("/images/scratch/sand.png")));
				items.put("deep_stone", ImageIO.read(getClass().getResourceAsStream("/images/deep_stone.png")));
				items.put("deep_cobble_stone", ImageIO.read(getClass().getResourceAsStream("/images/deep_cobble_stone.png")));
				items.put("magma", ImageIO.read(getClass().getResourceAsStream("/images/magma.png")));
				items.put("white_crystal", ImageIO.read(getClass().getResourceAsStream("/images/white_crystal.png")));
				items.put("black_crystal", ImageIO.read(getClass().getResourceAsStream("/images/black_crystal.png")));
				items.put("iron_bar", ImageIO.read(getClass().getResourceAsStream("/images/iron_bar.png")));
				items.put("stone_pickaxe", ImageIO.read(getClass().getResourceAsStream("/images/stone_pickaxe.png")));
				player = ImageIO.read(getClass().getResourceAsStream("/images/player_idle.png"));
				hotbarSlot = ImageIO.read(getClass().getResourceAsStream("/images/hotbar_slot.png"));
				heart = ImageIO.read(getClass().getResourceAsStream("/images/heart.png"));
				heart_half = ImageIO.read(getClass().getResourceAsStream("/images/heart_half.png"));
				selectedSlot = ImageIO.read(getClass().getResourceAsStream("/images/selected_slot.png"));
				green_slot = ImageIO.read(getClass().getResourceAsStream("/images/green_slot.png"));
				red_slot = ImageIO.read(getClass().getResourceAsStream("/images/red_slot.png"));
				inventory = ImageIO.read(getClass().getResourceAsStream("/images/inventory.png"));
				bubble = ImageIO.read(getClass().getResourceAsStream("/images/bubble.png"));
				bubble_half = ImageIO.read(getClass().getResourceAsStream("/images/bubble_half.png"));
				mouse = ImageIO.read(getClass().getResourceAsStream("/images/mouse.png"));
				water.add(ImageIO.read(getClass().getResourceAsStream("/images/scratch/water/water2.png")));
				water.add(ImageIO.read(getClass().getResourceAsStream("/images/scratch/water/water.png")));
				water.add(ImageIO.read(getClass().getResourceAsStream("/images/lava_top.png")));
				water.add(ImageIO.read(getClass().getResourceAsStream("/images/lava.png")));
				blockDamage.add(ImageIO.read(getClass().getResourceAsStream("/images/damage_0.png")));
				blockDamage.add(ImageIO.read(getClass().getResourceAsStream("/images/damage_1.png")));
				blockDamage.add(ImageIO.read(getClass().getResourceAsStream("/images/damage_2.png")));
				blockDamage.add(ImageIO.read(getClass().getResourceAsStream("/images/damage_3.png")));
				blockDamage.add(ImageIO.read(getClass().getResourceAsStream("/images/damage_4.png")));
				blockDamage.add(ImageIO.read(getClass().getResourceAsStream("/images/damage_5.png")));
				entities.add(ImageIO.read(getClass().getResourceAsStream("/images/pig.png")));
				particles.add(ImageIO.read(getClass().getResourceAsStream("/images/particles/flame.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for (String name : Game.imr.getAll()) {
			moded.put(name, Game.imr.getImage(name));
		}
	}
}
