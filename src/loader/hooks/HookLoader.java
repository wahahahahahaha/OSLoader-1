package loader.hooks;

import loader.game.storage.Storage;
import loader.hooks.structure.RSClass;
import loader.jarutils.JarLoader;

public class HookLoader {

	private JarLoader jarLoader;

	public HookLoader(JarLoader jarLoader) {
		this.jarLoader = jarLoader;
	}

	public void manualLoad() {

		try {
			//rev 77
			
			Class<?> c = jarLoader.loadClass("client");
			Storage.classStorage.put("Client", new RSClass("Client", "client", c));
			Storage.classStorage.get("Client").putField("getFps", jarLoader.loadClass("ep"), "pu", -1645540843);
			Storage.classStorage.get("Client").putField("getExperiences", null, "ht", 1);
			Storage.classStorage.get("Client").putField("getGameState", null, "j", -672382549);
			Storage.classStorage.get("Client").putMethod("changeGameState", jarLoader.loadClass("y"), "u", new Class<?>[] { int.class, int.class }, new Object[] { "gameState", 518904246 });
			Storage.classStorage.get("Client").putMethod("setWorld", jarLoader.loadClass("es"), "k", new Class<?>[] { jarLoader.loadClass("i"), byte.class }, new Object[] { "world", (byte) -67 });
			Storage.classStorage.get("Client").putField("getSettingsArray", jarLoader.loadClass("fy"), "h", -1);
			Storage.classStorage.get("Client").putField("getPlayers", null, "hn", 1);
			Storage.classStorage.get("Client").putField("getNpcs", null, "ck", 1);
			
			Storage.classStorage.get("Client").putField("getCurrentLevelArray", null, "hc", 1);
			Storage.classStorage.get("Client").putField("getBaseLevelArray", null, "hl", 1);
			
			
			Storage.classStorage.get("Client").putField("getTileSettings", jarLoader.loadClass("t"), "s", 1);
			Storage.classStorage.get("Client").putField("getTileHeights", jarLoader.loadClass("t"), "q", 1);
			
			Storage.classStorage.get("Client").putField("getBaseX", jarLoader.loadClass("bd"), "cb", 707716209);
			Storage.classStorage.get("Client").putField("getBaseY", jarLoader.loadClass("dk"), "cw", -2080426823);
			Storage.classStorage.get("Client").putField("getPlane", jarLoader.loadClass("cr"), "q", 1951686105);

			Storage.classStorage.get("Client").putField("getCameraX", jarLoader.loadClass("fj"), "fk", -929761141);
			Storage.classStorage.get("Client").putField("getCameraY", jarLoader.loadClass("da"), "fn", 1246349073);
			Storage.classStorage.get("Client").putField("getCameraZ", jarLoader.loadClass("p"), "fo", -409216847);
			Storage.classStorage.get("Client").putField("getCameraPitch", jarLoader.loadClass("bg"), "fl", 1673524137);
			Storage.classStorage.get("Client").putField("getCameraYaw", jarLoader.loadClass("client"), "fw", 1090233445);

			Storage.classStorage.get("Client").putField("getWidgets", jarLoader.loadClass("fl"), "q", 1);
			
			
			c = jarLoader.loadClass("i");
			Storage.classStorage.put("World", new RSClass("World", "i", c));
			Storage.classStorage.get("World").putField("getWorld", null, "q", -1216514709);
		//	Storage.classStorage.get("World").putField("getActivity", null, "r", 1);
			Storage.classStorage.get("World").putField("getMask", null, "l", -511233731);
			Storage.classStorage.get("World").putField("getDomain", null, "e", 1);
			Storage.classStorage.get("World").putField("getLocation", null, "t", -1350591325);

			c = jarLoader.loadClass("en");
			Storage.classStorage.put("Socket", new RSClass("Socket", "en", c));
			Storage.classStorage.get("Socket").putMethod("close", null, "n", new Class<?>[] { byte.class }, new Object[] { (byte) -112 });

			c = jarLoader.loadClass("dk");
			Storage.classStorage.put("SocketHandler", new RSClass("SocketHandler", "dk", c));
			Storage.classStorage.get("SocketHandler").putField("getSocket", null, "cs", 1);

			c = jarLoader.loadClass("h");
			Storage.classStorage.put("Player", new RSClass("Player", "h", c));
			Storage.classStorage.get("Player").putField("getName", null, "q", 1);

			c = jarLoader.loadClass("av");
			Storage.classStorage.put("Npc", new RSClass("Npc", "av", c));
			Storage.classStorage.get("Npc").putField("getNpcDefinition", null, "q", 1);

			c = jarLoader.loadClass("al");
			Storage.classStorage.put("NpcDefinition", new RSClass("NpcDefinition", "al", c));
			Storage.classStorage.get("NpcDefinition").putField("getName", null, "t", 1);
			Storage.classStorage.get("NpcDefinition").putField("getId", null, "n", -1876632011);

			c = jarLoader.loadClass("ak");
			Storage.classStorage.put("Character", new RSClass("Character", "ak", c));
			Storage.classStorage.get("Character").putField("getX", null, "z", 75290311);
			Storage.classStorage.get("Character").putField("getY", null, "x", -363141049);
			Storage.classStorage.get("Character").putField("getHealth", null, "ap", 1114196473);

			c = jarLoader.loadClass("cq");
			Storage.classStorage.put("RenderableNode", new RSClass("RenderableNode", "cq", c));
			Storage.classStorage.get("RenderableNode").putField("getHeight", null, "ct", -114090705);

			c = jarLoader.loadClass("gb");
			Storage.classStorage.put("CacheableNode", new RSClass("CacheableNode", "gb", c));

			c = jarLoader.loadClass("gq");
			Storage.classStorage.put("Node", new RSClass("Character", "gq", c));

			c = jarLoader.loadClass("fl");
			Storage.classStorage.put("Widget", new RSClass("Widget", "fl", c));
			Storage.classStorage.get("Widget").putField("getChildren", null, "dz", 1);
			Storage.classStorage.get("Widget").putField("getId", null, "u", -676538189);
			Storage.classStorage.get("Widget").putField("getText", null, "bt", 1);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
