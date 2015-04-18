package loader.game;

import java.applet.Applet;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.util.ArrayList;
import java.util.List;

import loader.game.accessors.Client;
import loader.game.listeners.DebugTextPaintListener;
import loader.game.listeners.ExpPaintListener;
import loader.game.listeners.FpsPaintListener;
import loader.game.listeners.InputListeners;
import loader.game.listeners.NpcPaintListener;
import loader.game.listeners.PaintListener;
import loader.game.listeners.PlayerAlertPaintListener;
import loader.game.listeners.PrayerAlertPaintListener;
import loader.game.listeners.TextPaintListener;
import loader.game.listeners.zulrah.ZulrahPaintListener;
import loader.jarutils.JarLoader;

public class Game extends Canvas implements Runnable {

	public ThreadGroup threadGroup;
	private Thread paintThread;
	private final BufferedImage gameImage;
	public final BufferedImage paintImage;
	private JarLoader jarLoader;
	private List<PaintListener> paintListeners;
	private List<TextPaintListener> textPaintListeners;
	private InputListeners inputListeners;
	private Applet applet;
	public boolean loading = true;
	private static final long serialVersionUID = 1L;
	private Client client;

	public Client getClient() {
		return client;
	}

	public Game() {
		threadGroup = new ThreadGroup("RSGame");
		gameImage = new BufferedImage(765, 503, BufferedImage.TYPE_INT_RGB);
		paintImage = new BufferedImage(765, 503, BufferedImage.TYPE_INT_RGB);

		new Thread(new Runnable() {
			public void run() {
				try {
					jarLoader = new JarLoader();

					Class<?> c = jarLoader.loadClass("client");
					applet = (Applet) c.newInstance();
					applet.setStub(jarLoader.getAppletStub());
					applet.init();
					applet.start();

					//Sleeping to let the game load
					Thread.sleep(1000);

					client = new Client(applet);

					inputListeners = new InputListeners(true, applet);
					addMouseListener(inputListeners);
					addMouseMotionListener(inputListeners);
					addMouseWheelListener(inputListeners);
					addKeyListener(inputListeners);
					addFocusListener(inputListeners);

					paintListeners.add(new FpsPaintListener(client));
					paintListeners.add(new ExpPaintListener(client));
					paintListeners.add(new PlayerAlertPaintListener(client));
					paintListeners.add(new NpcPaintListener(client));
					paintListeners.add(new PrayerAlertPaintListener(client));
					paintListeners.add(new ZulrahPaintListener(client));

					textPaintListeners.add(new DebugTextPaintListener(client));

				} catch (Exception e) {
					e.printStackTrace();
				}

				loading = false;
				System.out.println("Done Loading");
			}
		}).start();

		paintListeners = new ArrayList<PaintListener>();
		textPaintListeners = new ArrayList<TextPaintListener>();

		paintThread = new Thread(threadGroup, this, "paint");
		paintThread.start();

		this.setSize(765, 503);
		System.out.println("Game started...");
	}

	@Override
	public void run() {
		while (true)
			try {
				if (this.isShowing()) {
					repaint();
					Thread.sleep(1000 / 50);
				} else {
					Thread.sleep(300);
				}
			} catch (InterruptedException ignored) {
			}
	}

	@Override
	public void paint(Graphics g) {
		try {
			if (!this.isVisible())
				return;

			if (client != null && client.getCache() != null) {
				client.getCache().clear();
			}

			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			Rectangle r = g2d.getClipBounds();
			if (loading) {
				g2d.drawImage(gameImage, r.x, r.y, r.x + r.width, r.y + r.height, r.x, r.y, r.x + r.width, r.y + r.height, null);
				return;
			}

			if (gameImage != null) {
				paintImage.flush();

				Graphics paintGraphics = paintImage.getGraphics();
				paintGraphics.drawImage(gameImage, r.x, r.y, r.x + r.width, r.y + r.height, r.x, r.y, r.x + r.width, r.y + r.height, null);

				for (PaintListener pl : paintListeners) {
					pl.onRepaint(paintGraphics);
				}

				for (TextPaintListener tpl : textPaintListeners) {
					int y = 40;
					paintGraphics.setColor(Color.cyan);
					String[] lines = tpl.onTextRepaint();
					if (lines != null) {
						for (String line : lines) {
							if (line == null)
								continue;
							paintGraphics.drawString(line, 20, y);
							y += 15;
						}
					}
				}

				g2d.drawImage(paintImage, r.x, r.y, r.x + r.width, r.y + r.height, r.x, r.y, r.x + r.width, r.y + r.height, null);
				paintGraphics.dispose();
			}
		} catch (RasterFormatException ignored) {
		}
	}

	@Override
	public void repaint(int x, int y, int width, int height) {
		super.repaint(0, 0, 765, 503);
	}

	@Override
	public void repaint(long tm, int x, int y, int width, int height) {
		super.repaint(tm, 0, 0, 765, 503);
	}

	@Override
	public void paintAll(Graphics g) {
		paint(g);
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	public Graphics gamePaint() {
		return gameImage.getGraphics();
	}

	public Applet getApplet() {
		return applet;
	}

}
