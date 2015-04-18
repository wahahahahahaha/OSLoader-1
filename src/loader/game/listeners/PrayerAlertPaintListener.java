package loader.game.listeners;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;

import loader.Settings;
import loader.game.accessors.Client;

public class PrayerAlertPaintListener implements PaintListener {
	private Client game;

	public PrayerAlertPaintListener(Client game) {
		this.game = game;
	}

	@Override
	public void onRepaint(Graphics g) {
		if (Settings.PRAYER_ALERT) {
			double currentLevel = game.getCurrentLevelArray()[5];
			int baseLevel = game.getBaseLevelArray()[5];

			//System.out.println(currentLevel + " " + baseLevel);
			if (currentLevel / baseLevel < 0.2) {
				g.setColor(Color.red);
				g.setFont(new Font("Helvetica", Font.BOLD, 20));
				g.drawString("PRAYER LOW", 250, 150);
				Toolkit.getDefaultToolkit().beep();
			}
		}

	}
}
