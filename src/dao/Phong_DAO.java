package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.Phong;

public class Phong_DAO {
	private static Phong_DAO instance = new Phong_DAO();
	
	public static Phong_DAO getInstance() {
		if(instance == null)
			instance = new Phong_DAO();
		return instance;
	}
	public List<Phong> getAllPhong(){
		List<Phong> dsPhong = new ArrayList<Phong>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		
		try(Statement statement = con.createStatement()){
			String sql = "select * from Phong";
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				String id = rs.getString(1);
				String ten = rs.getString(2);
				String loai = rs.getString(3);
				String thietBi = rs.getString(4);
				String tinhTrang = rs.getString(5);
				String ghiChu = rs.getString(6);
				float gia = rs.getFloat(7);
				Phong p = new Phong(id, ten, loai, thietBi, tinhTrang, ghiChu, gia);
				dsPhong.add(p);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return dsPhong;
	}
	public void addPhong(Phong p) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("insert into Phong values(?,?,?,?,?,?,?)");
			stmt.setString(1, p.getMaPhong());
			stmt.setString(2, p.getTenPhong());
			stmt.setString(3, p.getLoaiPhong());
			stmt.setString(4, p.getThietBi());
			stmt.setString(5, p.getTinhTrang());
			stmt.setString(6, p.getGhiChu());
			stmt.setDouble(7, p.getGiaPhong());
			stmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(stmt);
		}
	}
	public void updatePhong(Phong p) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("update Phong set TenPhong = ?, LoaiPhong = ?, ThietBi = ?, TinhTrang = ?, GhiChu = ?, GiaPhongMoiGioTheoCa = ? where IDPhong = ?");
			stmt.setString(1, p.getTenPhong());
			stmt.setString(2, p.getLoaiPhong());
			stmt.setString(3, p.getThietBi());
			stmt.setString(4, p.getTinhTrang());
			stmt.setString(5, p.getGhiChu());
			stmt.setFloat(6, p.getGiaPhong());
			stmt.setString(7, p.getMaPhong());
			stmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(stmt);
		}
	}
	
	public void updatePhongTrongSangDaDat(String maPhong) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("update Phong set TinhTrang = N'Đã đặt' where IDPhong = ?");
			stmt.setString(1, maPhong);
			stmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(stmt);
		}
	}
	
	public void updatePhongDaDatSangPhongTrong(String maPhong) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("update Phong set TinhTrang = N'Trống' where IDPhong = ?");
			stmt.setString(1, maPhong);
			stmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(stmt);
		}
	}
	
	public void deletePhong(String maPhong) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		String sql = "delete from Phong where IDPhong =?";
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, maPhong);
			stmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(stmt);
		}
	}
	private void close(PreparedStatement stmt) {
		if(stmt != null)
			try {
				stmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
	}
	
	
	
}
