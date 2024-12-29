package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import connectDB.ConnectDB;
import dao.NhanVien_DAO;
import entity.NhanVien;

import javax.swing.JRadioButton;

public class TraCuuNhanVien_GUI extends JFrame implements ActionListener{
    private static final long serialVersionUID = 1L;
    private JPanel pnContent, pnCenter, pnNorth, pnSouth, pnThem, pnTable;
    private JLabel lblMaNV, lblTenNV, lblSDT, lblTuoi, lblGioiTinh, lblBoPhan, lblLuong;
	private JRadioButton radNam, radNu;
	private JComboBox<String> cmbBoPhan;
	private JTextField txtMaNV, txtTenNV, txtSDT, txtTuoi, txtLuong;
    private DefaultTableModel model;
    private JTable table;
    private JButton btnTim;
    private NhanVien_DAO nv;
	
    Color mauNen = new Color(205, 38, 38); // màu đỏ
    Color mauBang = new Color(99, 184, 255); // màu lam

	/***
	 * Chỉnh sửa tiền tệ
	 */
	Locale localeVN = new Locale("vi", "VN");
	NumberFormat tienTeVN = NumberFormat.getCurrencyInstance(localeVN);

    public TraCuuNhanVien_GUI() throws Exception {
    	try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		nv = new NhanVien_DAO();
        createGui();
    }

    public void createGui() {
        setTitle("Tra cứu nhân viên");
        setSize(800, 650);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);

        nhanVien();
    }

    public void nhanVien() {
        pnContent = new JPanel();
        pnContent.setLayout(new BorderLayout());

        pnNorth = new JPanel();
        pnContent.add(pnNorth, BorderLayout.NORTH);

        JLabel lblTitle = new JLabel("Tra cứu thông tin nhân viên");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        pnNorth.add(lblTitle);
        pnNorth.setBackground(mauNen);

        pnCenter = new JPanel(new GridLayout(1, 1));
        pnContent.add(pnCenter, BorderLayout.CENTER);

        pnThem = new JPanel();
        pnCenter.add(pnThem);
        Box b = Box.createVerticalBox();
        Box b1 = Box.createHorizontalBox();
        Box b2 = Box.createHorizontalBox();
		Box b3 = Box.createHorizontalBox();
		Box b4 = Box.createHorizontalBox();

        TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
                "Tìm kiếm theo");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        pnThem.setBorder(titledBorder);

        lblMaNV = new JLabel("Mã nhân viên: ");
		txtMaNV = new JTextField(20);
		lblTenNV = new JLabel("Tên nhân viên: ");
		txtTenNV = new JTextField(25);
		b1.add(lblMaNV);
		b1.add(Box.createHorizontalStrut(10));
		b1.add(txtMaNV);
		b1.add(Box.createHorizontalStrut(50));
		b1.add(lblTenNV);
		b1.add(Box.createHorizontalStrut(10));
		b1.add(txtTenNV);
		
		lblGioiTinh = new JLabel("Giới tính: ");
		radNam = new JRadioButton("Nam");
		radNu = new JRadioButton("Nữ");
		ButtonGroup bg = new ButtonGroup();
		bg.add(radNam); bg.add(radNu);
		lblBoPhan = new JLabel("Bộ phận:");
		String boPhan[] = {"Tất cả","Nhân viên tiếp tân","Nhân viên kế toán", "Nhân viên phục vụ"
				, "Nhân viên kỹ thuật", "Nhân viên quản lý"};
		cmbBoPhan = new JComboBox(boPhan);
		b2.add(lblGioiTinh);
		b2.add(Box.createHorizontalStrut(30));
		b2.add(radNam); b2.add(radNu);
		b2.add(Box.createHorizontalStrut(150));
		b2.add(lblBoPhan);
		b2.add(Box.createHorizontalStrut(40));
		b2.add(cmbBoPhan);
		 
		lblSDT = new JLabel("Số điện thoại:");
		txtSDT = new JTextField(11);
		lblLuong = new JLabel("Lương/ca:");
//		String luong[] = {"26000", "27500", "30000"};
		txtLuong = new JTextField(15);
		b3.add(lblSDT);
		b3.add(Box.createHorizontalStrut(10));
		b3.add(txtSDT);
		b3.add(Box.createHorizontalStrut(60));
		b3.add(lblLuong);
		b3.add(Box.createHorizontalStrut(10));
		b3.add(txtLuong);
		lblLuong.setPreferredSize(lblTenNV.getPreferredSize());
		
		lblTuoi = new JLabel("Tuổi:");
		txtTuoi = new JTextField();
		btnTim = new JButton("Tìm");
		btnTim.setBackground(mauNen);
		btnTim.setForeground(Color.WHITE);
		b4.add(lblTuoi);
		b4.add(Box.createHorizontalStrut(20));
		b4.add(txtTuoi);
		b4.add(Box.createHorizontalStrut(140));
		b4.add(btnTim);
		b4.add(Box.createHorizontalStrut(250));
		lblTuoi.setPreferredSize(lblSDT.getPreferredSize());

        pnThem.add(b);
        b.add(b1);
        b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
        b.add(b2);
        b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
        b.add(b3);
        b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
        b.add(b4);
        
        pnSouth = new JPanel();
        pnContent.add(pnSouth, BorderLayout.SOUTH);

        pnTable = new JPanel();
        createTable();
        pnSouth.add(pnTable);
        TitledBorder titledBorder1 = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
                "Danh sách nhân viên ");
        titledBorder1.setTitleJustification(TitledBorder.CENTER);
        pnTable.setBorder(titledBorder1);

        this.add(pnContent);
        btnTim.addActionListener(this);
    }

    public void createTable() {
        model = new DefaultTableModel();
        table = new JTable(model);
        model.addColumn("ID Nhân viên");
        model.addColumn("Tên nhân viên");
        model.addColumn("Số điện thoại");
        model.addColumn("Tuổi");
        model.addColumn("Bộ phận");
        model.addColumn("Giới tính");
        model.addColumn("Lương");

		try {
			for (NhanVien nhanVien : nv.getAllNhanVien()) {
				Object[] row = { nhanVien.getIDNhanVien(), nhanVien.getTenNV(), nhanVien.getSDT(), nhanVien.getTuoi(),
						 nhanVien.getBoPhan(), nhanVien.getGioiTinh(), tienTeVN.format(nhanVien.getLuong())  };
				model.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

        pnTable.add(table);
        JScrollPane sp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setPreferredSize(new Dimension(750, 300));
        pnTable.add(sp);

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(mauBang);
        table.getTableHeader().setDefaultRenderer(headerRenderer);
        headerRenderer.setForeground(Color.WHITE);
        table.setGridColor(mauNen);
    }

    public static void main(String[] args) throws Exception {
        new TraCuuNhanVien_GUI().setVisible(true);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(btnTim))
			timKiem();
	}
	public void timKiem() {
	    int numRows = model.getRowCount();
	    for (int i = numRows - 1; i >= 0; i--) {
	        model.removeRow(i);
	    }

	    String maNV = txtMaNV.getText().trim();
	    String tenNV = txtTenNV.getText().trim();
	    String gioiTinh = ""; // Thêm biến giới tính
	    if (radNam.isSelected()) {
	        gioiTinh = "Nam";
	    } else if (radNu.isSelected()) {
	        gioiTinh = "Nữ";
	    }
	    
	    String boPhan = (String) cmbBoPhan.getSelectedItem();
	    String sDT = txtSDT.getText().trim();
	    String tuoi = txtTuoi.getText().trim();
	    String luong = txtLuong.getText().trim();

	    try {
	        boolean tim = false;
	        for (NhanVien nhanVien : nv.getAllNhanVien()) {
	            boolean maNVMatch = maNV.isEmpty() || nhanVien.getIDNhanVien().equalsIgnoreCase(maNV);
	            boolean tenNVMatch = tenNV.isEmpty() || nhanVien.getTenNV().toLowerCase().contains(tenNV.toLowerCase());
	            boolean gioiTinhMatch = gioiTinh.isEmpty() || nhanVien.getGioiTinh().equalsIgnoreCase(gioiTinh);
	            boolean boPhanMatch = boPhan.equals("Tất cả") || nhanVien.getBoPhan().equalsIgnoreCase(boPhan);
	            boolean sDTMatch = sDT.isEmpty() || nhanVien.getSDT().contains(sDT);
	            boolean tuoiMatch = tuoi.isEmpty() || Integer.toString(nhanVien.getTuoi()).equals(tuoi);
	            boolean luongMatch = luong.isEmpty() || tienTeVN.format(nhanVien.getLuong()).contains(luong);

	            if (maNVMatch && tenNVMatch && gioiTinhMatch && boPhanMatch && sDTMatch && tuoiMatch && luongMatch) {
	                Object[] row = {
	                    nhanVien.getIDNhanVien(), nhanVien.getTenNV(),
	                    nhanVien.getSDT(), nhanVien.getTuoi(),
	                    nhanVien.getBoPhan(), nhanVien.getGioiTinh(),
	                    tienTeVN.format(nhanVien.getLuong())
	                };
	                model.addRow(row);
	                tim = true;
	            }
	        }

	        if (!tim) {
	            JOptionPane.showMessageDialog(this, "Không tìm thấy");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
