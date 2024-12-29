package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import connectDB.ConnectDB;
import dao.KhachHang_DAO;
import entity.KhachHang;

public class TraCuuKhachHang_GUI extends JFrame implements ActionListener{
    private static final long serialVersionUID = 1L;
    private JPanel pnContent, pnCenter, pnNorth, pnSouth, pnThem, pnTable;
    private JLabel lblMaKH, lblTenKH, lblTuoi, lblGioiTinh, lblSDT, lblTrangThai;
    private JRadioButton radNam, radNu;
	private JComboBox<String> cbTrangThai;
	private JTextField txtMaKH, txtTenKH, txtSDT, txtTuoi;
    private DefaultTableModel model;
    private JTable table;
    private JButton btnTim;
    private KhachHang_DAO kh;

    Color mauNen = new Color(205, 38, 38); // màu đỏ
    Color mauBang = new Color(99, 184, 255); // màu lam

    public TraCuuKhachHang_GUI() throws Exception {
    	try {
			ConnectDB.getInstance().connect();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		kh = new KhachHang_DAO();
        createGui();
    }

    public void createGui() {
        setTitle("Tra cứu khách hàng");
        setSize(800, 650);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);

        khachHang();
    }

    public void khachHang() {
        pnContent = new JPanel();
        pnContent.setLayout(new BorderLayout());

        pnNorth = new JPanel();
        pnContent.add(pnNorth, BorderLayout.NORTH);

        JLabel lblTitle = new JLabel("Tra cứu thông tin khách hàng");
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
        Box b5 = Box.createHorizontalBox();

        TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
                "Tìm kiếm theo");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        pnThem.setBorder(titledBorder);

        lblMaKH = new JLabel("Mã khách hàng: ");
		txtMaKH = new JTextField(20);
		b1.add(lblMaKH);
//		Box.createHorizontalStrut(10): tạo khoảng cách giữa Jlabel với JTextField
		b1.add(Box.createHorizontalStrut(10));
		b1.add(txtMaKH);
		
//		Box.createHorizontalStrut(50): tạo khoảng cách của "Mã khách hàng" và "Giới tính" trên cùng 1 dòng 
		b1.add(Box.createHorizontalStrut(30));
		lblGioiTinh = new JLabel("Giới tính: ");
		radNam = new JRadioButton("Nam");
		radNu = new JRadioButton("Nữ");
		ButtonGroup bg = new ButtonGroup();
		b1.add(Box.createHorizontalStrut(20));
		bg.add(radNam); bg.add(radNu);
		b1.add(lblGioiTinh);
		b1.add(Box.createHorizontalStrut(30));
		b1.add(radNam); b1.add(radNu);
		 
		lblTenKH = new JLabel("Tên khách hàng: ");
		txtTenKH = new JTextField();
		lblSDT = new JLabel("SDT: ");
		txtSDT = new JTextField();
		b2.add(lblTenKH);
		b2.add(Box.createHorizontalStrut(10));
		b2.add(txtTenKH);
		b2.add(Box.createHorizontalStrut(80));
		b2.add(lblSDT);
		b2.add(Box.createHorizontalStrut(20));
		b2.add(txtSDT);
		
		lblTuoi = new JLabel("Tuổi: ");
		txtTuoi = new JTextField(10);
		b3.add(lblTuoi);
		b3.add(Box.createHorizontalStrut(10));
		b3.add(txtTuoi);
		b3.add(Box.createHorizontalStrut(50));
		lblTrangThai = new JLabel("Trạng thái: ");
		String trangthai[] = {"Tất cả","Đã thanh toán", "Chưa thanh toán"};
		cbTrangThai = new JComboBox(trangthai);
		cbTrangThai.setBounds(100, 50, 150, 20);
		b3.add(lblTrangThai);
		b3.add(Box.createHorizontalStrut(10));
		b3.add(cbTrangThai);
		
//		Canh tiêu đề 'mã khách hàng' bằng tiêu đề 'tên khách hàng', 
//		...
		lblMaKH.setPreferredSize(lblTenKH.getPreferredSize());
		lblTuoi.setPreferredSize(lblTenKH.getPreferredSize());

        btnTim = new JButton("Tìm");
        btnTim.setBackground(mauNen);
        btnTim.setForeground(Color.WHITE);
        b5.add(btnTim);
        b5.add(Box.createHorizontalStrut(20));

        pnThem.add(b);
        b.add(b1);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b2);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b3);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b4);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b5);

        pnSouth = new JPanel();
        pnContent.add(pnSouth, BorderLayout.SOUTH);

        pnTable = new JPanel();
        createTable();
        pnSouth.add(pnTable);
        TitledBorder titledBorder1 = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(mauNen),
                "Danh sách khách hàng ");
        titledBorder1.setTitleJustification(TitledBorder.CENTER);
        pnTable.setBorder(titledBorder1);

        this.add(pnContent);
        btnTim.addActionListener(this);
    }

    public void createTable() {
        model = new DefaultTableModel();
        table = new JTable(model);
        model.addColumn("Mã khách hàng");
        model.addColumn("Tên khách hàng");
        model.addColumn("Tuổi");
        model.addColumn("Giới tính");
        model.addColumn("Số điện thoại");
        model.addColumn("Trạng thái");

        try {
			for(KhachHang khachHang : kh.getAllKhachHang()) {
				Object[] row = { khachHang.getMaKH(), khachHang.getTenKH(),
						khachHang.getTuoi(), khachHang.getGioiTinh(),
						khachHang.getsDT(), khachHang.getTrangThai()};
				model.addRow(row);
			}
		}catch (Exception e) {
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
        new TraCuuKhachHang_GUI().setVisible(true);
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

	    String maKH = txtMaKH.getText().trim();
	    String tenKH = txtTenKH.getText().trim();
	    String tuoi = txtTuoi.getText().trim();
	    String trangThai = (String) cbTrangThai.getSelectedItem();
	    
	    // Lấy giới tính được chọn
	    String gioiTinh = "";
	    if (radNam.isSelected()) {
	        gioiTinh = "Nam";
	    } else if (radNu.isSelected()) {
	        gioiTinh = "Nữ";
	    }

	    try {
	        boolean tim = false;
	        for (KhachHang khachHang : kh.getAllKhachHang()) {
	            boolean maKHMatch = maKH.isEmpty() || khachHang.getMaKH().equalsIgnoreCase(maKH);
	            boolean tenKHMatch = tenKH.isEmpty() || khachHang.getTenKH().toLowerCase().contains(tenKH.toLowerCase());
	            boolean tuoiMatch = tuoi.isEmpty() || Integer.toString(khachHang.getTuoi()).equals(tuoi);
	            boolean trangThaiMatch = trangThai.equals("Tất cả") || khachHang.getTrangThai().equalsIgnoreCase(trangThai);
	            boolean gioiTinhMatch = gioiTinh.isEmpty() || khachHang.getGioiTinh().equalsIgnoreCase(gioiTinh);
	            
	            if (maKHMatch && tenKHMatch && tuoiMatch && trangThaiMatch && gioiTinhMatch) {
	                Object[] row = {
	                    khachHang.getMaKH(), khachHang.getTenKH(),
	                    khachHang.getTuoi(), khachHang.getGioiTinh(),
	                    khachHang.getsDT(), khachHang.getTrangThai()
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
