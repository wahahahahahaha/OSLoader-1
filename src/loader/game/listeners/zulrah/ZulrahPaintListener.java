package loader.game.listeners.zulrah;

import java.awt.Graphics;

import loader.Settings;
import loader.game.accessors.Client;
import loader.game.accessors.Npc;
import loader.game.helpers.Tile;
import loader.game.listeners.PaintListener;
import loader.game.listeners.zulrah.patterns.ZulrahPattern;
import loader.game.listeners.zulrah.patterns.ZulrahPatternA;
import loader.game.listeners.zulrah.patterns.ZulrahPatternB;
import loader.game.listeners.zulrah.patterns.ZulrahPatternC;
import loader.game.listeners.zulrah.patterns.ZulrahPatternD;

public class ZulrahPaintListener implements PaintListener {
	private Client game;
	private Thread thread;
	private boolean threadRunning;

	//http://www.reddit.com/r/2007scape/comments/2vse5h/zulrah_4_forms_and_where_to_stand_updated/
	//pattern
	private int index = 0;
	private int currentPattern = -1;
	private ZulrahPattern[] patterns;
	private ZulrahInstance previousInstance;
	private ZulrahInstance currentInstance;
	private Tile startTile;
	private int hp = 500;
	public ZulrahPaintListener(Client game) {
		this.game = game;

		patterns = new ZulrahPattern[4];
		patterns[0] = new ZulrahPatternA(game);
		patterns[1] = new ZulrahPatternB(game);
		patterns[2] = new ZulrahPatternC(game);
		patterns[3] = new ZulrahPatternD(game);

		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						check();
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
				}
			}

		});
		threadRunning = false;
	}

	public void check() {
		Npc[] npcs = game.getNpcs();
		Npc zulrah = null;
		for (Npc npc : npcs) {
			if (npc.getNpcDefinition().getName().equals("Zulrah")) {
				zulrah = npc;
				break;
			}
		}

		if (zulrah == null)
			return;

		hp = zulrah.getHealth();
		
		//on entering
		if (startTile == null || startTile.distanceTo(zulrah.getLocation()) > 15) {
			startTile = zulrah.getLocation();
			index = 0;
			currentPattern = -1;
			System.out.println("startTile: " + startTile.toString());
			previousInstance = null;
			currentInstance = null;
		}

		ZulrahInstance temp = new ZulrahInstance(zulrah, startTile);

		if (currentInstance == null) {
			currentInstance = temp;
		}

		if (!currentInstance.equals(temp)) {
			previousInstance = currentInstance;
			currentInstance = temp;
			index++;

		}

		if (currentPattern == -1) {
			int potential = 0;
			int potentialPattern = -1;
			for (int i = 0; i < patterns.length; i++) {
				if (patterns[i].accept(index, currentInstance)) {
					potential++;
					potentialPattern = i;
				}
			}

			if (potential == 1) {
				currentPattern = potentialPattern;
			}
		} else {
			if (patterns[currentPattern].canReset(index)) {
				if (currentInstance.equals(patterns[currentPattern].get(0))) {
					currentPattern = -1;
					index = 0;
				}
			}
		}

	}

	@Override
	public void onRepaint(Graphics g) {
		if (Settings.ZULRAH) {
			if (!threadRunning) {
				if (thread.isAlive())
					thread.resume();
				else
					thread.start();
				threadRunning = true;
			}
		} else {
			if (thread.isAlive()) {
				thread.suspend();
				threadRunning = false;
			}
			return;
		}

		if (Settings.ZULRAH) {
			//g.drawString("threadRunning: " + threadRunning, 200, 150);
			//g.drawString("thread.isAlive(): " + thread.isAlive(), 200, 165);
			g.drawString(hp + "/500", 5, 40);
			
			if (currentPattern == -1) {
				g.drawString("unknown", 200, 200);
				g.drawString("current: " + currentInstance, 200, 215);
				g.drawString("previous: " + previousInstance, 200, 230);
				g.drawString("index: " + index, 200, 245);

			} else {
				patterns[currentPattern].onRepaint(g, startTile, index);
			}
			if (startTile != null) {
				Tile t = game.getLocation();
				int dx = startTile.getX() - t.getX();
				int dy = startTile.getY() - t.getY();
				g.drawString("dx: " + dx + ", dy: " + dy, 400, 200);
			}

		}

	}
}
