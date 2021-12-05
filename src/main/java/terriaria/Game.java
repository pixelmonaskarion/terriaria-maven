package terriaria;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import terriaria.blocks.AirBlock;
import terriaria.blocks.BlackCrystalOreBlock;
import terriaria.blocks.CaveAirBlock;
import terriaria.blocks.CobbleStoneBlock;
import terriaria.blocks.CraftingTableBlock;
import terriaria.blocks.DeepCobbleStoneBlock;
import terriaria.blocks.DeepStoneBlock;
import terriaria.blocks.DiamondOreBlock;
import terriaria.blocks.DirtBlock;
import terriaria.blocks.GoldOreBlock;
import terriaria.blocks.GrassBlock;
import terriaria.blocks.IronOreBlock;
import terriaria.blocks.LavaBlock;
import terriaria.blocks.LeafBlock;
import terriaria.blocks.MagmaOreBlock;
import terriaria.blocks.PlankBlock;
import terriaria.blocks.SandBlock;
import terriaria.blocks.StoneBlock;
import terriaria.blocks.TorchBlock;
import terriaria.blocks.WaterBlock;
import terriaria.blocks.WhiteCrystalOreBlock;
import terriaria.blocks.WoodBlock;
import terriaria.entities.Item;
import terriaria.entities.Pig;
import terriaria.items.BlackCrystalItem;
import terriaria.items.CobblestoneItem;
import terriaria.items.CraftingTableItem;
import terriaria.items.DeepCobblestoneItem;
import terriaria.items.DeepStoneItem;
import terriaria.items.DiamondItem;
import terriaria.items.DirtItem;
import terriaria.items.GoldOreItem;
import terriaria.items.IronOreItem;
import terriaria.items.LeavesItem;
import terriaria.items.MagmaItem;
import terriaria.items.SandItem;
import terriaria.items.StoneItem;
import terriaria.items.TorchItem;
import terriaria.items.WhiteCrystalItem;
import terriaria.items.WoodItem;
import terriaria.items.WoodPickaxeItem;
import terriaria.items.WoodPlanksItem;
import terriaria.menu.Menu;
import terriaria.registries.BlockRegistry;
import terriaria.registries.EntityRegistry;
import terriaria.registries.ImageRegistry;
import terriaria.registries.ItemRegistry;

@SuppressWarnings("serial")
public class Game extends JPanel implements ActionListener{
	public static Images images;
	public JFrame frame;
	public World world;
	public static int mouseclicked;
	public static Keys keys;
	public static int blockSize = 30;
	public static int scrollX;
	public static int scrollY;
	public static float skylight = 1f;
	public static int gm = 0;
	public static Player p;
	public static Mouse m;
	public File gamefile;
	public static BlockRegistry br = new BlockRegistry();
	public static ItemRegistry ir = new ItemRegistry();
	public static ImageRegistry imr = new ImageRegistry();
	public static EntityRegistry er = new EntityRegistry();
	public static boolean inventoryOpen = false;
	public static ItemStack handItem;
	public static int slot = 1;
	public static ArrayList<Toast> toasts = new ArrayList<Toast>();
	public static WheelListener wheel = new WheelListener();
	public static boolean scratchtextures = false;
	public boolean saving = false;
	public static int dt = 1;
	public long oldTime = 0;
	public long nowTime = 0;
	public void newGame() {
		world = new World();
		int col = ((Start.screenSize.width/2)+250*blockSize)/blockSize;
		try {
			p = new Player((Start.screenSize.width/2)+250*blockSize, (world.getTopBlock(col).y-2)*blockSize, world);
		} catch (NullPointerException e) {
			p = new Player((Start.screenSize.width/2)+250*blockSize, 10*blockSize, world);
		}
		scrollX = p.x - Start.screenSize.width/2;
		scrollY = p.y - Start.screenSize.height/2;
	}
	public Game(JFrame frame, File gamefile) {
		this.gamefile = gamefile;
		this.frame = frame;
		keys = new Keys();
		m = new Mouse();
		this.frame.addKeyListener(keys);
		this.frame.addMouseListener(m);
		this.frame.addMouseWheelListener(wheel);
		this.frame.setCursor(java.awt.Toolkit.getDefaultToolkit().createCustomCursor((Image)new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), getLocation(), "hiden cursor"));
		register();
		images = new Images(scratchtextures);
		
		 if (gamefile != null) { ObjectMapper mapper = new ObjectMapper(); try { try {
		 Scanner WorldReader = new Scanner(new File(this.gamefile, "world.txt"));
		 Scanner playerReader = new Scanner(new File(this.gamefile, "player.txt"));
		 world = mapper.readValue(WorldReader.nextLine(), World.class); p =
		 mapper.readValue(playerReader.nextLine(), Player.class); p.w = world; scrollX
		 = p.x - Start.screenSize.width/2; scrollY = p.y - Start.screenSize.height/2;
		 world.setWorlds(); WorldReader.close(); } catch (FileNotFoundException e) {
		 e.printStackTrace(); } } catch (JsonMappingException e) {
		 e.printStackTrace(); } catch (JsonProcessingException e) {
		e.printStackTrace(); } } else { newGame(); }
		 
		newGame();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				save();
			}
		});
	}
	public void register() {
		br.registerBlock(AirBlock.class, "air");
		br.registerBlock(BlackCrystalOreBlock.class, "black_crystal_ore");
		br.registerBlock(CaveAirBlock.class, "cave_air");
		br.registerBlock(CobbleStoneBlock.class, "cobble_stone");
		br.registerBlock(CraftingTableBlock.class, "crafting_table");
		br.registerBlock(DeepCobbleStoneBlock.class, "deep_cobble_stone");
		br.registerBlock(DeepStoneBlock.class, "deep_stone");
		br.registerBlock(DiamondOreBlock.class, "diamond_ore");
		br.registerBlock(DirtBlock.class, "dirt");
		br.registerBlock(GoldOreBlock.class, "gold_ore");
		br.registerBlock(GrassBlock.class, "grass_block");
		br.registerBlock(IronOreBlock.class, "iron_ore");
		br.registerBlock(LavaBlock.class, "lava");
		br.registerBlock(LeafBlock.class, "leaf_block");
		br.registerBlock(MagmaOreBlock.class, "magma_ore");
		br.registerBlock(PlankBlock.class, "planks");
		br.registerBlock(SandBlock.class, "sand");
		br.registerBlock(StoneBlock.class, "stone");
		br.registerBlock(TorchBlock.class, "torch");
		br.registerBlock(WaterBlock.class, "water");
		br.registerBlock(WhiteCrystalOreBlock.class, "white_crystal_ore");
		br.registerBlock(WoodBlock.class, "wood");
		ir.registerItem(BlackCrystalItem.class, "black_crystal");
		ir.registerItem(CobblestoneItem.class, "cobble_stone");
		ir.registerItem(CraftingTableItem.class, "crafting_table");
		ir.registerItem(DeepCobblestoneItem.class, "deep_cobblestone");
		ir.registerItem(DeepStoneItem.class, "deep_stone");
		ir.registerItem(DiamondItem.class, "diamond");
		ir.registerItem(DirtItem.class, "dirt");
		ir.registerItem(GoldOreItem.class, "gold_ore");
		ir.registerItem(IronOreItem.class, "iron_ore");
		ir.registerItem(LeavesItem.class, "leaves");
		ir.registerItem(MagmaItem.class, "magma");
		ir.registerItem(SandItem.class, "sand");
		ir.registerItem(StoneItem.class, "stone");
		ir.registerItem(TorchItem.class, "torch");
		ir.registerItem(WhiteCrystalItem.class, "white_crystal");
		ir.registerItem(WoodItem.class, "wood");
		ir.registerItem(WoodPickaxeItem.class, "wood_pickaxe");
		ir.registerItem(WoodPlanksItem.class, "planks");
		er.registerEntity(Pig.class, "pig");
		er.registerEntity(Item.class, "item");
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}
	@Override
	protected void paintComponent(Graphics g) {
		oldTime = nowTime;
		nowTime = System.currentTimeMillis();
		dt = (int) (nowTime - oldTime)/10;
		if (dt <= 0) {
			dt = 1;
		}
		if (!saving) {
			gameloop(g);
		} else {
			g.setColor(new Color(0f,0f,0f,0.5f));
			g.drawRect(0, 0, Start.screenSize.width, Start.screenSize.height);
			g.drawString("saving", (Start.screenSize.width/2)-g.getFontMetrics().stringWidth("saving")/2, Start.screenSize.height/2);
		}
	}
	public void gameloop(Graphics g) {
		super.paintComponent(g);
//		int mouseX = MouseInfo.getPointerInfo().getLocation().x - this.getLocationOnScreen().x;
//		int mouseY = MouseInfo.getPointerInfo().getLocation().y - this.getLocationOnScreen().y;
		Point mouse = new Point(MouseInfo.getPointerInfo().getLocation().x - this.getLocationOnScreen().x, MouseInfo.getPointerInfo().getLocation().y - this.getLocationOnScreen().y);
		g.setColor(/*Color.getHSBColor(new Random().nextInt(101), new Random().nextInt(101), new Random().nextInt(101))*/new Color(126, 227, 255));
		g.fillRect(0, 0, Start.screenSize.width, Start.screenSize.height);
		if (keys.keys.contains("a") || keys.keys.contains("A")) {
			p.move(-p.speed, 0);
		}
		if (keys.keys.contains("d") || keys.keys.contains("D")) {
			p.move(p.speed, 0);
		}
		if (gm == 0) {
			if (keys.keys.contains("w") || keys.keys.contains("W")) {
				p.move(0, -p.speed);
			}
			if (keys.keys.contains("s") || keys.keys.contains("S")) {
				p.move(0, p.speed);
			}
			if (!inventoryOpen) {
				if (m.buttons.contains(1)) {
					int mx = (int)((mouse.x+scrollX)/blockSize);
					int my = (int)((mouse.y+scrollY)/blockSize);
					world.getBlock(mx, my).damage((int)Double.POSITIVE_INFINITY);
				}
			}
		}
		if (gm == 1) {
			if (keys.keys.contains("w") || keys.keys.contains("W")) {
				if (p.inAir == 0) {
					p.Sy -= 10;
				}
				if (p.inLava(p.getBlocks()) || p.inWater(p.getBlocks())) {
					p.Sy -= p.speed*3;
				}
			}
			if (keys.keys.contains("s") || keys.keys.contains("S")) {
				if (p.inLava(p.getBlocks()) || p.inWater(p.getBlocks())) {
					p.move(0, p.speed);
				}
			}
			if (!inventoryOpen) {
				if (m.buttons.contains(1)) {
					int mx = (int)((mouse.x+scrollX)/blockSize);
					int my = (int)((mouse.y+scrollY)/blockSize);
					int px = p.x-Game.scrollX;
					int py = p.y-Game.scrollY;
					int distance = (int) Math.sqrt(Math.pow((px-mouse.x)+(py-mouse.y), 2));
					if (distance < 5*blockSize) {
						try {
							Class<? extends terriaria.items.Item> itemClass = ir.getItem(p.inv.items[slot-1].type);
							try {
								try {
									terriaria.items.Item item = (terriaria.items.Item) itemClass.getConstructor().newInstance();
									if (item.damageBlock(world.getBlock(mx, my))) {
										p.inv.items[slot-1].amount -= 1;
										if (p.inv.items[slot-1].amount <= 0) {
											p.inv.items[slot-1] = null;
										}
									}
								} catch (NullPointerException e) {
									world.getBlock(mx, my).damage(1);
								}
							} catch (InstantiationException e) {
							} catch (IllegalAccessException e) {
							} catch (IllegalArgumentException e) {
							} catch (InvocationTargetException e) {
							}
						} catch (NoSuchMethodException e) {
						} catch (SecurityException e) {
						} catch (NullPointerException e) {
							world.getBlock(mx, my).damage(1);
						}
					}
				}
			}
		}
		try {
			p.moveandstuff();
		} catch (NullPointerException e) {
			System.exit(0);
		}
		world.updateAll(g, this.frame);
		for (int i = 1; i<10; i++) {
			if (keys.keys.contains(String.valueOf(i))) {
				slot = i;
			}
		}
		if (inventoryOpen) {
			g.setColor(new Color(0f, 0f, 0f, 0.5f));
			g.fillRect(0, 0, Start.screenSize.width, Start.screenSize.height);
			Image invImage = images.inventory;
			g.drawImage(invImage, (Start.screenSize.width/2)-invImage.getWidth(this), (Start.screenSize.height/2)-invImage.getHeight(this), invImage.getWidth(this)*2, invImage.getHeight(this)*2, this);
			for (int i = 0; i<36; i++) {
				ItemStack item = null;
				try {
					item = p.inv.items[i];
				} catch (IndexOutOfBoundsException e){
					
				}
				int mx = MouseInfo.getPointerInfo().getLocation().x;
				int my = MouseInfo.getPointerInfo().getLocation().y-30;
				if (i < 9) {
					if (item != null) {
						g.drawImage(images.items.get(item.type), i*72+((Start.screenSize.width/2)-invImage.getWidth(this))+32, ((Start.screenSize.height/2)-invImage.getHeight(this))+568, 64, 64, this);
						g.drawString(String.valueOf(item.amount), i*72-g.getFontMetrics().stringWidth(String.valueOf(p.inv.items[i].amount)), ((Start.screenSize.height/2)-invImage.getHeight(this))+568);
					}
					if (mx > i*72+((Start.screenSize.width/2)-invImage.getWidth(this))+28 && mx < i*72+((Start.screenSize.width/2)-invImage.getWidth(this))+28+72) {
						if (my > ((Start.screenSize.height/2)-invImage.getHeight(this))+564 && my < ((Start.screenSize.height/2)-invImage.getHeight(this))+564+72) {
							if (mouseclicked == 1) {
								p.inv.items[i] = handItem;
								handItem = item;
								mouseclicked = -1;
							}
						}
					}
				} else {
					if (item != null) {
						g.drawImage(images.items.get(item.type), (i%9)*72+((Start.screenSize.width/2)-invImage.getWidth(this))+28, ((Start.screenSize.height/2)-invImage.getHeight(this))+260+(((int)(i/9))*72), 72, 72, this);
					}
					if (mx > (i%9)*72+((Start.screenSize.width/2)-invImage.getWidth(this))+28 && mx < (i%9)*72+((Start.screenSize.width/2)-invImage.getWidth(this))+28+72) {
						if (my > ((Start.screenSize.height/2)-invImage.getHeight(this))+260+(((int)(i/9))*72) && my < ((Start.screenSize.height/2)-invImage.getHeight(this))+260+(((int)(i/9))*72)+72) {
							if (mouseclicked == 1) {
								p.inv.items[i] = handItem;
								handItem = item;
								mouseclicked = -1;
							}
						}
					}
				}
			}
			int x = 0;
			for (Recipe r : p.recipies) {
				if (r.canMake(p.inv)) {
					g.drawImage(Game.images.green_slot, 50+x, (Start.screenSize.height/2)-invImage.getHeight(this), 100, 100, this);
				} else {
					g.drawImage(Game.images.red_slot, 50+x, (Start.screenSize.height/2)-invImage.getHeight(this), 100, 100, this);
				}
				g.drawImage(Game.images.items.get(r.output.type), 75+x, (Start.screenSize.height/2)-invImage.getHeight(this)+25, 50, 50, this);
				g.setColor(Color.white);
				g.drawString(String.valueOf(r.output.amount), 75+x-g.getFontMetrics().stringWidth(String.valueOf(r.output.amount)),  (Start.screenSize.height/2)-invImage.getHeight(this)+25);
				g.setColor(new Color(0, 0, 0, 0.5f));
				int mx = MouseInfo.getPointerInfo().getLocation().x;
				int my = MouseInfo.getPointerInfo().getLocation().y-30;
				if (mx < 160+x && mx > x+55) {
					if (my < (Start.screenSize.height/2)-invImage.getHeight(this)+100 && my > (Start.screenSize.height/2)-invImage.getHeight(this)) {
						g.fillRect(mx+10, my+20, 32*r.input.size(), 32);
						int i1 = 0;
						for (ItemStack input : r.input) {
							g.drawImage(images.items.get(input.type), 32*i1+mx+10, my+20, 30, 30, this);
							g.setColor(Color.white);
							g.drawString(String.valueOf(input.amount), 32*i1+mx+10, my+50);
							g.setColor(new Color(0, 0, 0, 0.5f));
							i1 += 1;
						}
						if (mouseclicked == 1 && r.canMake(p.inv)) {
							boolean failed = false;
							for (ItemStack type : r.input) {
								failed = !p.inv.remove(type);
							}
							if (!failed) {
								p.inv.add(new ItemStack(r.output));
							}
						}
					}
				}
				x += 120;
			}
			if (handItem != null) {
				int mx = MouseInfo.getPointerInfo().getLocation().x;
				int my = MouseInfo.getPointerInfo().getLocation().y-30;
				g.drawImage(images.items.get(handItem.type), mx, my, 50, 50, this);
			}
		} else {
			if (handItem != null) {
				for (int i=0; i<handItem.amount; i++) {
					world.entities.add(new Item((p.x)+Game.blockSize/4,(p.y)+Game.blockSize/2, handItem.type, world));
				}
				handItem = null;
			}
		}
		int heartSize = 36;
		for (int i=0; i<9;i++) {
				g.drawImage(images.hotbarSlot, i*100, heartSize, 100, 100, this);
				try {
					g.drawImage(images.items.get(p.inv.items[i].type), i*100+10, heartSize+10, 80, 80, this);
					g.setFont(new Font(g.getFont().getName(), g.getFont().getStyle(), 30));
					g.setColor(Color.white);
					if (i+1 == slot) {
						g.drawImage(images.selectedSlot, i*100, heartSize, 100, 100, this);
					}
					g.drawString(String.valueOf(p.inv.items[i].amount), ((i+1)*100)-g.getFontMetrics().stringWidth(String.valueOf(p.inv.items[i].amount)), 100+heartSize);
				} catch (IndexOutOfBoundsException e) {
					if (i+1 == slot) {
						g.drawImage(images.selectedSlot, i*100, heartSize, 100, 100, this);
					}
				} catch (NullPointerException e) {
					if (i+1 == slot) {
						g.drawImage(images.selectedSlot, i*100, heartSize, 100, 100, this);
					}
				}
		}
		if (wheel.scrollAmount != 0) {
			slot += wheel.scrollAmount;
			if (slot > 9) {
				slot = 1;
			}
			if (slot < 1) {
				slot = 9;
			}
			wheel.scrollAmount = 0;
		}
		if (m.buttons.contains(3)) {
			int mx = (int)((mouse.x+scrollX)/blockSize);
			int my = (int)((mouse.y+scrollY)/blockSize);
			int px = p.x-Game.scrollX;
			int py = p.y-Game.scrollY;
			int distance = (int) Math.sqrt(Math.pow((px-mouse.x)+(py-mouse.y), 2));
			if (distance < 5*blockSize) {
				if (world.getBlock(mx, my).canBeReplaced()) {
					try {
						Class<? extends terriaria.items.Item> itemClass = ir.getItem(p.inv.items[slot-1].type);
						try {
							terriaria.items.Item item = (terriaria.items.Item) itemClass.getConstructor().newInstance();
							if (item.rightClickBlock(world.getBlock(mx, my))) {
								if (gm == 1) {
									p.inv.items[slot-1].amount -= 1;
									if (p.inv.items[slot-1].amount <= 0) {
										p.inv.items[slot-1] = null;
									}
								}
							}
						} catch (InstantiationException e) {
						} catch (IllegalAccessException e) {
						} catch (IllegalArgumentException e) {
						} catch (InvocationTargetException e) {
						}
					} catch (NoSuchMethodException e) {
					} catch (SecurityException e) {
					} catch (NullPointerException e) {
					}
				}
			}
		}
		if (gm == 1) {
			for (int i=0; i<10; i++) {
//				System.out.println(p.health);
				if (i*2 <= p.health) {
					if (!(p.health % 2 == 1) && i == p.health) {
						g.drawImage(images.heart, i*heartSize, 0, heartSize, heartSize, this);
					}
				} else {
					g.drawImage(images.heart_alpha, (i+1)*heartSize, 0, heartSize, heartSize, this);
				}
			}
			if (p.health % 2 == 1) {
				g.drawImage(images.heart_half, (int)(p.health/2)*heartSize+heartSize, 0, heartSize, heartSize, this);
			}
			if (p.bubbles != 20) {
				for (int i=0; i<(int)(p.bubbles/2); i++) {
					g.drawImage(images.bubble, i*heartSize+(heartSize*12), 0, heartSize, heartSize, this);
				}
				if ((int)p.bubbles % 2 == 1) {
					g.drawImage(images.bubble_half,((int)(p.bubbles/2)*heartSize)+(heartSize*12), 0, heartSize, heartSize, this);
				}
			}
		}
		if (toasts.size() > 0) {
			toasts.get(0).update(g, this);
			if (toasts.get(0).ticks <= -50) {
				toasts.remove(0);
			}
		}
		g.drawImage(images.mouse, mouse.x, mouse.y, this);
		mouseclicked = -1;//do this last
	}
	public static void switchTextures() {
		scratchtextures = !scratchtextures;
		images = new Images(scratchtextures);
		p.w.reloadTextures();
	}
	public void playSound(String file) {
		try {
			Clip sound = AudioSystem.getClip();
			sound.open(AudioSystem.getAudioInputStream(
					getClass().getResource(file)));
			sound.start();
		} 
		catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (LineUnavailableException e) { 
			e.printStackTrace();
		}
	}
	public void save() {
		System.out.println("saved");
		if (this.gamefile == null) {
			this.gamefile = new File(Menu.dir, "world.txt");
		} else {
			this.gamefile = new File(gamefile, "world.txt");
		}
		Save save = new Save();
		try {
			FileOutputStream outputStream = new FileOutputStream(this.gamefile);
			try {
				outputStream.write(save.saveWorld(this));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		File playerfile = new File(Menu.dir, "player.txt");
		try {
			FileOutputStream outputStream = new FileOutputStream(playerfile);
			try {
				outputStream.write(save.savePlayer(this));
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}