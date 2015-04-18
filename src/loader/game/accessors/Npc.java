package loader.game.accessors;

import loader.game.storage.Storage;
import loader.hooks.structure.RSField;

public class Npc extends Character {

	public Npc(Object npc, Client client) {
		super();
		this.client = client;
		this.obj = npc;
		this.hash = System.identityHashCode(this);
	}

	@Override
	public String toString() {
		return "Npc [getNpcDefinition()=" + getNpcDefinition() + ", getLocation()=" + getLocation() + "]";
	}

	public NpcDefinition getNpcDefinition() {
		Object cachedObject = client.getCache().get("Npc#getNpcDefinition" + hash);
		if (cachedObject != null) {
			return (NpcDefinition) cachedObject;
		}

		RSField field = Storage.classStorage.get("Npc").getField("getNpcDefinition");
		Object o = field.getValue(obj);
		NpcDefinition def = new NpcDefinition(o, client);
		client.getCache().put("Npc#getNpcDefinition" + hash, def);
		return def;
	}
}
