package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TroChoiCaroPanel extends JPanel{
	private final int BOARD_SIZE = 15;
	private JButton[][] buttons;

	public TroChoiCaroPanel() {
		setSize(700, 700);
		setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));

		buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				buttons[i][j] = new JButton();
				buttons[i][j].setFont(new Font("Arial", Font.BOLD, 14));
				buttons[i][j].addMouseListener(new ButtonClickListener(i, j));
				add(buttons[i][j]);
			}
		}

		setVisible(true);
	}

	private class ButtonClickListener implements MouseListener {
		private int row;
		private int col;

		public ButtonClickListener(int row, int col) {
			this.row = row;
			this.col = col;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			if (SwingUtilities.isRightMouseButton(e)) {
				buttons[row][col].setForeground(Color.red);
				buttons[row][col].setText("X");
			}
			if (SwingUtilities.isLeftMouseButton(e)) {
				buttons[row][col].setForeground(Color.green);
				buttons[row][col].setText("O");
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}
	}
}