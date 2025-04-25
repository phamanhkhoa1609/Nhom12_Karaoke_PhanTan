package gui;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.time.LocalDate;
import interfaces.DichVuIDAO;
import utils.guicomponents.MyPanel;
import utils.guicomponents.charts.*;

import java.awt.Color;
import java.awt.Dimension;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Comparator;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import entities.DichVu;
import entities.enums.LoaiDichVu;
import entities.enums.TrangThaiDichVu;

public class ThongKeDichVuPanel extends MyPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final LocalDate[] days = { LocalDate.now(), LocalDate.now().minusDays(1), LocalDate.now().minusDays(2),
			LocalDate.now().minusDays(3), LocalDate.now().minusDays(4), LocalDate.now().minusDays(5),
			LocalDate.now().minusDays(6) };

	private static final Color[] listColor = { Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.CYAN,
			Color.MAGENTA, Color.ORANGE };
	private DichVuIDAO dichVu_dao;
	private List<DichVu> listDichVu;
	private Map<Long, ArrayList<Double>> listThongKe = new HashMap<>();
	private Map<Long, Integer> listDaBan = new HashMap<>();
	List<ModelChartPie> listPieChart = new ArrayList<>();
	List<ModelChartLine> listLineChart = new ArrayList<>();
	private Map<Object, Boolean> columnSortState = new HashMap<>();

	public ThongKeDichVuPanel() {

	}

	@Override
	public void KhoiTaoGiaoDien() {
		khoiTaoThanhPhan();
		khoiTaoDuLieu();
	}

	private void khoiTaoDuLieu() {
		// Test Data table
		this.listDichVu = new ArrayList<DichVu>();
		try {
			listDichVu = dichVu_dao.layTatCa();

			for (DichVu dv : listDichVu) {
				ArrayList<Double> listTienTheoNgay = new ArrayList<>();
				ArrayList<Integer> listDaBanTheoNgay = new ArrayList<>();
				for (LocalDate day : this.days) {
					try {
						listTienTheoNgay.add(dichVu_dao.getTongTienDichVuTheoNgay(dv.getMaDV(), day));
						listDaBanTheoNgay.add(dichVu_dao.getSoLuongDichVuDaBanTheoNgay(dv.getMaDV(), day));
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
				listThongKe.put(dv.getMaDV(), listTienTheoNgay);

				int count = 0;
				for (Integer countDV : listDaBanTheoNgay)
					count += countDV;
				listDaBan.put(dv.getMaDV(), count);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		DefaultTableModel model = (DefaultTableModel) table.getModel();

		listDichVu.forEach(dv -> {
			Vector<Object> rowData = new Vector<>();
			rowData.add(dv.getHinh(50, 50, 5));
			rowData.add(dv.getMaDV());
			rowData.add(dv.getTenDV());
			rowData.add(dv.getGiaDV());
			rowData.add(dv.getSoLuong());
			rowData.add(listDaBan.get(dv.getMaDV()));
			rowData.add(dv.getLoaiDV());
			rowData.add(dv.getTrangThai());
			model.addRow(rowData);
		});

		table.fixTable(scrDanhSach);
		listPieChart.add(new ModelChartPie(days[6].toString(), 10, listColor[6]));
		listPieChart.add(new ModelChartPie(days[5].toString(), 150, listColor[5]));
		listPieChart.add(new ModelChartPie(days[4].toString(), 80, listColor[4]));
		listPieChart.add(new ModelChartPie(days[4].toString(), 100, listColor[3]));
		listPieChart.add(new ModelChartPie(days[2].toString(), 125, listColor[2]));
		listPieChart.add(new ModelChartPie(days[1].toString(), 80, listColor[1]));
		listPieChart.add(new ModelChartPie(days[0].toString(), 200, listColor[0]));
		chartPie.setModel(listPieChart);
		// Test data chart line

		listLineChart.add(new ModelChartLine(days[6].toString(), 10));
		listLineChart.add(new ModelChartLine(days[5].toString(), 150));
		listLineChart.add(new ModelChartLine(days[4].toString(), 80));
		listLineChart.add(new ModelChartLine(days[3].toString(), 100));
		listLineChart.add(new ModelChartLine(days[2].toString(), 125));
		listLineChart.add(new ModelChartLine(days[1].toString(), 80));
		listLineChart.add(new ModelChartLine(days[0].toString(), 200));
		chartLine1.setModel(listLineChart);

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) { // Đảm bảo sự kiện không phải là sự kiện trung gian
					int selectedRow = table.getSelectedRow();
					if (selectedRow != -1) { // Kiểm tra xem một hàng đã được chọn chưa
						Long maDV = (Long) table.getValueAt(selectedRow, 1); // Lấy mã dịch vụ từ hàng được chọn
						ArrayList<Double> listTienTheoNgay = listThongKe.get(maDV); // Lấy danh sách giá trị từ
																					// listThongKe

						// Cập nhật dữ liệu của ModelChartLine và ModelChartPie với danh sách giá trị
						// mới
						listLineChart.clear();
						listPieChart.clear();

						// Thêm dữ liệu mới vào listLineChart và listPieChart
						for (int i = 0; i < days.length; i++) {
							listLineChart.add(new ModelChartLine(days[i].toString(), listTienTheoNgay.get(i)));
							listPieChart
									.add(new ModelChartPie(days[i].toString(), listTienTheoNgay.get(i), listColor[i]));
						}

						// Cập nhật model của chartLine1 và chartPie với dữ liệu mới
						chartLine1.setModel(listLineChart);
						chartPie.setModel(listPieChart);

						// Gọi lại initData() để vẽ lại biểu đồ với dữ liệu mới
						chartLine1.initData();
						chartPie.initData();
					}
				}
			}
		});

	}

	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void khoiTaoThanhPhan() {
		dichVu_dao = clientIDAO.getDichVuIDAO();
		chartPie = new ChartPie();
		chartLine1 = new ChartLine();
		lblDanhSachDV = new javax.swing.JLabel();
		jLabelSearch = new javax.swing.JLabel(); // Tạo một JLabel mới cho ô tìm kiếm
		jTextFieldSearch = new javax.swing.JTextField(); // Tạo một JTextField mới cho ô tìm kiếm
		jTextFieldSearch.setPreferredSize(new Dimension(150, 25)); // Đặt kích thước mới cho JTextField
		// Thêm DocumentListener cho JTextField
		jTextFieldSearch.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				search(jTextFieldSearch.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				search(jTextFieldSearch.getText());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// This method is not used for plain text fields
			}

			// Phương thức tìm kiếm dựa trên từ khóa trong tất cả các cột
			private void search(String text) {
				TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
				table.setRowSorter(sorter);
				if (text.length() == 0) {
					sorter.setRowFilter(null);
				} else {
					sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text)); // Tìm kiếm không phân biệt chữ hoa và
																				// chữ thường
				}
			}
		});

		scrDanhSach = new javax.swing.JScrollPane();
		JPanel buttonPanel = new JPanel();
		btnLamMoi = new JButton("Làm mới");
		btnLamMoi.addActionListener(this);
		buttonPanel.add(btnLamMoi);
		buttonPanel.add(jLabelSearch); // Thêm JLabel cho ô tìm kiếm
		buttonPanel.add(jTextFieldSearch); // Thêm JTextField cho ô tìm kiếm

		table = new Table();

		setBackground(new java.awt.Color(250, 250, 250));

		lblDanhSachDV.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
		lblDanhSachDV.setForeground(Color.WHITE);
		lblDanhSachDV.setText("Danh sách dịch vụ");
		

		table.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "Hình ảnh", "Mã", "Tên", "Giá", "Tồn kho", "Đã bán", "Loại", "Trạng thái" }) {
			private static final long serialVersionUID = -6014743404360881363L;
			boolean[] canEdit = new boolean[] { false, false, false, false, false, false, false, false };

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		table.getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int columnIndex = table.columnAtPoint(e.getPoint());
				String columnName = table.getColumnName(columnIndex);
				sortTable(columnName);
			}
		});
		scrDanhSach.setViewportView(table);
		if (table.getColumnModel().getColumnCount() > 0) {
			table.getColumnModel().getColumn(0).setPreferredWidth(50);
			table.getColumnModel().getColumn(4).setPreferredWidth(50);
		}

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
								.addComponent(chartLine1, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(chartPie, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE))
						.addGroup(layout.createSequentialGroup().addComponent(lblDanhSachDV).addGap(0, 0,
								Short.MAX_VALUE))
						.addComponent(scrDanhSach).addComponent(buttonPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addContainerGap()));

		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(chartLine1, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(chartPie, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(lblDanhSachDV).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(scrDanhSach).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
	}

	private void sortTable(String columnName) {
		boolean ascending = columnSortState.getOrDefault(columnName, true);
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		Vector<Vector> dataVector = model.getDataVector();

		ArrayList<Object[]> data = new ArrayList<>();
		for (Vector row : dataVector) {
			Object[] rowData = new Object[row.size()];
			for (int i = 0; i < row.size(); i++) {
				rowData[i] = row.get(i);
			}
			data.add(rowData);
		}

		Collections.sort(data, new Comparator<Object[]>() {
			@Override
			public int compare(Object[] o1, Object[] o2) {
				Object obj1 = o1[table.getColumn(columnName).getModelIndex()];
				Object obj2 = o2[table.getColumn(columnName).getModelIndex()];
				if (obj1 instanceof String && obj2 instanceof String) {
					return ascending ? ((String) obj1).compareTo((String) obj2)
							: ((String) obj2).compareTo((String) obj1);
				} else if (obj1 instanceof Integer && obj2 instanceof Integer) {
					return ascending ? ((Integer) obj1).compareTo((Integer) obj2)
							: ((Integer) obj2).compareTo((Integer) obj1);
				} else if (obj1 instanceof Double && obj2 instanceof Double) {
					return ascending ? ((Double) obj1).compareTo((Double) obj2)
							: ((Double) obj2).compareTo((Double) obj1);
				} else if (obj1 instanceof Long && obj2 instanceof Long) {
					return ascending ? ((Long) obj1).compareTo((Long) obj2) : ((Long) obj2).compareTo((Long) obj1);
				} else if (obj1 instanceof Date && obj2 instanceof Date) {
					return ascending ? ((Date) obj1).compareTo((Date) obj2) : ((Date) obj2).compareTo((Date) obj1);
				} else if (obj1 instanceof LoaiDichVu && obj2 instanceof LoaiDichVu) {
					return ascending
							? ((LoaiDichVu) obj1).getLoaiDichVu().compareTo(((LoaiDichVu) obj2).getLoaiDichVu())
							: ((LoaiDichVu) obj2).getLoaiDichVu().compareTo(((LoaiDichVu) obj1).getLoaiDichVu());
				} else if (obj1 instanceof TrangThaiDichVu && obj2 instanceof TrangThaiDichVu) {
					return ascending
							? ((TrangThaiDichVu) obj1).getTrangThaiDichVu()
									.compareTo(((TrangThaiDichVu) obj2).getTrangThaiDichVu())
							: ((TrangThaiDichVu) obj2).getTrangThaiDichVu()
									.compareTo(((TrangThaiDichVu) obj1).getTrangThaiDichVu());
				}
				return 0;
			}
		});

		// Cập nhật dữ liệu cho model
		model.setRowCount(0);
		for (Object[] rowData : data) {
			model.addRow(rowData);
		}

		columnSortState.put(columnName, !ascending); // Đảo ngược trạng thái sắp xếp của cột
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private ChartLine chartLine1;
	private ChartPie chartPie;
	private JLabel lblDanhSachDV;
	private JScrollPane scrDanhSach;
	private Table table;
	private JLabel jLabelSearch;
	private JTextField jTextFieldSearch;
	private JButton btnLamMoi;

	// End of variables declaration//GEN-END:variables
	// Main method to run the application

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnLamMoi) {
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.setRowCount(0);
			khoiTaoDuLieu();
		}

		// chart
		Long maDV = (Long) table.getValueAt(0, 1); // Lấy mã dịch vụ từ hàng được chọn
		ArrayList<Double> listTienTheoNgay = listThongKe.get(maDV); // Lấy danh sách giá trị từ listThongKe

		// Cập nhật dữ liệu của ModelChartLine và ModelChartPie với danh sách giá trị mới
		listLineChart.clear();
		listPieChart.clear();

		// Thêm dữ liệu mới vào listLineChart và listPieChart
		for (int i = 0; i < days.length; i++) {
			listLineChart.add(new ModelChartLine(days[i].toString(), listTienTheoNgay.get(i)));
			listPieChart.add(new ModelChartPie(days[i].toString(), listTienTheoNgay.get(i), listColor[i]));
		}

		// Cập nhật model của chartLine1 và chartPie với dữ liệu mới
		chartLine1.setModel(listLineChart);
		chartPie.setModel(listPieChart);

		// Gọi lại initData() để vẽ lại biểu đồ với dữ liệu mới
		chartLine1.initData();
		chartPie.initData();
	}
}