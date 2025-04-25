package gui;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import utils.VietnameseFormatter;
import utils.guicomponents.MyPanel;
import utils.guicomponents.datechooser.EventDateChooser;
import utils.guicomponents.datechooser.MyDateChooser;
import utils.guicomponents.datechooser.SelectedAction;
import utils.guicomponents.datechooser.SelectedDate;
import utils.guicomponents.timepicker.MyTimePicker;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.raven.event.EventTimePicker;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

import java.awt.Color;

public class ChonNgayGioDatPhongPanel extends MyPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField txtNgaySuDung;
	private JTextField txtThoiGianBatDau;
	private JTextField txtThoiGianKetThuc;
	private JLabel lblCanhBao;
	private JButton btnXacNhan;
	private MyTimePicker chonThoiGianKetThuc;
	private MyTimePicker chonThoiGianBatDau;
	private LocalDateTime thoiGianBatDau;
	private LocalTime thoiGianKetThuc;

	public LocalDateTime getThoiGianBatDau() {
		return thoiGianBatDau;
	}


	public LocalTime getThoiGianKetThuc() {
		return thoiGianKetThuc;
	}


	/**
	 * Create the panel.
	 */
	public ChonNgayGioDatPhongPanel() {}
	@Override
	public void KhoiTaoGiaoDien() {
		setLayout(new MigLayout("", "[][grow]", "[][][][][][]"));
		JLabel lblTieuDe = new JLabel("Chọn thời gian bắt đầu và kết thúc");
		lblTieuDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblTieuDe.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblTieuDe.setForeground(Color.WHITE);
		add(lblTieuDe, "cell 0 0 2 1,growx");

		JLabel lblNgaySuDung = new JLabel("Ngày sử dụng");
		lblNgaySuDung.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(lblNgaySuDung, "cell 0 1,alignx trailing");

		txtNgaySuDung = new JTextField();
		txtNgaySuDung.setEditable(false);
		txtNgaySuDung.setFocusable(false);
		txtNgaySuDung.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtNgaySuDung.setHorizontalAlignment(SwingConstants.TRAILING);
		add(txtNgaySuDung, "cell 1 1,growx");
		txtNgaySuDung.setColumns(10);

		MyDateChooser chonNgaySuDung = new MyDateChooser();
		chonNgaySuDung.setTextRefernce(txtNgaySuDung);

		chonNgaySuDung.addEventDateChooser(new EventDateChooser() {

			@Override
			public void dateSelected(SelectedAction action, SelectedDate date) {
				// TODO Auto-generated method stub
				LocalDate ngaySuDung = LocalDate.parse(txtNgaySuDung.getText().trim(),
						DateTimeFormatter.ofPattern("dd-MM-yyyy"));
				LocalDate ngayHienTai = LocalDate.now();
				LocalDate motTuanSau = ngayHienTai.plusDays(7);
				if (ngaySuDung.isBefore(ngayHienTai)) {
					ngaySuDungHopLe = false;
					lblCanhBao.setForeground(Color.red);
					lblCanhBao.setText("Sao bạn có thể chọn ngày đặt là quá khứ được nhỉ?");
				} else if (ngaySuDung.isAfter(motTuanSau)) {
					ngaySuDungHopLe = false;
					lblCanhBao.setForeground(Color.red);
					lblCanhBao.setText("Bạn không thể đặt quá 7 ngày");
				} else {
					ngaySuDungHopLe = true;
					lblCanhBao.setForeground(Color.green);
					lblCanhBao.setText("Chọn ngày hợp lý");
				}
				hienNutXacNhan();
			}
		});

		JLabel lblThoiGianBatDau = new JLabel("Thời gian bắt đầu");
		lblThoiGianBatDau.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(lblThoiGianBatDau, "cell 0 2,alignx trailing");

		txtThoiGianBatDau = new JTextField();
		txtThoiGianBatDau.setHorizontalAlignment(SwingConstants.TRAILING);
		txtThoiGianBatDau.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtThoiGianBatDau.setColumns(10);
		txtThoiGianBatDau.setEditable(false);
		txtThoiGianBatDau.setFocusable(false);
		add(txtThoiGianBatDau, "cell 1 2,growx");

		chonThoiGianBatDau = new MyTimePicker();
		chonThoiGianBatDau.setDisplayText(txtThoiGianBatDau);
		chonThoiGianBatDau.setSelectedTime(LocalTime.now());
		txtThoiGianBatDau.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				chonThoiGianBatDau.showPopup(manHinhChua, (getWidth() - chonThoiGianBatDau.getPreferredSize().width) / 2,
						(getHeight() - chonThoiGianBatDau.getPreferredSize().height) / 2);
			}
		});
		chonThoiGianBatDau.addEventTimePicker(new EventTimePicker() {

			@Override
			public void timeSelected(String arg0) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				LocalTime thoiGianBatDau = VietnameseFormatter
						.dinhDangThoiGianLocalTime(chonThoiGianBatDau.getSelectedTime());
				if (thoiGianBatDau.isBefore(LocalTime.of(7, 0))) {
					thoiGianBatDauHopLe = false;
					lblCanhBao.setForeground(Color.RED);
					lblCanhBao.setText("Thời gian bắt đầu không sớm hơn 7 giờ sáng");
				} else if (thoiGianBatDau.compareTo(
						VietnameseFormatter.dinhDangThoiGianLocalTime(chonThoiGianKetThuc.getSelectedTime()))>=0) {
					thoiGianKetThucHopLe = false;
					lblCanhBao.setForeground(Color.RED);
					lblCanhBao.setText("Thời gian bắt đầu không thể trễ hơn hoặc bằng thời gian kết thúc");
				} else if (thoiGianBatDau.isAfter(LocalTime.of(23, 59))) {
					thoiGianKetThucHopLe = false;
					lblCanhBao.setForeground(Color.RED);
					lblCanhBao.setText("Thời gian bắt đầu không thể trễ hơn 23 giờ 59 phút");
				} else {
					thoiGianBatDauHopLe = true;
					lblCanhBao.setForeground(Color.green);
					lblCanhBao.setText("Thời gian bắt đầu hợp lệ");
				}

				hienNutXacNhan();
			}
		});

		JLabel lblThoiGianKetThuc = new JLabel("Thời gian kết thúc");
		lblThoiGianKetThuc.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		add(lblThoiGianKetThuc, "cell 0 3,alignx trailing");

		txtThoiGianKetThuc = new JTextField();
		txtThoiGianKetThuc.setHorizontalAlignment(SwingConstants.TRAILING);
		txtThoiGianKetThuc.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtThoiGianKetThuc.setColumns(10);
		txtThoiGianKetThuc.setEditable(false);
		txtThoiGianKetThuc.setFocusable(false);
		add(txtThoiGianKetThuc, "cell 1 3,growx");

		chonThoiGianKetThuc = new MyTimePicker();
		chonThoiGianKetThuc.setDisplayText(txtThoiGianKetThuc);
		LocalTime oneHourLater = LocalTime.now().plusHours(1);
		chonThoiGianKetThuc.setSelectedTime(oneHourLater);
		txtThoiGianKetThuc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				chonThoiGianKetThuc.showPopup(manHinhChua,
						(getWidth() - chonThoiGianKetThuc.getPreferredSize().width) / 2,
						(getHeight() - chonThoiGianKetThuc.getPreferredSize().height) / 2);
			}
		});
		chonThoiGianKetThuc.addEventTimePicker(new EventTimePicker() {

			@Override
			public void timeSelected(String arg0) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				LocalTime thoiGianKetThuc = VietnameseFormatter
						.dinhDangThoiGianLocalTime(chonThoiGianKetThuc.getSelectedTime());
				if (thoiGianKetThuc.isAfter(LocalTime.of(23, 59))) {
					thoiGianKetThucHopLe = false;
					lblCanhBao.setForeground(Color.RED);
					lblCanhBao.setText("Thời gian kết thúc không quá 23 giờ 59 phút đêm");
				} else if (thoiGianKetThuc.compareTo(
						VietnameseFormatter.dinhDangThoiGianLocalTime(chonThoiGianBatDau.getSelectedTime()))<=0) {
					thoiGianKetThucHopLe = false;
					lblCanhBao.setForeground(Color.RED);
					lblCanhBao.setText("Thời gian kết thúc không thể sớm hơn hoặc bằng thời gian bắt đầu");
				} else if (thoiGianKetThuc.isBefore(LocalTime.of(7, 0))) {
					thoiGianKetThucHopLe = false;
					lblCanhBao.setForeground(Color.RED);
					lblCanhBao.setText("Thời gian kết thúc không thể sớm hơn 7 giờ");
				} else {
					thoiGianKetThucHopLe = true;
					lblCanhBao.setForeground(Color.green);
					lblCanhBao.setText("Thời gian kết thúc hợp lệ");
				}
				hienNutXacNhan();
			}
		});

		btnXacNhan = new JButton("Xác nhận");
		btnXacNhan.setForeground(Color.LIGHT_GRAY);
		btnXacNhan.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		add(btnXacNhan, "cell 0 4 2 1,grow");
		btnXacNhan.setEnabled(false);
		btnXacNhan.addActionListener(this);

		lblCanhBao = new JLabel("");
		lblCanhBao.setForeground(Color.WHITE);
		add(lblCanhBao, "cell 0 5 2 1");
		hienNutXacNhan();
	}

	private boolean ngaySuDungHopLe = true;
	private boolean thoiGianBatDauHopLe = true;
	private boolean thoiGianKetThucHopLe = true;

	private void hienNutXacNhan() {
		if (ngaySuDungHopLe && thoiGianBatDauHopLe && thoiGianKetThucHopLe) {
			btnXacNhan.setEnabled(true);
		} else {
			btnXacNhan.setEnabled(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnXacNhan)) {
			thoiGianBatDau = LocalDateTime.of(
					LocalDate.parse(txtNgaySuDung.getText().trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy")),
					VietnameseFormatter.dinhDangThoiGianLocalTime(chonThoiGianBatDau.getSelectedTime()));
			thoiGianKetThuc = VietnameseFormatter.dinhDangThoiGianLocalTime(chonThoiGianKetThuc.getSelectedTime());
			manHinhChua.dispose();
		}
	}
}
