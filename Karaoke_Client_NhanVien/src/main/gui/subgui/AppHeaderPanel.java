package gui.subgui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;

import javax.swing.SwingConstants;

import utils.guicomponents.MyImageIcon;

import java.awt.GridLayout;
import java.awt.Font;

public class AppHeaderPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public AppHeaderPanel() {
		setSize(490, 205);
		setOpaque(false);
		setLayout(new GridLayout(0, 1, 0, 0));

		JLabel lblTenNhom = new JLabel("Nhóm 6");
		lblTenNhom.setForeground(Color.LIGHT_GRAY);
		lblTenNhom.setFont(new Font("Times New Roman", Font.BOLD, 35));
		lblTenNhom.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblTenNhom);

		JLabel lblAppLogo = new JLabel();
		lblAppLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblAppLogo.setIcon(new MyImageIcon("src/main/resources/images/icons/applogo.png", 100, 100, 999));
		add(lblAppLogo);

		JLabel lblTenApp = new JLabel("Phần mềm quản lý KaraGOke");
		lblTenApp.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblTenApp.setForeground(Color.WHITE);
		lblTenApp.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblTenApp);
	}
}
