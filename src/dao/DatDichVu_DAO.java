package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.DatDichVu;
import entity.DichVu;

public class DatDichVu_DAO {
	private static DatDichVu_DAO instance = new DatDichVu_DAO();

	public static DatDichVu_DAO getInstance() {
		if (instance == null)
			instance = new DatDichVu_DAO();
		return instance;
	}

	public List<DatDichVu> getAllDatDichVu() {
		List<DatDichVu> dsDatDichVu = new ArrayList<DatDichVu>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();

		try (Statement statement = con.createStatement()) {
			String sql = "select *\r\n" + "from ChiTietDichVu ct inner join DichVu dv on ct.IDDichVu = dv.IDDichVu\r\n"
					+ "inner join KhachHang k on ct.IDKhachHang = k.IDKhachHang";
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String idHD = rs.getString("IDHoaDon");
				String idDV = rs.getString("IDDichVu");
				String idKH = rs.getString("IDKhachHang");
				String tenDV = rs.getString("TenDV");
				String tenKH = rs.getString("TenKH");
				int soLuong = rs.getInt("soLuong");
				Float gia = rs.getFloat("Gia");
				DatDichVu ddv = new DatDichVu(idHD, idDV, idKH, tenDV, tenKH, soLuong, gia);
				dsDatDichVu.add(ddv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsDatDichVu;
	}

	/***
	 * Lấy dịch vụ từ những phòng trong trạng thái: Đã đặt Sử dụng trong
	 * DatDichVu_GUI
	 * 
	 * @return
	 */
	public List<DatDichVu> getDatDichVuForPhongDaDat() {
		List<DatDichVu> dsDatDichVu = new ArrayList<DatDichVu>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();

		try (Statement statement = con.createStatement()) {
			String sql = "SELECT *\r\n" + "FROM ChiTietDichVu ct\r\n"
					+ "INNER JOIN DichVu dv ON ct.IDDichVu = dv.IDDichVu\r\n"
					+ "INNER JOIN KhachHang kh on ct.IDKhachHang = kh.IDKhachHang\r\n"
					+ "WHERE kh.TinhTrang = N'Chưa thanh toán';";
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String idHD = rs.getString("IDHoaDon");
				String idDV = rs.getString("IDDichVu");
				String idKH = rs.getString("IDKhachHang");
				String tenDV = rs.getString("TenDV");
				String tenKH = rs.getString("TenKH");
				int soLuong = rs.getInt("soLuong");
				Float gia = rs.getFloat("Gia");
				DatDichVu ddv = new DatDichVu(idHD, idDV, idKH, tenDV, tenKH, soLuong, gia);
				dsDatDichVu.add(ddv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsDatDichVu;
	}

	/***
	 * Lấy những dịch vụ đã đặt từ tên Phòng Sử dụng trong LapHoaHon_GUI
	 * 
	 * @param ten
	 * @return
	 */
	public List<DatDichVu> getDatDichVuForPhongDaDat(String ten) {
		List<DatDichVu> dsDatDichVu = new ArrayList<DatDichVu>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;

		try (Statement statement = con.createStatement()) {
			String sql = "SELECT *\r\n" + "FROM ChiTietDichVu ct_dv\r\n"
					+ "INNER JOIN HoaDon hd ON ct_dv.IDHoaDon = hd.IDHoaDon\r\n"
					+ "INNER JOIN DichVu dv ON ct_dv.IDDichVu = dv.IDDichVu\r\n"
					+ "INNER JOIN KhachHang kh on ct_dv.IDKhachHang = kh.IDKhachHang\r\n"
					+ "INNER JOIN ChiTietHoaDon ct_hd ON hd.IDHoaDon = ct_hd.IDHoaDon\r\n"
					+ "INNER JOIN Phong p ON ct_hd.IDPhong = p.IDPhong\r\n"
					+ "WHERE p.TinhTrang = N'Đã đặt' and kh.TinhTrang = N'Chưa thanh toán' " + "and p.TenPhong= ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, ten);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String idHD = rs.getString("IDHoaDon");
				String idDV = rs.getString("IDDichVu");
				String idKH = rs.getString("IDKhachHang");
				String tenDV = rs.getString("TenDV");
				String tenKH = rs.getString("TenKH");
				int soLuong = rs.getInt("soLuong");
				Float gia = rs.getFloat("Gia");
				DatDichVu ddv = new DatDichVu(idHD, idDV, idKH, tenDV, tenKH, soLuong, gia);
				dsDatDichVu.add(ddv);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsDatDichVu;
	}

	public void addDatDichVu(DatDichVu ddv) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("insert into ChiTietDichVu values(?,?,?,?)");
			stmt.setString(1, ddv.getMaDichVu());
			stmt.setString(2, ddv.getMaHoaDon());
			stmt.setString(3, ddv.getMaKhachHang());
			stmt.setInt(4, ddv.getSoLuong());
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

	public void updateDatDichVu(DatDichVu ddv) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("update ChiTietDichVu  \r\n" + "set [SoLuong] = ? \r\n"
					+ "where IDDichVu = ? and IDKhachHang = ? and IDHoaDon = ?");
			stmt.setInt(1, ddv.getSoLuong());
			stmt.setString(2, ddv.getMaDichVu());
			stmt.setString(3, ddv.getMaKhachHang());
			stmt.setString(4, ddv.getMaHoaDon());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
		}
	}

	public void deleteDatDichVu(String maDV, String maKH, String maHD) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		String sql = "delete from ChiTietDichVu \r\n" + "where IDDichVu = ? and IDKhachHang = ? and IDHoaDon = ?";
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, maDV);
			stmt.setString(2, maKH);
			stmt.setString(3, maHD);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
		}
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
		int tongSoLuong = 0;

		try (Statement statement = con.createStatement()) {
			String sql = "SELECT ct_dv.IDDichVu, dv.TenDV, SUM(ct_dv.SoLuong) AS TongSoLuong, dv.Gia, SUM(ct_dv.SoLuong * dv.Gia) AS ThanhTien\r\n"
					+ "FROM ChiTietDichVu ct_dv\r\n" + "INNER JOIN DichVu dv ON ct_dv.IDDichVu = dv.IDDichVu\r\n"
					+ "INNER JOIN ChiTietHoaDon ct_hd ON ct_dv.IDHoaDon = ct_hd.IDHoaDon\r\n"
					+ "WHERE ThoiGianThue IS NOT NULL AND ThoiGianKetThuc IS NOT NULL\r\n"
					+ "AND ct_hd.IDHoaDon = ? \r\n"
					+ "GROUP BY ct_dv.IDDichVu, dv.TenDV, dv.Gia;\r\n" ;
			stmt = con.prepareStatement(sql);
			stmt.setString(1, ma);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				tongSoLuong = rs.getInt("TongSoLuong");
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
}
