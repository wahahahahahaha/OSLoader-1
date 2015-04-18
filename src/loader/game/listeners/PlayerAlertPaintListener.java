package loader.game.listeners;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Arrays;

import loader.Settings;
import loader.game.accessors.Client;
import loader.game.accessors.Player;

public class PlayerAlertPaintListener implements PaintListener {

	private Client game;

	public PlayerAlertPaintListener(Client game) {
		this.game = game;
	}

	@Override
	public void onRepaint(Graphics g) {
		if (Settings.PLAYER_ALERT){
			Player[] arr = game.getPlayers();
			if (arr.length > 1){
				System.out.println(Arrays.toString(arr));
				g.setColor(Color.red);
				g.setFont(new Font("Helvetica", Font.BOLD, 20));
				g.drawString("PLAYER FOUND", 250, 150);
			}
		}
	}

}
