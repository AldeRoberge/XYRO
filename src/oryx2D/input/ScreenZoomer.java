package oryx2D.input;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import oryx2D.graphics.Screen;

public class ScreenZoomer implements MouseWheelListener {

	Screen screen;

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int notches = e.getWheelRotation();
	}

}
