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
import entity.DatDichVu;
import entity.DatPhong;

public class DatPhong_DAO {
	private static DatPhong_DAO instance = new DatPhong_DAO();

	public static DatPhong_DAO getInstance() {
		if (instance == null)
			instance = new DatPhong_DAO();
		return instance;
	}

	public List<DatPhong> getAllDatPhong() {
		List<DatPhong> dsDatPhong = new ArrayList<DatPhong>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();

		try (Statement statement = con.createStatement()) {
			String sql = "SELECT *\r\n" + "FROM PhieuDatPhong pdp\r\n"
					+ "INNER JOIN KhachHang kh ON pdp.IDKhachHang = kh.IDKhachHang\r\n"
					+ "INNER JOIN Phong p ON pdp.IDPhong = p.IDPhong\r\n" + "WHERE kh.TinhTrang = N'Chưa thanh toán'";
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String idHD = rs.getString("IDHoaDon");
				String idPhong = rs.getString("IDPhong");
				String tenPhong = rs.getString("TenPhong");
				String idKH = rs.getString("IDKhachHang");
				String tenKH = rs.getString("TenKH");
				String idNV = rs.getString("IDNhanVien");
				LocalDateTime tgDat = rs.getTimestamp("ThoiGianDat").toLocalDateTime();
				int luongKhach = rs.getInt("SoLuongKhach");
				String ghiChu = rs.getString("GhiChu");
				DatPhong dp = new DatPhong(idHD, idPhong, tenPhong, idKH, tenKH, idNV, tgDat, luongKhach, ghiChu);
				dsDatPhong.add(dp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsDatPhong;
	}

	/***
	 * Lấy mã Hóa Đơn Sử dụng trong tự động tăng mã Hóa Đơn (DatPhong_GUI)
	 * 
	 * @return
	 */
	public List<DatPhong> getIDHoaDon() {
		List<DatPhong> dsDatPhong = new ArrayList<DatPhong>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();

		try (Statement statement = con.createStatement()) {
			String sql = "SELECT *\r\n" + "FROM PhieuDatPhong pdp\r\n"
					+ "INNER JOIN KhachHang kh ON pdp.IDKhachHang = kh.IDKhachHang\r\n"
					+ "INNER JOIN Phong p ON pdp.IDPhong = p.IDPhong\r\n";
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String idHD = rs.getString("IDHoaDon");
				DatPhong dp = new DatPhong(idHD, "", "", "", "", "", null, 0, "");
				dsDatPhong.add(dp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsDatPhong;
	}

	public void addDatPhong(DatPhong dp) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;

		try {
			// INSERT INTO ChiTietHoaDon
			stmt = con.prepareStatement("INSERT INTO ChiTietHoaDon VALUES (?, ?, NULL, NULL, 1);");
			stmt.setString(1, dp.getMaHoaDon());
			stmt.setString(2, dp.getMaPhong());
			stmt.executeUpdate();
			stmt.close();

			// INSERT INTO HoaDon
			stmt = con.prepareStatement("INSERT INTO HoaDon VALUES (?, ?, ?, 0);");
			stmt.setString(1, dp.getMaHoaDon());
			stmt.setString(2, dp.getMaKhachHang());
			stmt.setString(3, dp.getMaNhanVien());
			stmt.executeUpdate();
			stmt.close();

			// INSERT INTO PhieuDatPhong
			stmt = con.prepareStatement("INSERT INTO PhieuDatPhong VALUES (?, ?, ?, ?, ?, ?, ?);");
			stmt.setString(1, dp.getMaHoaDon());
			stmt.setString(2, dp.getMaPhong());
			stmt.setString(3, dp.getMaKhachHang());
			stmt.setString(4, dp.getMaNhanVien());
			stmt.setTimestamp(5, Timestamp.valueOf(dp.getTgianDat()));
			stmt.setInt(6, dp.getSoLuongKhach());
			stmt.setString(7, dp.getGhiChu());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
		}
	}

	public void updateDatPhong(DatPhong dp) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(
					"update PhieuDatPhong set ThoiGianDat = ?, " 
			+ "SoLuongKhach = ?, GhiChu= ? where IDHoaDon=?");
			stmt.setTimestamp(1, Timestamp.valueOf(dp.getTgianDat()));
			stmt.setInt(2, dp.getSoLuongKhach());
			stmt.setString(3, dp.getGhiChu());
			stmt.setString(4, dp.getMaHoaDon());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
		}
	}

	public void deleteDatPhong(String maHD) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		String sql = "delete from PhieuDatPhong where IDHoaDon = ?\r\n" + "delete from HoaDon where IDHoaDon = ?\r\n"
				+ "delete from ChiTietHoaDon where IDHoaDon = ?";
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, maHD);
			stmt.setString(2, maHD);
			stmt.setString(3, maHD);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
		}
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
