package loader.game.helpers;

import java.awt.Point;

import loader.game.accessors.Client;

public class Calculations {
	public static final int[] CURVESIN = new int[2048];
	public static final int[] CURVECOS = new int[2048];

	private Client client;

	static {
		for (int i = 0; i < 2048; i++) {
			CURVESIN[i] = (int) (65536D * Math.sin((double) i * 0.0030679614999999999D));
			CURVECOS[i] = (int) (65536D * Math.cos((double) i * 0.0030679614999999999D));
		}
	}

	public Calculations(Client client) {
		this.client = client;
	}

	public int tileHeight(int plane, int x, int y) {
		final int[][][] groundHeights = client.getTileHeights();
		if (groundHeights == null)
			return 0;
		int x1 = x >> 7;
		int y1 = y >> 7;
		if (x1 < 0 || y1 < 0 || x1 > 103 || y1 > 103)
			return 0;
		int x2 = x & 0x7f;
		int y2 = y & 0x7f;
		int zIndex = plane;
		if (zIndex > 3 && (client.getTileSettings()[1][x1][y1] & 2) == 2)
			zIndex++;
		int i2 = (((-x2 + 128) * groundHeights[zIndex][x1][y1]) + (x2 * groundHeights[zIndex][x1 + 1][y1])) >> 7;
		int j2 = ((groundHeights[zIndex][x1][1 + y1] * (128 - x2)) + (groundHeights[zIndex][1 + x1][1 + y1] * x2)) >> 7;
		return ((i2 * (128 - y2)) - -(y2 * j2)) >> 7;
	}

	public Point worldToScreen(int X, int Y, int height) {

		if (X < 128 || Y < 128 || X > 13056 || Y > 13056) {
			return new Point(-1, -1);
		}

		int Z = tileHeight(client.getPlane(), X, Y) - height;

		X -= client.getCameraX();
		Y -= client.getCameraY();
		Z -= client.getCameraZ();

		final int curveX = client.getCameraYaw();
		final int curveY = client.getCameraPitch();
		int curveCosX = CURVECOS[curveX];
		int curveCosY = CURVECOS[curveY];

		int curveSinX = CURVESIN[curveX];
		int curveSinY = CURVESIN[curveY];

		int tempCalculation = ((curveCosX * X) + (Y * curveSinX)) >> 16;
		Y = ((Y * curveCosX) - (X * curveSinX)) >> 16;
		X = tempCalculation;

		tempCalculation = ((Z * curveCosY) - (Y * curveSinY)) >> 16;
		Y = ((Z * curveSinY) - -(curveCosY * Y)) >> 16;
		Z = tempCalculation;

		if (Y < 50) {
			return new Point(-1, -1);
		} else {
			int calculatedScreenPosX = 256 + ((X << 9) / Y);
			int calculatedScreenPosY = (Z << 9) / Y + 167;

			return new Point(calculatedScreenPosX, calculatedScreenPosY);
		}
	}

	public int tileHeight(int plane, int x, int y, byte[][][] tileSettings, int[][][] groundHeights) {

		if (groundHeights == null)
			return 0;
		int x1 = x >> 7;
		int y1 = y >> 7;
		if (x1 < 0 || y1 < 0 || x1 > 103 || y1 > 103)
			return 0;
		int x2 = x & 0x7f;
		int y2 = y & 0x7f;
		int zIndex = plane;
		if (zIndex > 3 && (tileSettings[1][x1][y1] & 2) == 2)
			zIndex++;
		int i2 = (((-x2 + 128) * groundHeights[zIndex][x1][y1]) + (x2 * groundHeights[zIndex][x1 + 1][y1])) >> 7;
		int j2 = ((groundHeights[zIndex][x1][1 + y1] * (128 - x2)) + (groundHeights[zIndex][1 + x1][1 + y1] * x2)) >> 7;
		return ((i2 * (128 - y2)) - -(y2 * j2)) >> 7;
	}

	public Point worldToScreen(int X, int Y, int height, int cameraX, int cameraY, int cameraZ, int cameraYaw, int cameraPitch, int plane, byte[][][] tileSettings, int[][][] groundHeights) {

		if (X < 128 || Y < 128 || X > 13056 || Y > 13056) {
			return new Point(-1, -1);
		}

		int Z = tileHeight(plane, X, Y, tileSettings, groundHeights) - height;

		X -= cameraX;
		Y -= cameraY;
		Z -= cameraZ;

		final int curveX = cameraYaw;
		final int curveY = cameraPitch;
		int curveCosX = CURVECOS[curveX];
		int curveCosY = CURVECOS[curveY];

		int curveSinX = CURVESIN[curveX];
		int curveSinY = CURVESIN[curveY];

		int tempCalculation = ((curveCosX * X) + (Y * curveSinX)) >> 16;
		Y = ((Y * curveCosX) - (X * curveSinX)) >> 16;
		X = tempCalculation;

		tempCalculation = ((Z * curveCosY) - (Y * curveSinY)) >> 16;
		Y = ((Z * curveSinY) - -(curveCosY * Y)) >> 16;
		Z = tempCalculation;

		if (Y < 50) {
			return new Point(-1, -1);
		} else {
			int calculatedScreenPosX = 256 + ((X << 9) / Y);
			int calculatedScreenPosY = (Z << 9) / Y + 167;

			return new Point(calculatedScreenPosX, calculatedScreenPosY);
		}
	}

	public Point tileToScreen(Tile t) {
		return tileToScreen(t.getX(), t.getY(), 0.5, 0.5, 0);
	}

	public Point tileToScreen(int tileX, int tileY, double dX, double dY, int height) {
		return worldToScreen((int) ((tileX - client.getBaseX() + dX) * 128), (int) ((tileY - client.getBaseY() + dY) * 128), height);
	}

	public static int random(int min, int max) {
		return ((int) (Math.random() * (max - min))) + min;
	}

	public static double random(double min, double max) {
		return Math.random() * (max - min) + min;
	}

	public static boolean onScreen(int x, int y) {
		return x > 3 && y > 3 && x < 513 && y < 335;
	}

	public static boolean onScreen(Point p) {
		return onScreen(p.x, p.y);
	}

	public static boolean onFullScreen(int x, int y) {
		return x > -1 && y > -1 && x < 757 && y < 504;
	}

	public static boolean onFullScreen(Point p) {
		return onScreen(p.x, p.y);
	}

}
