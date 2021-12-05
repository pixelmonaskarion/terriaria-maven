package terriaria.particles;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;

import terriaria.Game;
import terriaria.Start;

public class Particle {
	public int x;
	public int y;
	public Image image;
	public int ticks;
	public int maxTicks;
	public Particle(int x, int y, int ticks) {
		this.x = x;
		this.y = y;
		this.ticks = ticks;
		this.maxTicks = ticks;
		Start.game.world.addParticle(this);
	}
	public void update(Graphics g, ImageObserver ob) {
		if (x-Game.scrollX>0 && x-Game.scrollX<Start.screenSize.getWidth()) {
			if (y-Game.scrollY>0 && y-Game.scrollY<Start.screenSize.getHeight()) {
				AlphaComposite transparent = java.awt.AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)ticks/(float)maxTicks);
				((Graphics2D)g).setComposite(transparent);
				g.drawImage(image, x-Game.scrollX, y-Game.scrollY, image.getWidth(ob), image.getHeight(ob), ob);
				AlphaComposite full = java.awt.AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F);
				((Graphics2D)g).setComposite(full);
				this.ticks -= 1;
				if (ticks <= 0) {
					Start.game.world.removeParticle(this);
				}
			}
		}
	}
}
