package loader.game.accessors;

import java.lang.reflect.InvocationTargetException;



import loader.game.storage.Storage;

public class Socket extends GameObject {

	public Socket(Object socket, Client client) {
		this.obj = socket;
		this.client = client;
		this.hash = System.identityHashCode(this);
	}

	public Object getSocketObject() {
		return obj;
	}

	public void close() {
		try {
			Storage.classStorage.get("Socket").getMethod("close").invoke(obj, new String[0], new Object[0]);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
