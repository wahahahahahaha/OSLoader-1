package loader.game.accessors;

import loader.game.storage.Storage;
import loader.hooks.structure.RSField;

public class NpcDefinition extends GameObject {

	public NpcDefinition(Object npcdef, Client client) {
		this.obj = npcdef;
		this.client = client;
		this.hash = System.identityHashCode(this);
	}

	public String getName() {
		Object cachedObject = client.getCache().get("Character#getName" + hash);
		if (cachedObject != null) {
			return (String) cachedObject;
		}

		RSField field = Storage.classStorage.get("NpcDefinition").getField("getName");
		String name = (String) field.getValue(obj);

		client.getCache().put("Character#getName" + hash, name);
		return name;
	}

	public int getId() {
		Object cachedObject = client.getCache().get("Character#getId" + hash);
		if (cachedObject != null) {
			return (int) cachedObject;
		}
		
		RSField field = Storage.classStorage.get("NpcDefinition").getField("getId");
		int id = (int) field.getValue(obj) * field.getMultiplier();
		
		client.getCache().put("Character#getId" + hash, id);
		return id;
	}

	@Override
	public String toString() {
		return "NpcDefinition [getName()=" + getName() + ", getId()=" + getId() + "]";
	}
}
