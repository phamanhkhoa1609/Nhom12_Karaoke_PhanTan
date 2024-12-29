package dao;

import java.sql.Statement;
import java.io.Closeable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.KhachHang;

public class KhachHang_DAO {
	private static KhachHang_DAO instance = new KhachHang_DAO();
	
	public static KhachHang_DAO getInstance() {
		if (instance == null)
			instance = new KhachHang_DAO();
		return instance;
	}
	
	public List<KhachHang> getAllKhachHang(){
		List<KhachHang> dsKH = new ArrayList<KhachHang>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		
		try (Statement statement = con.createStatement()){
			String sql = "select * from KhachHang";
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				String id = rs.getString(1);
				String ten = rs.getString(2);
				int tuoi = rs.getInt(3);
				String gioiTinh = rs.getString(4);
				String sDT = rs.getString(5);
				String trangThai = rs.getString(6);
				KhachHang kh = new KhachHang(id, ten, tuoi, gioiTinh, sDT, trangThai);
				dsKH.add(kh);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return dsKH;
	}
	
	public void addKhachHang(KhachHang kh) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("insert into KhachHang values(?,?,?,?,?,?)");
			stmt.setString(1, kh.getMaKH());
			stmt.setString(2, kh.getTenKH());
			stmt.setInt(3, kh.getTuoi());
			stmt.setString(4, kh.getGioiTinh());
			stmt.setString(5, kh.getsDT());
			stmt.setString(6, kh.getTrangThai());
			stmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(stmt);
		}
	}
	
	public void updateKhachHang(KhachHang kh) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(
					"update KhachHang "
				+ "set [TenKH] = ?, " 
				+ "[Tuoi] = ?, " 
				+ "[GioiTinh] = ?, " 
				+ "[SDT] = ?, "
				+ "[TinhTrang] = ? "
				+ "where IDKhachHang = ?");
			stmt.setString(1, kh.getTenKH());
			stmt.setInt(2, kh.getTuoi());
			stmt.setString(3, kh.getGioiTinh());
			stmt.setString(4, kh.getsDT());
			stmt.setString(5, kh.getTrangThai());
			stmt.setString(6, kh.getMaKH());
			stmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(stmt);
		}
	}

	public void updateKhachHangSangDaThanhToan(String maKhachHang) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("update KhachHang set "
					+ "TinhTrang = N'Đã thanh toán' where IDKhachHang = ?");
			stmt.setString(1, maKhachHang);
			stmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(stmt);
		}
	}
	
	
	public void deleteKhachHang(String maKH) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		String sql = "delete from KhachHang where IDKhachHang = ?";
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, maKH);
			stmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
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
