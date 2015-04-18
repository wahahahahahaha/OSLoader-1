package loader.game.listeners.zulrah;

import loader.game.accessors.Npc;
import loader.game.helpers.Tile;

public class ZulrahInstance {

	@Override
	public String toString() {
		return "ZulrahInstance [loc=" + loc + ", id=" + id + ", type=" + type + ", jad=" + jad + ", standLoc=" + standLoc + "]";
	}

	private LOCATION loc;
	private int id;
	private TYPE type;
	private boolean jad;
	private STAND_LOCATION standLoc;

	public enum TYPE {
		RANGE, MAGIC, MELEE
	};

	public enum LOCATION {
		NORTH, SOUTH, EAST, WEST
	};

	public enum STAND_LOCATION {
		WEST, EAST, SOUTH, TOP_EAST, TOP_WEST, PILLAR_WEST_INSIDE, PILLAR_WEST_OUTSIDE, PILLAR_EAST_INSIDE, PILLAR_EAST_OUTSIDE
	};

	public ZulrahInstance(LOCATION loc, TYPE type, boolean jad, STAND_LOCATION standLoc) {
		this.loc = loc;
		this.type = type;
		this.jad = jad;
		this.standLoc = standLoc;
	}

	public Tile getZulrahTile(Tile startTile) {
		Tile adjusted = startTile.clone();
		switch (loc) {
		case NORTH:
			adjusted.setPosX(adjusted.getX() + 0);
			adjusted.setPosY(adjusted.getY() + 0);
			break;
		case SOUTH:
			adjusted.setPosX(adjusted.getX() + 0);
			adjusted.setPosY(adjusted.getY() - 11);
			break;
		case EAST:
			adjusted.setPosX(adjusted.getX() + 10);
			adjusted.setPosY(adjusted.getY() - 2);
			break;
		case WEST:
			adjusted.setPosX(adjusted.getX() - 10);
			adjusted.setPosY(adjusted.getY() - 2);
			break;
		}
		return adjusted;
	}

	public Tile getStandTile(Tile startTile) {
		Tile adjusted = startTile.clone();
		switch (standLoc) {
		case WEST:
			adjusted.setPosX(adjusted.getX() - 5);
			adjusted.setPosY(adjusted.getY() - 2);
			break;
		case EAST:
			adjusted.setPosX(adjusted.getX() + 5);
			adjusted.setPosY(adjusted.getY() - 2);
			break;
		case SOUTH:
			adjusted.setPosX(adjusted.getX() - 0);
			adjusted.setPosY(adjusted.getY() - 6);
			break;
		case TOP_EAST:
			adjusted.setPosX(adjusted.getX() + 4);
			adjusted.setPosY(adjusted.getY() + 3);
			break;
		case TOP_WEST:
			adjusted.setPosX(adjusted.getX() - 4);
			adjusted.setPosY(adjusted.getY() + 3);
			break;
		case PILLAR_WEST_INSIDE:
			adjusted.setPosX(adjusted.getX() - 2);
			adjusted.setPosY(adjusted.getY() - 5);
			break;
		case PILLAR_WEST_OUTSIDE:
			adjusted.setPosX(adjusted.getX() - 4);
			adjusted.setPosY(adjusted.getY() - 4);
			break;
		case PILLAR_EAST_INSIDE:
			adjusted.setPosX(adjusted.getX() + 2);
			adjusted.setPosY(adjusted.getY() - 5);
			break;
		case PILLAR_EAST_OUTSIDE:
			adjusted.setPosX(adjusted.getX() + 4);
			adjusted.setPosY(adjusted.getY() - 4);
			break;

		}
		return adjusted;
	}

	public LOCATION getLoc() {
		return loc;
	}

	public TYPE getType() {
		return type;
	}

	public boolean isJad() {
		return jad;
	}

	public STAND_LOCATION getStandLoc() {
		return standLoc;
	}

	public ZulrahInstance(Npc npc, Tile start) {
		//TODO somethign with locaition
		Tile t = npc.getLocation();
		int dx = start.getX() - t.getX();
		int dy = start.getY() - t.getY();

		//System.out.println("dx: " + dx + ", dy: " + dy);

		if (dx == -10 && dy == 2) {
			this.loc = LOCATION.EAST;
		} else if (dx == 10 && dy == 2) {
			this.loc = LOCATION.WEST;
		} else if (dx == 0 && dy == 11) {
			this.loc = LOCATION.SOUTH;
		} else if (dx == 0 && dy == 0) {
			this.loc = LOCATION.NORTH;
		}

		this.id = npc.getNpcDefinition().getId();
		switch (id) {
		case 2042:
			type = TYPE.RANGE;
			break;
		case 2043:
			type = TYPE.MELEE;
			break;
		case 2044:
			type = TYPE.MAGIC;
			break;

		}

		jad = false;
	}

	public boolean equals(Object o) {
		ZulrahInstance i = (ZulrahInstance) o;
		return loc.equals(i.loc) && type.equals(i.type) && jad == i.jad;
	}

}
