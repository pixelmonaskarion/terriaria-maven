package terriaria.blocks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.beans.ConstructorProperties;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import terriaria.Game;
import terriaria.Start;
import terriaria.World;
@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME, 
		include = JsonTypeInfo.As.PROPERTY, 
		property = "type")
@JsonSubTypes({
	@Type(value = AirBlock.class, name = "air"),
	@Type(value = BlackCrystalOreBlock.class, name = "black_crystal_ore"),
	@Type(value = CaveAirBlock.class, name = "cave_air"),
	@Type(value = CobbleStoneBlock.class, name = "cobble_stone"),
	@Type(value = CraftingTableBlock.class, name = "crafting_table"),
	@Type(value = DeepCobbleStoneBlock.class, name = "deep_cobble_stone"),
	@Type(value = DeepStoneBlock.class, name = "deep_stone"),
	@Type(value = DiamondOreBlock.class, name = "diamond_ore"),
	@Type(value = DirtBlock.class, name = "dirt"),
	@Type(value = GoldOreBlock.class, name = "gold_ore"),
	@Type(value = GrassBlock.class, name = "grass"),
	@Type(value = IronOreBlock.class, name = "iron_ore"),
	@Type(value = LavaBlock.class, name = "lava"),
	@Type(value = LeafBlock.class, name = "leaves"),
	@Type(value = MagmaOreBlock.class, name = "magma_ore"),
	@Type(value = PlankBlock.class, name = "planks"),
	@Type(value = SandBlock.class, name = "sand"),
	@Type(value = StoneBlock.class, name = "stone"),
	@Type(value = TorchBlock.class, name = "torch"),
	@Type(value = WaterBlock.class, name = "water"),
	@Type(value = WhiteCrystalOreBlock.class, name = "white_crystal_ore"),
	@Type(value = WoodBlock.class, name = "wood")
})
public abstract class Block {
	public int x;
	public int y;
	public int damage = 0;
	@JsonIgnore
	private Image damageImage;
	@JsonIgnore
	public Image image;
	@JsonIgnore
	public World world;
	public float light = 0f;
	public Block(int x, int y, World w) {
		this.world = w;
		this.x = x;
		this.y = y;
		this.damageImage = Game.images.blockDamage.get((this.damage/100));
		BufferedImage combined = new BufferedImage(Game.blockSize, Game.blockSize, BufferedImage.TYPE_INT_ARGB);
		// paint both images, preserving the alpha channels
		Graphics Imageg = combined.getGraphics();
		Imageg.drawImage(image, 0, 0, null);
		Imageg.drawImage(damageImage.getScaledInstance(Game.blockSize, Game.blockSize, 0), 0, 0, null);
		Imageg.dispose();
		image = combined;
	}
	@ConstructorProperties({"x", "y"})
	public Block(int x, int y) {
		this.x = x;
		this.y = y;
		this.damageImage = Game.images.blockDamage.get((this.damage/100));
		BufferedImage combined = new BufferedImage(Game.blockSize, Game.blockSize, BufferedImage.TYPE_INT_ARGB);
		// paint both images, preserving the alpha channels
		Graphics Imageg = combined.getGraphics();
		Imageg.drawImage(image, 0, 0, null);
		Imageg.drawImage(damageImage.getScaledInstance(Game.blockSize, Game.blockSize, 0), 0, 0, null);
		Imageg.dispose();
		image = combined;
	}
	@Override
	public String toString() {
		return String.format("block: %s:%s:%s", x, y, Game.br.getName(this.getClass()));
	}
	public void update(Graphics g, ImageObserver ob) {
		int size = Game.blockSize;
		if ((x*size)-Game.scrollX>-Game.blockSize && (x*size)-Game.scrollX<Start.screenSize.getWidth()) {
			if ((y*size)-Game.scrollY>-Game.blockSize && (y*size)-Game.scrollY<Start.screenSize.getHeight()) {
				light = light();
				if (light != 0) {
					g.drawImage(image, (x*size)-Game.scrollX, (y*size)-Game.scrollY,size, size, ob);
				}
				onTick();
			}
		}
	}
	public void damage(int amount) {
		this.damage += amount;
		if (this.damage >= getHardness()) {
			breakBlock();
			Start.game.world.setBlock(x, y, new AirBlock(x, y, Start.game.world));
			this.damage = 0;
		} else {
			try {
				this.damageImage = Game.images.blockDamage.get((this.damage/(getHardness()/6)));
				System.out.println(Game.blockSize);
				BufferedImage combined = new BufferedImage(Game.blockSize, Game.blockSize, BufferedImage.TYPE_INT_ARGB);
				// paint both images, preserving the alpha channels
				Graphics Imageg = combined.getGraphics();
				Imageg.drawImage(image.getScaledInstance(Game.blockSize, Game.blockSize, 0), 0, 0, null);
				Imageg.drawImage(damageImage.getScaledInstance(Game.blockSize, Game.blockSize, 0), 0, 0, null);
				Imageg.dispose();
				image = combined;
			} catch (IndexOutOfBoundsException e) {
				this.damageImage = Game.images.blockDamage.get(5);
				BufferedImage combined = new BufferedImage(Game.blockSize, Game.blockSize, BufferedImage.TYPE_INT_ARGB);
				// paint both images, preserving the alpha channels
				Graphics Imageg = combined.getGraphics();
				Imageg.drawImage(image.getScaledInstance(Game.blockSize, Game.blockSize, 0), 0, 0, null);
				Imageg.drawImage(damageImage.getScaledInstance(Game.blockSize, Game.blockSize, 0), 0, 0, null);
				Imageg.dispose();
				image = combined;
			}
		}
	}
	@JsonIgnore
	public int getHardness() {
		return 600;
	}
	public void breakBlock() {
	}
	public boolean hasHitbox() {
		return true;
	}
	public float light() {
		try {
			if (Start.game.world.getTopLightingBlock(x).y >= y) {
				light = Game.skylight;
			} else {
				float tl;
				float ll;
				float rl;
				float bl;
				int airblocks = 0;
				if (Start.game.world.getBlock(x, y+1).letsLightThrough()) {
					tl = Start.game.world.getBlock(x, y+1).light;
					airblocks += 1;
				} else {
					tl = 0f;
				}
				if (Start.game.world.getBlock(x-1, y).letsLightThrough()) {
					ll = Start.game.world.getBlock(x-1, y).light;
					airblocks += 1;
				} else {
					ll = 0f;
				}
				if (Start.game.world.getBlock(x, y-1).letsLightThrough()) {
					bl = Start.game.world.getBlock(x, y-1).light;
					airblocks += 1;
				} else {
					bl = 0f;
				}
				if (Start.game.world.getBlock(x+1, y).letsLightThrough()) {
					rl = Start.game.world.getBlock(x+1, y).light;
					airblocks += 1;
				} else {
					rl = 0f;
				}
				if (airblocks > 0) {
					light = (tl+ll+bl+rl)/(airblocks*1.5f);
				} else {
					light = 0;
				}
				
			}
		} catch (NullPointerException e) {
			light = Game.skylight;
		}
		
		return light;
	}
	public void onTick() {
		
	}
	public static ArrayList<Block> getBlocksAroundBlock(Block b) {
		ArrayList<Block> blocks = new ArrayList<Block>();
		blocks.add(Start.game.world.getBlock(b.x+1, b.y));
		blocks.add(Start.game.world.getBlock(b.x-1, b.y));
		blocks.add(Start.game.world.getBlock(b.x, b.y+1));
		blocks.add(Start.game.world.getBlock(b.x, b.y-1));
		blocks.add(Start.game.world.getBlock(b.x-1, b.y-1));
		blocks.add(Start.game.world.getBlock(b.x+1, b.y+1));
		blocks.add(Start.game.world.getBlock(b.x-1, b.y+1));
		blocks.add(Start.game.world.getBlock(b.x+1, b.y-1));
		return blocks;
	}
	public boolean letsLightThrough() {
		return !hasHitbox();
	}
	public abstract void loadTexture();
		//image = Game.images.blocks.get(type);
	public void drawLight(Graphics g) {
		int size = Game.blockSize;
		if ((x*size)-Game.scrollX>-Game.blockSize && (x*size)-Game.scrollX<Start.screenSize.getWidth()) {
			if ((y*size)-Game.scrollY>-Game.blockSize && (y*size)-Game.scrollY<Start.screenSize.getHeight()) {
				if (light > 1) {
					g.setColor(new Color(0f, 0f, 0f, 0f));
				} else {
					g.setColor(new Color(0f, 0f, 0f, 1-light));
				}
				g.fillRect((x*size)-Game.scrollX, (y*size)-Game.scrollY,size, size);
			}
		}
	}
//	public void fromString(String block) {
//		ArrayList<Character> charList = new ArrayList<>();
//		ArrayList<Character> charListWithoutColons = new ArrayList<>();
//		for (int i = 7; i<block.length()-7; i++) {
//			charList.add(block.charAt(i));
//			if (block.charAt(i) != ':') {
//				charListWithoutColons.add(block.charAt(i));
//			}
//		}
//		ArrayList<Integer> colonPlaces = new ArrayList<>();
//		int i = 7;
//		for (char c : charList) {
//			if (c == ':') {
//				colonPlaces.add(i);
//			}
//			i++;
//		}
//		String pwc = String.valueOf(charListWithoutColons.toArray());
//		int x = Integer.valueOf(pwc.substring(7, colonPlaces.get(0)));
//		int y = Integer.valueOf(pwc.substring(colonPlaces.get(0), colonPlaces.get(1)));
//		String type = pwc.substring(colonPlaces.get(1), colonPlaces.get(2));
//		try {
//			Class entityClass = Game.er.getEntity(type);
//			try {
//				Entity e;
//				if (type == "item") {
//					String itemType = pwc.substring(colonPlaces.get(2), colonPlaces.get(3));
//					e = (Entity) entityClass.getConstructor().newInstance(x, y, Start.game.world, itemType);
//				} else {
//					e = (Entity) entityClass.getConstructor().newInstance(x, y, Start.game.world);
//				}
//				return e;
//			} catch (InstantiationException e) {
//			} catch (IllegalAccessException e) {
//			} catch (IllegalArgumentException e) {
//			} catch (InvocationTargetException e) {
//			}
//		} catch (NoSuchMethodException e) {
//		} catch (SecurityException e) {
//		} catch (NullPointerException e) {
//		}
//		return null;
//	}
	public boolean canBeReplaced() {
		return false;
	}
}
