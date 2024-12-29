package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import connectDB.ConnectDB;

public class DangNhap_GUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel pnContent, pnWest, pnEAST, pnNorth, pnSouth;
	private JLabel lblError;
	private JTextField txtTaiKhoan;
	private JPasswordField txtMK;
	private JButton btnDangNhap, btnThoat;

	public DangNhap_GUI() throws Exception {
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		createGui();
	}

	public void createGui() throws IOException {
		setTitle("Đăng nhập");
//		setSize(500, 300);
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(true);
		setLocationRelativeTo(null);
		dangNhap();
	}

	public void dangNhap() throws IOException {
		pnContent = new JPanel();
		pnContent.setLayout(new BorderLayout());
		Color mauNen = new Color(205, 38, 38);

		pnNorth = new JPanel();
		pnContent.add(pnNorth, BorderLayout.NORTH);
		pnContent.setBackground(mauNen);
		
//		JLabel lblTenUngDung = new JLabel("Quản lý Karaoke Nice");
//		lblTenUngDung.setFont(new Font("Arial", Font.BOLD, 20));
//		lblTenUngDung.setForeground(Color.WHITE);
//		pnNorth.add(lblTenUngDung);
//		pnNorth.setBackground(mauNen);

		pnWest = new JPanel();
		pnContent.add(pnWest, BorderLayout.WEST);
		JLabel imageLabel = new JLabel();
		BufferedImage image = ImageIO.read(new File("src/images/IMG_KARAOKE_NICE.png"));
		Image scaledImage = image.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
		// Đặt hình ảnh vào JLabel
		imageLabel.setIcon(new ImageIcon(scaledImage));
		pnWest.add(imageLabel);

		pnEAST = new JPanel(new GridBagLayout());
		pnContent.add(pnEAST, BorderLayout.EAST);
		Color mauNenDangNhap = new Color(175, 238, 238);
		pnEAST.setBackground(mauNenDangNhap);
		
		JPanel pnDangNhap = new JPanel(new GridLayout(2,1));
		JPanel pnTitle = new JPanel(new GridBagLayout());
		JLabel lblTenUngDung = new JLabel("Quản lý Karaoke Nice");
		lblTenUngDung.setFont(new Font("Arial", Font.BOLD, 35));
//		lblTenUngDung.setForeground(Color.WHITE);
		pnTitle.add(lblTenUngDung);
		Color mauNenTitle = new Color(224, 255, 255);
		pnTitle.setBackground(mauNenTitle);
		
		Box b = Box.createVerticalBox();
		Box b1 = Box.createHorizontalBox();
		Box b2 = Box.createHorizontalBox();
		Box b3 = Box.createHorizontalBox();
		Box b4 = Box.createHorizontalBox();
		Box bError = Box.createHorizontalBox();
		TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
				"Đăng nhập");
		titledBorder.setTitleJustification(TitledBorder.CENTER);
		b.setBorder(titledBorder);

		ImageIcon iconUser = new ImageIcon(ClassLoader.getSystemResource("icons/addemp.png"));
		Image imageUser = iconUser.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		ImageIcon iconUser1 = new ImageIcon(imageUser);
		JLabel User = new JLabel();
		b1.add(Box.createHorizontalStrut(30));
		User.setIcon(iconUser1);
		b1.add(User);
		b1.add(Box.createHorizontalStrut(30));
		txtTaiKhoan = new JTextField(15);
		b1.add(txtTaiKhoan);
		b1.add(Box.createHorizontalStrut(30));

		ImageIcon iconPass = new ImageIcon(ClassLoader.getSystemResource("icons/change-password.png"));
		Image imagePass = iconPass.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		ImageIcon iconPass1 = new ImageIcon(imagePass);
		JLabel Pass = new JLabel();
		Pass.setIcon(iconPass1);
		b2.add(Box.createHorizontalStrut(30));
		b2.add(Pass);
		b2.add(Box.createHorizontalStrut(30));
		txtMK = new JPasswordField(15);
		b2.add(txtMK);
		txtTaiKhoan.setColumns(30);
		b2.add(Box.createHorizontalStrut(30));

		lblError = new JLabel("");
		lblError.setForeground(Color.red);
		bError.add(lblError);

		pnSouth = new JPanel();
		pnContent.add(pnSouth, BorderLayout.SOUTH);

		btnDangNhap = new JButton("Đăng nhập");
		btnDangNhap.setBackground(mauNen);
		btnDangNhap.setForeground(Color.WHITE);
		btnThoat = new JButton("Thoát");
		btnThoat.setBackground(mauNen);
		btnThoat.setForeground(Color.WHITE);
		b4.add(btnDangNhap);
		b4.add(Box.createHorizontalStrut(30));
		b4.add(btnThoat);

//		b.add(Box.createRigidArea(new Dimension(0, 200)));
		b.add(b1);
		b.add(Box.createRigidArea(new Dimension(0, 10)));
		b.add(b2);
		b.add(Box.createRigidArea(new Dimension(0, 20)));
		b.add(bError);
		b.add(Box.createRigidArea(new Dimension(0, 20)));
		b.add(b3);
		b.add(Box.createRigidArea(new Dimension(0, 20)));
		b.add(b4);
		b.add(Box.createRigidArea(new Dimension(0, 20)));
//		pnEAST.add(b);
//		pnEAST.setPreferredSize(new Dimension(500, 1000));
		pnDangNhap.add(pnTitle);
		pnDangNhap.add(b);
		
		// Canh giữa
		GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); 
        pnEAST.add(pnDangNhap, gbc);

		this.add(pnContent);
		btnDangNhap.addActionListener(this);
		btnThoat.addActionListener(this);
		
		txtTaiKhoan.setText("QL001");
		txtMK.setText("1234");
	}

	public static void main(String[] args) throws Exception {
		new DangNhap_GUI().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		try {
			if (o.equals(btnDangNhap)) {
				dNhap();
			} else if (o.equals(btnThoat)) {
				dispose();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	public void dNhap() {
		PreparedStatement stmt = null;
		Connection con = ConnectDB.getConnection();
		try (Statement statement = con.createStatement()) {
			String user = txtTaiKhoan.getText();
			char[] pass = txtMK.getPassword();

			String sql = "SELECT * FROM DangNhap WHERE TenTK = ? AND MatKhau = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, user);
			String pwd = new String(pass);
			stmt.setString(2, pwd);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String boPhan = rs.getString("BoPhan");
				if (boPhan != null && boPhan.startsWith("Nhân viên quản lý")) {
					new TrangChuNVQuanLy_GUI().setVisible(true);
					this.dispose();
				} else if (boPhan != null && boPhan.startsWith("Nhân viên tiếp tân")) {
					new TrangChuNVTiepTan_GUI().setVisible(true);
					this.dispose();
				} else if (boPhan != null && boPhan.startsWith("Nhân viên kế toán")) {
					new TrangChuNVKeToan_GUI().setVisible(true);
					this.dispose();
				}
			} else {
				lblError.setText("Vui lòng kiểm tra: tên tài khoản phải bắt đầu bằng QLxxx hoặc NVxxx");
				JOptionPane.showMessageDialog(this, "Tên tài khoản hoặc mật khẩu không đúng");
				txtMK.setText("");
				txtMK.requestFocus();
			}
			rs.close();
			stmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
