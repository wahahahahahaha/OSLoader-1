package loader.game.listeners;

import java.applet.Applet;
import java.awt.Canvas;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import loader.Settings;

public class InputListeners implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener, FocusListener {

	private boolean inputEnabled;
	private boolean hasFocus;
	public Applet client;

	public InputListeners(boolean inputEnabled, Applet client) {
		this.inputEnabled = inputEnabled;
		this.client = client;
	}

	// Listeners
	public void mouseClicked(MouseEvent e) {
		if (inputEnabled && client != null) {
			e.setSource(client);
			Canvas canvas = (Canvas) client.getComponent(0);
			if (canvas != null)
				canvas.dispatchEvent(e);
		}
	}

	public void mousePressed(MouseEvent e) {
		if (inputEnabled && client != null) {
			e.setSource(client);
			Canvas canvas = (Canvas) client.getComponent(0);
			if (canvas != null) {
				if (!hasFocus)
					canvas.requestFocus();

				canvas.dispatchEvent(e);
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (inputEnabled && client != null) {
			e.setSource(client);
			Canvas canvas = (Canvas) client.getComponent(0);
			if (canvas != null)
				canvas.dispatchEvent(e);
		}
	}

	public void mouseEntered(MouseEvent e) {
		e.setSource(client);
		if (inputEnabled && client != null) {
			Canvas canvas = (Canvas) client.getComponent(0);
			if (canvas != null)
				canvas.dispatchEvent(e);
		}
	}

	public void mouseExited(MouseEvent e) {
		e.setSource(client);
		if (inputEnabled && client != null) {
			Canvas canvas = (Canvas) client.getComponent(0);
			if (canvas != null)
				canvas.dispatchEvent(e);
		}
	}

	public void mouseDragged(MouseEvent e) {
		e.setSource(client);
		if (inputEnabled && client != null) {
			Canvas canvas = (Canvas) client.getComponent(0);
			if (canvas != null)
				canvas.dispatchEvent(e);
		}
	}

	public void mouseMoved(MouseEvent e) {
		e.setSource(client);
		if (inputEnabled && client != null) {
			Canvas canvas = (Canvas) client.getComponent(0);
			if (canvas != null)
				canvas.dispatchEvent(e);
		}
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		e.setSource(client);
		if (inputEnabled && client != null) {
			Canvas canvas = (Canvas) client.getComponent(0);
			if (canvas != null)
				canvas.dispatchEvent(e);
		}
	}

	public void keyTyped(KeyEvent e) {
		if (Settings.NEW_FKEYS) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_F2:
				//combat
				e.setKeyCode(KeyEvent.VK_F1);
				break;
			case KeyEvent.VK_F1:
				//inv
				e.setKeyCode(KeyEvent.VK_ESCAPE);
				break;
			case KeyEvent.VK_F3:
				//pray
				e.setKeyCode(KeyEvent.VK_F5);
				break;
			case KeyEvent.VK_F4:
				//magic
				e.setKeyCode(KeyEvent.VK_F6);
				break;

			case KeyEvent.VK_F5:
				//equip
				e.setKeyCode(KeyEvent.VK_F4);
				break;
			}
		}

		e.setSource(client);
		if (client != null) {
			Canvas canvas = (Canvas) client.getComponent(0);
			if (canvas != null)
				canvas.dispatchEvent(e);
		}
	}

	public void keyPressed(KeyEvent e) {
		if (Settings.NEW_FKEYS) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_F2:
				//combat
				e.setKeyCode(KeyEvent.VK_F1);
				break;
			case KeyEvent.VK_F1:
				//inv
				e.setKeyCode(KeyEvent.VK_ESCAPE);
				break;
			case KeyEvent.VK_F3:
				//pray
				e.setKeyCode(KeyEvent.VK_F5);
				break;
			case KeyEvent.VK_F4:
				//magic
				e.setKeyCode(KeyEvent.VK_F6);
				break;

			case KeyEvent.VK_F5:
				//equip
				e.setKeyCode(KeyEvent.VK_F4);
				break;
			}
		}
		
		e.setSource(client);
		if (client != null) {
			Canvas canvas = (Canvas) client.getComponent(0);
			if (canvas != null)
				canvas.dispatchEvent(e);
		}
	}

	public void keyReleased(KeyEvent e) {
		if (Settings.NEW_FKEYS) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_F2:
				//combat
				e.setKeyCode(KeyEvent.VK_F1);
				break;
			case KeyEvent.VK_F1:
				//inv
				e.setKeyCode(KeyEvent.VK_ESCAPE);
				break;
			case KeyEvent.VK_F3:
				//pray
				e.setKeyCode(KeyEvent.VK_F5);
				break;
			case KeyEvent.VK_F4:
				//magic
				e.setKeyCode(KeyEvent.VK_F6);
				break;

			case KeyEvent.VK_F5:
				//equip
				e.setKeyCode(KeyEvent.VK_F4);
				break;
			}
		}
		
		e.setSource(client);
		if (client != null) {
			Canvas canvas = (Canvas) client.getComponent(0);
			if (canvas != null) {
				canvas.dispatchEvent(e);
			}
		}
	}

	public void focusGained(FocusEvent e) {
		hasFocus = true;
		e.setSource(client);
		if (inputEnabled && client != null) {
			Canvas canvas = (Canvas) client.getComponent(0);
			if (canvas != null)
				canvas.dispatchEvent(e);
		}
	}

	public void focusLost(FocusEvent e) {
		hasFocus = false;
		e.setSource(client);
		if (inputEnabled && client != null) {
			Canvas canvas = (Canvas) client.getComponent(0);
			if (canvas != null)
				canvas.dispatchEvent(e);
		}
	}

	// End listeners

	public boolean isInputEnabled() {
		return inputEnabled;
	}

	public void setInputeEnabled(boolean inputEnabled) {
		this.inputEnabled = inputEnabled;
	}

}
