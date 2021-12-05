package terriaria;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class WheelListener implements MouseWheelListener{
	public int scrollAmount = 0;
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		scrollAmount = e.getWheelRotation();
	}
}
