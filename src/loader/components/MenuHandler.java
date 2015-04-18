package loader.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import loader.Settings;
import loader.game.Game;

public class MenuHandler implements ActionListener {

	private JFrame frame;
	private Game game;
	private Console console;

	//Menus
	private JMenuBar menuBar;

	//Hopping
	private JMenu hopMenu;
	private JMenu worldsMenu;
	private JMenu activitiesMenu;
	private Hashtable<Integer, BufferedImage> flags;

	//Settings
	private JMenu settingsMenu;
	private JMenuItem debug;
	private JMenuItem fps;
	private JMenuItem exp;
	private JMenuItem pack;
	private JMenuItem consoleItem;
	private JMenuItem playAlert;
	private JMenuItem drawNpcs;
	private JMenuItem prayerAlert;
	private JMenuItem zulrah;
	
	
	private JMenu expTrackingMenu;
	private JMenuItem resetExph;

	public MenuHandler(JFrame frame, Game game, Console console) {
		this.game = game;
		this.frame = frame;
		this.console = console;

		menuBar = new JMenuBar();

		//Hop
		hopMenu = new JMenu("Hop");
		worldsMenu = new JMenu("Worlds");
		activitiesMenu = new JMenu("Activities");

		hopMenu.add(activitiesMenu);
		hopMenu.add(worldsMenu);
		menuBar.add(hopMenu);

		flags = new Hashtable<Integer, BufferedImage>();
		try {
			flags.put(0, getScaledImage(ImageIO.read(new File("imgs/flags/0.png")), 15));
			flags.put(1, getScaledImage(ImageIO.read(new File("imgs/flags/1.png")), 15));
			flags.put(7, getScaledImage(ImageIO.read(new File("imgs/flags/7.png")), 15));
		} catch (IOException e) {
			e.printStackTrace();
		}

		//Exp tracking
		expTrackingMenu = new JMenu("Exp/h");
		resetExph = new JMenuItem(new AbstractAction("Reset") {
			public void actionPerformed(ActionEvent e) {
				Settings.RESET_EXPH = true;
			}
		});
		expTrackingMenu.add(resetExph);
		menuBar.add(expTrackingMenu);

		//Settings
		settingsMenu = new JMenu("Settings");
		debug = new JMenuItem("Draw Debug");
		fps = new JMenuItem("Draw Fps");
		exp = new JMenuItem("Draw Exp");
		pack = new JMenuItem("Pack");
		consoleItem = new JMenuItem("Console");
		playAlert = new JMenuItem("Player Alert");
		drawNpcs = new JMenuItem("Draw Npcs");
		zulrah = new JMenuItem("Zulrah");
		prayerAlert = new JMenuItem("Prayer Alert");
		
		debug.addActionListener(this);
		fps.addActionListener(this);
		exp.addActionListener(this);
		pack.addActionListener(this);
		consoleItem.addActionListener(this);
		playAlert.addActionListener(this);
		drawNpcs.addActionListener(this);
		zulrah.addActionListener(this);
		prayerAlert.addActionListener(this);
		
		settingsMenu.add(fps);
		settingsMenu.add(exp);
		settingsMenu.add(debug);
		settingsMenu.add(pack);
		settingsMenu.add(consoleItem);
		settingsMenu.add(playAlert);
		settingsMenu.add(drawNpcs);
		settingsMenu.add(zulrah);
		settingsMenu.add(prayerAlert);
		
		menuBar.add(settingsMenu);

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

	public void makeWorldMenu() {
		WorldParser wp = new WorldParser();
		worldsMenu.removeAll();
		JPopupMenu worldsPopupMenu = worldsMenu.getPopupMenu();
		worldsPopupMenu.setLayout(new GridBagLayout());//17, 3

		JPopupMenu activitiesPopupMenu = activitiesMenu.getPopupMenu();
		activitiesPopupMenu.setLayout(new GridBagLayout());//16, 2

		int i = 0;
		int j = 0;
		for (World w : wp.getWorlds()) {
			//System.out.println(w);
			if (i >= 18) {
				j++;
				i = 0;
			}

			String text = (w.id - 300) + "";
			if (w.flags == 0) {
				text += "ғ";
			}
			JMenuItem mi = new JMenuItem(new AbstractAction(text) {
				public void actionPerformed(ActionEvent e) {
					game.getClient().hopWorld(w.id);
				}
			});

			mi.setIcon(new ImageIcon(flags.get(w.location)));
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = j;
			c.gridy = i;
			worldsPopupMenu.add(mi, c);
			i++;
		}

		World[] worlds = wp.getWorlds();
		Arrays.sort(worlds);
		i = 0;
		j = 0;
		for (World w : worlds) {
			if (!w.activity.equals("-")) {

				if (i >= 17) {
					j++;
					i = 0;
				}

				JMenuItem mi2 = new JMenuItem(new AbstractAction(w.activity) {
					public void actionPerformed(ActionEvent e) {
						game.getClient().hopWorld(w.id);
					}
				});
				GridBagConstraints c = new GridBagConstraints();
				c.gridx = j;
				c.gridy = i;
				activitiesPopupMenu.add(mi2, c);
				i++;
			}
		}

	}

	public JMenuBar makeJMenuBar() {
		makeWorldMenu();

		return menuBar;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(debug)) {
			Settings.SHOW_DEBUG = !Settings.SHOW_DEBUG;
		}
		if (e.getSource().equals(fps)) {
			Settings.SHOW_FPS = !Settings.SHOW_FPS;
		}
		if (e.getSource().equals(exp)) {
			Settings.SHOW_EXP = !Settings.SHOW_EXP;
		}
		if (e.getSource().equals(pack)) {
			frame.pack();
		}
		if (e.getSource().equals(consoleItem)) {
			for (Component c : frame.getContentPane().getComponents()) {
				if (c.equals(console)){
					frame.getContentPane().remove(console);
					frame.pack();
					return;
				}
			}
			frame.getContentPane().add(console, BorderLayout.EAST);
			frame.pack();
		}
		if (e.getSource().equals(playAlert)){
			Settings.PLAYER_ALERT = !Settings.PLAYER_ALERT;
		}
		if (e.getSource().equals(drawNpcs)){
			Settings.DRAW_NPCS = !Settings.DRAW_NPCS;
		}
		
		if (e.getSource().equals(zulrah)){
			Settings.ZULRAH = !Settings.ZULRAH;
		}
		
		if (e.getSource().equals(prayerAlert)){
			Settings.PRAYER_ALERT = !Settings.PRAYER_ALERT;
		}
	}
	

}
