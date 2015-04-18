package loader.game.accessors;

import java.awt.Point;

import loader.game.helpers.Calculations;
import loader.game.helpers.Tile;
import loader.game.storage.Storage;
import loader.hooks.structure.RSField;

public class Character extends RenderableNode {

	protected Character() {
	}

	public Character(Object character, Client client) {
		this.obj = character;
		this.client = client;
		this.hash = System.identityHashCode(this);
	}

	public int getX() {
		Object cachedObject = client.getCache().get("Character#getX" + hash);
		if (cachedObject != null) {
			return (int) cachedObject;
		}

		RSField field = Storage.classStorage.get("Character").getField("getX");
		int x = (int) field.getValue(obj) * field.getMultiplier();

		client.getCache().put("Character#getX" + hash, x);
		return x;
	}

	public int getY() {
		Object cachedObject = client.getCache().get("Character#getY" + hash);
		if (cachedObject != null) {
			return (int) cachedObject;
		}

		RSField field = Storage.classStorage.get("Character").getField("getY");
		int y = (int) field.getValue(obj) * field.getMultiplier();

		client.getCache().put("Character#getY" + hash, y);
		return y;
	}

	public Tile getLocation() {
		int posX = client.getBaseX() + (getX() >> 7);
		int posY = client.getBaseY() + (getY() >> 7);
		return new Tile(posX, posY);
	}

	public Point getScreenPos() {
		return client.getCalculations().worldToScreen(getX(), getY(), (getHeight() / 2));
	}

	public boolean isOnScreen() {
		return Calculations.onScreen(getScreenPos());
	}

	public int getHealth() {
		Object cachedObject = client.getCache().get("Character#getHealth" + hash);
		if (cachedObject != null) {
			return (int) cachedObject;
		}

		RSField field = Storage.classStorage.get("Character").getField("getHealth");
		int y = (int) field.getValue(obj) * field.getMultiplier();

		client.getCache().put("Character#getHealth" + hash, y);
		return y;
	}
}
