package loader.components;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class WorldParser {

	private byte[] bytes = new byte[5000];
	private int offset;

	public static void main(String[] args) {
		WorldParser parser = new WorldParser();
		World[] worlds = parser.getWorlds();
		if (worlds != null) {
			System.out.println(worlds.length);
			for (World world : worlds) {
				System.out.println(world.toString());
			}
		}
	}

	public World[] getWorlds() {
		offset = 0;
		try {
			URL url = new URL("http://www.runescape.com/slr.ws?order=WMLPA");
			InputStream is = url.openStream();
			int success = is.read(bytes);
			if (success != 5000) {
				System.err.println("Invalid size");
				return getWorlds();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		int length = bytes[5];
		World[] worlds = new World[length + 2];
		offset = 6;
		for (int i = 0; i < length; i++) {
			World world = new World();
			world.id = readShort();
			world.flags = readInt();
			world.domain = readString();
			world.activity = readString();
			world.location = readByte();
			world.playerCount = readShort();
			world.index = i;
			worlds[i] = world;
		}
		
		
		World world = new World();
		world.id = 385;
		world.flags = 0;
		world.domain = "oldschool85.runescape.com";
		world.activity = "Bot world - Free";
		world.location = 0;
		world.playerCount = -1;
		world.index = length;
		worlds[length] = world;
		
		
		World world2 = new World();
		world2.id = 386;
		world2.flags = 1;
		world2.domain = "oldschool86.runescape.com";
		world2.activity = "Bot world";
		world2.location = 0;
		world2.playerCount = -1;
		world2.index = length + 1;
		worlds[length + 1] = world2;
		
		return worlds;
	}

	private int readByte() {
		offset += 2;
		return bytes[offset - 1] & 0xF;
	}

	private int readShort() {
		offset += 2;
		return ((bytes[offset - 2] & 0xFF) << 8) + (bytes[offset - 1] & 0xFF);
	}

	private int readInt() {
		offset += 4;
		return ((bytes[offset - 4] & 0xFF) << 24) + ((bytes[offset - 3] & 0xFF) << 16) + ((bytes[offset - 2] & 0xFF) << 8) + (bytes[offset - 1] & 0xFF);
	}

	private String readString() {
		String s = "";
		if (bytes[offset] == 0) {
			offset++;
		}
		while (bytes[offset] != 0) {
			s += (char) bytes[offset];
			offset++;
		}
		return s;
	}

}