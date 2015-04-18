package loader.game.listeners.zulrah.patterns;

import java.awt.Graphics;

import loader.game.accessors.Client;
import loader.game.helpers.Tile;
import loader.game.listeners.zulrah.ZulrahInstance;

public class ZulrahPatternC extends ZulrahPattern {

	public ZulrahPatternC(Client game) {
		this.game = game;
		pattern = new ZulrahInstance[10];

		pattern[0] = new ZulrahInstance(ZulrahInstance.LOCATION.NORTH, ZulrahInstance.TYPE.RANGE, false, ZulrahInstance.STAND_LOCATION.TOP_EAST);
		pattern[1] = new ZulrahInstance(ZulrahInstance.LOCATION.EAST, ZulrahInstance.TYPE.RANGE, false, ZulrahInstance.STAND_LOCATION.TOP_EAST);
		pattern[2] = new ZulrahInstance(ZulrahInstance.LOCATION.NORTH, ZulrahInstance.TYPE.MELEE, false, ZulrahInstance.STAND_LOCATION.PILLAR_WEST_INSIDE);
		pattern[3] = new ZulrahInstance(ZulrahInstance.LOCATION.WEST, ZulrahInstance.TYPE.MAGIC, false, ZulrahInstance.STAND_LOCATION.PILLAR_WEST_INSIDE);
		pattern[4] = new ZulrahInstance(ZulrahInstance.LOCATION.SOUTH, ZulrahInstance.TYPE.RANGE, false, ZulrahInstance.STAND_LOCATION.EAST);
		pattern[5] = new ZulrahInstance(ZulrahInstance.LOCATION.EAST, ZulrahInstance.TYPE.MAGIC, false, ZulrahInstance.STAND_LOCATION.PILLAR_EAST_OUTSIDE);
		pattern[6] = new ZulrahInstance(ZulrahInstance.LOCATION.NORTH, ZulrahInstance.TYPE.RANGE, false, ZulrahInstance.STAND_LOCATION.PILLAR_WEST_OUTSIDE);
		pattern[7] = new ZulrahInstance(ZulrahInstance.LOCATION.WEST, ZulrahInstance.TYPE.RANGE, false, ZulrahInstance.STAND_LOCATION.PILLAR_WEST_OUTSIDE);
		pattern[8] = new ZulrahInstance(ZulrahInstance.LOCATION.NORTH, ZulrahInstance.TYPE.MAGIC, false, ZulrahInstance.STAND_LOCATION.PILLAR_WEST_OUTSIDE);
		pattern[9] = new ZulrahInstance(ZulrahInstance.LOCATION.EAST, ZulrahInstance.TYPE.MAGIC, true, ZulrahInstance.STAND_LOCATION.PILLAR_EAST_OUTSIDE);

	}

	@Override
	public void onRepaint(Graphics g, Tile startTile, int index) {
		super.onRepaint(g, startTile, index);
		g.drawString("Pattern C", 200, 200);
	}

	@Override
	public
	boolean canReset(int index) {
		return index >= 7;
	}

}
