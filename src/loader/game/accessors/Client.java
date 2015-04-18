package loader.game.accessors;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;

import loader.game.helpers.Cache;
import loader.game.helpers.Calculations;
import loader.game.helpers.Tile;
import loader.game.storage.Storage;
import loader.hooks.structure.RSClass;
import loader.hooks.structure.RSField;
import loader.hooks.structure.RSMethod;
import loader.reflection.ReflectUtil;

public class Client extends GameObject {

	private RSClass client;
	private SocketHandler socketHandler;

	private Calculations calculations;
	private Cache cache = new Cache();

	public Client(Object reference) {
		obj = reference;
		this.client = Storage.classStorage.get("Client");
		this.socketHandler = new SocketHandler(reference, this);
		this.calculations = new Calculations(this);
		this.hash = System.identityHashCode(this);
		this.cache = new Cache();
	}

	public Cache getCache() {
		return cache;
	}

	public Calculations getCalculations() {
		return calculations;
	}

	public int getFps() {
		Object o = this.getCache().get("Client#getFps" + hash);
		if (o != null) {
			return (int) o;
		}

		RSField field = client.getField("getFps");
		int fps = (int) field.getValue(obj) * field.getMultiplier();

		this.getCache().put("Client#getFps" + hash, fps);
		return fps;
	}

	public int[] getExperiences() {
		Object o = this.getCache().get("Client#getExperiences" + hash);
		if (o != null) {
			return (int[]) o;
		}

		RSField field = client.getField("getExperiences");
		Object arr = field.getValue(obj);
		int length = Array.getLength(arr);
		int[] experiences = new int[length];
		for (int i = 0; i < length; i++) {
			experiences[i] = (int) Array.get(arr, i);
		}

		this.getCache().put("Client#getExperiences" + hash, experiences);
		return experiences;
	}

	public int[] getCurrentLevelArray() {
		Object o = this.getCache().get("Client#getCurrentLevelArray" + hash);
		if (o != null) {
			return (int[]) o;
		}

		RSField field = client.getField("getCurrentLevelArray");
		Object arr = field.getValue(obj);
		int length = Array.getLength(arr);
		int[] levels = new int[length];
		for (int i = 0; i < length; i++) {
			levels[i] = (int) Array.get(arr, i);
		}

		this.getCache().put("Client#getCurrentLevelArray" + hash, levels);
		return levels;
	}

	public int[] getBaseLevelArray() {
		Object o = this.getCache().get("Client#getBaseLevelArray" + hash);
		if (o != null) {
			return (int[]) o;
		}

		RSField field = client.getField("getBaseLevelArray");
		Object arr = field.getValue(obj);
		int length = Array.getLength(arr);
		int[] levels = new int[length];
		for (int i = 0; i < length; i++) {
			levels[i] = (int) Array.get(arr, i);
		}
		this.getCache().put("Client#getBaseLevelArray" + hash, levels);
		return levels;
	}

	public int getGameState() {
		Object o = this.getCache().get("Client#getGameState" + hash);
		if (o != null) {
			return (int) o;
		}

		RSField field = client.getField("getGameState");
		int gameState = (int) field.getValue(obj) * field.getMultiplier();

		this.getCache().put("Client#getGameState" + hash, gameState);
		return gameState;
	}

	public void setWorld(World w) {
		RSClass client = Storage.classStorage.get("Client");
		RSMethod method = client.getMethod("setWorld");
		try {
			method.invoke(obj, new String[] { "world" }, w.getWorldObject());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public void changeGameState(int gameState) {
		RSClass client = Storage.classStorage.get("Client");
		RSMethod method = client.getMethod("changeGameState");
		try {
			method.invoke(obj, new String[] { "gameState" }, gameState);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public void hopWorld(int world) {
		if (world < 300) {
			world = world + 300;
		}

		System.out.println("hopWorld(" + world + ")");
		World w = new World();
		w.setDomain("oldschool" + (world - 300) + ".runescape.com");
		w.setWorld(world);
		int mask = 1;
		w.setMask(mask);

		if (this.getGameState() == 30) {
			this.changeGameState(45);
			socketHandler.getSocket().close();
			this.socketHandler.setSocket(null);
		}
		setWorld(w);

		/*		rev 76
			v localv = new v();                        create world
			localv.c = paramString;                    set domain
			localv.p = (paramInt1 * 282792177);        set world
			localv.y = (paramInt2 * -405603287);       set mask
			ad.m(45, -366305740);                      changeGameState 45
			hv.cd.r((byte)98);                         close socket
			hv.cd = null;                              set socket to null
			t.s(localv, (short)-3773);                 change world
			
			
				rev 77
			i locali = new i();
		    locali.e = paramString;
		    locali.q = (paramInt1 * 974851907);
		    locali.s = (paramInt2 * 1444729017);
		    y.u(45, 518904246);
		    dk.cs.n((byte)-112);
		    dk.cs = null;
		    es.k(locali, (byte)-67);
		 */
	}

	public int[] getSettingsArray() {
		Object o = this.getCache().get("Client#getSettingsArray" + hash);
		if (o != null) {
			return (int[]) o;
		}

		Object array = client.getField("getSettingsArray").getValue(obj);
		if (array == null)
			return null;

		int length = Array.getLength(array);
		int[] arr = new int[length];
		for (int i = 0; i < length; i++) {
			arr[i] = (int) Array.get(array, i);
		}

		this.getCache().put("Client#getSettingsArray" + hash, arr);
		return arr;
	}

	public int getSetting(int index) {
		int[] settings = getSettingsArray();
		if (index < settings.length) {
			return settings[index];
		}
		return 0;
	}

	public Player[] getPlayers() {
		Object cachedObject = this.getCache().get("Client#getPlayers" + hash);
		if (cachedObject != null) {
			return (Player[]) cachedObject;
		}

		Object array = client.getField("getPlayers").getValue(obj);
		if (array == null)
			return null;

		int length = Array.getLength(array);
		Player[] arr = new Player[length];
		int nonNullCount = 0;
		for (int i = 0; i < length; i++) {
			Object o = Array.get(array, i);
			if (o != null) {
				nonNullCount++;
				arr[i] = new Player(o, this);
			}
		}

		Player[] finalArr = new Player[nonNullCount];
		int ct = 0;
		for (Player p : arr) {
			if (p != null) {
				finalArr[ct] = p;
				ct++;
			}
		}
		this.getCache().put("Client#getPlayers" + hash, finalArr);
		return finalArr;
	}

	public Npc[] getNpcs() {
		Object cachedObject = this.getCache().get("Client#getNpcs" + hash);
		if (cachedObject != null) {
			return (Npc[]) cachedObject;
		}

		Object array = client.getField("getNpcs").getValue(obj);
		if (array == null)
			return null;

		int length = Array.getLength(array);
		Npc[] arr = new Npc[length];
		int nonNullCount = 0;
		for (int i = 0; i < length; i++) {
			Object o = Array.get(array, i);
			if (o != null) {
				nonNullCount++;
				arr[i] = new Npc(o, this);
			}
		}

		Npc[] finalArr = new Npc[nonNullCount];
		int ct = 0;
		for (Npc p : arr) {
			if (p != null) {
				finalArr[ct] = p;
				ct++;
			}
		}
		this.getCache().put("Client#getNpcs" + hash, finalArr);
		return finalArr;
	}

	public int getBaseX() {
		Object cachedObject = this.getCache().get("Client#getBaseX" + hash);
		if (cachedObject != null) {
			return (int) cachedObject;
		}

		RSField field = client.getField("getBaseX");
		int baseX = (int) field.getValue(obj) * field.getMultiplier();
		this.getCache().put("Client#getBaseX" + hash, baseX);
		return baseX;
	}

	public int getBaseY() {
		Object cachedObject = this.getCache().get("Client#getBaseY" + hash);
		if (cachedObject != null) {
			return (int) cachedObject;
		}

		RSField field = client.getField("getBaseY");
		int baseY = (int) field.getValue(obj) * field.getMultiplier();
		this.getCache().put("Client#getBaseY" + hash, baseY);
		return baseY;
	}

	public int getPlane() {
		Object cachedObject = this.getCache().get("Client#getPlane" + hash);
		if (cachedObject != null) {
			return (int) cachedObject;
		}
		RSField field = client.getField("getPlane");
		int val = (int) field.getValue(obj) * field.getMultiplier();
		this.getCache().put("Client#getPlane" + hash, val);
		return val;
	}

	public Socket getSocket() {
		return socketHandler.getSocket();
	}

	public int[][][] getTileHeights() {
		Object cachedObject = this.getCache().get("Client#getTileHeights" + hash);
		if (cachedObject != null) {
			return (int[][][]) cachedObject;
		}

		RSField field = client.getField("getTileHeights");

		Object[][][] temp = ReflectUtil.get3DArray(obj, field.getField());
		int[][][] arr = new int[temp.length][0][0];

		for (int i = 0; i < temp.length; i++) {
			arr[i] = new int[temp[i].length][0];
			for (int j = 0; j < temp[i].length; j++) {
				arr[i][j] = new int[temp[i][j].length];
				for (int k = 0; k < temp[i][j].length; k++) {
					arr[i][j][k] = (int) temp[i][j][k];
				}
			}
		}
		this.getCache().put("Client#getTileHeights" + hash, arr);
		return arr;
	}

	public byte[][][] getTileSettings() {
		Object cachedObject = this.getCache().get("Client#getTileSettings" + hash);
		if (cachedObject != null) {
			return (byte[][][]) cachedObject;
		}

		RSField field = client.getField("getTileSettings");

		Object[][][] temp = ReflectUtil.get3DArray(obj, field.getField());
		byte[][][] arr = new byte[temp.length][0][0];

		for (int i = 0; i < temp.length; i++) {
			arr[i] = new byte[temp[i].length][0];
			for (int j = 0; j < temp[i].length; j++) {
				arr[i][j] = new byte[temp[i][j].length];
				for (int k = 0; k < temp[i][j].length; k++) {
					arr[i][j][k] = (byte) temp[i][j][k];
				}
			}
		}

		this.getCache().put("Client#getTileSettings" + hash, arr);
		return arr;
	}

	public int getCameraX() {
		Object cachedObject = this.getCache().get("Client#getCameraX" + hash);
		if (cachedObject != null) {
			return (int) cachedObject;
		}

		RSField field = client.getField("getCameraX");
		int val = (int) field.getValue(obj) * field.getMultiplier();

		this.getCache().put("Client#getCameraX" + hash, val);
		return val;
	}

	public int getCameraY() {
		Object cachedObject = this.getCache().get("Client#getCameraY" + hash);
		if (cachedObject != null) {
			return (int) cachedObject;
		}

		RSField field = client.getField("getCameraY");
		int val = (int) field.getValue(obj) * field.getMultiplier();

		this.getCache().put("Client#getCameraY" + hash, val);
		return val;
	}

	public int getCameraZ() {
		Object cachedObject = this.getCache().get("Client#getCameraZ" + hash);
		if (cachedObject != null) {
			return (int) cachedObject;
		}

		RSField field = client.getField("getCameraZ");
		int val = (int) field.getValue(obj) * field.getMultiplier();

		this.getCache().put("Client#getCameraZ" + hash, val);
		return val;
	}

	public int getCameraYaw() {
		Object cachedObject = this.getCache().get("Client#getCameraYaw" + hash);
		if (cachedObject != null) {
			return (int) cachedObject;
		}

		RSField field = client.getField("getCameraYaw");
		int val = (int) field.getValue(obj) * field.getMultiplier();

		this.getCache().put("Client#getCameraYaw" + hash, val);
		return val;
	}

	public int getCameraPitch() {
		Object cachedObject = this.getCache().get("Client#getCameraPitch" + hash);
		if (cachedObject != null) {
			return (int) cachedObject;
		}

		RSField field = client.getField("getCameraPitch");
		int val = (int) field.getValue(obj) * field.getMultiplier();

		this.getCache().put("Client#getCameraPitch" + hash, val);
		return val;
	}

	public Widget[] getWidgets() {
		Object cachedObject = this.getCache().get("Client#getWidgets" + hash);
		if (cachedObject != null) {
			return (Widget[]) cachedObject;
		}

		Object array = client.getField("getWidgets").getValue(obj);
		if (array == null)
			return null;

		int length = Array.getLength(array);
		Widget[] arr = new Widget[length];
		int nonNullCount = 0;
		for (int i = 0; i < length; i++) {
			Object o = Array.get(array, i);
			if (o != null) {
				nonNullCount++;
				arr[i] = new Widget(o, this);
			}
		}

		Widget[] finalArr = new Widget[nonNullCount];
		int ct = 0;
		for (Widget p : arr) {
			if (p != null) {
				finalArr[ct] = p;
				ct++;
			}
		}

		this.getCache().put("Client#getWidgets" + hash, finalArr);
		return finalArr;
	}

	public Tile getLocation() {
		Object cachedObject = this.getCache().get("Client#getLocation" + hash);
		if (cachedObject != null) {
			return (Tile) cachedObject;
		}

		Player[] arr = getPlayers();

		Player self = arr[arr.length - 1];
		int posX = getBaseX() + (self.getX() >> 7);
		int posY = getBaseY() + (self.getY() >> 7);

		Tile tile = new Tile(posX, posY);
		this.getCache().put("Client#getCameraYaw" + hash, tile);
		return tile;
	}

}
