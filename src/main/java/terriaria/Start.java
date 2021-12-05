package terriaria;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.Timer;

import terriaria.menu.Menu;

public class Start {
	public static JFrame frame;
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static Game game;
	public static Menu menu;
	public static Timer t;
	public static void main(String[] args) {
		frame = new JFrame("ooga booga");
		menu = new Menu();
		frame.add(menu);
		frame.setSize((int)screenSize.getWidth(), (int)screenSize.getHeight());
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		t = new Timer(1, menu);
		t.start();
	}
	public static void start(File f) {
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		frame = new JFrame("ooga booga");
		game = new Game(frame, f);
		frame.add(game);
		frame.setSize((int)screenSize.getWidth(), (int)screenSize.getHeight());
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		t = new Timer(1, game);
		t.start();
	}
}
