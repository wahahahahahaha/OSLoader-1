package loader.game.listeners.zulrah.patterns;

import java.awt.Graphics;

import loader.game.accessors.Client;
import loader.game.helpers.Tile;
import loader.game.listeners.zulrah.ZulrahInstance;

public class ZulrahPatternB extends ZulrahPattern {


	public ZulrahPatternB(Client game) {
		this.game = game;
		pattern = new ZulrahInstance[11];
		pattern[0] = new ZulrahInstance(ZulrahInstance.LOCATION.NORTH, ZulrahInstance.TYPE.RANGE, false, ZulrahInstance.STAND_LOCATION.TOP_EAST);
		pattern[1] = new ZulrahInstance(ZulrahInstance.LOCATION.NORTH, ZulrahInstance.TYPE.MELEE, false, ZulrahInstance.STAND_LOCATION.TOP_EAST);
		pattern[2] = new ZulrahInstance(ZulrahInstance.LOCATION.NORTH, ZulrahInstance.TYPE.MAGIC, false, ZulrahInstance.STAND_LOCATION.TOP_EAST);
		pattern[3] = new ZulrahInstance(ZulrahInstance.LOCATION.WEST, ZulrahInstance.TYPE.RANGE, false, ZulrahInstance.STAND_LOCATION.PILLAR_WEST_OUTSIDE);
		pattern[4] = new ZulrahInstance(ZulrahInstance.LOCATION.SOUTH, ZulrahInstance.TYPE.MAGIC, false, ZulrahInstance.STAND_LOCATION.WEST);
		pattern[5] = new ZulrahInstance(ZulrahInstance.LOCATION.NORTH, ZulrahInstance.TYPE.MELEE, false, ZulrahInstance.STAND_LOCATION.WEST);
		pattern[6] = new ZulrahInstance(ZulrahInstance.LOCATION.EAST, ZulrahInstance.TYPE.RANGE, false, ZulrahInstance.STAND_LOCATION.SOUTH);
		pattern[7] = new ZulrahInstance(ZulrahInstance.LOCATION.SOUTH, ZulrahInstance.TYPE.MAGIC, false, ZulrahInstance.STAND_LOCATION.PILLAR_WEST_INSIDE);
		pattern[8] = new ZulrahInstance(ZulrahInstance.LOCATION.WEST, ZulrahInstance.TYPE.RANGE, true, ZulrahInstance.STAND_LOCATION.TOP_WEST);
		pattern[9] = new ZulrahInstance(ZulrahInstance.LOCATION.NORTH, ZulrahInstance.TYPE.MELEE, false, ZulrahInstance.STAND_LOCATION.TOP_WEST);
		pattern[10] = new ZulrahInstance(ZulrahInstance.LOCATION.NORTH, ZulrahInstance.TYPE.RANGE, false, ZulrahInstance.STAND_LOCATION.TOP_WEST);
		
	
	
	
	}

	@Override
	public void onRepaint(Graphics g, Tile startTile, int index) {
		super.onRepaint(g, startTile, index);
		g.drawString("Pattern B", 200, 200);
	}

	@Override
	public boolean canReset(int index) {
		return index >= 10;//11
	}

}
