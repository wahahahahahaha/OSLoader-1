package loader.game.accessors;

import loader.game.storage.Storage;
import loader.hooks.structure.RSField;

public class RenderableNode extends CacheableNode {

	public int getHeight() {
		Object cachedObject = client.getCache().get("RenderableNode#getHeight" + hash);
		if (cachedObject != null) {
			return (int) cachedObject;
		}

		RSField field = Storage.classStorage.get("RenderableNode").getField("getHeight");
		int height = (int) field.getValue(obj) * field.getMultiplier();

		client.getCache().put("RenderableNode#getHeight" + hash, height);
		return height;
	}
}
