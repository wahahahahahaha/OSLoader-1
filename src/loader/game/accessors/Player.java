package loader.game.accessors;

import loader.game.storage.Storage;
import loader.hooks.structure.RSField;

public class Player extends Character {

	public Player(Object player, Client client) {
		this.obj = player;
		this.client = client;
		this.hash = System.identityHashCode(this);
	}

	public String getName() {
		Object cachedObject = client.getCache().get("Player#getName" + hash);
		if (cachedObject != null) {
			return (String) cachedObject;
		}

		RSField field = Storage.classStorage.get("Player").getField("getName");
		String name = (String) field.getValue(obj);

		client.getCache().put("Player#getName" + hash, name);
		return name;
	}

	@Override
	public String toString() {
		return "Player [getName()=" + getName() + "]";
	}

}
