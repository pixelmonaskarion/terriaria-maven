package terriaria.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import terriaria.Game;
import terriaria.Player;
import terriaria.Start;
import terriaria.World;
import terriaria.blocks.Block;
import terriaria.blocks.LavaBlock;
import terriaria.blocks.WaterBlock;
@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME, 
		include = JsonTypeInfo.As.PROPERTY, 
		property = "type")
@JsonSubTypes({
	@Type(value = Item.class, name = "item"),
	@Type(value = Pig.class, name = "pig")
})
public class Entity {
	public int x;
	public int y;
	public float Sx = 0;
	public float Sy = 0;
	@JsonIgnore
	public World world;
	@JsonIgnore
	public Image image;
	public int baseSpeed = 10;
	public int speed = baseSpeed;
	public int maxhealth = 20;
	public float bubbles = 20;
	public int lasty;
	public int moveX = 0;
	public int moveY = 0;
	public char facing = 'r';
	public float health = maxhealth;
	public Entity(int x, int y, World world) {
		this.x = x;
		this.y = y;
		this.world = world;
	}
	@ConstructorProperties({"x", "y"})
	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void update(Graphics g, ImageObserver ob) {
		int xSize = getPoints().get(getPoints().size()-1).x-x;
		int ySize = getPoints().get(getPoints().size()-1).y-y;
		if (x-Game.scrollX>-Game.blockSize && x-Game.scrollX<Start.screenSize.getWidth()) {
			if (y-Game.scrollY>-Game.blockSize && y-Game.scrollY<Start.screenSize.getHeight()) {
				if (facing == 'r') {
					g.drawImage(image, x-Game.scrollX, y-Game.scrollY,xSize, ySize, ob);
				} else {
					g.drawImage(image, x-Game.scrollX+xSize, y-Game.scrollY,-xSize, ySize, ob);
				}
				if (health != maxhealth) {
					g.setColor(new Color(1f, 1f, 0f, 0.5f));
					g.fillRect(x, y, 100, 10);
				}
			}
		}
	}
	public void moveandstuff() {
		if (inWater(getBlocks())) {
			Sy = Sy*0.5f;
			Sy += 0.5f;
			speed = 3;
			this.lasty = y;
		} else if (inLava(getBlocks())) {
			Sy = Sy*0.2f;
			Sy += 1f;
			speed = 2;
			health -= 0.1f;
			bubbles = 20;
		} else {
			Sy += 0.5f;
			speed = 4;
			bubbles = 20;
		}
		if (moveX != 0) {
			int amount = moveX/Math.abs(moveX);
			moveX -= amount;
			move(amount, 0);
		}
		if (moveY != 0) {
			int amount = moveY/Math.abs(moveY);
			moveY -= amount;
			move(0, amount);
		}
		move((int) Sx, (int)Sy);
		if (Game.mouseclicked == 1) {
			Point mouse = MouseInfo.getPointerInfo().getLocation();
			int mx = (int)((mouse.x));
			int my = (int)((mouse.y))-25;
			if (mx > x-Game.scrollX && mx < getPoints().get(getPoints().size()-1).x-Game.scrollX) {
				if (my > y-Game.scrollY && my < getPoints().get(getPoints().size()-1).y-Game.scrollY) {
					attack(Game.p);
				}
			}
		}
		if (Game.mouseclicked == 3) {
			Point mouse = MouseInfo.getPointerInfo().getLocation();
			int mx = (int)((mouse.x));
			int my = (int)((mouse.y));
			if (mx > x-Game.scrollX && mx < getPoints().get(getPoints().size()-1).x-Game.scrollX) {
				if (my > y-Game.scrollY && my < getPoints().get(getPoints().size()-1).y-Game.scrollY) {
					use(Game.p);
				}
			}
		}
		onTick();
		if (health <= 0) {
			onDeath();
			this.world.deleteEntity(this);
		}
	}
	public void use(Player p) {
	}
	public void attack(Player p) {
	}
	public void onTick() {
	}
	public void onDeath() {
		
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
				Sy = 0;
			}
		}
	}
	public ArrayList<Block> getBlocks() {
		ArrayList<Block> blocks = new ArrayList<Block>();
		for (Point p : getPoints()) {
			try {
				blocks.add(this.world.getBlock((int)p.x/Game.blockSize, (int)p.y/Game.blockSize));
			} catch (NullPointerException e) {
			}
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
	public boolean touchingBlock(ArrayList<Block> blocks, Class<? extends Block> c) {
		for (Block b : blocks) {
			if (c.isInstance(b)) {
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
	public void loadImage(int type) {
		this.image = Game.images.entities.get(type);
	}
	
	
	//ai
	
	
	public void passiveTick() {
		if (new Random().nextInt(100) == 0) {
			lookRandom();
		}
		if (new Random().nextInt(20) == 0) {
			moveForward();
		}
		if (inWater(getBlocks())) {
			moveY = -Game.blockSize;
		}
	}
	public void moveForward() {
		if (facing == 'r') {
			moveX = Game.blockSize;
		}
		if (facing == 'l') {
			moveX = -Game.blockSize;
		}
	}
	public void lookRandom() {
		if (new Random().nextBoolean()) {
			facing = 'l';
		} else {
			facing = 'r';
		}
	}
	public ArrayList<Point> getPoints() {
		ArrayList<Point> p = new ArrayList<Point>();
		p.add(new Point(x, y));
		p.add(new Point((int) (x+Game.blockSize*0.5), y));
		p.add(new Point(x, (int) (y+Game.blockSize*0.5)));
		p.add(new Point((int) (x+Game.blockSize*0.5), (int) (y+Game.blockSize*0.5)));
		return p;
	}
	public void onAttack(Player p, terriaria.items.Item item) {
		if (item != null) {
			this.health -= item.damage;
		}
	}
	@Override
	public String toString() {
		return String.format("entity:%s:%s:%s", x, y, Game.er.getName(this.getClass()));
	}
}
