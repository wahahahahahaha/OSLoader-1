package loader.game.accessors;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import loader.game.storage.Storage;
import loader.hooks.structure.RSClass;
import loader.hooks.structure.RSField;

public class World extends GameObject {

	public World() {
		RSClass client = Storage.classStorage.get("World");
		try {
			Class<?> clazz = client.getClasz();
			Constructor<?> constructor = clazz.getDeclaredConstructor();
			constructor.setAccessible(true);
			obj = constructor.newInstance();
		} catch (InstantiationException | IllegalAccessException | SecurityException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public World(int world, int mask, String domain, int location) {
		super();
		RSClass client = Storage.classStorage.get("World");
		try {
			Class<?> clazz = client.getClasz();
			Constructor<?> constructor = clazz.getDeclaredConstructor();
			constructor.setAccessible(true);
			obj = constructor.newInstance();
			setWorld(world);
			setMask(mask);
			setDomain(domain);
			setLocation(location);
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

	}

	//Only needed to hop
	public World(String domain, int world, int mask) {
		RSClass client = Storage.classStorage.get("World");
		try {
			Class<?> clazz = client.getClasz();
			Constructor<?> constructor = clazz.getDeclaredConstructor();
			constructor.setAccessible(true);
			obj = constructor.newInstance();
			setWorld(world);
			setMask(mask);
			setDomain(domain);
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public Object getWorldObject() {
		return obj;
	}

	public void setWorld(int world) {
		RSClass client = Storage.classStorage.get("World");
		RSField field = client.getField("getWorld");
		int multi = field.getInverseMultiplier();
		try {
			field.getField().setInt(obj, multi * world);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

//	public void setActivity(String activity) {
//		RSClass client = Storage.classStorage.get("World");
//		RSField field = client.getField("getActivity");
//		try {
//			field.getField().set(obj, activity);
//		} catch (IllegalArgumentException | IllegalAccessException e) {
//			e.printStackTrace();
//		}
//	}

	public void setMask(int mask) {
		RSClass client = Storage.classStorage.get("World");
		RSField field = client.getField("getMask");
		int multi = field.getInverseMultiplier();
		try {
			field.getField().setInt(obj, multi * mask);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void setDomain(String domain) {
		RSClass client = Storage.classStorage.get("World");
		RSField field = client.getField("getDomain");
		try {
			field.getField().set(obj, domain);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void setLocation(int location) {
		RSClass client = Storage.classStorage.get("World");
		RSField field = client.getField("getLocation");
		int multi = field.getInverseMultiplier();
		try {
			field.getField().setInt(obj, multi * location);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
