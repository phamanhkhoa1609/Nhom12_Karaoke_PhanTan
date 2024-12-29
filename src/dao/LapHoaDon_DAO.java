package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.DichVu;
import entity.LapHoaDon;

public class LapHoaDon_DAO {
	private static LapHoaDon_DAO instance = new LapHoaDon_DAO();

	public static LapHoaDon_DAO getInstance() {
		if (instance == null)
			instance = new LapHoaDon_DAO();
		return instance;
	}

	public List<LapHoaDon> getAllLapHoaDon() {
		List<LapHoaDon> dsHoaDon = new ArrayList<LapHoaDon>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();

		try (Statement statement = con.createStatement()) {
			String sql = "SELECT *\r\n" + "FROM HoaDon hd\r\n"
					+ "INNER JOIN PhieuDatPhong pdp ON hd.IDHoaDon = pdp.IDHoaDon\r\n"
					+ "INNER JOIN KhachHang kh ON hd.IDKhachHang = kh.IDKhachHang\r\n"
					+ "INNER JOIN ChiTietHoaDon ct_hd ON hd.IDHoaDon = ct_hd.IDHoaDon \r\n"
					+ "INNER JOIN Phong p ON ct_hd.IDPhong = p.IDPhong\r\n"
					+ "WHERE ct_hd.ThoiGianThue is not null and ct_hd.ThoiGianKetThuc is not null";
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String idHD = rs.getString("IDHoaDon");
				String idKH = rs.getString("IDKhachHang");
				String tenPhong = rs.getString("TenPhong");
				String idPhong = rs.getString("IDPhong");
				String idNV = rs.getString("IDNhanVien");
				int phieuGiamGia = rs.getInt("PhieuGiamGia");
				int luongKhach = rs.getInt("SoLuongKhach");
				LocalDateTime tgThue = rs.getTimestamp("ThoiGianThue").toLocalDateTime();
				LocalDateTime tgKetThuc = rs.getTimestamp("ThoiGianKetThuc").toLocalDateTime();
				int soLuong = rs.getInt("SoLuong");
				LapHoaDon hd = new LapHoaDon(idHD, idKH, idPhong, idNV, tenPhong, phieuGiamGia, luongKhach, tgThue,
						tgKetThuc, soLuong, 0);
				dsHoaDon.add(hd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsHoaDon;
	}

	/***
	 * Lấy mã hóa đơn từ khách hàng chưa thanh toán Sử dụng trong DatDichVu_GUI
	 * 
	 * @param ma
	 * @return
	 */
	public List<LapHoaDon> getHoaDonForKhachHang(String ma) {
		List<LapHoaDon> dsHoaDon = new ArrayList<LapHoaDon>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;

		try (Statement statement = con.createStatement()) {
			String sql = "SELECT *\r\n" + "FROM HoaDon hd\r\n"
					+ "INNER JOIN KhachHang kh ON hd.IDKhachHang = kh.IDKhachHang\r\n"
					+ "INNER JOIN ChiTietHoaDon ct_hd ON hd.IDHoaDon = ct_hd.IDHoaDon \r\n"
					+ "WHERE ct_hd.ThoiGianThue is null and ct_hd.ThoiGianKetThuc is null\r\n"
					+ " and kh.TinhTrang = N'Chưa thanh toán' and kh.IDKhachHang = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, ma);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String idHD = rs.getString("IDHoaDon");
				LapHoaDon hd = new LapHoaDon(idHD, ma, "", "", "", 0, 0, null, null, 0, 0);
				dsHoaDon.add(hd);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsHoaDon;
	}

	public void lapHoaDon(LapHoaDon lhd) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(
					"update HoaDon " + "set PhieuGiamGia = ? " + "where IDHoaDon = ? and IDKhachHang = ?;");
			stmt.setInt(1, lhd.getPhieuGiamGia());
			stmt.setString(2, lhd.getMaHoaDon());
			stmt.setString(3, lhd.getMaKhachHang());
			stmt.executeUpdate();
			stmt.close();

			stmt = con.prepareStatement("update ChiTietHoaDon " + "set ThoiGianThue = ?, ThoiGianKetThuc = ? "
					+ "where IDHoaDon = ? and IDPhong = ?;");
			stmt.setTimestamp(1, Timestamp.valueOf(lhd.getThoiGianThue()));
			stmt.setTimestamp(2, Timestamp.valueOf(lhd.getThoiGianKetThuc()));
			stmt.setString(3, lhd.getMaHoaDon());
			stmt.setString(4, lhd.getMaPhong());

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
		}
	}

	/***
	 * Tính thành tiền của Phòng
	 * 
	 * @param ma
	 * @return
	 */
	public double tinhThanhTienPhong(String ma) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		double thanhTien = 0.0;

		try (Statement statement = con.createStatement()) {
			String sql = "SELECT\r\n" + "    ct_hd.IDHoaDon,\r\n" + "    ct_hd.IDPhong,\r\n" + "	p.TenPhong,\r\n"
					+ "	p.ThietBi,\r\n" + "	p.TinhTrang,\r\n" + "	p.GhiChu,\r\n" + "    ct_hd.ThoiGianThue,\r\n"
					+ "    ct_hd.ThoiGianKetThuc,\r\n" + "    p.GiaPhongMoiGioTheoCa,\r\n"
					+ "    ((DATEDIFF(MINUTE, ct_hd.ThoiGianThue, ct_hd.ThoiGianKetThuc) / 60.0) * p.GiaPhongMoiGioTheoCa) as ThanhTien\r\n"
					+ "FROM Phong p INNER JOIN ChiTietHoaDon ct_hd ON p.IDPhong = ct_hd.IDPhong\r\n"
					+ "WHERE ThoiGianThue IS NOT NULL AND ThoiGianKetThuc IS NOT NULL AND ct_hd.IDHoaDon = ?;\r\n";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, ma);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				thanhTien = rs.getDouble("ThanhTien");
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		return thanhTien;
	}

	/***
	 * Tính thành tiền của Dịch Vụ
	 * 
	 * @param ma
	 * @return
	 */
	public double tinhThanhTienDichVu(String ma) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		double thanhTien = 0.0;
		try (Statement statement = con.createStatement()) {
			String sql = "SELECT ct_hd.IDHoaDon, ct_dv.IDDichVu, dv.TenDV, ct_dv.SoLuong, dv.Gia, (ct_dv.SoLuong * dv.Gia) AS ThanhTien\r\n"
					+ "FROM ChiTietDichVu ct_dv INNER JOIN DichVu dv ON ct_dv.IDDichVu = dv.IDDichVu\r\n"
					+ "INNER JOIN ChiTietHoaDon ct_hd ON ct_dv.IDHoaDon = ct_hd.IDHoaDon\r\n"
					+ "WHERE ThoiGianThue IS NOT NULL AND ThoiGianKetThuc IS NOT NULL AND ct_hd.IDHoaDon = ?;";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, ma);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				thanhTien = rs.getDouble("ThanhTien");
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		return thanhTien;
	}

	private void close(PreparedStatement stmt) {
		if (stmt != null)
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
