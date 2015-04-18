package loader.game.helpers;


public class Tile {
	private int posX;
	private int posY;
	private int plane = -999;

	public Tile(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}

	public Tile(int posX, int posY, int plane) {
		this.posX = posX;
		this.posY = posY;
		this.plane = plane;
	}

	public int getX() {
		return posX;
	}

	public int getY() {
		return posY;
	}

	public int getPlane() {
		return plane;
	}

	public void setPlane(int plane) {
		this.plane = plane;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int distanceTo(Tile wo) {
		Tile tile = wo.getLocation();
		return (int) Math.hypot(tile.posX - this.posX, tile.posY - this.posY);
	}

	public double distanceToPrecise(Tile wo) {
		Tile tile = wo.getLocation();
		return Math.hypot(tile.posX - this.posX, tile.posY - this.posY);
	}

	public String toString() {
		if (plane == -999) {
			return "[x=" + posX + ",y=" + posY + "]";
		} else {
			return "[x=" + posX + ",y=" + posY + "plane=" + plane + "]";
		}
	}

	public boolean equals(Tile tile) {
		return posX == tile.posX && posY == tile.posY && plane == tile.plane;
	}

	public Tile getLocation() {
		return this;
	}

	public Tile clone() {
		return new Tile(posX, posY, plane);
	}
}
