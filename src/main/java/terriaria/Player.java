package terriaria;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonIgnore;

import terriaria.blocks.Block;
import terriaria.blocks.LavaBlock;
import terriaria.blocks.WaterBlock;
import terriaria.entities.Item;
import terriaria.entities.Pig;

public class Player {
	public int x;
	public int y;
	public int inAir = 0;
	public float Sx = 0;
	public float Sy = 0;
	public int spawnx;
	public int spawny;
	public int baseSpeed = 10;
	public int speed = baseSpeed;
	public int maxhealth = 20;
	public float bubbles = 20;
	public float drownTime = 100;
	public float health = maxhealth;
	public int lasty;
	public Inventory inv = new Inventory();
	@JsonIgnore
	public World w;
	public ArrayList<Recipe> recipies = new ArrayList<>();
	public Player(int x, int y, World w) {
		this.x = x;
		this.y = y;
		this.spawnx = x;
		this.spawny = y;
		this.w = w;
		this.lasty = y;
		move(0,0);
		w.entities.add(new Pig(x, y, w));
	}
	@ConstructorProperties({"x", "y"})
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
		this.spawnx = x;
		this.spawny = y;
		this.lasty = y;
		//move(0,0);
		//w.entities.add(new Pig(x, y, w));
	}
	public void update(Graphics g, ImageObserver ob) {
		Image image = Images.player;
		int size = Game.blockSize;
		g.drawImage(image, x-Game.scrollX, y-Game.scrollY,(int)(size*0.6), (int)(size*1.8), ob);
	}
	public void moveandstuff() {
//		if (inv.items.size() < 35) {
//			for (int i = 0; i<36-inv.items.size(); i++) {
//				inv.items.add(null);
//				System.out.println(inv.items.size());
//			}
//		}
		Game.scrollX += (x - Game.scrollX-Start.screenSize.width/2)/10;
		Game.scrollY += (y - Game.scrollY-Start.screenSize.height/2)/10;
		if (Game.gm == 1) {
			if (inWater(getBlocks())) {
				Sy = Sy*0.5f;
				Sy += 0.5f;
				speed = 3;
				this.lasty = y;
			} else if (inLava(getBlocks())) {
				Sy = Sy*0.2f;
				Sy += 1f;
				speed = 2;
				health -= 0.1f/Game.dt;
				bubbles = 20;
				drownTime = 100;
			} else {
				Sy += 0.5f;
				speed = 4;
				bubbles = 20;
				drownTime = 100;
			}
			if (inWater(getHeadBlocks())) {
				bubbles -= 0.02f/Game.dt;
			}
			if (bubbles <= 0) {
				drownTime -= 1;
				if (drownTime < 0) {
					drownTime = 100;
					damage(1f);
				}
			}
		}
		move((int) Sx, (int)Sy);
		if (health <= 0) {
			die();
		}
	}
	public void move(int dx, int dy) {
		this.x += dx;
		if (isSuficating(getBlocks())) {
			while (isSuficating(getBlocks())) {
				if (dx > 0) {
					this.x -= 1;
				}
				if (dx < 0) {
					this.x += 1;
				}
				if (dx == 0) {
					break;
				}
			}
			Sx = 0;
		}
		if (dy != 0) {
			this.y += dy;
			if (isSuficating(getBlocks())) {
				while (isSuficating(getBlocks())) {
					if (dy > 0) {
						this.y -= 1;
					}
					if (dy < 0) {
						this.y += 1;
					}
				}
				if ((lasty-y)/Game.blockSize < -5) {
					if (Game.gm == 1) {
						if (!inWater(getBlocks())) {
							damage(-((lasty-y)/Game.blockSize)+5);
						}
					}
				}
				Sy = 0;
				if (dy >= 0) {
					inAir = 0;
					lasty = y;
				}
			} else {
				inAir += 1;
			}
		}
	}
	public ArrayList<Block> getBlocks() {
		Point tl = new Point(x, y);
		Point tr = new Point((int) (x+Game.blockSize*0.6), y);
		Point bl = new Point(x, (int) (y+Game.blockSize*1.8));
		Point br = new Point((int) (x+Game.blockSize*0.6), (int) (y+Game.blockSize*1.8));
		Point ml = new Point(x, y+Game.blockSize);
		Point mr = new Point((int) (x+Game.blockSize*0.6), y+Game.blockSize);
		ArrayList<Block> blocks = new ArrayList<Block>();
		try {
			blocks.add(w.getBlock((int)tl.x/Game.blockSize, (int)tl.y/Game.blockSize));
			blocks.add(w.getBlock((int)tr.x/Game.blockSize, (int)tr.y/Game.blockSize));
			blocks.add(w.getBlock((int)bl.x/Game.blockSize, (int)bl.y/Game.blockSize));
			blocks.add(w.getBlock((int)br.x/Game.blockSize, (int)br.y/Game.blockSize));
			blocks.add(w.getBlock((int)mr.x/Game.blockSize, (int)mr.y/Game.blockSize));
			blocks.add(w.getBlock((int)ml.x/Game.blockSize, (int)mr.y/Game.blockSize));
		} catch (NullPointerException e) {
		}
		return blocks;
	}
	public ArrayList<Block> getHeadBlocks() {
		Point tl = new Point(x, y);
		Point tr = new Point((int) (x+Game.blockSize*0.6), y);
		ArrayList<Block> blocks = new ArrayList<Block>();
		try {
			blocks.add(w.getBlock((int)tl.x/Game.blockSize, (int)tl.y/Game.blockSize));
			blocks.add(w.getBlock((int)tr.x/Game.blockSize, (int)tr.y/Game.blockSize));
		} catch (NullPointerException e) {
		}
		return blocks;
	}
	public boolean isSuficating(ArrayList<Block> blocks) {
		for (Block b : blocks) {
			if (b.hasHitbox()) {
				return true;
			}
		}
		return false;
	}
	public boolean inWater(ArrayList<Block> blocks) {
		for (Block b : blocks) {
			if (b instanceof WaterBlock) {
				return true;
			}
		}
		return false;
	}
	public boolean inLava(ArrayList<Block> blocks) {
		for (Block b : blocks) {
			if (b instanceof LavaBlock) {
				return true;
			}
		}
		return false;
	}
	public void die() {
		for (ItemStack item : inv.items) {
			if (item != null) {
				w.entities.add(new Item(x+new Random().nextInt(10)-5, y+new Random().nextInt(10)-5, item.type, w));
			}
		}
		inv.items = new ItemStack[36];
		this.x = spawnx;
		y = spawny;
		health = maxhealth;
	}
	public boolean touchingBlock(ArrayList<Block> blocks, Class<? extends Block> c) {
		for (Block b : blocks) {
			if (c.isInstance(b)) {
				return true;
			}
		}
		return false;
	}
	@Override
	public String toString() {
		return String.format("player:%s:%s:%s:%s:%s:%s:%s:%s:%s:%s:%s:%s:%s:%s", x, y, inAir, Sx, Sy, spawnx, spawny, baseSpeed, speed, maxhealth, bubbles, health, lasty, w);
	}
	public static Player fromString(String player) {
		ArrayList<Character> charList = new ArrayList<>();
		ArrayList<Character> charListWithoutColons = new ArrayList<>();
		for (int i = 7; i<player.length()-7; i++) {
			charList.add(player.charAt(i));
			if (player.charAt(i) != ':') {
				charListWithoutColons.add(player.charAt(i));
			}
		}
		ArrayList<Integer> colonPlaces = new ArrayList<>();
		int i = 7;
		for (char c : charList) {
			if (c == ':') {
				colonPlaces.add(i);
			}
			i++;
		}
		String pwc = String.valueOf(charListWithoutColons.toArray());
		int x = Integer.valueOf(pwc.substring(7, colonPlaces.get(0)));
		int y = Integer.valueOf(pwc.substring(colonPlaces.get(0), colonPlaces.get(1)));
		Player p = new Player(x, y, Start.game.world);
		p.inAir = Integer.valueOf(pwc.substring(colonPlaces.get(1), colonPlaces.get(2)));
		p.Sx = Integer.valueOf(pwc.substring(colonPlaces.get(2), colonPlaces.get(3)));
		p.Sy = Integer.valueOf(pwc.substring(colonPlaces.get(3), colonPlaces.get(4)));
		p.spawnx = Integer.valueOf(pwc.substring(colonPlaces.get(4), colonPlaces.get(5)));
		p.spawny = Integer.valueOf(pwc.substring(colonPlaces.get(5), colonPlaces.get(6)));
		p.baseSpeed = Integer.valueOf(pwc.substring(colonPlaces.get(6), colonPlaces.get(7)));
		p.speed = Integer.valueOf(pwc.substring(colonPlaces.get(7), colonPlaces.get(8)));
		p.maxhealth = Integer.valueOf(pwc.substring(colonPlaces.get(8), colonPlaces.get(9)));
		p.bubbles = Integer.valueOf(pwc.substring(colonPlaces.get(9), colonPlaces.get(10)));
		p.health = Integer.valueOf(pwc.substring(colonPlaces.get(10), colonPlaces.get(11)));
		p.lasty = Integer.valueOf(pwc.substring(colonPlaces.get(11), colonPlaces.get(12)));
		return p;
	}
	public void damage(float amount) {
		Start.game.playSound("/sounds/Player_hurt1.wav");
		health -= amount;
	}
}
