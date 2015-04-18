package loader.game.listeners;

import java.awt.Graphics;
import java.awt.Point;

import loader.Settings;
import loader.game.accessors.Client;
import loader.game.accessors.Npc;
import loader.game.helpers.Calculations;

public class NpcPaintListener implements PaintListener {

	private Client game;

	public NpcPaintListener(Client game) {
		this.game = game;
	}

	@Override
	public void onRepaint(Graphics g) {
		if (!Settings.DRAW_NPCS)
			return;

		for (Npc npc : game.getNpcs()) {
			Point p = npc.getScreenPos();
			game.getCalculations();
			if (Calculations.onScreen(p)) {
				System.out.println(npc.toString());
				String text = npc.getNpcDefinition().getName();
				int stringLen = (int) g.getFontMetrics().getStringBounds(text, g).getWidth();

				g.drawString(text, p.x - (int) (stringLen / 2.0), p.y);
			}
		}
	}

}
