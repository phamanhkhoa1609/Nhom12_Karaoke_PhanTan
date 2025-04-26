package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;

public class TroChoiDoMinPanel extends JPanel {

	private JPanel mainPanel;

	private int boardX = 15;
	private int boardY = 15;
	private int bombs = 35;

	private int bombsMarked;
	private int cleanFields;
	private int seconds;

	private boolean gameOver = false;

	private Map<Integer, Map<Integer, mweeperField>> boardMap;
	private Map<Integer, position> bombMap;
	private JPanel boardPanel;
	private JPanel headPanel;
	private JSpinner spnBomb;
	private JLabel lblRong;
	private JLabel lblSoBom;

	public TroChoiDoMinPanel() {
		setLayout(new BorderLayout());
		init();
		setPanel();
	}

	private void init() {
		bombMap = new HashMap<Integer, TroChoiDoMinPanel.position>();
		boardMap = new HashMap<Integer, Map<Integer, mweeperField>>();
		bombsMarked = 0;
		cleanFields = (boardX * boardY) - bombs;
		seconds = 0;

		for (int i = 1; i <= boardX; i++) {
			boardMap.put(i, new HashMap<Integer, mweeperField>());
			for (int j = 1; j <= boardY; j++) {
				boardMap.get(i).put(j, new mweeperField(i, j));
			}
		}
		placeBombs();
	}

	private boolean placeBombs() {

		Random pX = new Random();
		Random pY = new Random();
		int bombCount = 0;
		// while( bombMap.size() < bombs ) {
		while (bombCount < bombs) {
			int x = (1 + pX.nextInt(boardX));
			int y = (1 + pY.nextInt(boardY));
			if (!boardMap.get(x).get(y).isBomb()) {
				boardMap.get(x).get(y).setBomb();
				bombCount++;
				bombMap.put(bombCount, new position(x, y));
			}
		}
		return true;
	}

	private void setPanel() {

		add(head(), BorderLayout.PAGE_START);

		add(board(), BorderLayout.CENTER);
	}

	private JPanel head() {
		headPanel = new JPanel();

		spnBomb = new JSpinner(new SpinnerNumberModel(bombs, 1, boardX*boardY, 1));

		JButton start = new JButton("Chơi mới");
		start.addActionListener(new mweeperAction(GameActions.START));
		headPanel.setLayout(new MigLayout("", "[][47px][grow][73px]", "[21px]"));
		
		lblSoBom = new JLabel("Số lượng boom:");
		lblSoBom.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSoBom.setForeground(Color.WHITE);
		headPanel.add(lblSoBom, "cell 0 0");

		headPanel.add(spnBomb, "cell 1 0,alignx left,growy");
		
		lblRong = new JLabel("");
		headPanel.add(lblRong, "cell 2 0,growx");

		headPanel.add(start, "cell 3 0,alignx left,growy");
		return headPanel;
	}

	private JPanel board() {
		boardPanel = new JPanel();
		GridLayout gLayout = new GridLayout(15, 15, 0, 0);
		boardPanel.setLayout(gLayout);

		for (Integer x : boardMap.keySet()) {
			for (Integer y : boardMap.get(x).keySet()) {
				boardPanel.add(boardMap.get(x).get(y).getButton());
			}
		}

		return boardPanel;
	}

	private void gameOver() {
		this.gameOver = true;
		for (Integer x : boardMap.keySet()) {
			for (Integer y : boardMap.get(x).keySet()) {
				boardMap.get(x).get(y).trigger();
			}
		}
	}

	public class mweeperField implements mousePerformer {
		private position pos;
		private FieldStatus status = FieldStatus.HIDE_UNMARKED;
		private boolean isBomb = false;
		private int bombsAroundMe = 0;
		private JButton but;
		private boolean isTriggered = false;

		public mweeperField(int x, int y) {
			this.pos = new position(x, y);
			init();
		}

		public mweeperField(position p) {
			this.pos = p;
			init();
		}

		public void resetField() {
			status = FieldStatus.HIDE_UNMARKED;
			isBomb = false;
			bombsAroundMe = 0;
			isTriggered = false;
			but.setFont(new Font("Arial", Font.BOLD, 13));
			but.setBackground(Color.LIGHT_GRAY);
			but.setText(" ");
			but.setBorder(new LineBorder(Color.black));
			but.setEnabled(true);
		}

		public void setBomb() {
			this.isBomb = true;
		}

		public boolean isBomb() {
			return isBomb;
		}

		private void init() {
			but = new JButton(" ");
			but.setMaximumSize(new Dimension(16, 16));
			but.setMinimumSize(new Dimension(16, 16));
			but.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
			but.setMargin(new Insets(0, 0, 0, 0));
			but.setBackground(Color.LIGHT_GRAY);
			but.addMouseListener(new mweeperMouseListener(this.pos, this));
			but.setFont(new Font("Arial", Font.BOLD, 14));
		}

		private void setButton() {

			switch (status) {
			case HIDE_MARKED:
				// but.setForeground( new Color(224, 124, 168) );
				but.setForeground(Color.RED);
				but.setText("@");
				but.setEnabled(true);
				break;
			case HIDE_UNMARKED:
				but.setForeground(Color.BLACK);
				but.setText(" ");
				but.setEnabled(true);
				break;
			case OPEN_NOBOMB:
				switch (this.bombsAroundMe) {
				case 1:
				case 2:
					but.setForeground(Color.BLUE);
					break;
				case 3:
				case 4:
					but.setForeground(Color.MAGENTA);
					break;
				case 5:
				case 6:
					but.setForeground(Color.RED);
					break;
				case 7:
				case 8:
					but.setForeground(Color.PINK);
					break;
				}
				String butText = " ";
				if (this.bombsAroundMe > 0) {
					butText = String.valueOf(this.bombsAroundMe);
				}
				but.setEnabled(false);
				but.setText(butText);

				break;

			case OPEN_BOMB: // GAME OVER
				but.setForeground(Color.BLACK);
				but.setFont(new Font("Arial", Font.BOLD, 20));
				but.setVerticalAlignment(SwingConstants.CENTER);
				but.setHorizontalAlignment(SwingConstants.CENTER);
				but.setText("*");

				break;
			}
//          but.setEnabled(false);
			but.validate();
			but.repaint();
			boardPanel.validate();
			boardPanel.repaint();
			repaint();
		}

		public JButton getButton() {
			return but;
		}

		/*
		 * +-----+-----+-----+ | x-1 | x | x+1 | | y-1 | y-1 | y-1 | +-----+-----+-----+
		 * | x-1 | x/y | x+1 | | y | | y | +-----+-----+-----+ | x-1 | x | x+1 | | y+1 |
		 * y+1 | y+1 | +-----+-----+-----+
		 */

		private void scan() {
			bombsAroundMe = 0;
			for (Integer k : pos.posAroundMe.keySet()) {
				position p2 = pos.posAroundMe.get(k);
				if (boardMap.get(p2.x).get(p2.y).isBomb()) {
					bombsAroundMe++;
				}
			}
		}

		public void trigger() {
			if (!isTriggered) {
				isTriggered = true;
				if (!isBomb) {
					status = FieldStatus.OPEN_NOBOMB;
				} else {
					status = FieldStatus.OPEN_BOMB;
				}
				scan();

				setButton();
				if (bombsAroundMe == 0) {
					// um mich herum triggern
					for (Integer k : pos.posAroundMe.keySet()) {
						position p2 = pos.posAroundMe.get(k);
						boardMap.get(p2.x).get(p2.y).trigger();
					}
				}
			}
		}

		@Override
		public void doClick(MouseEvent e, position pos) {
			switch (e.getButton()) {
			case 1: // Links Klick = triggern wenn nich markiert und hide
				if (this.status.equals(FieldStatus.HIDE_UNMARKED)) {
					if (this.isBomb) {
						// GAME OVER =8-(
						status = FieldStatus.OPEN_BOMB;
						but.setBackground(Color.RED);
						gameOver();
					} else {
						trigger();
					}
				}
				break;

			case 3: // Rechtsklick

				if (this.status.equals(FieldStatus.HIDE_UNMARKED)) {
					// Mark Field
					this.status = FieldStatus.HIDE_MARKED;
					bombsMarked++;
				} else {
					// Umark Field
					this.status = FieldStatus.HIDE_UNMARKED;
					bombsMarked--;
				}
				setButton();
				break;
			}

		}

	}

	public class position {
		public int x = 0;
		public int y = 0;
		public Map<Integer, position> posAroundMe;

		public position(int x, int y) {
			this.x = x;
			this.y = y;
			posAroundMe = new HashMap<Integer, TroChoiDoMinPanel.position>();
			setPosAroundMe();
		}

		public position(int x, int y, boolean setPos) {
			posAroundMe = new HashMap<Integer, TroChoiDoMinPanel.position>();
			this.x = x;
			this.y = y;
		}

		private void setPosAroundMe() {
			int c = 1;
			for (int x2 = (x - 1); x2 <= (x + 1); x2++) {
				for (int y2 = (y - 1); y2 <= (y + 1); y2++) {
					if (((x2 != x) || (y2 != y)) && (x2 > 0 && x2 <= boardX && y2 > 0 && y2 <= boardY)) {
						posAroundMe.put(c++, new position(x2, y2, false));
					}
				}
			}
		}
	}

	public enum FieldStatus {
		HIDE_UNMARKED, HIDE_MARKED, OPEN_NOBOMB, OPEN_BOMB;
	}

	public enum GameActions {
		START;
	}

	public class mweeperAction extends AbstractAction {
		private GameActions gameAction;

		public mweeperAction(GameActions ga) {
			this.gameAction = ga;
		}

		@Override
		public void actionPerformed(ActionEvent ae) {
			switch (gameAction) {
			case START:
				for (Integer x : boardMap.keySet()) {
					for (Integer y : boardMap.get(x).keySet()) {
						boardMap.get(x).get(y).resetField();
						;
						boardMap.get(x).get(y).getButton().validate();
						boardMap.get(x).get(y).getButton().repaint();
						;
					}
				}
				int newBombCount = (int) spnBomb.getValue();
				if (newBombCount < 10) {
					newBombCount = 10;
				}
				if (newBombCount > ((boardX * 2) + 20)) {
					newBombCount = ((boardX * 2) + 20);
				}
				bombs = newBombCount;
				spnBomb.setValue(bombs);
				placeBombs();
				boardPanel.validate();
				boardPanel.repaint();
				repaint();
				break;
			}
		}
	}

	public class mweeperMouseListener implements MouseListener {
		private position pos;
		mousePerformer performer;

		public mweeperMouseListener(position pos, mousePerformer acPerf) {
			this.pos = pos;
			this.performer = acPerf;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			this.performer.doClick(e, pos);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

	}

	public interface mousePerformer {
		public void doClick(MouseEvent e, position pos);
	}

	public interface actionPerformer {
		public void doAction(ActionEvent ae, GameActions ga);
	}
}