package terriaria;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

public class Toast {
	public int ticks;
	public Image image;
	public String text;
	public Toast(int ticks, Image image, String text) {
		this.image = image;
		this.ticks = ticks;
		this.text = text;
	}
	public void update(Graphics g, ImageObserver ob) {
		ticks -= 1;
		if (ticks > 0) {
			g.setColor(Color.getHSBColor(1f, 0f, 1f));
			g.fillRect(0, 0, 10+g.getFontMetrics().stringWidth(text)+10+image.getWidth(ob)+10, image.getHeight(ob)+20);
			g.setColor(Color.getHSBColor(1f, 0f, 0f));
			g.drawString(text, 10, (image.getHeight(ob)+20)-g.getFontMetrics().getHeight()/2+10);
			g.drawImage(image, 10+g.getFontMetrics().stringWidth(text)+10, 10, image.getWidth(ob), image.getHeight(ob), ob);
		}
	}
}
