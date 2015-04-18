package loader.game.accessors;

import loader.game.storage.Storage;
import loader.hooks.structure.RSClass;
import loader.hooks.structure.RSField;

//bo in r42
public class SocketHandler extends GameObject {

	public SocketHandler(Object socketHandler, Client client) {
		this.obj = socketHandler;
		this.client = client;
		this.hash = System.identityHashCode(this);
	}

	public Socket getSocket() {
		Object cachedObject = client.getCache().get("SocketHandler#getSocket" + hash);
		if (cachedObject != null) {
			return (Socket) cachedObject;
		}

		RSClass clasz = Storage.classStorage.get("SocketHandler");
		RSField field = clasz.getField("getSocket");
		Socket sock = new Socket(field.getValue(obj), client);

		client.getCache().put("SocketHandler#getSocket" + hash, sock);
		return sock;
	}

	public void setSocket(Socket socket) {
		RSClass clasz = Storage.classStorage.get("SocketHandler");
		RSField field = clasz.getField("getSocket");
		try {
			if (socket != null)
				field.getField().set(obj, socket.getSocketObject());
			else
				field.getField().set(obj, null);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		client.getCache().put("SocketHandler#getSocket" + hash, null);
	}
}
