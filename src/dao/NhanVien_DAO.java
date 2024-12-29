package dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.NhanVien;

public class NhanVien_DAO {
	private static NhanVien_DAO instance = new NhanVien_DAO();
	
	public static NhanVien_DAO getInstance() {
		if (instance == null)
			instance = new NhanVien_DAO();
		return instance;
	}
	
	public List<NhanVien> getAllNhanVien(){
		List<NhanVien> dsNV = new ArrayList<NhanVien>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		
		try (Statement statement = con.createStatement()){
			String sql = "select * from NhanVien";
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				String iDNhanVien = rs.getString(1);
				String tenNV = rs.getString(2);
				String SDT = rs.getString(3);
				int tuoi = rs.getInt(4);
				String boPhan = rs.getString(5);
				Float luong = rs.getFloat(6);
				String gioiTinh = rs.getString(7);
				NhanVien nv = new NhanVien(iDNhanVien, tenNV, SDT, tuoi, boPhan, luong, gioiTinh);
				dsNV.add(nv);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return dsNV;
	}
	
	public void addNhanVien(NhanVien nv) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("insert into NhanVien values(?,?,?,?,?,?,?)");
			stmt.setString(1, nv.getIDNhanVien());
			stmt.setString(2, nv.getTenNV());
			stmt.setString(3, nv.getSDT());
			stmt.setInt(4, nv.getTuoi());		
			stmt.setString(5, nv.getBoPhan());
			stmt.setFloat(6, nv.getLuong());
			stmt.setString(7, nv.getGioiTinh());
			stmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(stmt);
		}
	}
	
	public void updateNhanVien(NhanVien nv) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(
					"update NhanVien "
				+ "set [TenNV] = ?, " 
				+ "[SDT] = ?, " 
				+ "[Tuoi] = ?, " 
				+ "[BoPhan] = ?, " 
				+ "[Luong] = ?, "
				+ "[GioiTinh] = ? "
				+ "where IDNhanVien = ?");
			
			stmt.setString(1, nv.getTenNV());
			stmt.setString(2, nv.getSDT());
			stmt.setInt(3, nv.getTuoi());		
			stmt.setString(4, nv.getBoPhan());
			stmt.setFloat(5, nv.getLuong());
			stmt.setString(6, nv.getGioiTinh());
			stmt.setString(7, nv.getIDNhanVien());
			stmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(stmt);
		}
	}

	public void deleteNhanVien(String maNV) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		String sql = "delete from NhanVien where IDNhanVien = ?";
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, maNV);
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
