package loader.game.listeners;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import loader.Settings;
import loader.game.accessors.Client;

public class ExpPaintListener implements PaintListener {
	private Client game;
	private DecimalFormat formatter;
	private long totalExp = 0;
	private int[] lastExps = new int[25];
	private ArrayList<ExpDrop> drops;
	private Thread expUpdateThread;
	private Hashtable<String, BufferedImage> icons;
	private String[] skillIds = { "Attack", "Defense", "Strength", "Hitpoints", "Ranged", "Prayer", "Magic", "Cooking", "Woodcutting", "Fletching", "Fishing", "Firemaking", "Crafting", "Smithing",
			"Mining", "Herblore", "Agility", "Thieving", "Slayer", "Farming", "Runecrafting", "Hunter", "Construction", "Construction", "Construction" };
	private Font font = new Font("Dialog", Font.PLAIN, 12);
	private boolean threadRunning = false;

	private int[] startExp;
	private int[] currentExps;
	private long startTime;

	public ExpPaintListener(Client game) {
		this.game = game;
		this.drops = new ArrayList<ExpDrop>();
		this.formatter = new DecimalFormat("#,###");
		this.icons = new Hashtable<String, BufferedImage>();

		expUpdateThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {

						if (game.getGameState() == 30 || game.getGameState() == 25) {
							currentExps = game.getExperiences();

							List<BufferedImage> changed = new ArrayList<BufferedImage>();
							int increasedExp = 0;
							for (int i = 0; i < currentExps.length; i++) {
								if (currentExps[i] > lastExps[i]) {
									changed.add(deepCopy(icons.get(skillIds[i])));
									increasedExp += currentExps[i] - lastExps[i];
								}
							}

							if (increasedExp > 0) {
								drops.add(new ExpDrop(new Point(515, 100), formatter.format(increasedExp), changed));
								lastExps = currentExps;
							}

							if (Settings.RESET_EXPH) {
								startExp = game.getExperiences();
								startTime = System.currentTimeMillis();

								//hacky attempts to prevent start exp from starting at 0 when the user does login
								for (int i : startExp) {
									if (i != 0) {
										Settings.RESET_EXPH = false;
										break;
									}
								}

							}

						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						Thread.sleep(1000 / 50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		});

		expUpdateThread.start();
		threadRunning = true;

		try {
			icons.put("Agility", getScaledImage(ImageIO.read(new File("imgs/skill_icons/Agility-icon.png")), 13));
			icons.put("Attack", getScaledImage(ImageIO.read(new File("imgs/skill_icons/Attack_icon.png")), 13));
			icons.put("Construction", getScaledImage(ImageIO.read(new File("imgs/skill_icons/Construction_icon.png")), 13));
			icons.put("Cooking", getScaledImage(ImageIO.read(new File("imgs/skill_icons/Cooking-icon.png")), 13));
			icons.put("Crafting", getScaledImage(ImageIO.read(new File("imgs/skill_icons/Crafting-icon.png")), 13));
			icons.put("Defense", getScaledImage(ImageIO.read(new File("imgs/skill_icons/Defence-icon.png")), 13));
			icons.put("Farming", getScaledImage(ImageIO.read(new File("imgs/skill_icons/Farming-icon.png")), 13));
			icons.put("Firemaking", getScaledImage(ImageIO.read(new File("imgs/skill_icons/Firemaking-icon.png")), 13));
			icons.put("Fishing", getScaledImage(ImageIO.read(new File("imgs/skill_icons/Fishing-icon.png")), 13));
			icons.put("Fletching", getScaledImage(ImageIO.read(new File("imgs/skill_icons/Fletching-icon.png")), 13));
			icons.put("Herblore", getScaledImage(ImageIO.read(new File("imgs/skill_icons/Herblore-icon.png")), 13));
			icons.put("Hitpoints", getScaledImage(ImageIO.read(new File("imgs/skill_icons/Hitpoints_icon.png")), 13));
			icons.put("Hunter", getScaledImage(ImageIO.read(new File("imgs/skill_icons/Hunter-icon.png")), 13));
			icons.put("Magic", getScaledImage(ImageIO.read(new File("imgs/skill_icons/Magic-icon.png")), 13));
			icons.put("Mining", getScaledImage(ImageIO.read(new File("imgs/skill_icons/Mining-icon.png")), 13));
			icons.put("Prayer", getScaledImage(ImageIO.read(new File("imgs/skill_icons/Prayer-icon.png")), 13));
			icons.put("Ranged", getScaledImage(ImageIO.read(new File("imgs/skill_icons/Ranged-icon.png")), 13));
			icons.put("Runecrafting", getScaledImage(ImageIO.read(new File("imgs/skill_icons/Runecrafting-icon.png")), 13));
			icons.put("Slayer", getScaledImage(ImageIO.read(new File("imgs/skill_icons/Slayer-icon.png")), 13));
			icons.put("Smithing", getScaledImage(ImageIO.read(new File("imgs/skill_icons/Smithing-icon.png")), 13));
			icons.put("Strength", getScaledImage(ImageIO.read(new File("imgs/skill_icons/Strength-icon.png")), 13));
			icons.put("Thieving", getScaledImage(ImageIO.read(new File("imgs/skill_icons/Thieving-icon.png")), 13));
			icons.put("Woodcutting", getScaledImage(ImageIO.read(new File("imgs/skill_icons/Woodcutting-icon.png")), 13));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onRepaint(Graphics g) {
		
		if (Settings.SHOW_EXP) {
			if (!threadRunning) {
				expUpdateThread.resume();
				threadRunning = true;
			}
		} else {
			if (expUpdateThread.isAlive()) {
				expUpdateThread.suspend();
				threadRunning = false;
			}
			return;
		}

		// TODO check if logged in
		if (game.getGameState() != 30 && game.getGameState() != 25) {
			return;
		}

		g.setFont(font);
		g.setColor(Color.yellow);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

		//Drops
		for (ExpDrop drop : (ArrayList<ExpDrop>) drops.clone()) {
			Point p = drop.getLoc();
			p.y = p.y - 1;
			if (p.y < 16) {
				totalExp += Long.valueOf(drop.getText().replaceAll(",", ""));
				drops.remove(drop);
			} else {
				FontMetrics fm = g2d.getFontMetrics();
				int x = p.x - fm.stringWidth(drop.getText());
				int width = 2;
				if (p.y < 35) {
					for (BufferedImage img : drop.getImgs()) {
						modAlpha(img, 0.8);
					}
				}
				for (BufferedImage img : drop.getImgs()) {
					width += img.getWidth();
					g2d.drawImage(img, x - width, p.y - img.getHeight() + 2, null);
				}
				g2d.setColor(Color.black);
				g2d.drawString(drop.getText(), x + 1, p.y + 1);
				g2d.setColor(Color.yellow);
				g2d.drawString(drop.getText(), x, p.y);
			}
		}

		//Total
		String text = formatter.format(totalExp);
		FontMetrics fm = g2d.getFontMetrics();
		int x = 515 - fm.stringWidth(text);
		g2d.setColor(Color.black);
		g2d.drawString(text, x + 1, 15  + 1);
		g2d.setColor(Color.yellow);
		g2d.drawString(text, x, 15);
		
		//Exph
		if (currentExps != null && startExp != null) {
			Map<Double, BufferedImage> map = new TreeMap<Double, BufferedImage>();
			for (int i = 0; i < currentExps.length; i++) {
				if (currentExps[i] > startExp[i]) {
					map.put(perHour(currentExps[i] - startExp[i]), icons.get(skillIds[i]));
				}
			}

			int textSpacing = 16;
			int startHeight = 335;
			
			//rectangle
			
			
			int i = 0;
			for (Entry<Double, BufferedImage> entry : map.entrySet()) {
				String rate = new DecimalFormat("0.00").format(entry.getKey()) + "k";
				fm = g2d.getFontMetrics();
				int yval = startHeight - i * textSpacing;
				int xval = 515 - fm.stringWidth(rate) - 2;
				int width = 2;
				
				
				//g2d.drawRect(xval - width - entry.getValue().getWidth(), yval - entry.getValue().getHeight() + 2, 515 - xval +  entry.getValue().getWidth(),  entry.getValue().getHeight());
				
				g2d.setColor(new Color(69, 60, 51, 210));
				g2d.fillRect(xval - width - entry.getValue().getWidth() - 4, yval - entry.getValue().getHeight() + 2 - 2, 515 - xval +  entry.getValue().getWidth() + 7,  entry.getValue().getHeight() + 3);
				
				g2d.setColor(Color.black);
				g2d.drawString(rate, xval + 1, yval  + 1);
				g2d.setColor(Color.yellow);
				g2d.drawString(rate, xval, yval);
				g2d.drawImage(entry.getValue(), xval - width - entry.getValue().getWidth(), yval - entry.getValue().getHeight() + 2, null);
				i++;
			}

		}
	}

	/**
	 * 
	 * @param : gained in k/ph
	 * @return
	 */
	public double perHour(int gained) {
		return ((gained) * 3600000D / (System.currentTimeMillis() - startTime) / 1000.0);
	}

	public static BufferedImage deepCopy(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	public static void modAlpha(BufferedImage modMe, double modAmount) {
		//
		for (int x = 0; x < modMe.getWidth(); x++) {
			for (int y = 0; y < modMe.getHeight(); y++) {
				//
				int argb = modMe.getRGB(x, y); // always returns TYPE_INT_ARGB
				int alpha = (argb >> 24) & 0xff; // isolate alpha

				alpha *= modAmount; // similar distortion to tape saturation
									// (has scrunching effect, eliminates
									// clipping)
				alpha &= 0xff; // keeps alpha in 0-255 range

				argb &= 0x00ffffff; // remove old alpha info
				argb |= (alpha << 24); // add new alpha info
				modMe.setRGB(x, y, argb);
			}
		}
	}

	private BufferedImage getScaledImage(BufferedImage src, int h) {

		int finalh = h;
		double factor = 1.0d;

		factor = ((double) src.getWidth() / (double) src.getHeight());
		int finalw = (int) (finalh * factor);

		BufferedImage resizedImg = new BufferedImage(finalw, finalh, BufferedImage.TRANSLUCENT);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(src, 0, 0, finalw, finalh, null);
		g2.dispose();
		return resizedImg;
	}

}

class ExpDrop {

	private Point loc;
	private String text;
	private List<BufferedImage> imgs;

	public Point getLoc() {
		return loc;
	}

	public void setLoc(Point loc) {
		this.loc = loc;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<BufferedImage> getImgs() {
		return imgs;
	}

	public ExpDrop(Point loc, String text, List<BufferedImage> imgs) {
		super();
		this.loc = loc;
		this.text = text;
		this.imgs = imgs;
	}

}