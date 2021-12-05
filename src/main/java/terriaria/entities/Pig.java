package terriaria.entities;

import java.awt.Point;
import java.beans.ConstructorProperties;
import java.util.ArrayList;

import terriaria.Game;
import terriaria.Player;
import terriaria.Start;
import terriaria.World;

public class Pig extends Entity {
	public Pig(int x, int y, World world) {
		super(x, y, world);
		this.maxhealth = 10;
		loadImage(0);
	}
	@ConstructorProperties({"x", "y"})
	public Pig(int x, int y) {
		super(x, y);
		this.maxhealth = 10;
		loadImage(0);
	}
	@Override
	public void onTick() {
		passiveTick();
	}
	@Override
	public ArrayList<Point> getPoints() {
		ArrayList<Point> p = new ArrayList<Point>();
		p.add(new Point(x, y));
		p.add(new Point((int) (x+Game.blockSize*1.8), y));
		p.add(new Point((int) (x+Game.blockSize*0.9), y));
		p.add(new Point((int) (x+Game.blockSize*0.9), (int) (y+Game.blockSize)));
		p.add(new Point(x, (int) (y+Game.blockSize)));
		p.add(new Point((int) (x+Game.blockSize*1.8), (int) (y+Game.blockSize)));
		return p;
	}
	@Override
	public void onAttack(Player p, terriaria.items.Item item) {
		onAttack(p, item);
		Start.game.playSound("/sounds/Pig_idle1.wav");
	}
	@Override
	public void onDeath() {
		world.spawnEntity(new Item((x*Game.blockSize)+Game.blockSize/4,(y*Game.blockSize)+Game.blockSize/2, "black_crystal", world));
	}
}
