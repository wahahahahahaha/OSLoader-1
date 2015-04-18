package loader.components;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import loader.game.Game;

public class Console extends JPanel implements ActionListener {
	protected JTextField textField;
	protected JTextArea textArea;
	private final static String newline = "\n";
	private Game game;

	public Console(Game game) {
		super(new GridBagLayout());
		this.game = game;

		textField = new JTextField(20);
		textField.addActionListener(this);

		textArea = new JTextArea(15, 20);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(textArea);

		//Add Components to this panel.
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;

		c.fill = GridBagConstraints.HORIZONTAL;
		add(textField, c);

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		this.add(scrollPane, c);
	}

	private int[] settingsArray;

	public void actionPerformed(ActionEvent evt) {
		String text = textField.getText();

		//textField.selectAll();
		textField.setText("");

		//Make sure the new text is visible, even if there
		//was a selection in the text area.
		textArea.setCaretPosition(textArea.getDocument().getLength());

		//handle text
		if (text.startsWith("clear")) {
			textArea.setText("");
		}
		if (text.equals("settings print")) {
			String s = "";
			for (int i = 0; i < 2000; i++) {
				s += i + ": " + game.getClient().getSetting(i) + "\r\n";
			}
			textArea.setText(s);
		}
		if (text.equals("settings first")) {
			settingsArray = game.getClient().getSettingsArray();
			textArea.append("cached settings" + newline);
		}

		if (text.equals("settings changes")) {
			int[] temp =  game.getClient().getSettingsArray();
			String s = "";
			for (int i = 0; i < settingsArray.length; i ++){
				if (settingsArray[i] != temp[i]){
					s += i + ": " + settingsArray[i] + " to " + temp[i] + "\r\n";
				}
			}
			textArea.setText(s);
		}
		if (text.equals("socket close")){
			textArea.append("closing socket" + newline);
			game.getClient().getSocket().close();
			
		}

	}

}
