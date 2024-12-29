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
import entity.CaTruc;


public class CaTruc_DAO {
	private static CaTruc_DAO instance = new CaTruc_DAO();
	
	public static CaTruc_DAO getInstance() {
		if (instance == null)
			instance = new CaTruc_DAO();
		return instance;
	}
	
	public List<CaTruc> getAllCaTruc(){
		List<CaTruc> dsCT = new ArrayList<CaTruc>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		try (Statement statement = con.createStatement()){
			String sql = "select * from CaTruc";
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				String iDCaTruc = rs.getString(1);
				String caTruc = rs.getString(2);

				CaTruc ct = new CaTruc(iDCaTruc, caTruc);
				dsCT.add(ct);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return dsCT;
	}
	
	public void addCaTruc(CaTruc ct) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("insert into CaTruc values(?,?)");
			stmt.setString(1, ct.getIDCaTruc());
			stmt.setString(2, ct.getCaTruc());

			stmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(stmt);
		}
	}
	
	public void updateCaTruc(CaTruc ct) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(
					"update CaTruc "
				+ "set [IDCaTruc] = ?, " 
				+ "[CaTruc] = ?, " 
				+ "where IDCaTruc = ?");
			
			stmt.setString(1, ct.getIDCaTruc());
			stmt.setString(2, ct.getCaTruc());

			stmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(stmt);
		}
	}

	public void deleteCaTruc(String IDCaTruc) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		String sql = "delete from CaTruc where IDCaTruc = ?";
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, IDCaTruc);
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
