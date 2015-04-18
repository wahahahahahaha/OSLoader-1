package loader.injection.canvas;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import loader.LoaderWindow;

public class GameCanvas extends Canvas {

	private static final long serialVersionUID = 1L;

	@Override
	public Graphics getGraphics() {

		Graphics botGraphics = LoaderWindow.game.gamePaint();

		if (botGraphics != null) {
			return botGraphics;
		} else {
			return super.getGraphics();
		}
	}

	@Override
	public Image createImage(int width, int height) {
		return new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	}
}
