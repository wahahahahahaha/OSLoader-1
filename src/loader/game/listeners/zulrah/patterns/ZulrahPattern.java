package loader.game.listeners.zulrah.patterns;

import java.awt.Graphics;
import java.awt.Point;

import loader.game.accessors.Client;
import loader.game.helpers.Tile;
import loader.game.listeners.PaintListener;
import loader.game.listeners.zulrah.ZulrahInstance;

public abstract class ZulrahPattern implements ZulrahPatternPaintListener {
	protected ZulrahInstance[] pattern;
	protected Client game;

	public ZulrahInstance get(int index) {
		if (index >= pattern.length) {
			return null;
		}
		return pattern[index];
	}

	public boolean accept(int index, ZulrahInstance instance) {
		ZulrahInstance patternInstance = get(index);
		if (patternInstance == null) {
			return false;
		}
		return patternInstance.equals(instance);
	}

	public abstract boolean canReset(int index);

	public void onRepaint(Graphics g, Tile startTile, int index) {
		ZulrahInstance current = get(index);
		g.drawString("startTile: " + startTile, 270, 200);
		g.drawString("current: " + current, 200, 215);
		if (current != null) {
			Tile currentZulrahTile = current.getZulrahTile(startTile);
			System.out.println("currentZulrahTile: " + currentZulrahTile);
			Point currentZulrahTilePoint = game.getCalculations().tileToScreen(currentZulrahTile);
			g.drawString("c: " + current.getType() + current.isJad(), (int) currentZulrahTilePoint.getX(), (int) currentZulrahTilePoint.getY());
		
			Tile currentStandTile = current.getStandTile(startTile);
			System.out.println("currentStandTile: " + currentStandTile);
			Point currentStandTilePoint = game.getCalculations().tileToScreen(currentStandTile);
			g.drawString("c", (int) currentStandTilePoint.getX(), (int) currentStandTilePoint.getY());
		}

		ZulrahInstance next = get(index + 1);
		g.drawString("next: " + next, 200, 230);
		if (next != null) {
			Tile nextZulrahTile = next.getZulrahTile(startTile);
			System.out.println("nextZulrahTile: " + nextZulrahTile);
			Point nextZulrahTilePoint = game.getCalculations().tileToScreen(nextZulrahTile);
			g.drawString("n: " + next.getType() + next.isJad(), (int) nextZulrahTilePoint.getX(), (int) nextZulrahTilePoint.getY());
		
			Tile nextStandTile = next.getStandTile(startTile);
			System.out.println("nextStandTile: " + nextStandTile);
			Point nextStandTilePoint = game.getCalculations().tileToScreen(nextStandTile);
			g.drawString("n", (int) nextStandTilePoint.getX(), (int) nextStandTilePoint.getY());
		}
	}
}
