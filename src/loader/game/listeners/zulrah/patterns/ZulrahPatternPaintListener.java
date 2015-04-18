package loader.game.listeners.zulrah.patterns;

import java.awt.Graphics;

import loader.game.helpers.Tile;

public interface ZulrahPatternPaintListener {
	public void onRepaint(Graphics g, Tile startTile, int index);
}
