package loader.game.listeners;

import java.util.ArrayList;

import loader.Settings;
import loader.game.accessors.Client;


public class DebugTextPaintListener implements TextPaintListener {

	private Client game;

	public DebugTextPaintListener(Client game) {
		this.game = game;
	}

	@Override
	public String[] onTextRepaint() {
		ArrayList<String> list = new ArrayList<String>();

		if (Settings.SHOW_DEBUG) {
			list.add("fps: " + game.getFps());
			list.add("gamestate: " + game.getGameState());
			list.add("run (173) settings: " + game.getSetting(173));
			list.add("512 settings: " + game.getSetting(512));
			
		
		}

		return list.toArray(new String[list.size()]);
	}

}
