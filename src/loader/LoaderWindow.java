package loader;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.ToolTipManager;

import loader.components.Console;
import loader.components.MenuHandler;
import loader.game.Game;

public class LoaderWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	public static Game game;
	private JPanel gamePanel;
	private Console console;
	private MenuHandler menuHandler;

	public LoaderWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(765, 503);
		setTitle("OSLoader");
		setLayout(new BorderLayout());

		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);

		Container contentPanel = getContentPane();
		contentPanel.setLayout(new BorderLayout());
		gamePanel = new JPanel();
		contentPanel.add(gamePanel, BorderLayout.WEST);

		try {
			gamePanel.repaint();
			gamePanel.revalidate();
			game = new Game();
			gamePanel.add(game, BorderLayout.WEST);
			gamePanel.setVisible(true);
			gamePanel.setSize(getSize());
			gamePanel.repaint();
			gamePanel.revalidate();
		} catch (Exception e) {
		}

		console = new Console(game);

		menuHandler = new MenuHandler(this, game, console);
		this.setJMenuBar(menuHandler.makeJMenuBar());

	}

}