package loader.components;

public class World implements Comparable {
	public String activity, domain;
	public int id, index, location, flags, playerCount;

	@Override
	public String toString() {
		return String.format("World[index:%d id:%d players:%d location:%d flags:%s domain:'%s' activity:'%s']", index, id, playerCount, location, flags, domain, activity);
	}

	@Override
	public int compareTo(Object a) {
		return this.activity.compareTo(((World) a).activity);
	}
}
