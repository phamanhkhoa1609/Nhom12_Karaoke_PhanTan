package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import connectDB.ConnectDB;
import entity.DichVu;

public class DichVu_DAO {
	private static DichVu_DAO instance = new DichVu_DAO();

	public static DichVu_DAO getInstance() {
		if (instance == null)
			instance = new DichVu_DAO();
		return instance;
	}
	public List<DichVu> getAllDichVu() {
		List<DichVu> dsDichVu = new ArrayList<DichVu>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();

		try (Statement statement = con.createStatement()) {
			String sql = "select * from DichVu";
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String id = rs.getString(1);
				String ten = rs.getString(2);
				int soLuong = rs.getInt(3);
				Float gia = rs.getFloat(4);
				DichVu dv = new DichVu(id, ten, soLuong, gia);
				dsDichVu.add(dv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsDichVu;
	}
	public void addDichVu(DichVu dv) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("insert into DichVu values(?,?,?,?)");
			stmt.setString(1, dv.getMaDichVu());
			stmt.setString(2, dv.getTenDichVu());
			stmt.setInt(3, dv.getSoLuong());
			stmt.setFloat(4, dv.getGia());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
		}
	}
	public void updateDichVu(DichVu dv) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(
					"update DichVu " 
			+ "set [TenDV] = ?, " 
			+ "set [SoLuong] = ?, " 
			+ "[Gia] = ? " 
			+ "where IDDichVu = ?");
			stmt.setString(1, dv.getTenDichVu());
			stmt.setInt(2, dv.getSoLuong());
			stmt.setFloat(3, dv.getGia());
			stmt.setString(4, dv.getMaDichVu());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
		}
	}
	
	public void updateSoLuongCuaDichVu(String maDV, int soLuong) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(
					"update DichVu " 
			+ "set [SoLuong] = ? " 
			+ "where IDDichVu = ?");
			stmt.setInt(1, soLuong);
			stmt.setString(2, maDV);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
		}
	}
	
	public void deleteDichVu(String ma) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		String sql = "delete from DichVu where IDDichVu =?";
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, ma);
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
