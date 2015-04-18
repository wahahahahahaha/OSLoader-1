package loader.game.listeners;

import java.awt.FontMetrics;
import java.awt.Graphics;

import loader.Settings;
import loader.game.accessors.Client;


public class FpsPaintListener implements PaintListener {

	private Client game;

	public FpsPaintListener(Client game) {
		this.game = game;
	}

	@Override
	public void onRepaint(Graphics g) {
		if (Settings.SHOW_FPS) {
			FontMetrics fm = g.getFontMetrics();
			String fps = "" + game.getFps();
			int x = 760 - fm.stringWidth(fps);

			g.drawString(fps, x, 15);
		}
	}

}
