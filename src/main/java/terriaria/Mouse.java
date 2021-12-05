package terriaria;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Mouse implements MouseListener {
	public ArrayList<Integer> buttons = new ArrayList<Integer>();
	@Override
	public void mouseClicked(MouseEvent e) {
		Game.mouseclicked = (Integer)e.getButton();
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		//System.out.println(e.getButton());
		buttons.remove((Integer)e.getButton());
	}
	@Override
	public void mousePressed(MouseEvent e) {
		if (!buttons.contains(e.getButton())) {
			buttons.add(e.getButton());
//			System.out.println(String.valueOf(e.getButton()));
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
