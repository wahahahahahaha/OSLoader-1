package loader.game.accessors;

import java.lang.reflect.Array;

import loader.game.storage.Storage;
import loader.hooks.structure.RSField;

public class Widget extends Node {

	public Widget(Object widget, Client client) {
		this.obj = widget;
		this.client = client;
		this.hash = System.identityHashCode(this);
	}

	public Widget[] getChildren() {
		Object cachedObject = client.getCache().get("Widget#getChildren" + hash);
		if (cachedObject != null) {
			return (Widget[]) cachedObject;
		}
		
		Object array = Storage.classStorage.get("Widget").getField("getChildren").getValue(obj);
		if (array == null)
			return null;

		int length = Array.getLength(array);
		Widget[] arr = new Widget[length];
		int nonNullCount = 0;
		for (int i = 0; i < length; i++) {
			Object o = Array.get(array, i);
			if (o != null) {
				nonNullCount++;
				arr[i] = new Widget(o, client);
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
		
		client.getCache().put("Widget#getChildren" + hash, finalArr);
		return finalArr;
	}

	public String getText() {
		Object cachedObject = client.getCache().get("Widget#getChildren" + hash);
		if (cachedObject != null) {
			return (String) cachedObject;
		}
		
		RSField field = Storage.classStorage.get("Widget").getField("getText");
		String text = (String) field.getValue(obj);
		
		client.getCache().put("Widget#getText" + hash, text);
		return text;
	}

	public int getId() {
		Object cachedObject = client.getCache().get("Widget#getChildren" + hash);
		if (cachedObject != null) {
			return (int) cachedObject;
		}
		
		RSField field = Storage.classStorage.get("Widget").getField("getId");
		int id = (int) field.getValue(obj);
		
		client.getCache().put("Widget#getId" + hash, id);
		return id;
	}

}
