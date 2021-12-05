package terriaria;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Keys extends KeyAdapter{
	public ArrayList<String> keys = new ArrayList<String>();
	public ArrayList<Integer> codes = new ArrayList<Integer>();
	@Override
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		if (!keys.contains(String.valueOf(e.getKeyChar()))){
			keys.add(String.valueOf(e.getKeyChar()));
		}
		if (!codes.contains(e.getKeyCode())){
			codes.add(e.getKeyCode());
		}
		if (e.getKeyChar() == 'e' || e.getKeyChar() == 'E') {
			Game.inventoryOpen = !Game.inventoryOpen;
		}
		if (e.getKeyChar() == 't' || e.getKeyChar() == 'T') {
			Game.switchTextures();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		super.keyPressed(e);
		if (keys.contains(String.valueOf(e.getKeyChar()))){
			keys.remove(String.valueOf(e.getKeyChar()));
		}
		if (codes.contains((Integer)e.getKeyCode())){
			codes.remove((Integer)e.getKeyCode());
		}
	}
}
