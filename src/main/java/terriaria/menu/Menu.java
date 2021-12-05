package terriaria.menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JPanel;

import terriaria.Start;

@SuppressWarnings("serial")
public class Menu extends JPanel implements ActionListener{
	public boolean isLegit = true;
	public static File dir = new File(System.getProperty("user.home")+"/Documents/terraria");
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}
	public Menu() {
		if (!dir.exists()) {
			dir.mkdir();
		}
		dir = new File(dir, "/world");
		if (!dir.exists()) {
			dir.mkdir();
		}
	}
	@Override
	public void paintComponent(Graphics g) {
		if (isLegit) {
			super.paintComponent(g);
			g.setColor(Color.BLUE);
			g.fillRect(0, 0, Start.screenSize.width, Start.screenSize.height);
			File world = new File(dir, "world.txt");
			if (world.exists()) {
				Start.start(dir);
			} else {
				Start.start(null);
			}
			isLegit = false;
		}
	}
}
